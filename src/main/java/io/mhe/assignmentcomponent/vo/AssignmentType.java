package io.mhe.assignmentcomponent.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
/*
import com.mhe.connect.business.common.ExceptionUtils;
import com.mhe.connect.business.common.Logger;
import com.mhe.connect.business.common.exceptions.InvalidAssignmentTypeException;
import com.mhe.connect.business.course.service.api.ICourseManagementBusinessService;
import com.mhe.connect.business.lti.vo.LTIExternalCustomAssignmentVO;
import com.mhe.connect.business.lti.vo.LTISaveCustomAssignmentVO;
import com.mhe.connect.business.product.services.IProductService;
 */
import io.mhe.assignmentcomponent.service.AssignmentCopyService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author Kameshwar 
 * @Author: sprasad $
 */
@JsonInclude(Include.NON_NULL)
public class AssignmentType extends Enum {
	private static final Logger logger = LoggerFactory.getLogger(AssignmentType.class);
	private static final long serialVersionUID = 1L;
	
	//private static Logger logger = Logger.getInstance(AssignmentType.class);
	
	//private IProductService productService;
	
	//@JsonIgnore
	//private static ICourseManagementBusinessService courseManagementBusinessService;
	
	
	private static String[] ENUM = new String[] { "GRADED", "ASSIGNED",
			"FLAGGED", "SCORED", "PRACTICE", "EBOOK_READING", "WEB_ASSIGNMENT",
			"ASSESMENT", "URL_BASED", "VIDEO", "ALE", "LEARNSMART", "GROUP",
			"FILEATTACH", "WRITING", "BLOG", "DISCUSSION", "LS_DIAGNOSTIC",
			"MHP","LABSMART", "ACHIEVE", "PREP","NONCONNECT","PLAYBOOK","GROUPASSIGNEMNTS",
			"POWER_OF_PROCESS","LEARNSMART_MASTER", "MUZZY_LANE","READER17", "AVALON", "ADAPTIVELEARNINGASSIGNMENT" };
	
	private static Set<String> dynamicAssignmentTypes = new HashSet<String>();

	//public IProductService getProductService() {
	//    return productService;
	//}

	//public void setProductService(IProductService productService) {
	//    this.productService = productService;
	//}
	
	

	public static Set<String> getDynamicAssignmentTypes() {
		return dynamicAssignmentTypes;
	}

	/*
	@JsonIgnore
	public ICourseManagementBusinessService getCourseManagementBusinessService() {
		return courseManagementBusinessService;
	}

	@JsonIgnore
	public void setCourseManagementBusinessService(ICourseManagementBusinessService courseManagementBusinessService) {
		AssignmentType.courseManagementBusinessService = courseManagementBusinessService;
	}

	 */


	public static final AssignmentType GRADED = new AssignmentType(ENUM[0]);
	public static final AssignmentType ASSIGNED = new AssignmentType(ENUM[1]);
	public static final AssignmentType FLAGGED = new AssignmentType(ENUM[2]);
	public static final AssignmentType SCORED = new AssignmentType(ENUM[3]);
	public static final AssignmentType PRACTICE = new AssignmentType(ENUM[4]);
	public static final AssignmentType EBOOK_READING = new AssignmentType(
			ENUM[5]);
	public static final AssignmentType WEB_READING = new AssignmentType(ENUM[6]);
	public static final AssignmentType ASSESMENT = new AssignmentType(ENUM[7]);
	public static final AssignmentType URLBased = new AssignmentType(ENUM[8]);
	public static final AssignmentType VIDEO = new AssignmentType(ENUM[9]);
	public static final AssignmentType ALE = new AssignmentType(ENUM[10]);
	public static final AssignmentType LEARN_SMART = new AssignmentType(
			ENUM[11]);
	public static final AssignmentType GROUP = new AssignmentType(ENUM[12]);
	public static final AssignmentType FILEATTACH = new AssignmentType(ENUM[13]);
	
	public static final AssignmentType WRITING = new AssignmentType(ENUM[14]);
	public static final AssignmentType BLOG = new AssignmentType(ENUM[15]);
	public static final AssignmentType DISCUSSION = new AssignmentType(ENUM[16]);
	public static final AssignmentType LS_DIAGNOSTIC = new AssignmentType(ENUM[17]);
	public static final AssignmentType MHP = new AssignmentType(ENUM[18]);
	public static final AssignmentType LABSMART = new AssignmentType(ENUM[19]);
	public static final AssignmentType ACHIEVE = new AssignmentType(ENUM[20]);
	public static final AssignmentType PREP = new AssignmentType(ENUM[21]);
	//Added this entry as part of GB-NONCONNECT assignments
	public static final AssignmentType NONCONNECT = new AssignmentType(ENUM[22]);
	//Added this entry for Playbook type assignments
	public static final AssignmentType PLAYBOOK = new AssignmentType(ENUM[23]);
	public static final AssignmentType GROUPASSIGNEMNTS = new AssignmentType(ENUM[24]);
	public static final AssignmentType POWEROFPROCESS = new AssignmentType(ENUM[25]);
	public static final AssignmentType LEARNSMART_MASTER = new AssignmentType(ENUM[26]);
	public static final AssignmentType MUZZY_LANE = new AssignmentType(ENUM[27]);
	public static final AssignmentType READER17 = new AssignmentType(ENUM[28]);
	public static final AssignmentType AVALON = new AssignmentType(ENUM[29]);
	public static final AssignmentType ADAPTIVELEARNINGASSIGNMENT = new AssignmentType(ENUM[30]);
	
	private AssignmentType(String data) {
		super(data);
	}

	public static AssignmentType newInstance(String value)
			throws Exception {
		if (allowed(value, ENUM)) {
			return new AssignmentType(value.toUpperCase());
		} else {
			logger.error("Value:" + value + " allowed:" + ENUM);
			throw new Exception("Invalid Assignment Type");
		}
	}
	public String toString() {
	    return this.getValue();
	}
		
    public static boolean contains(String value){
    	if (allowed(value, ENUM)) {
			return true;
		} 
	   	return false;
    }
    
    /**
     * Overriding allowed method.
     *  1. Search in static assignment types
     *  2. If not found, search in generic assignment types
     *  3. If not found, refresh generic assignment types and search again
     *  
     * @param data
     * @param pValues
     * @return
     */
    protected static boolean allowed( String data , String[] pValues) {
    	boolean allowed = Enum.allowed(data, pValues);
        return allowed;
    }

} 
