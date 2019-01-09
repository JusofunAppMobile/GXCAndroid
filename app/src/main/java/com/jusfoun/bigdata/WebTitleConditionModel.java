package com.jusfoun.bigdata;

import com.jusfoun.jusfouninquire.net.model.BaseModel;

/**
 * Created by JUSFOUN on 2015/9/10. Description
 */
public class WebTitleConditionModel extends BaseModel {

	private int ID;
	private String MenuName;
	private int PID;
	private String AppLinkUrl;
	private boolean isSelected = false;
	private int postionId;
	private int OID;
	private int Type;
	private String T;
	private int index;

	private int imageRes;
	private int noDataImageRes;

	private String imageResName;
	private String noDataImageResName;

	private int hasNumType;

	public int getHasNumType() {
		return hasNumType;
	}

	public void setHasNumType(int hasNumType) {
		this.hasNumType = hasNumType;
	}

	public String getImageResName() {
		return imageResName;
	}

	public void setImageResName(String imageResName) {
		this.imageResName = imageResName;
	}

	public String getNoDataImageResName() {
		return noDataImageResName;
	}

	public void setNoDataImageResName(String noDataImageResName) {
		this.noDataImageResName = noDataImageResName;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getT() {
		return T;
	}

	public void setT(String t) {
		T = t;
	}

	public int getNoDataImageRes() {
		return noDataImageRes;
	}

	public void setNoDataImageRes(int noDataImageRes) {
		this.noDataImageRes = noDataImageRes;
	}

	public int getImageRes() {
		return imageRes;
	}

	public void setImageRes(int imageRes) {
		this.imageRes = imageRes;
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}

	public int getPostionId() {
		return postionId;
	}

	public int getOID() {
		return OID;
	}

	public void setOID(int OID) {
		this.OID = OID;
	}

	public void setPostionId(int postionId) {
		this.postionId = postionId;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getMenuName() {
		return MenuName;
	}

	public void setMenuName(String menuName) {
		MenuName = menuName;
	}

	public int getPID() {
		return PID;
	}

	public void setPID(int PID) {
		this.PID = PID;
	}

	public String getAppLinkUrl() {
		return AppLinkUrl;
	}

	public void setAppLinkUrl(String appLinkUrl) {
		AppLinkUrl = appLinkUrl;
	}
}
