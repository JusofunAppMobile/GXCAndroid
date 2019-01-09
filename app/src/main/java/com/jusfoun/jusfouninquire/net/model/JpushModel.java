package com.jusfoun.jusfouninquire.net.model;

import java.lang.ref.PhantomReference;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/27.
 * Description 推送信息Model,a title b content c type d companyid e url
 */
public class JpushModel extends BaseModel {

    private String a;
    private String b;
    private String c;
    private String d;
    private String e;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }
}
