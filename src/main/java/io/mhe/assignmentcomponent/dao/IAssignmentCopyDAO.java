package io.mhe.assignmentcomponent.dao;

import io.mhe.assignmentcomponent.vo.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IAssignmentCopyDAO {


    public String getAssignmentName(long id);
    boolean copyHMPublicAssignments(CopyAssignment[] srcAssignments,
                                    long srcSectionId, long dstSectionId, long[] oldCategoryIds,
                                    long[] newCategoryIds, long newCourseId, long originalCourseId) throws Exception;
    void copySectionAssignmentXref(Map<Long, Long> sectionIdsMap, Map<Long, Long> assignmentsMap);

    public boolean deleteMultipleAssignments(List<Long> assignmentIds);


    Assignment getURLBasedAssignment(long assignmentId);


    Activity[] getActivitiesForAssignment(long assignmentId);


    ActivityItem[] getActivityItemsForActivity(long id);


    boolean addActivityItemsToActivity(ActivityItem[] items, long id);


    long updateActivity(Activity activity, long assignmentId);

    void updateAssignmentUpdatedDate(long assignmentId);

    long addActivityToAssignment(Activity activity, long assignmentId);

    ActivityItem[] getActivityItemsByAssignmentId(long assignmentId);

    void deleteActivityItemsByActivityId(long actNID);

    void updatePointsAndQuestionsForAssignment(long assignmentId, float points, int numQuestions, int availableQuestions);

    void insertSyncStatusForAssignment(long assignmentId, String nativeAlaId, String status);

    void insertParentAssignmentStatusForAssignment(long assignmentId, long parentAssignmentId, String status);

    GroupAssignment getGroupAssignmentById(long assignmentId, long sectionId);

    int[] copyGroupAssignmentPropertiesForCopyAssignment(CopyAssignment[] ca);
}
