package io.mhe.assignmentcomponent.vo;

import java.util.ArrayList;
import java.util.List;

public class CopyAssignment implements Model {
	private long		assignmentId;
	private long		newAssignmentId;
	private String		newPrimaryInstructorId;

	private String		nativeAlaId;
	private String		newNativeAlaId;

	private String		title;
	private String		newTitle;

	private long		courseId;
	private long		newCourseId;

	private long		sectionId;
	private long		newSectionId;

	private String		provider;
	private String		type;
	private String		copyEZTStatus;
	private String		error;
	// Property weight has been changed to float
	private float		weight					= 0.0f;
	// to support fractional points for Ez Test.
	private List<Long>	assignmetnLineItemIds	= new ArrayList<Long>();
	
	private String parentAssignmentStatus;

	public long getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(long assignmentId) {
		this.assignmentId = assignmentId;
	}

	public long getNewAssignmentId() {
		return newAssignmentId;
	}

	public void setNewAssignmentId(long newAssignmentId) {
		this.newAssignmentId = newAssignmentId;
	}

	public String getNativeAlaId() {
		return nativeAlaId;
	}

	public void setNativeAlaId(String nativeAlaId) {
		this.nativeAlaId = nativeAlaId;
	}

	public String getNewNativeAlaId() {
		return newNativeAlaId;
	}

	public void setNewNativeAlaId(String newNativeAlaId) {
		this.newNativeAlaId = newNativeAlaId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNewTitle() {
		return newTitle;
	}

	public void setNewTitle(String newTitle) {
		this.newTitle = newTitle;
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public long getNewCourseId() {
		return newCourseId;
	}

	public void setNewCourseId(long newCourseId) {
		this.newCourseId = newCourseId;
	}

	public long getSectionId() {
		return sectionId;
	}

	public void setSectionId(long gradebookId) {
		this.sectionId = gradebookId;
	}

	public long getNewSectionId() {
		return newSectionId;
	}

	public void setNewSectionId(long newGradebookId) {
		this.newSectionId = newGradebookId;
	}

	public String getNewPrimaryInstructorId() {
		return newPrimaryInstructorId;
	}

	public void setNewPrimaryInstructorId(String newPrimaryInstructorId) {
		this.newPrimaryInstructorId = newPrimaryInstructorId;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getProvider() {
		return provider;
	}

	public String getCopyEZTStatus() {
		return copyEZTStatus;
	}

	public void setCopyEZTStatus(String copyEZTStatus) {
		this.copyEZTStatus = copyEZTStatus;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	private String	uuid	= null;

	/**
	 * <p>
	 * Get the type.
	 * </p>
	 * @return the type.
	 * @see #type
	 */

	public String getType() {
		return type;
	}

	/**
	 * <p>
	 * Set the type.
	 * </p>
	 * @param type new value for the type field.
	 * @see #type
	 */

	public void setType(String type) {
		this.type = type;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public void setAssignmetnLineItemIds(List<Long> assignmetnLineItemIds) {
		this.assignmetnLineItemIds = assignmetnLineItemIds;
	}

	public List<Long> getAssignmetnLineItemIds() {
		return assignmetnLineItemIds;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CopyAssignment [assignmentId=").append(assignmentId).append(", assignmetnLineItemIds=").append(assignmetnLineItemIds).append(
				", copyEZTStatus=").append(copyEZTStatus).append(", courseId=").append(courseId).append(", error=").append(error).append(
				", nativeAlaId=").append(nativeAlaId).append(", newAssignmentId=").append(newAssignmentId).append(", newCourseId=").append(
				newCourseId).append(", newNativeAlaId=").append(newNativeAlaId).append(", newPrimaryInstructorId=").append(newPrimaryInstructorId)
				.append(", newSectionId=").append(newSectionId).append(", newTitle=").append(newTitle).append(", provider=").append(provider).append(
						", sectionId=").append(sectionId).append(", title=").append(title).append(", type=").append(type).append(", uuid=").append(
						uuid).append(", weight=").append(weight).append(", parentAssignmentStatus=").append(parentAssignmentStatus).append("]");
		return builder.toString();
	}

	/**
	 * @return the parentAssignmentStatus
	 */
	public String getParentAssignmentStatus() {
		return parentAssignmentStatus;
	}

	/**
	 * @param parentAssignmentStatus the parentAssignmentStatus to set
	 */
	public void setParentAssignmentStatus(String parentAssignmentStatus) {
		this.parentAssignmentStatus = parentAssignmentStatus;
	}
}
