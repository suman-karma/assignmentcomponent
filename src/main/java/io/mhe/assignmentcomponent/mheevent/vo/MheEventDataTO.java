package io.mhe.assignmentcomponent.mheevent.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MheEventDataTO {
	private Object sectionId;
	private Object assignmentId;
	private Object assignmentType;
	private Object studentId;
	private Object attemptNo;
	private Object activityId;
	private Object sectionLineItemActivityId;
	private Boolean isForcedScore;
	private Boolean isForcedZero;


	
	public static class MheEventDataTOBuilder {
		
		private Object sectionId;
		private Object assignmentId;
		private Object assignmentType;
		private Object studentId;
		private Object attemptNo;
		private Object activityId;
		private Object sectionLineItemActivityId;
		private Boolean isForcedScore;

		private Boolean isForcedZero;

		
		MheEventDataTOBuilder() {
		}


		
		public MheEventDataTOBuilder sectionId(final Object sectionId) {
			this.sectionId = sectionId;
			return this;
		}


		
		public MheEventDataTOBuilder assignmentId(final Object assignmentId) {
			this.assignmentId = assignmentId;
			return this;
		}


		
		public MheEventDataTOBuilder assignmentType(final Object assignmentType) {
			this.assignmentType = assignmentType;
			return this;
		}


		
		public MheEventDataTOBuilder studentId(final Object studentId) {
			this.studentId = studentId;
			return this;
		}


		
		public MheEventDataTOBuilder attemptNo(final Object attemptNo) {
			this.attemptNo = attemptNo;
			return this;
		}


		
		public MheEventDataTOBuilder activityId(final Object activityId) {
			this.activityId = activityId;
			return this;
		}


		
		public MheEventDataTOBuilder sectionLineItemActivityId(final Object sectionLineItemActivityId) {
			this.sectionLineItemActivityId = sectionLineItemActivityId;
			return this;
		}

		public MheEventDataTOBuilder isForcedScore(final Boolean isForcedScore) {
			this.isForcedScore = isForcedScore;
			return this;
		}
		public MheEventDataTOBuilder isForcedZero(final Boolean isForcedZero) {
			this.isForcedZero = isForcedZero;
			return this;
		}


		public MheEventDataTO build() {
			return new MheEventDataTO(this.sectionId, this.assignmentId, this.assignmentType, this.studentId, this.attemptNo, this.activityId, this.sectionLineItemActivityId, this.isForcedScore,this.isForcedZero);
		}

		@java.lang.Override
		
		public java.lang.String toString() {
			return "MheEventDataTOBuilder(sectionId=" + this.sectionId + ", assignmentId=" + this.assignmentId + ", assignmentType=" + this.assignmentType + ", studentId=" + this.studentId + ", attemptNo=" + this.attemptNo + ", activityId=" + this.activityId + ", sectionLineItemActivityId=" + this.sectionLineItemActivityId + ", isForcedScore=" + this.isForcedScore + ")";
		}
	}

	
	public static MheEventDataTOBuilder builder() {
		return new MheEventDataTOBuilder();
	}

	
	public Object getSectionId() {
		return this.sectionId;
	}

	
	public Object getAssignmentId() {
		return this.assignmentId;
	}

	
	public Object getAssignmentType() {
		return this.assignmentType;
	}

	
	public Object getStudentId() {
		return this.studentId;
	}

	
	public Object getAttemptNo() {
		return this.attemptNo;
	}

	
	public Object getActivityId() {
		return this.activityId;
	}

	
	public Object getSectionLineItemActivityId() {
		return this.sectionLineItemActivityId;
	}

	public Boolean getIsForcedScore() {
		return this.isForcedScore;
	}
	public Boolean getIsForcedZero() {
		return this.isForcedZero;
	}


	
	public void setSectionId(final Object sectionId) {
		this.sectionId = sectionId;
	}

	
	public void setAssignmentId(final Object assignmentId) {
		this.assignmentId = assignmentId;
	}

	
	public void setAssignmentType(final Object assignmentType) {
		this.assignmentType = assignmentType;
	}

	
	public void setStudentId(final Object studentId) {
		this.studentId = studentId;
	}

	
	public void setAttemptNo(final Object attemptNo) {
		this.attemptNo = attemptNo;
	}

	
	public void setActivityId(final Object activityId) {
		this.activityId = activityId;
	}

	
	public void setSectionLineItemActivityId(final Object sectionLineItemActivityId) {
		this.sectionLineItemActivityId = sectionLineItemActivityId;
	}

	public void setIsForcedScore(final Boolean isForcedScore) {
		this.isForcedScore = isForcedScore;
	}

	public void setIsForcedZero(final Boolean isForcedZero){this.isForcedZero = isForcedZero;}

	@java.lang.Override
	
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof MheEventDataTO)) return false;
		final MheEventDataTO other = (MheEventDataTO) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		final java.lang.Object this$sectionId = this.getSectionId();
		final java.lang.Object other$sectionId = other.getSectionId();
		if (this$sectionId == null ? other$sectionId != null : !this$sectionId.equals(other$sectionId)) return false;
		final java.lang.Object this$assignmentId = this.getAssignmentId();
		final java.lang.Object other$assignmentId = other.getAssignmentId();
		if (this$assignmentId == null ? other$assignmentId != null : !this$assignmentId.equals(other$assignmentId)) return false;
		final java.lang.Object this$assignmentType = this.getAssignmentType();
		final java.lang.Object other$assignmentType = other.getAssignmentType();
		if (this$assignmentType == null ? other$assignmentType != null : !this$assignmentType.equals(other$assignmentType)) return false;
		final java.lang.Object this$studentId = this.getStudentId();
		final java.lang.Object other$studentId = other.getStudentId();
		if (this$studentId == null ? other$studentId != null : !this$studentId.equals(other$studentId)) return false;
		final java.lang.Object this$attemptNo = this.getAttemptNo();
		final java.lang.Object other$attemptNo = other.getAttemptNo();
		if (this$attemptNo == null ? other$attemptNo != null : !this$attemptNo.equals(other$attemptNo)) return false;
		final java.lang.Object this$activityId = this.getActivityId();
		final java.lang.Object other$activityId = other.getActivityId();
		if (this$activityId == null ? other$activityId != null : !this$activityId.equals(other$activityId)) return false;
		final java.lang.Object this$sectionLineItemActivityId = this.getSectionLineItemActivityId();
		final java.lang.Object other$sectionLineItemActivityId = other.getSectionLineItemActivityId();
		if (this$sectionLineItemActivityId == null ? other$sectionLineItemActivityId != null : !this$sectionLineItemActivityId.equals(other$sectionLineItemActivityId)) return false;
		final java.lang.Boolean this$isForcedScore = this.getIsForcedScore();
		final java.lang.Boolean other$isForcedScore = other.getIsForcedScore();
		if (this$isForcedScore == null ? other$isForcedScore != null : !this$isForcedScore.equals(other$isForcedScore)) return false;
		final java.lang.Boolean this$isForcedZero = this.getIsForcedZero();
		final java.lang.Boolean other$isForcedZero = other.getIsForcedZero();
		if (this$isForcedZero == null ? other$isForcedZero != null : !this$isForcedZero.equals(other$isForcedZero)) return false;
		return true;
	}

	
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof MheEventDataTO;
	}

	@java.lang.Override
	
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $sectionId = this.getSectionId();
		result = result * PRIME + ($sectionId == null ? 43 : $sectionId.hashCode());
		final java.lang.Object $assignmentId = this.getAssignmentId();
		result = result * PRIME + ($assignmentId == null ? 43 : $assignmentId.hashCode());
		final java.lang.Object $assignmentType = this.getAssignmentType();
		result = result * PRIME + ($assignmentType == null ? 43 : $assignmentType.hashCode());
		final java.lang.Object $studentId = this.getStudentId();
		result = result * PRIME + ($studentId == null ? 43 : $studentId.hashCode());
		final java.lang.Object $attemptNo = this.getAttemptNo();
		result = result * PRIME + ($attemptNo == null ? 43 : $attemptNo.hashCode());
		final java.lang.Object $activityId = this.getActivityId();
		result = result * PRIME + ($activityId == null ? 43 : $activityId.hashCode());
		final java.lang.Object $sectionLineItemActivityId = this.getSectionLineItemActivityId();
		result = result * PRIME + ($sectionLineItemActivityId == null ? 43 : $sectionLineItemActivityId.hashCode());
		final java.lang.Boolean $isForcedScore = this.getIsForcedScore();
		result = result * PRIME + ($isForcedScore == null ? 43 : $isForcedScore.hashCode());
		final java.lang.Boolean $isForcedZero = this.getIsForcedZero();
		result = result * PRIME + ($isForcedZero== null ? 43 : $isForcedZero.hashCode());
		return result;
	}

	@java.lang.Override
	
	public java.lang.String toString() {
		return "MheEventDataTO(sectionId=" + this.getSectionId() + ", assignmentId=" + this.getAssignmentId() + ", assignmentType=" + this.getAssignmentType() + ", studentId=" + this.getStudentId() + ", attemptNo=" + this.getAttemptNo() + ", activityId=" + this.getActivityId() + ", sectionLineItemActivityId=" + this.getSectionLineItemActivityId() + ", isForcedScore=" + this.getIsForcedScore() + ",isForcedZero="+this.getIsForcedZero()+")";
	}

	
	public MheEventDataTO(final Object sectionId, final Object assignmentId, final Object assignmentType, final Object studentId, final Object attemptNo, final Object activityId, final Object sectionLineItemActivityId, final Boolean isForcedScore,final Boolean isForcedZero) {
		this.sectionId = sectionId;
		this.assignmentId = assignmentId;
		this.assignmentType = assignmentType;
		this.studentId = studentId;
		this.attemptNo = attemptNo;
		this.activityId = activityId;
		this.sectionLineItemActivityId = sectionLineItemActivityId;
		this.isForcedScore = isForcedScore;
		this.isForcedZero = isForcedZero;
	}

	
	public MheEventDataTO() {
	}
}
