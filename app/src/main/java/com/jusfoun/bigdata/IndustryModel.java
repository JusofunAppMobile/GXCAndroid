package com.jusfoun.bigdata;

import com.jusfoun.jusfouninquire.net.model.BaseModel;

/**
 * @author lee
 * @version create time:2015年7月29日_下午5:18:26
 * @Description 行业的model
 */

public class IndustryModel extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String CountryClassificationCode;
	private String CountryClassName;
	private String PCode;
	private String Level;
	private String KW;
	private int ifExists;
	private boolean isSelect;

	
	public IndustryModel(String countryClassificationCode,
                         String countryClassName, String pCode, String level, String kW,
                         int ifExists) {
		super();
		CountryClassificationCode = countryClassificationCode;
		CountryClassName = countryClassName;
		PCode = pCode;
		Level = level;
		KW = kW;
		this.ifExists = ifExists;
	}

	public IndustryModel(){

	}

	public int getIfExists() {
		return ifExists;
	}
	public void setIfExists(int ifExists) {
		this.ifExists = ifExists;
	}
	public String getCountryClassificationCode() {
		return CountryClassificationCode;
	}
	public void setCountryClassificationCode(String countryClassificationCode) {
		CountryClassificationCode = countryClassificationCode;
	}

	public boolean isSelect() {
		return isSelect;
	}

	public void setIsSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}

	public String getCountryClassName() {
		return CountryClassName;
	}
	public void setCountryClassName(String countryClassName) {
		CountryClassName = countryClassName;
	}
	public String getPCode() {
		return PCode;
	}
	public void setPCode(String pCode) {
		PCode = pCode;
	}
	public String getLevel() {
		return Level;
	}
	public void setLevel(String level) {
		Level = level;
	}
	public String getKW() {
		return KW;
	}
	public void setKW(String kW) {
		KW = kW;
	}
	@Override
	public String toString() {
		return "IndustryModel [CountryClassificationCode="
				+ CountryClassificationCode + ", CountryClassName="
				+ CountryClassName + ", PCode=" + PCode + ", Level=" + Level
				+ ", KW=" + KW + ", ifExists=" + ifExists + "]";
	}
	
	
}
