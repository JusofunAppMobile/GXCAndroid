package com.jusfoun.bigdata;

import com.jusfoun.jusfouninquire.net.model.BaseModel;

public class FavoriterUpdateResultModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String favoriteid;

	public String getFavoriteid() {
		return favoriteid;
	}

	public void setFavoriteid(String favoriteid) {
		this.favoriteid = favoriteid;
	}

	@Override
	public String toString() {
		return "FavoriterUpdateResultModel{" +
				"favoriteid='" + favoriteid + '\'' +
				'}';
	}
}
