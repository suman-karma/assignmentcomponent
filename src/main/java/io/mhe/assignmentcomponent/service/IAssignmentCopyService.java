package io.mhe.assignmentcomponent.service;

import io.mhe.assignmentcomponent.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public interface IAssignmentCopyService {

    public void copyAssignmentsToNewSection(CopyAssignment srcAssignment, long oldSectionID,
                                            long newSectionID,
                                            long[] origCategoryIds,
                                            long[] newCategoryIds,
                                            long newCourseId,
                                            long newSectionId,
                                            HashMap modulesMap,
                                            Map assignMap) throws Exception ;


    public boolean copyHMPublicAssignments(CopyAssignment[] srcAssignmentIds,
                                           long srcGBID, long dstGBId, long[] oldCategoryIds,
                                           long[] newCategoryIds, long newCourseId, long originalCourseId) throws Exception;


    public void copySectionAssignmentXref(Map<Long, Long> sectionIdsMap,
                                          Map<Long, Long> assignmentsMap);



    public String[] copyXWorkFlow(CopyAssignment[] assignments) throws Exception;

    public void registerActivityFirstTime(long assignmentId, String nativeAlaId,
                                          String alaTitle) throws Exception;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    Activity[] getActivitiesForAssignment(long assignmentId) throws Exception;

    public void updatePointsAndQuestionsForAssignment(long assignmentId,
                                                      float points, int numQuestions, int availableQuestions);

    public void insertSyncStatusForAssignment(long assignmentId,
                                              String nativeAlaId, String status);

    public void insertParentAssignmentStatusForAssignment(long assignmentId, long parentAssignmentId, String status) throws Exception;

    public GroupAssignment getGroupAssignmentById(long assignmentId, long sectionId); // is this requried group assignmet import?

    public void copyGroupAssignmentPropertiesForCopyAssignment(CopyAssignment[] ca);


    Assignment getURLBasedAssignment(long assignmentId);

    ActivityItem[] getActivityItemsForActivity(long id);

    void addActivityItemsToActivity(ActivityItem[] items, long id);

    Assignment getAssignment(long assignmentId);


    Product getProduct(String type);


    boolean addActivitiesAndItemsForAssignment(Activity activity, long assignmentId)throws Exception;

    long updateActivity(Activity activity, long assignmentId);

    void updateAssignmentUpdatedDate(long assignmentId);

    long addActivityToAssignment(Activity activity, long assignmentId);

    ActivityItem[] getActivityItemsByAssignmentId(long assignmentId);

    void deleteActivityItemsByActivityId(long actNID);

    void addActivityAndALAInfoForAssignment(Assignment assignment);
}
