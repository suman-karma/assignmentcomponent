package io.mhe.assignmentcomponent.vo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ProductConstant implements Model {

	/**
	 * Serial Version Id
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	
	private long productId;
	
	private String productType;
	
	private String featureType;
	
	private Map<String,String> constantDetailsMap = new HashMap<String, String>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getFeatureType() {
		return featureType;
	}

	public void setFeatureType(String featureType) {
		this.featureType = featureType;
	}

	public Map<String, String> getConstantDetailsMap() {
	    return Collections.unmodifiableMap(constantDetailsMap);
	}

	public void setConstantDetailsMap(Map<String, String> constantDetailsMap) {
		this.constantDetailsMap = constantDetailsMap;
	}
	
	public void addKeyValueToConstantDetailsMap(String key, String value)
	{
		this.constantDetailsMap.put(key, value);
	}

	@Override
	public String toString() {
		return new StringBuilder("ProductConstant [id=" ).append( id ).append( ", productId=" ).append( productId
				).append( ", productType=" ).append( productType ).append( ", featureType="
				).append( featureType ).append( ", constantDetailsMap=" ).append( constantDetailsMap
				).append( "]").toString();
	}
}
