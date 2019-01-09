package com.jusfoun.bigdata;

import com.jusfoun.jusfouninquire.net.model.BaseModel;

import java.util.List;

/**
 * @author lee
 * @version create time:2015年7月28日_下午5:17:21
 * @Description 1 2 3级地图model
 */

public class NearMapModel extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int total;
	private String totalNum,alreadyShowNum;


	private List<NearMapDataModel> data;
	
	public List<NearMapDataModel> getData() {
		return data;
	}
	public void setData(List<NearMapDataModel> data) {
		this.data = data;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public String getAlreadyShowNum() {
		return alreadyShowNum;
	}

	public void setAlreadyShowNum(String alreadyShowNum) {
		this.alreadyShowNum = alreadyShowNum;
	}

	@Override
	public String toString() {
		return "NearMapModel [total=" + total + ", data=" + data + "]";
	}
	
}
