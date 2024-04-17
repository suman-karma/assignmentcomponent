package io.mhe.assignmentcomponent.vo;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public class ProductDetailsMapping implements Model {

	/**
	 * Serial Version Id
	 */
	private static final long serialVersionUID = 1L;	

	private long id;
	
	private String courseIsbn;
	
	private long productId;
	
	private String productType;
	
	private String externalProductId;
	
	private Timestamp createdOn;
	
	private Timestamp modifiedOn;
	
	private String createdBy;
	
	private String modifiedBy;
	
	private String forkingStartDate;
	
	private String parisProductKey;
	
	private Map<ProductDetailsMappingInfo.FeatureType,Map<String,String>> productDetailsMappingInfos;
	
    private boolean ebookType;
    
	public boolean isEbookType() {
		return ebookType;
	}

	public void setEbookType(boolean ebookType) {
		this.ebookType = ebookType;
	}

	/**
	 * @return the parisProductKey
	 */
	public String getParisProductKey() {
		return parisProductKey;
	}

	/**
	 * @param parisProductKey the parisProductKey to set
	 */
	public void setParisProductKey(String parisProductKey) {
		this.parisProductKey = parisProductKey;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCourseIsbn() {
		return courseIsbn;
	}

	public void setCourseIsbn(String courseIsbn) {
		this.courseIsbn = courseIsbn;
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

	public String getExternalProductId() {
		return externalProductId;
	}

	public void setExternalProductId(String externalProductId) {
		this.externalProductId = externalProductId;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Timestamp modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((courseIsbn == null) ? 0 : courseIsbn.hashCode());
		result = prime * result
				+ ((productType == null) ? 0 : productType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || (getClass() != obj.getClass())) {
			return false;
		}

		ProductDetailsMapping other = (ProductDetailsMapping) obj;
		if (courseIsbn == null && other.courseIsbn != null) {
			return false;
		} else if (!courseIsbn.equals(other.courseIsbn)) {
			return false;
		}
		if (productType == null && other.productType != null) {
			return false;
		} else if (!productType.equals(other.productType)) {
			return false;
		}
		return true;
	}

	public void setForkingStartDate(String forkingStartDate) {
		this.forkingStartDate = forkingStartDate;
		
	}

	

	public Timestamp getForkingStartDateAndTime() {
		if(!GenUtil.isBlankString(forkingStartDate)){
			Calendar forkDate =  Calendar.getInstance();
			forkDate.setTimeInMillis(DateTimeSupport.convertToDateTime(forkingStartDate, DateTimeSupport.MM_DD_YYYY_SLASH_DELEMITED).getMillis());
			forkDate.set(Calendar.HOUR, 0);
			forkDate.set(Calendar.MINUTE, 0);
			forkDate.set(Calendar.SECOND, 0);
			forkDate.set(Calendar.MILLISECOND, 0);
			return new Timestamp(forkDate.getTime().getTime());
		}
		return null;
			
		
	}
	
	public Map<ProductDetailsMappingInfo.FeatureType, Map<String, String>> getProductDetailsMappingInfos() {
		return productDetailsMappingInfos;
	}

	public void setProductDetailsMappingInfos(Map<ProductDetailsMappingInfo.FeatureType, Map<String, String>> productDetailsMappingInfos) {
		this.productDetailsMappingInfos = productDetailsMappingInfos;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductDetailsMapping [id=");
		builder.append(id);
		builder.append(", courseIsbn=");
		builder.append(courseIsbn);
		builder.append(", productId=");
		builder.append(productId);
		builder.append(", productType=");
		builder.append(productType);
		builder.append(", externalProductId=");
		builder.append(externalProductId);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", modifiedOn=");
		builder.append(modifiedOn);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", modifiedBy=");
		builder.append(modifiedBy);
		builder.append(", forkingStartDate=");
		builder.append(forkingStartDate);
		builder.append(", parisProductKey=");
		builder.append(parisProductKey);
		builder.append(", productDetailsMappingInfos=");
		builder.append(productDetailsMappingInfos);
		builder.append(", ebookType=");
		builder.append(ebookType);
		builder.append("]");
		return builder.toString();
	}
}
