package com.jusfoun.jusfouninquire.net.update;


import com.jusfoun.jusfouninquire.net.model.BaseModel;

/**
 * @author lee
 * @version create time:2015年4月21日_上午11:10:27
 * @Description TODO
 */
public class VersionNumModel extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int update;
	private String describe;
	private String url;
	private int filter;
	private String versionname;
	private String titletext;
	private String cacletext;
	private String updatetext;
	
	public VersionNumModel() {
	}

	public int getUpdate() {
		return update;
	}

	public void setUpdate(int update) {
		this.update = update;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getFilter() {
		return filter;
	}

	public void setFilter(int filter) {
		this.filter = filter;
	}

	public String getTitletext() {
		return titletext;
	}

	public void setTitletext(String titletext) {
		this.titletext = titletext;
	}

	public String getCacletext() {
		return cacletext;
	}

	public void setCacletext(String cacletext) {
		this.cacletext = cacletext;
	}

	public String getUpdatetext() {
		return updatetext;
	}

	public void setUpdatetext(String updatetext) {
		this.updatetext = updatetext;
	}


	public String getVersionname() {
		return versionname;
	}

	public void setVersionname(String versionname) {
		this.versionname = versionname;
	}

	public VersionNumModel(int update, String describe, String url, int filter) {
		super();
		this.update = update;
		this.describe = describe;
		this.url = url;
		this.filter = filter;

	}

	@Override
	public String toString() {
		return "VersionNumModel [update=" + update + ", describe=" + describe + ", url=" + url + ", filter=" + filter
				+ ", versionname=" + versionname + ", titletext=" + titletext + ", cacletext=" + cacletext
				+ ", updatetext=" + updatetext + "]";
	}
	
	
}
