package io.mhe.assignmentcomponent.vo;

import io.mhe.assignmentcomponent.service.AssignmentCopyService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by IntelliJ IDEA.
 * User: Tarams
 * Date: Oct 28, 2010
 * Time: 10:08:03 AM
 * To change this template use File | Settings | File Templates.
 */
public final class AssignmentUtility {
	
	private AssignmentUtility(){
		//Not called
	}

    private static final Logger logger = LoggerFactory.getLogger(AssignmentUtility.class);

    public static Activity prepareActivityWithActivityItems(Assignment assignment) {
    	logger.debug("Inside prepareActivityWithActivityItems, assignment: {}", assignment);
        Activity activity = new Activity();
		if (StringUtils.isNotEmpty(assignment.getNativeAlaId())
				&& assignment.getNativeAlaId().startsWith(ProductVariables.PROVIDER_GENERIC)) {
			activity.setNativeAlaId(assignment.getNativeAlaId());
		} else if (ProductVariables.PROVIDER_GENERIC.equals(assignment.getProvider())
				&& (StringUtils.isNotEmpty(assignment.getToolId()))) {
			activity.setNativeAlaId(ProductVariables.PROVIDER_GENERIC + "_" + assignment.getToolId());
		} else {
			activity.setNativeAlaId("connect_" + assignment.getID());
		}
        activity.setType("Custom");
        if (assignment.getTitle() != null) {
            activity.setTitle(assignment.getTitle());
        } else {
            activity.setTitle("dummy");
        }
        activity.setAssignmentID(assignment.getID());
        activity.setAlaManagerID(0l);
        activity.setWeightBased(false);
        activity.setRepeatable(false);
        activity.setBeginNote(" ");
        activity.setEndNote(" ");
        activity.setSequenceNo(0);
        activity.setWeight(assignment.getWeight());
        
        List<ActivityItem> activityItems = new ArrayList<ActivityItem>();
        if (assignment.getType().equals(AssignmentType.URLBased) && assignment.getContentProvider().equals(ProductVariables.PROVIDER_CONNECT)) {
            //Web Assignment
            WebLink[] webLinks = assignment.getWebLinks();
            for (int i = 0; i < webLinks.length; i++) {
                ActivityItem activityItem = new ActivityItem();
                activityItem.setNativeAlaId("connect");
                activityItem.setTitle(webLinks[i].getWebLinkName());
                activityItem.setRenderingUrl(webLinks[i].getWebLinkURL());
                activityItem.setSequenceNo(i + 1);
                activityItem.setWeight(assignment.getWeight());
                activityItems.add(activityItem);
            }
            activity.setAlaContentProvider(ProductVariables.PROVIDER_CONNECT);
        } else if (assignment.getType().equals(AssignmentType.URLBased) && assignment.getContentProvider().equals(ProductVariables.PROVIDER_TEXTFLOW)) {
            String readingURL = assignment.getEbookReadingURL();
            //Setting Uuid in case if assignment object having null value for Reading textflow. 
            if(GenUtil.isBlankString(readingURL)){
            	readingURL = assignment.getUuid();
            }
            ActivityItem activityItem = new ActivityItem();
            activityItem.setNativeAlaId("textflow");
            activityItem.setTitle(readingURL);
            activityItem.setRenderingUrl(readingURL);
            activityItem.setSequenceNo(1);
            activityItem.setWeight(assignment.getWeight());
            activityItems.add(activityItem);
            activity.setAlaContentProvider(ProductVariables.PROVIDER_TEXTFLOW);
        } else if ((assignment.getType().equals(AssignmentType.VIDEO) ) ) {
            ActivityItem activityItem = new ActivityItem();
            activityItem.setNativeAlaId("connect_" + assignment.getID());
            activityItem.setTitle("dummy");
            activityItem.setRenderingUrl("dummy");
            activityItem.setSequenceNo(1);
            activityItem.setWeight(assignment.getWeight());
            activityItems.add(activityItem);
            activity.setAlaContentProvider(ProductVariables.PROVIDER_CONNECT);
        }else if( assignment.getType().equals(AssignmentType.FILEATTACH)  && assignment.getContentProvider().equals(ProductVariables.PROVIDER_CONNECT)) 
        {
            ActivityItem activityItem = new ActivityItem();
            activityItem.setNativeAlaId("connect_" + assignment.getID());
            activityItem.setTitle(assignment.getTitle());
            activityItem.setRenderingUrl("dummy");
            activityItem.setSequenceNo(1);
            activityItem.setWeight(assignment.getWeight());
            activityItems.add(activityItem);
        }else if (assignment.getType().equals(AssignmentType.ALE) && assignment.getContentProvider().equals(ProductVariables.PROVIDER_ALE)) {
            ActivityItem activityItem = new ActivityItem();
            activityItem.setNativeAlaId("connect_" + assignment.getID());
            activityItem.setTitle(assignment.getTitle());
            activityItem.setRenderingUrl("dummy");
            activityItem.setSequenceNo(1);
            activityItem.setWeight(assignment.getWeight());
            activityItems.add(activityItem);
            activity.setAlaContentProvider(ProductVariables.PROVIDER_ALE);
            
        } else if (assignment.getContentProvider().equals(ProductVariables.PROVIDER_AREA9) || "learnsmart".equalsIgnoreCase(assignment.getContentProvider())){
            ActivityItem activityItem = new ActivityItem();
            activityItem.setNativeAlaId("connect_" + assignment.getID());
            activityItem.setTitle(assignment.getTitle());
            activityItem.setRenderingUrl("http://dummy.com");
            activityItem.setSequenceNo(1);
            activityItem.setWeight(assignment.getWeight());
            activityItems.add(activityItem);
            activity.setAlaContentProvider(ProductVariables.LEARN_SMART);
            activity.setNativeAlaId("connect_" + assignment.getID());
        } 
        //for sealworks provider
        else if (assignment.getContentProvider().equalsIgnoreCase(ProductVariables.PROVIDER_SEALWORKS)){  
            ActivityItem activityItem = new ActivityItem();
            activityItem.setNativeAlaId("connect_" + assignment.getID());
            activityItem.setTitle(assignment.getTitle());
            activityItem.setRenderingUrl("http://dummy.com");
            activityItem.setSequenceNo(1);
            activityItem.setWeight(assignment.getWeight());
            activityItems.add(activityItem);
            activity.setNativeAlaId("connect_" + assignment.getID());
        } else if (assignment.getType().equals(AssignmentType.GROUP)) {
        	activity.setNativeAlaId("group_" + assignment.getID());
        	activity.setWeight((long) assignment.getWeight());
        	activity.setAlaContentProvider(ProductVariables.PROVIDER_CONNECT);
        	
        	//CST-1373-added to populate the activity_item table
        	ActivityItem activityItem = new ActivityItem();
        	activityItem.setNativeAlaId("group_" + assignment.getID());
        	activityItem.setTitle("dummy_group_"+ assignment.getID());
        	activityItem.setRenderingUrl("dummy");
        	activityItem.setSequenceNo(1);
        	activityItem.setWeight(assignment.getWeight());
        	activityItems.add(activityItem);
        	//End of CST-1373
        	
        }else if (assignment.getType().equals(AssignmentType.WRITING) || assignment.getType().equals(AssignmentType.BLOG)
                || assignment.getType().equals(AssignmentType.DISCUSSION)) {

            activity.setWeight((long) assignment.getWeight());
            activity.setAlaContentProvider(ProductVariables.PROVIDER_CONNECT);
            activity.setNativeAlaId("writing_" + assignment.getID());

            ActivityItem activityItem = new ActivityItem();
            activityItem.setNativeAlaId("writing_" + assignment.getID());
            activityItem.setTitle(assignment.getTitle());
            activityItem.setRenderingUrl("http://dummy.com");
            activityItem.setSequenceNo(1);
            activityItem.setWeight(assignment.getWeight());
            activityItems.add(activityItem);


        }else if (assignment.getType().equals(AssignmentType.READER17)){
            activity.setAlaContentProvider(ProductVariables.PROVIDER_CONNECT);
            activity.setNativeAlaId("reader17_" + assignment.getID());

            ActivityItem activityItem = new ActivityItem();
            activityItem.setNativeAlaId("reader17_" + assignment.getID());
            activityItem.setTitle(assignment.getTitle());
            activityItem.setRenderingUrl("http://dummy.com");
            activityItem.setSequenceNo(1);
            activityItem.setWeight(assignment.getWeight());
            activityItems.add(activityItem);
        } else if (assignment.getType().equals(AssignmentType.MUZZY_LANE)){
            activity.setNativeAlaId("connect_" + assignment.getID());
            ActivityItem activityItem = new ActivityItem();
            activityItem.setNativeAlaId("connect_" + assignment.getID());
            activityItem.setTitle(assignment.getTitle());
            activityItem.setRenderingUrl("http://dummy.com");
            activityItem.setSequenceNo(1);
            activityItem.setWeight(assignment.getWeight());
            activityItems.add(activityItem);
        }
        
        // Assuming one video assignment is equivalent to one activity and one activity item.
        float weight = 0;
        for (int j = 0; j < activityItems.size(); j++) {
            logger.debug("Activity Item id " + activityItems.get(j).getID());
            logger.debug("Activity Item ALAMGR " + activityItems.get(j).getAlaManagerID());
            logger.debug("Activity Item Weight " + activityItems.get(j).getWeight());
            weight += activityItems.get(j).getWeight();
        }
        if (weight != 0f) {
            activity.setWeight((long) weight);
            activity.setWeightBased(true);
        }
        activity.setQuestions(activityItems.size());

        activity.setActivityItems(activityItems);

        logger.debug("Inside prepareActivityWithActivityItems, prepared activity: {}", activity);
        return activity;
    }

	public static List<Long> getUnSynchedAssignments(Map<String,String> syncStatusMap){
		Set<Map.Entry<String, String>> entrySet = syncStatusMap.entrySet();
		List<Long> unsyncAssignments = new ArrayList<Long>();
		for(Map.Entry<String, String> entry : entrySet){
			if(!"Done".equals(entry.getValue())){
				unsyncAssignments.add(Long.parseLong(entry.getKey()));
			}
		}
		return unsyncAssignments;
	}
}