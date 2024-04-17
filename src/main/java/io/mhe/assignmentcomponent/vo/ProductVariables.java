package io.mhe.assignmentcomponent.vo;

/**
 * Created by IntelliJ IDEA.
 * User: jodie_wu
 * Date: Sep 22, 2006
 * Time: 11:16:05 AM
 * To change this template use File | Settings | File Templates.
 */
public final class ProductVariables {
	
	private ProductVariables() {
	}
	
    //Define brands - need to match the value in SAS databse
    public static final String BRAND_MATHZONE ="MathZone";
    public static final String BRAND_ARIS="ARIS";
    public static final String BRAND_CATALYST ="Catalyst";
    public static final String BRAND_HOMEWORKMANAGER = "HomeworkManager";
    public static final String BRAND_HM="hm_";
    public static final String BRAND_MATHIQ = "MathZoneIQ";
    public static final String BRAND_CLASSWARE = "ClassWare";

    //define quiz engine
    public static final String ENGINE_BERNOULLI ="Bernoulli";
    public static final String ENGINE_ALEKS="ALEKS";
    public static final String ENGINE_EASYTESTONLINE="EZTestOnline";
    public static final String ENGINE_IBIS="IBIS";
    public static final String ENGINE_AUTOGRADER="AutoGrader";

    //define quiz engine or content providers
    public static final String PROVIDER_LSI = "LSI";
    public static final String PROVIDER_SAPLING = "Sapling";
    public static final String PROVIDER_NOVELLA = "novella";
    public static final String PROVIDER_ALEKS = "ALEKS";
    public static final String PROVIDER_EZTEST = "EZTestOnline";
    public static final String PROVIDER_AUTOGRADER="AutoGrader";
    public static final String PROVIDER_EZTEST_FOR_HOMEWORKMANAGER = "EZTestOnline_HM";
    public static final String PROVIDER_CONNECT = "Connect";
    public static final String PROVIDER_TEXTFLOW = "TextFlow";
    public static final String PROVIDER_PLP = "PLP";
    public static final String PROVIDER_LEARNSMART = "LearnSmart";
    public static final String PROVIDER_ALE = "ale";
    
    public static final String PROVIDER_AREA9 = "AREA9";
    
    public static final String PROVIDER_SEALWORKS = "Sealworks";

    public static final String PROVIDER_GENERIC = "Generic";

    //MathIQ Asset Type
    public static final String ASSET_TYPE_OVERVIEW = "Overview";
    public static final String ASSET_TYPE_VIDEO = "Video";
    public static final String ASSET_TYPE_ANIMATEDEXAMPLE = "Animated Example";
    public static final String ASSET_TYPE_FORMULA_DEFINITION = "Formula & Definition";
    public static final String ASSET_TYPE_QUIZZES = "Quizzes";

    public static final String LEARN_SMART = "learnsmart";
}