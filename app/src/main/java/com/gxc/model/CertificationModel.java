package com.gxc.model;

import com.gxc.retrofit.NetModel;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/1615:00
 * @Email zyp@jusfoun.com
 * @Description ${认证企业信息model}
 */
public class CertificationModel extends NetModel {
//    {
//        "result": "0",
//            "msg": "成功",
//            "data": {
//        "companyname":"企业名称",
//                "name":"法人身份证姓名",
//                "job":"职位",
//                "phone":"手机号",
//                "email":"邮箱",
//                "licenseImage":"营业执照.jpg",
//                "idcardImage":"本人身份证.jpg",
//                "status":0, // 0：未认证 1：审核中 2：审核失败 3：审核成功
//    }
//    }


    public String companyname;
    public String name;
    public String phone;
    public String email;
    public String licenseImage;
    public String idcardImage;
    public String status;
    public String idcard;
    public String licenseimgpath;
    public String idcardImgpath;

}
