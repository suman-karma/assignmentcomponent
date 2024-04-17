/**
 * 
 */
package io.mhe.assignmentcomponent.vo;

import java.util.Date;

/**
 * @author krahul
 *
 */
public class ProductDetailsMappingInfo {
	
	//feature type
	public static enum FeatureType{
		COMMON,EBOOK,PAAM
	}
	
	private Long productDetailsMappingId;
	private FeatureType featureType;
	private String key;
	private String value;
	private boolean isActive;
	private String createdBy;
	private Date createdDate;
	public Long getProductDetailsMappingId() {
		return productDetailsMappingId;
	}
	public void setProductDetailsMappingId(Long productDetailsMappingId) {
		this.productDetailsMappingId = productDetailsMappingId;
	}
	public FeatureType getFeatureType() {
		return featureType;
	}
	public void setFeatureType(FeatureType featureType) {
		this.featureType = featureType;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductDetailsMappingInfo [productDetailsMappingId=");
		builder.append(productDetailsMappingId);
		builder.append(", featureType=");
		builder.append(featureType);
		builder.append(", key=");
		builder.append(key);
		builder.append(", value=");
		builder.append(value);
		builder.append(", isActive=");
		builder.append(isActive);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append("]");
		return builder.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		final int constA = 1231;
		final int constB = 1237;
		result = prime * result + ((featureType == null) ? 0 : featureType.hashCode());
		result = prime * result + (isActive ? constA : constB);
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((productDetailsMappingId == null) ? 0 : productDetailsMappingId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
			
		if (getClass() != obj.getClass()) {
			return false;
		}
			
		ProductDetailsMappingInfo other = (ProductDetailsMappingInfo) obj;
		if (featureType != other.featureType) {
			return false;
		}
			
		if (isActive != other.isActive) {
			return false;
		}
			
		if (key == null) {
			if (other.key != null){
				return false;
			}
		} else if (!key.equals(other.key)){
			return false;
		}
		
		if (productDetailsMappingId == null) {
			if (other.productDetailsMappingId != null){
				return false;
			}
		} else if (!productDetailsMappingId.equals(other.productDetailsMappingId)){
			return false;
		}
			
		return true;
	}
	
	
	
}
