package io.mhe.assignmentcomponent.vo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Product implements Model {

	/**
	 * Serial Version Id
	 */
	private static final long		serialVersionUID	= 1L;

	private long					productId;

	private String					productType;

	private long					platformId;

	private int						sequenceNo;

	private boolean					isLicenseEnabled;

	private boolean					isEnabled;

	private String					template;

	private String					parisProductKey;

	private boolean					isBundledEnabled;

	private boolean					isAssignmentEnabled;

	private boolean					isWidgetEnabled;

	private boolean					isExternalReportEnabled;
	
	private boolean					isEbookEnabled;

	private Timestamp				createdDate;

	private Timestamp				updatedDate;

	private String					createdBy;

	private String					updatedBy;

	private ConnectProductConfig	connectProductConfig;

	private List<ProductConstant>	productConstant		= new ArrayList<ProductConstant>();

	/**
	 * List of Area9 Modules Object for each External Product Id;
	 */

	private Map<String, String>		productExceptionMap	= new HashMap<String, String>();

	private Platform				platform;

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

	public long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(long platformId) {
		this.platformId = platformId;
	}

	public int getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(int sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public boolean isLicenseEnabled() {
		return isLicenseEnabled;
	}

	public void setLicenseEnabled(boolean isLicenseEnabled) {
		this.isLicenseEnabled = isLicenseEnabled;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getTemplate() {
		return template;
	}

	public ProductTemplate getProductTemplate()
	{
		return ProductTemplate.getInstance(template);
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getParisProductKey() {
		return parisProductKey;
	}

	public void setParisProductKey(String parisProductKey) {
		this.parisProductKey = parisProductKey;
	}

	public boolean isBundledEnabled() {
		return isBundledEnabled;
	}

	public void setBundledEnabled(boolean isBundledEnabled) {
		this.isBundledEnabled = isBundledEnabled;
	}

	public boolean isAssignmentEnabled() {
		return isAssignmentEnabled;
	}

	public void setAssignmentEnabled(boolean isAssignmentEnabled) {
		this.isAssignmentEnabled = isAssignmentEnabled;
	}

	public boolean isWidgetEnabled() {
		return isWidgetEnabled;
	}

	public void setWidgetEnabled(boolean isWidgetEnabled) {
		this.isWidgetEnabled = isWidgetEnabled;
	}

	public boolean isExternalReportEnabled() {
		return isExternalReportEnabled;
	}

	public void setExternalReportEnabled(boolean isExternalReportEnabled) {
		this.isExternalReportEnabled = isExternalReportEnabled;
	}
	
	public boolean isEbookEnabled() {
		return isEbookEnabled;
	}
	
	public void setEbookEnabled(boolean isEbookEnabled) {
		this.isEbookEnabled = isEbookEnabled;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public List<ProductConstant> getProductConstant() {
		return productConstant;
	}

	public void setProductConstant(List<ProductConstant> productConstant) {
		this.productConstant = productConstant;
	}

	public void addProductConstant(ProductConstant productConstant)
	{
		this.productConstant.add(productConstant);
	}

	public ConnectProductConfig getConnectProductConfig() {
		return connectProductConfig;
	}

	public void setConnectProductConfig(ConnectProductConfig connectProductConfig) {
		this.connectProductConfig = connectProductConfig;
	}

	/*
	public ProductDetailsMapping getProductDeatailsMappingForCourseISBN(String courseIsbn) {
		return ProductServiceUtil.getProductDetailsMappingForACourseISBN(courseIsbn, this.productType);
	}

	 */

	public Map<String, String> getProductExceptionMap() {
		return productExceptionMap;
	}

	public String getProductException(String exceptionKey) {
		return productExceptionMap.get(exceptionKey);
	}

	public void setProductExceptionMap(Map<String, String> productExceptionMap) {
		this.productExceptionMap = productExceptionMap;
	}

	public void addProductExceptionMap(ProductExceptionMapping productExceptionMapping) {
		this.productExceptionMap.put(productExceptionMapping.getExceptionRule(), productExceptionMapping.getExceptionValue());
	}

	@Override
	public String toString() {
		return new StringBuilder("Product [productId=").append(productId).append(", productType="
				).append(productType).append(", platformId=").append(platformId).append(", sequenceNo="
				).append(sequenceNo).append(", isLicenseEnabled=").append(isLicenseEnabled
				).append(", isEnabled=").append(isEnabled).append(", template=").append(template
				).append(", parisProductKey=").append(parisProductKey
				).append(", isBundledEnabled=").append(isBundledEnabled
				).append(", isAssignmentEnabled=").append(isAssignmentEnabled
				).append(", isWidgetEnabled=").append(isWidgetEnabled
				).append(", isExternalReportEnabled=").append(isExternalReportEnabled
				).append(", isEbookEnabled=").append(isEbookEnabled
				).append(", connectProductConfig=").append(connectProductConfig
				).append(", productConstant=").append(productConstant
				).append(", productExceptionMap=").append(productExceptionMap).append(", platform=").append(platform + "]").toString();
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public ProductConstant getProductConstantForFeatureType(String featureType)
	{
		if (productConstant != null && !productConstant.isEmpty())
		{
			for (ProductConstant pc : productConstant)
			{
				if (featureType.equals(pc.getFeatureType()))
				{
					return pc;
				}
			}
		}
		return null;
	}
}
