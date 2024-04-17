package io.mhe.assignmentcomponent.service;

import io.mhe.assignmentcomponent.dao.IAssignmentCopyDAO;
import io.mhe.assignmentcomponent.mheevent.sqs.util.AmazonSQSConstants;

import io.mhe.assignmentcomponent.sqs.util.AmazonSQSHelper;
import io.mhe.assignmentcomponent.vo.*;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AssignmentCopyService  implements IAssignmentCopyService{
    private final Logger logger = LoggerFactory.getLogger(AssignmentCopyService.class);

    @Autowired
    private IAssignmentCopyDAO assignmentCopyDAO;

    @Autowired
    private AmazonSQSHelper amazonSQSHelper;


    public void copyAssignmentsToNewSection(CopyAssignment srcAssignment, long oldSectionID,
                                            long newSectionID,
                                            long[] origCategoryIds,
                                            long[] newCategoryIds,
                                            long newCourseId,
                                            long newSectionId,
                                            HashMap modulesMap,
                                            Map assignMap) throws Exception {
        try {

            if (logger.isDebugEnabled()) {
                logger.debug("started processing assignment now for section id : " + newSectionID + " assignment_id :"
                        + srcAssignment.getAssignmentId()+ ", modulesMap=" + modulesMap);
            }
            this.copyHMPublicAssignments(new CopyAssignment[] { srcAssignment }, oldSectionID, newSectionID,
                    origCategoryIds, newCategoryIds, newCourseId, newSectionId);
            if (logger.isDebugEnabled()) {
                logger.debug("created new assignment now for section id : " + newSectionID + " assignment_id :" + srcAssignment.getAssignmentId()
                        + " new assignment_id:" + srcAssignment.getNewAssignmentId());
            }
            Map<Long, Long> sectionIdsMap = new HashMap<Long, Long>();
            HashMap assignmentsMapForSection = new HashMap();

            sectionIdsMap.clear();
            assignmentsMapForSection.clear();

            sectionIdsMap.put(oldSectionID, newSectionID);
            assignmentsMapForSection.put(srcAssignment.getAssignmentId(), srcAssignment.getNewAssignmentId());
            this.copySectionAssignmentXref(sectionIdsMap, assignmentsMapForSection);

            if ("EZTestOnline".equals(srcAssignment.getProvider())) {
                this.copyXWorkFlow(new CopyAssignment[] { srcAssignment }); // ezt call to do
                if ("failed".equals(srcAssignment.getCopyEZTStatus())) {
                    assignmentCopyDAO.deleteMultipleAssignments(Arrays.asList(srcAssignment.getNewAssignmentId()));
                    throw new Exception("EZTO copy for the assignment failed");
                }
                if (srcAssignment.getNewNativeAlaId() != null) {
                    /*
                    if (logger.isDebugEnabled()) {
                        logger.debug("processing EZTO assignment : " + srcAssignment.getNewAssignmentId());
                    }*/

                    this.registerActivityFirstTime(srcAssignment.getNewAssignmentId(), srcAssignment.getNewNativeAlaId(),
                            srcAssignment.getTitle());
                    Activity[] activity = this.getActivitiesForAssignment(srcAssignment.getAssignmentId());
                    this.updatePointsAndQuestionsForAssignment(srcAssignment.getNewAssignmentId(),
                            activity[0].getWeight(), activity[0].getQuestions(), activity[0].getAvailableQuestions());

                    // Changing the initial sync status from Required to In-Progress because we are going to sync the
                    // assignment later using the pullRegistrationMultiple API.
                    this.insertSyncStatusForAssignment(srcAssignment.getNewAssignmentId(),
                            srcAssignment.getNewNativeAlaId(), "In Progress");
                    // Inserting record in assignment_parent_status to check the parent status in registration flow.
                    try{
                        this.insertParentAssignmentStatusForAssignment(srcAssignment.getNewAssignmentId(), srcAssignment.getAssignmentId(), srcAssignment.getParentAssignmentStatus());
                    }catch(Exception ex){
                        logger.error("Error while inserting parent assignment status", ex);
                    }

                } else {
                    throw new Exception("Assignment doesn't have any nativealaId");
                }

            }

            if (("Connect".equals(srcAssignment.getProvider()) || "TextFlow".equals(srcAssignment.getProvider())
                    || "ale".equalsIgnoreCase(srcAssignment.getProvider())) && "GROUP".equalsIgnoreCase(srcAssignment.getType())) {

                if (logger.isDebugEnabled()) {
                    logger.debug("processing GROUP assignment : " + srcAssignment.getNewAssignmentId());
                }

                if (srcAssignment.getNewAssignmentId() > 0)
                {
                    CopyAssignment ca = srcAssignment;

                    if (logger.isDebugEnabled()) {
                        logger.debug("calling getGroupAssignmentById for assignmentId = " + ca.getNewAssignmentId() + " : sectionId = "
                                + ca.getNewSectionId());
                    }
                    // to review this api
                    GroupAssignment ga = this.getGroupAssignmentById(ca.getNewAssignmentId(), ca.getNewSectionId());

                    if (logger.isDebugEnabled()) {
                        logger.debug("GroupAssignment exists? " + ga + " : points = " + ca.getWeight());
                    }

                    this.copyGroupAssignmentPropertiesForCopyAssignment(new CopyAssignment[] { ca });
                }
            }

            boolean genericAssignment = ("Generic".equals(srcAssignment.getProvider())
                    || (StringUtils.isNotEmpty(srcAssignment.getNativeAlaId())
                    && srcAssignment.getNativeAlaId().startsWith("Generic"))) ? true
                    : false;

            if ("Connect".equals(srcAssignment.getProvider()) || "TextFlow".equals(srcAssignment.getProvider())
                    || "ale".equalsIgnoreCase(srcAssignment.getProvider())
                    || "MUZZY_LANE".equalsIgnoreCase(srcAssignment.getProvider())
                    || genericAssignment) {

                if (logger.isDebugEnabled()) {
                    logger.debug("connectAssignments type = {} : assignmentId = {} : newAssignmentId = {}",
                            new Object[] {srcAssignment.getType(), srcAssignment.getAssignmentId(), srcAssignment.getNewAssignmentId()});
                }

                if (srcAssignment.getNewAssignmentId() != 0) {
                    Assignment assignment = this.getURLBasedAssignment(srcAssignment.getAssignmentId());
                    assignment.setID(srcAssignment.getNewAssignmentId());
                    if(genericAssignment) {
                        assignment.setProvider(srcAssignment.getProvider());
                        assignment.setNativeAlaId(srcAssignment.getNativeAlaId());
                    }
                    logger.debug("Copied assignment : {}  ", assignment);
                    ActivityItem[] items = null;
                    if ("GROUP".equalsIgnoreCase(assignment.getAssignmentType())) {
                        assignment.setWeight(srcAssignment.getWeight());
                        Activity[] activity = this.getActivitiesForAssignment(srcAssignment.getAssignmentId());
                        if (activity != null && activity.length > 0) {
                            items = this.getActivityItemsForActivity(activity[0].getID());
                        }

                    }
                    this.addActivityAndALAInfoForAssignment(assignment);
                    if ("GROUP".equalsIgnoreCase(assignment.getAssignmentType())) {
                        Activity[] activity = this.getActivitiesForAssignment(srcAssignment.getNewAssignmentId());
                        if (activity != null && activity.length > 0) {
                            if (items != null && items.length > 0) {
                                for (int j = 0; j < items.length; j++) {
                                    items[j].setActivityId(activity[0].getID());
                                }
                                this.addActivityItemsToActivity(items, activity[0].getID());
                            }
                        }
                    }
                }
                if("MUZZY_LANE".equalsIgnoreCase(srcAssignment.getProvider())){
                    ActivityItem[] items = null;
                    Activity[] activity = this.getActivitiesForAssignment(srcAssignment.getAssignmentId());
                    if (activity != null && activity.length > 0) {
                        items = this.getActivityItemsForActivity(activity[0].getID());
                    }

                    Activity[] activity2 = this.getActivitiesForAssignment(srcAssignment.getNewAssignmentId());
                    if (activity2 != null && activity2.length > 0) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("adding items " + items.length + "to activity2 " + activity2[0].getID());
                        }

                        if (items != null && items.length > 0) {
                            for (int k = 0; k < items.length; k++) {
                                items[k].setActivityId(activity2[0].getID());
                            }
                            this.addActivityItemsToActivity(items, activity2[0].getID());
                        }
                    }

                    Assignment copyFrom = this.getAssignment(srcAssignment.getAssignmentId());
                    Assignment copyTo = this.getAssignment(srcAssignment.getNewAssignmentId());
                    String consumer = "MUZZY_LANE".toLowerCase();
                    String baseUrl = ""; //Configuration.getSystemValue("MUZZY_LTI_POST_URL");
                    String consumerKey ="" ;// Configuration.getSystemValue("MUZZY_CONSUMER_KEY");
                    if (logger.isDebugEnabled()) {
                        logger.debug("Calling Muzzy Lane for assignment copy on their side");
                        logger.debug("Source Assignment : {}", copyFrom);
                        logger.debug("Destination Assignment : {}", copyTo);
                    }
                    // call muzzy lanne
                  //  boolean genericAssignmentCopied = this.prepareAndSendRestCallForGenericAssignment(copyFrom,
                    //        copyTo, consumer, baseUrl, GenericAssignmentConstants.WS_REQUEST_COPY_MODE, consumerKey);
                    if (logger.isDebugEnabled()) {
                        logger.debug("default template generic assignment copied status  + genericAssignmentCopied");
                    }
                }
            }


            Product product = this.getProduct(srcAssignment.getType());
            /*
            if (product != null) {

                if (logger.isDebugEnabled()) {
                    logger.debug("copying learnsmart assignments : parent assignment id " + srcAssignment.getAssignmentId());
                    logger.debug("copying learnsmart assignments : new  assignment id " + srcAssignment.getNewAssignmentId());
                }

                if (srcAssignment.getNewAssignmentId() > 0)
                {
                    Assignment toAssmt = getLearnSmartDataTransactionService().retrieveAssignment(srcAssignment.getAssignmentId()).getValueObject();
                    toAssmt.setID(srcAssignment.getNewAssignmentId());
                    toAssmt.setSectionIds(new long[] { srcAssignment.getNewSectionId() });
                    alaManagerBusinessService.addActivityAndALAInfoForAssignment(toAssmt);
                    Assignment fromAssmt = getLearnSmartDataTransactionService().retrieveAssignment(srcAssignment.getAssignmentId()).getValueObject();
                    if (NumberUtilities.parseLong(srcAssignment.getNewPrimaryInstructorId(), -1L) > -1)
                    {
                        toAssmt.setPrimary_instructor_id(srcAssignment.getNewPrimaryInstructorId());
                    }
                    try {

                        /**
                         * For duplicate section operation, MODULE_ASSIGNMENT_XREF is not populated for new assignmentId
                         * & new moduleId at this point. Getting new moduleId from map and setting the same to _to
                         * object
                         *

                        Long oldModuleId = modulesDao
                                .getModuleIdForAssignmentUsingSectionId(srcAssignment.getAssignmentId(), oldSectionID);
                        Long newModuleId = Long.parseLong((String) modulesMap.get(oldModuleId.toString()));

                        if (logger.isDebugEnabled()) {
                            logger.debug("Old module_id: " + oldModuleId + " new module_id: " + newModuleId);
                        }
                        toAssmt.setModuleID(newModuleId);
                        fromAssmt.setCurrentSectionId(oldSectionID);
                        logger.debug("product type : {}, product template: {}, product platform: {}", new Object[] { product.getProductType(),
                                product.getTemplate(), product.getPlatform().getPlatformName() });
                        if (!"AVALON".equalsIgnoreCase(product.getProductType()) && !"READER17".equalsIgnoreCase(product.getProductType()) && (ProductTemplate.BASIC.getTemplateName().equalsIgnoreCase(product.getTemplate())
                                || ProductTemplate.ADVANCED.getTemplateName().equalsIgnoreCase(product.getTemplate()))) {
                            // Checking for !AVALON and !READER17 Because Avalon having "basic" template in product and no need to enter here.
                            this.getArea9Service().copyAssignment(fromAssmt, toAssmt);
                        }
                        else if (ProductTemplate.DEFAULT.getTemplateName().equalsIgnoreCase(product.getTemplate())) {
                            //fix for connectg 1121
                            toAssmt.setParentSectionId(newSectionID);
                            this.genericAssignmentBusinessService.copyGenericAssignment(fromAssmt, toAssmt, product);
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage() + ": " + e.getCause(), e);
                        logger.error(e.getStackTrace());
                        throw new Exception("learn smart assignment failed to get copied : " + fromAssmt.getAssignmentId());
                    }

                }

            }*/


        } catch (Exception e) {
            assignmentCopyDAO.deleteMultipleAssignments(Arrays.asList(srcAssignment.getNewAssignmentId()));
            throw e;
        }
    }

    public boolean copyHMPublicAssignments(CopyAssignment[] srcAssignments, long srcSectionId, long dstSectionId, long[] oldCategoryIds,
                                           long[] newCategoryIds,
                                           long newCourseId, long originalCourseId) throws Exception {
        try {
            return assignmentCopyDAO.copyHMPublicAssignments(srcAssignments, srcSectionId, dstSectionId, oldCategoryIds, newCategoryIds,
                    newCourseId,
                    originalCourseId);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void copySectionAssignmentXref(Map<Long, Long> sectionIdsMap, Map<Long, Long> assignmentsMap) {
         assignmentCopyDAO.copySectionAssignmentXref(sectionIdsMap,assignmentsMap);
    }

    @Override
    public String[] copyXWorkFlow(CopyAssignment[] assignments) throws Exception {
        return new String[0]; // to do ezt call
    }

    @Override
    public void registerActivityFirstTime(long assignmentId, String nativeAlaId, String alaTitle) throws Exception{
        if (logger.isDebugEnabled()) {
            logger.debug("AlaManagerBean registerEmptyAla : assignment id = "
                    + assignmentId + " nativeAlaId = " + nativeAlaId
                    + " ala title = " + alaTitle);
        }
        String alaType = "Custom";
       // String contentProvider = ProductVariables.PROVIDER_EZTEST; public static final String PROVIDER_EZTEST = "EZTestOnline";
        String contentProvider = "EZTestOnline";
        Date currentDate = new Date();
        Activity activity = new Activity();
        activity.setNativeAlaId(nativeAlaId);
        activity.setType(alaType);
        activity.setAssignmentID(assignmentId);
        activity.setTitle(alaTitle);
        activity.setAlaContentProvider(contentProvider);

        this.addActivitiesAndItemsForAssignment(activity, assignmentId);

        // writing to SQS start
        // method parameters - long assignmentId, long sectionId, long studentId, String transactionType, int attemptNo, String source
        try {
            amazonSQSHelper.writeToSQSQueue(assignmentId, 0, 0, AmazonSQSConstants.ACTIVITY_TYPE_SKILL_CATEGORY, 0,
                    "AlaManagerBusinessService -> registerActivityFirstTime()", currentDate);
        } catch(Exception e) {
            logger.error("Error in writing to Amazon SQS with assignmentId {}", new Object[]{assignmentId}, e);
        }
        // writing to SQS done

    }

    @Override
    public Activity[] getActivitiesForAssignment(long assignmentId) throws Exception {
        return assignmentCopyDAO.getActivitiesForAssignment(assignmentId);
    }

    @Override
    public void updatePointsAndQuestionsForAssignment(long assignmentId, float points, int numQuestions, int availableQuestions) {
        assignmentCopyDAO.updatePointsAndQuestionsForAssignment(assignmentId,points,numQuestions,availableQuestions);
    }

    @Override
    public void insertSyncStatusForAssignment(long assignmentId, String nativeAlaId, String status) {
        assignmentCopyDAO.insertSyncStatusForAssignment(assignmentId,  nativeAlaId,  status);
    }

    @Override
    public void insertParentAssignmentStatusForAssignment(long assignmentId, long parentAssignmentId, String status) throws Exception {
        assignmentCopyDAO.insertParentAssignmentStatusForAssignment(assignmentId,  parentAssignmentId,  status);
    }

    @Override
    public GroupAssignment getGroupAssignmentById(long assignmentId, long sectionId) {
        return assignmentCopyDAO.getGroupAssignmentById(assignmentId,sectionId);
    }

    @Override
    public void copyGroupAssignmentPropertiesForCopyAssignment(CopyAssignment[] ca) {
        assignmentCopyDAO.copyGroupAssignmentPropertiesForCopyAssignment(ca);
    }

    @Override
    public Assignment getURLBasedAssignment(long assignmentId) {
        return assignmentCopyDAO.getURLBasedAssignment(assignmentId);
    }

    @Override
    public ActivityItem[] getActivityItemsForActivity(long id) {
        return assignmentCopyDAO.getActivityItemsForActivity(id);
    }

    @Override
    public void addActivityItemsToActivity(ActivityItem[] items, long id) {
        assignmentCopyDAO.addActivityItemsToActivity(items,id);
    }

    @Override
    public Assignment getAssignment(long assignmentId) {
        return null;
    }

    @Override
    public Product getProduct(String type) {
        return null;
    }

    @Override
    public boolean addActivitiesAndItemsForAssignment(Activity activity, long assignmentId) throws Exception{
        Activity[] existingActivities = this.getActivitiesForAssignment(assignmentId);

        // Step 5 If Existing Activities , Activity Items are not null then delete Activities and Activity Items and all
        // the Associations
        if (existingActivities != null && existingActivities.length > 1) {
            throw new Exception("The Activities associated with assignment cannot be greater than 1");
        }
        if (activity == null) {
            throw new Exception("Activity Cannot be Null");
        }

        long actNID = 0l;

        if (existingActivities != null && existingActivities.length == 1) {
            if (logger.isDebugEnabled()) {
                logger.debug("addAct = " + activity);
                logger.debug("alamanager_id = " + activity.getAlaManagerID());
            }
            activity.setID(existingActivities[0].getID());
            long beginUpdateActivity = System.currentTimeMillis();
            actNID = this.updateActivity(activity, assignmentId);
            this.updateAssignmentUpdatedDate(assignmentId);
            logger.debug("AlaManagerBusinessService.addActivitiesRegistrationInfo() -Time taken to update activity "
                    + (System.currentTimeMillis() - beginUpdateActivity) + " ms");
        } else {
            long addActivityTime = System.currentTimeMillis();
            actNID = this.addActivityToAssignment(activity, assignmentId);
            logger.debug("AlaManagerBusinessService.addActivitiesRegistrationInfo() - Time taken for adding activity to activity table "
                    + (System.currentTimeMillis() - addActivityTime) + " ms");
        }
        ActivityItem[] activityItems = this.getActivityItemsByAssignmentId(assignmentId);
        if (activityItems.length > 0) {
            // Deleting the existing activity items.
            this.deleteActivityItemsByActivityId(actNID);
        }
        if (activity.getActivityItems() != null) {
            this.addActivityItemsToActivity(activity.getActivityItems(), actNID);
        }
        logger.debug(" Activities Created and associated with Assignment ");
        return true;
    }

    @Override
    public long updateActivity(Activity activity, long assignmentId) {
        return assignmentCopyDAO.updateActivity(activity,assignmentId);
    }

    @Override
    public void updateAssignmentUpdatedDate(long assignmentId) {
         assignmentCopyDAO.updateAssignmentUpdatedDate(assignmentId);
    }

    @Override
    public long addActivityToAssignment(Activity activity, long assignmentId) {
        return assignmentCopyDAO.addActivityToAssignment(activity,assignmentId);
    }

    @Override
    public ActivityItem[] getActivityItemsByAssignmentId(long assignmentId) {
        return assignmentCopyDAO.getActivityItemsByAssignmentId(assignmentId);
    }

    @Override
    public void deleteActivityItemsByActivityId(long actNID) {
        assignmentCopyDAO.deleteActivityItemsByActivityId(actNID);
    }

    @Override
    public void addActivityAndALAInfoForAssignment(Assignment assignmentObj) {
        Activity activity = AssignmentUtility.prepareActivityWithActivityItems(assignmentObj);
        boolean flag = false;
        ProductTemplate productTemplate = null;
        try {
            Product product = this.getProduct(assignmentObj.getType().getValue());
            if (product != null)
            {
                productTemplate = product.getProductTemplate();
            }
        } catch (Exception e) {
            logger.error("exception in addActivityAndALAInfoForAssignment" + e);
        }
        logger.debug("addActivityAndALAInfoForAssignment assignmentObj.getType()"+assignmentObj.getType()+" Product type: "+  productTemplate);
        // Have Removed Check for LabSmat/LearnSmart and other assignment types which are configured through Products
        // table and making the check at template level
        if (AssignmentType.VIDEO.equals(assignmentObj.getType()) || AssignmentType.ALE.equals(assignmentObj.getType())
                || AssignmentType.URLBased.equals(assignmentObj.getType())
                || AssignmentType.FILEATTACH.equals(assignmentObj.getType())
                || AssignmentType.GROUP.equals(assignmentObj.getType())
                || AssignmentType.WRITING.equals(assignmentObj.getType())
                || AssignmentType.BLOG.equals(assignmentObj.getType())
                || AssignmentType.MUZZY_LANE.equals(assignmentObj.getType())
                || AssignmentType.DISCUSSION.equals(assignmentObj.getType())
                || ProductVariables.PROVIDER_GENERIC.equals(assignmentObj.getProvider())
                || (StringUtils.isNotEmpty(assignmentObj.getNativeAlaId())
                && assignmentObj.getNativeAlaId().startsWith(ProductVariables.PROVIDER_GENERIC))
                || (ProductTemplate.BASIC.equals(productTemplate) || ProductTemplate.ADVANCED.equals(productTemplate)
                || ProductTemplate.DEFAULT.equals(productTemplate))) {

            try {
                flag = addActivitiesAndItemsForAssignment(activity, assignmentObj.getID());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // writing to SQS start
            long sectionID = assignmentObj.getCurrentSectionId();
            //method parameters - long assignmentId, long sectionId, long studentId, String transactionType, int attemptNo, String source
            try {
                amazonSQSHelper.writeToSQSQueue(assignmentObj.getID(), sectionID, 0,
                        AmazonSQSConstants.ACTIVITY_TYPE_SKILL_CATEGORY, 0,
                        "AlaManagerBusinessService -> addActivityAndALAInfoForAssignment()", null);
            } catch(Exception e) {
                logger.error("Error in writing to Amazon SQS inside addActivityAndALAInfoForAssignment", e);
            }
            // writing to SQS done

        }
       // void?? return flag;
    }

}
