package io.mhe.assignmentcomponent.vo;

/**
 * An Enum for Product Templates
 * Any Template check should be using this.
 * 
 * @author birendra
 *
 */
public enum ProductTemplate {

	DEFAULT("default"), BASIC("basic"), ADVANCED("advanced");
	
	public static ProductTemplate getInstance(String templateName)
	{
		for(ProductTemplate eachTemplate  : ProductTemplate.values())
		{
			if(eachTemplate.getTemplateName().equals(templateName))
			{
				return eachTemplate;
			}
		}
		return null;
	}
	
	private String templateName;
	
	private ProductTemplate(String templateName)
	{
		this.templateName = templateName;
	}

	public String getTemplateName() {
		return templateName;
	}
}
