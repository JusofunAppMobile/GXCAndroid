package com.jusfoun.jusfouninquire.net.model;

import java.io.Serializable;

/**
 * @author zhaoyapeng
 * @version create time:17/11/811:41
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class TaxationItemModel extends BaseModel implements Serializable{


//     "id": "0",
//             "entName": "永州市零陵区鑫发车行",
//             "entType": "",
//             "taxCode": "432901197506053000",
//             "legal": "王中亮",
//             "url": "XXXXXX",//详情页链接
//             "idType": "",
//             "idNum": "432901197506053000",
//             "address": "湖南省永州市零陵区南津南路105号-103号",
//             "category": "",
//             "balance": "3625715.97",
//             "currentBalance": null,
//             "confirmDate": null,
//             "createTime": "1497801600000",
//             "updateTime": "1497801600000",
//             "dataStatus": null,
//             "src": null

    public String id;
    public String entName;
    public String entType;
    public String taxCode;
    public String legal;
    public String url;
    public String idType;
    public String idNum;
    public String address;
    public String category;
    public String balance;
    public String currentBalance;
    public String confirmDate;
    public String createTime;
    public String updateTime;
    public String dataStatus;
    public String src;



    public String identifierid;
    public String companyid;
    public String companyname;

}
