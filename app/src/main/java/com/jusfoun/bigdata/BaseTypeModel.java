package com.jusfoun.bigdata;

import com.jusfoun.jusfouninquire.net.model.BaseModel;

/**
 * @author henzil
 * @version create time:2014-3-14_上午10:33:43
 * @Description 所有分类的 基类
 */
public class BaseTypeModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 要拼接的参数
	 */
	private String params;
	private String title;
	private boolean isSelected;

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	@Override
	public String toString() {
		return "BaseTypeModel [id=" + id + ", params=" + params + ", title="
				+ title + ", isSelected=" + isSelected + "]";
	}

	
	
	
}