package com.jusfoun.bigdata;

import com.jusfoun.jusfouninquire.net.model.BaseModel;

/**
 * @author lee
 * @version create time:2015年7月17日_下午2:34:37
 * @Description 列表model 中的array
 */

public class XListDataModel extends BaseModel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

//    private String ent_id;
    private String qiyexingzhi;
//    private String entname;
    private String zhuyinghangye, zichanzongji, yingyeshouru;

    private String mapLat;
    private String mapLng;
    private String area;
    private String kw, createdate;
    private String distince, distinceunit;
//    private String legalperson;

    private String id;

    private String legalPerson;

    private String name;
    private String registerStatus;
    public String money;
    public String distance;

    public String getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(String registerStatus) {
        this.registerStatus = registerStatus;
    }

    public String getLegalperson() {
        return legalPerson;
    }

    public void setLegalperson(String legalperson) {
        this.legalPerson = legalperson;
    }

    public String getEnt_id() {
        return id;
    }

    private String zichanzongjiunit, yingyeshouruunit;

    public void setEnt_id(String ent_id) {
        this.id = ent_id;
    }

    public String getQiyexingzhi() {
        return qiyexingzhi;
    }

    public void setQiyexingzhi(String qiyexingzhi) {
        this.qiyexingzhi = qiyexingzhi;
    }

    public String getEntname() {
        return name;
    }

    public void setEntname(String entname) {
        this.name = entname;
    }

    public String getZhuyinghangye() {
        return zhuyinghangye;
    }

    public void setZhuyinghangye(String zhuyinghangye) {
        this.zhuyinghangye = zhuyinghangye;
    }

    public String getZichanzongji() {
        return zichanzongji;
    }

    public void setZichanzongji(String zichanzongji) {
        this.zichanzongji = zichanzongji;
    }

    public String getMap_lat() {
        return mapLat;
    }

    public void setMap_lat(String map_lat) {
        this.mapLat = map_lat;
    }

    public String getMap_lng() {
        return mapLng;
    }

    public void setMap_lng(String map_lng) {
        this.mapLng = map_lng;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getYingyeshouru() {
        return yingyeshouru;
    }

    public void setYingyeshouru(String yingyeshouru) {
        this.yingyeshouru = yingyeshouru;
    }

    public String getZichanzongjiunit() {
        return zichanzongjiunit;
    }

    public void setZichanzongjiunit(String zichanzongjiunit) {
        this.zichanzongjiunit = zichanzongjiunit;
    }

    public String getYingyeshouruunit() {
        return yingyeshouruunit;
    }

    public void setYingyeshouruunit(String yingyeshouruunit) {
        this.yingyeshouruunit = yingyeshouruunit;
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

    public XListDataModel(String ent_id) {
        this.id = ent_id;
    }

    @Override
    public String toString() {
        return "XListDataModel{" +
                "ent_id='" + id + '\'' +
                ", qiyexingzhi='" + qiyexingzhi + '\'' +
                ", entname='" + name + '\'' +
                ", zhuyinghangye='" + zhuyinghangye + '\'' +
                ", zichanzongji='" + zichanzongji + '\'' +
                ", yingyeshouru='" + yingyeshouru + '\'' +
                ", map_lat='" + mapLat + '\'' +
                ", map_lng='" + mapLng + '\'' +
                ", area='" + area + '\'' +
                ", kw='" + kw + '\'' +
                ", createdate='" + createdate + '\'' +
                ", distince='" + distince + '\'' +
                ", distinceunit='" + distinceunit + '\'' +
                ", zichanzongjiunit='" + zichanzongjiunit + '\'' +
                ", yingyeshouruunit='" + yingyeshouruunit + '\'' +
                '}';
    }
}
