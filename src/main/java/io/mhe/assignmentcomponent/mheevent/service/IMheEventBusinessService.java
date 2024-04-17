package io.mhe.assignmentcomponent.mheevent.service;

import java.util.List;

import io.mhe.assignmentcomponent.mheevent.vo.MheEventData;

public interface IMheEventBusinessService {
	/**
	 * To perform Trigger of event
	 * @param mheEventDataTO
	 */
	public void sendMheEventToSQS(MheEventData mheEventDataTO);
	
	/**
	 * To perform Trigger of event
	 * @param mheEventDataList
	 */
	public void sendMheEventToSQS(List<MheEventData> mheEventDataList);

}
