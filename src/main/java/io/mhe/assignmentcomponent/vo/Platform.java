package io.mhe.assignmentcomponent.vo;

import com.mhe.connect.business.product.constant.ProductPlatform;

public class Platform implements Model{

	/**
	 * Default Serial Version Id
	 */
	private static final long serialVersionUID = 1L;
	
	private long platformId;
	
	private String platformName;
	
	private String globalProperty;
	
	private String widgetBaseUrl;

	public long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(long platformId) {
		this.platformId = platformId;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
	
	public ProductPlatform getProductPlatform()
	{
		return ProductPlatform.getInstance(this.platformName);
	}

	public String getGlobalProperty() {
		return globalProperty;
	}

	public void setGlobalProperty(String globalProperty) {
		this.globalProperty = globalProperty;
	}

	public String getWidgetBaseUrl() {
		return widgetBaseUrl;
	}

	public void setWidgetBaseUrl(String widgetBaseUrl) {
		this.widgetBaseUrl = widgetBaseUrl;
	}

	@Override
	public String toString() {
		return new StringBuilder("Platform [platformId=" ).append( platformId ).append( ", platformName="
				).append( platformName ).append( ", globalProperty=" ).append( globalProperty
				).append( ", widgetBaseUrl=" ).append( widgetBaseUrl ).append( "]").toString();
	}
}
