package com.jusfoun.bigdata;

import com.jusfoun.jusfouninquire.net.model.BaseModel;

import java.util.List;

/**
 * @author lee
 * @version create time:2015年7月29日_下午5:29:44
 * @Description 行业外层model
 */

public class DataIndustryModel extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<IndustryModel> datalist;
	public List<IndustryModel> getDatalist() {
		return datalist;
	}
	public void setDatalist(List<IndustryModel> datalist) {
		this.datalist = datalist;
	}
	@Override
	public String toString() {
		return "ObjectIndustryModel [datalist=" + datalist + "]";
	}
	
}
