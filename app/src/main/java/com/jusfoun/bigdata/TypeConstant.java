package com.jusfoun.bigdata;
/**
 * @author henzil
 * @version create time:2015-7-20_下午5:20:32
 * @Description  类别 常量类
 */
public interface TypeConstant {

	String TYPE_BUT_TEXT = "typeButText";
	String TYPE_BUT_IMAGE = "typeButImage";

	int TYPE_BUTTON_SEARCH=0;
	int TYPE_LIST_MORE_SELECT=1;
	int TYPE_OTHER=2;
	int TYPE_LIST_SINGEL_SELECT=3;


	int KEY_TYPEBUTTON = 0;
	int FILTER_TYPEBUTTON = 1;
	int SORT_TYPEBUTTON = 2;

	/**行业*/
	int INDUSTRY_INTENT_RESULT_CODE = 1001;
	/**地区*/
	int AREA_INTENT_RESULT_CODE = 1002;
	/**营收*/
	int INCOME_INTENT_RESULT_CODE = 1003;
	/**资产*/
	int ASSETS_INTENT_RESULT_CODE = 1004;
	/**上市板块*/
	int SHANGSHI_BLOCK_INTENT_RESULT_CODE = 1005;
	/**上市所属阶段*/
	int SHANGSHI_JIEDUAN_INTENT_RESULT_CODE = 1006;
	/**拟上市板块*/
	int NISHANGSHI_BLOCK_INTENT_RESULT_CODE = 1007;
	/**融资需求*/
	int FINANCE_NEEDS_INTENT_RESULT_CODE = 1008;
	/**融资轮次*/
	int FINANCEROUNDS_INTENT_RESULT_CODE = 1009;
	/**注册日期*/
	int REGISTER_DATE_INTENT_RESULT_CODE = 1010;
	/**人员规模*/
	int PERSONSCOPE_INTENT_RESULT_CODE = 1011;
	/**处罚时间*/
	int PUNISH_TIME_INTENT_RESULT_CODE = 1012;
	/**处罚类型*/
	int PUNISH_TYPE_INTENT_RESULT_CODE = 1013;
	/**营业状态*/
	int DOBUSINESS_STATUS_INTENT_RESULT_CODE = 1014;
	/**注销时间*/
	int UNSUBSCRIBE_TIME_INTENT_RESULT_CODE = 1015;
}
