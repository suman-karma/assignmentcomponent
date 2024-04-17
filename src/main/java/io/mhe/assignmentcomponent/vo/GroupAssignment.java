package io.mhe.assignmentcomponent.vo;

/*
import com.mhe.connect.business.common.GenUtil;
import com.mhe.connect.business.course.valueobj.Student;
import com.mhe.connect.business.course.valueobj.StudentStatus;
*/

import java.util.LinkedHashMap;
import java.util.Map;

public class GroupAssignment extends Assignment {

	//private Group[] groups = null;
	private boolean isRosterUpdated = false;
	private int studentsPerGroup = 0;
	private int studentCount = 0;
	
	/*
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (groups == null) {
			sb.append(" Groups is NULL");
		} else {
			sb.append(" Number of groups = ").append(groups.length);
			for (Group group : groups) {
				sb.append(" group sectionId=").append(group.getSectionId())
				.append(", group Id : ").append(group.getGroupId()).append("\n");
				if (group.getStudents() != null) {
					for (Student student : group.getStudents()) {
						sb.append("--> Student id in this group : ").append(student.getID()).append("\n");
					}
				}
				sb.append("\n");
			}
		}

		sb.append(" isRosterUpdated=").append(isRosterUpdated).append(", studentsPerGroup=")
		.append(studentsPerGroup).append(", studentCount=").append(studentCount)
		.append(", submittedGroupIds=").append(submittedGroupIds);
		return sb.toString();
	}

	public Group[] getGroups() {
		return groups;
	}
	public void setGroups(Group[] groups) {
		this.groups = groups;
	}
	 */
	
	public int getStudentCount() {
		return studentCount;
	}
	public void setStudentCount(int studentCount) {
		this.studentCount = studentCount;
	}

	public boolean isRosterUpdated() {
		return isRosterUpdated;
	}
	public void setRosterUpdated(boolean isRosterUpdated) {
		this.isRosterUpdated = isRosterUpdated;
	}
	public int getStudentsPerGroup() {
		return studentsPerGroup;
	}
	public void setStudentsPerGroup(int studentsPerGroup) {
		this.studentsPerGroup = studentsPerGroup;
	}
	
	public String getJSON() {
		StringBuffer json = new StringBuffer("{\"group_assignment\":{\"id\": \"");
		json.append(this.getID());
		json.append("\",\"name\": \"");
		json.append(this.getTitle());
		json.append("\",\"sectionId\": \"");
		json.append(this.getParentSectionId());
		json.append("\",\"studentsPerGroup\": \"");
		json.append(this.getStudentsPerGroup());
		json.append("\",\"studentCount\": \"");
		json.append(this.getStudentCount());
		json.append("\",\"isRosterUpdated\": \"");
		json.append(this.isRosterUpdated() ? "Y" : "N");
		
        json.append("\",\"groups\":[");
        /*
        if(this.getGroups() != null) {
	        for(int i = 0; i < this.getGroups().length; i++) {
	        	Group group = this.getGroups()[i];
	        	json.append("{\"id\": \"");
	        	json.append(group.getGroupId());
	        	json.append("\",\"members\": [");
	        	
	        	if(group.getStudents() != null) {
		        	for(int j = 0; j < group.getStudents().length; j++) {
		        		Map<String, String> nameMap = new LinkedHashMap<String, String>();
		        		
		        		Student student = group.getStudents()[j];
		        		nameMap.put("userId", student.getID());
	        			nameMap.put("lastName",student.getName()==null?"":student.getName().getLastName());
	        			nameMap.put("firstName",student.getName()==null?"":student.getName().getFirstName());
	        			
		        		//need to change this to figure out new student..
		        		nameMap.put("isNew", student.isNewStudent() ? "Y" : "N");
		        		nameMap.put("isActive",StudentStatus.ACTIVE.equals(student.getStatus()) ? "Y" : "N");
		        		
		        		json.append(GenUtil.getJsonFormat(nameMap));

		        		if(j != group.getStudents().length - 1){
		        			json.append(",");
		        		}
		        	}
	        	}
	        	json.append("]");
	        	if(group.getTopic() != null) {
	        		json.append(",\"topic\": {\"id\": \"");
	        		json.append(group.getTopic().getID());
	        		json.append("\", \"title\": \"");
	        		json.append(group.getTopic().getTitle());
	        		json.append("\"}");
	        	}
	        	json.append("}");
	        	if(i != groups.length - 1){
	        		json.append(",");
	        	}
	        }
        }*/
        
        json.append("]}}");
		
		return json.toString();
	}
	
	private long[] submittedGroupIds = null;
	
	public void setSubmittedGroupIds(long[] submittedGroupIds) {
		this.submittedGroupIds = submittedGroupIds;
	}
	
	public long[] getSubmittedGroupIds() {
		return submittedGroupIds;
	}
}