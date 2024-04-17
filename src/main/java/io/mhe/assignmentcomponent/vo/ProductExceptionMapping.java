package io.mhe.assignmentcomponent.vo;

public class ProductExceptionMapping implements Model {

	/**
	 * Serial Version Id
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	
	private long productId;
	
	private String exceptionRule;
	
	private String exceptionValue;

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

	public String getExceptionRule() {
		return exceptionRule;
	}

	public void setExceptionRule(String exceptionRule) {
		this.exceptionRule = exceptionRule;
	}

	public String getExceptionValue() {
		return exceptionValue;
	}

	public void setExceptionValue(String exceptionValue) {
		this.exceptionValue = exceptionValue;
	}
}
