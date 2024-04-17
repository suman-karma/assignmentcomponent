package io.mhe.assignmentcomponent.vo;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class AssignmentsMetadata {
    long originalCourseId = 100l;
    long originalSectionId = 200l;
    long newCourseId = 300l;
    long newSectionId = 400l;
    String primaryInstructorId = "1234";
    long[] originalCategoryIds = {1l,2l};
    long[] newCategoryIds = {3l,4l};
    ConcurrentHashMap assignmentsMap = new ConcurrentHashMap();
    HashMap modulesMap = new HashMap();
    Boolean failureNotify = true;
    boolean isMarathon = true;

}
