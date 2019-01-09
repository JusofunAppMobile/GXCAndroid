package com.jusfoun.bigdata;

import com.jusfoun.jusfouninquire.net.model.BaseModel;

/**
 * @author henzil
 * @version create time:2015-7-22_上午9:43:53
 * @Description 用来存储类别选择中 更多选择的返回结果
 */
public class TypeMoreModel extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 创始人
	private String founder;

	// 融资
	private int financing;

	// 并购
	private int merger;

	// 上市
	private int onTheMarket;

	private String oneindustry, twoindustry, threeindustry, fourindustry;

	private String oneindustryname, twoindustryname, threeindustryname, fourindustryname;

	// 资产规模
	private String assetSize;
	private String assetValue;

	public String getFounder() {
		return founder;
	}

	public void setFounder(String founder) {
		this.founder = founder;
	}

	public int getFinancing() {
		return financing;
	}

	public void setFinancing(int financing) {
		this.financing = financing;
	}

	public int getMerger() {
		return merger;
	}

	public void setMerger(int merger) {
		this.merger = merger;
	}

	public int getOnTheMarket() {
		return onTheMarket;
	}

	public void setOnTheMarket(int onTheMarket) {
		this.onTheMarket = onTheMarket;
	}

	public String getOneindustry() {
		return oneindustry;
	}

	public void setOneindustry(String oneindustry) {
		this.oneindustry = oneindustry;
	}

	public String getTwoindustry() {
		return twoindustry;
	}

	public void setTwoindustry(String twoindustry) {
		this.twoindustry = twoindustry;
	}

	public String getThreeindustry() {
		return threeindustry;
	}

	public void setThreeindustry(String threeindustry) {
		this.threeindustry = threeindustry;
	}

	public String getFourindustry() {
		return fourindustry;
	}

	public void setFourindustry(String fourindustry) {
		this.fourindustry = fourindustry;
	}

	public String getOneindustryname() {
		return oneindustryname;
	}

	public void setOneindustryname(String oneindustryname) {
		this.oneindustryname = oneindustryname;
	}

	public String getTwoindustryname() {
		return twoindustryname;
	}

	public void setTwoindustryname(String twoindustryname) {
		this.twoindustryname = twoindustryname;
	}

	public String getThreeindustryname() {
		return threeindustryname;
	}

	public void setThreeindustryname(String threeindustryname) {
		this.threeindustryname = threeindustryname;
	}

	public String getFourindustryname() {
		return fourindustryname;
	}

	public void setFourindustryname(String fourindustryname) {
		this.fourindustryname = fourindustryname;
	}

	public String getAssetSize() {
		return assetSize;
	}

	public void setAssetSize(String assetSize) {
		this.assetSize = assetSize;
	}
	
	public String getAssetValue() {
		return assetValue;
	}

	public void setAssetValue(String assetValue) {
		this.assetValue = assetValue;
	}

	@Override
	public String toString() {
		return "TypeMoreModel [founder=" + founder + ", financing=" + financing
				+ ", merger=" + merger + ", onTheMarket=" + onTheMarket
				+ ", oneindustry=" + oneindustry + ", twoindustry="
				+ twoindustry + ", threeindustry=" + threeindustry
				+ ", fourindustry=" + fourindustry + ", oneindustryname="
				+ oneindustryname + ", twoindustryname=" + twoindustryname
				+ ", threeindustryname=" + threeindustryname
				+ ", fourindustryname=" + fourindustryname + ", assetSize="
				+ assetSize + ", assetValue=" + assetValue + "]";
	}

	public void reset() {
		// TODO 重置
		
		
	}

}
