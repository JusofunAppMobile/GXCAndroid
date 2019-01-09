package com.jusfoun.bigdata;

import com.jusfoun.jusfouninquire.net.model.BaseModel;

import java.util.List;

/**
 * @author lee
 * @version create time:2015年7月17日_下午2:34:00
 * @Description 列表model
 * 
 */

public class XListModel extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int total;
	private String totalNum;

	private List<XListDataModel> data;

	private String dataversion;

	private String distince;
	private String distinceunit;


	public String getDataversion() {
		return dataversion;
	}

	public void setDataversion(String dataversion) {
		this.dataversion = dataversion;
	}

	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<XListDataModel> getData() {
		return data;
	}
	public void setData(List<XListDataModel> data) {
		this.data = data;
	}

	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public String getDistince() {
		return distince;
	}

	public void setDistince(String distince) {
		this.distince = distince;
	}

	public String getDistinceunit() {
		return distinceunit;
	}

	public void setDistinceunit(String distinceunit) {
		this.distinceunit = distinceunit;
	}

	@Override
	public String toString() {
		return "XListModel{" +
				"total=" + total +
				", data=" + data +
				", dataversion='" + dataversion + '\'' +
				", totalNum='" + totalNum + '\'' +
				", distince='" + distince + '\'' +
				", distinceUnit='" + distinceunit + '\'' +
				'}';
	}
}
