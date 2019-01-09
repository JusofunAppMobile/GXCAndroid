package com.jusfoun.bigdata;

import com.jusfoun.jusfouninquire.net.model.BaseModel;

/**
 * @author lee
 * @version create time:2015年7月28日_下午5:22:26
 * @Description 地图 1 2 3 级data model
 */

public class NearMapDataModel extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String province;
	private String area;
	private String lng;
	private String lat;
	private int total;
	private String entname;
	private int ent_id;
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getEntname() {
		return entname;
	}
	public void setEntname(String entname) {
		this.entname = entname;
	}
	public int getEnt_id() {
		return ent_id;
	}
	public void setEnt_id(int ent_id) {
		this.ent_id = ent_id;
	}
	
	
}
