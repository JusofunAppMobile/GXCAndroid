package netlib.util;

import android.content.Context;
import android.text.TextUtils;

/**
 * 事件统计工具类
 *
 * @时间 2017/11/16
 * @作者 LiuGuangDan
 */

public class EventUtils {

    public final static String ADVERT22 = "advert22#轮播图HTML页－分享点击数";
    public final static String ADVERT23 = "advert23#启动广告页－左按键点击数";
    public final static String ADVERT24 = "advert24#启动广告页－右按键点击数";
    public final static String ADVERT25 = "advert25#启动广告HTML页－分享点击数";
    public final static String ADVERT26 = "advert26#工资查询宣传页－上按键点击数";
    public final static String ADVERT27 = "advert27#工资查询宣传页－下按键点击数";
    public final static String ADVERTISEMENT01 = "Advertisement01#广告背景点击数";
    public final static String ADVERTISEMENT02 = "Advertisement02#广告页跳过按钮点击数";
    public final static String ADVERTISEMENT03 = "Advertisement03#广告页进入按钮点击数";
    public final static String BUSINESSDETAILS01 = "Businessdetails01#企业详情-纠错点击数";
    public final static String BUSINESSDETAILS02 = "Businessdetails02#企业详情-联系电话点击数";
    public final static String BUSINESSDETAILS03 = "Businessdetails03#企业详情-转发点击数";
    public final static String BUSINESSDETAILS04 = "Businessdetails04#企业详情-下拉刷新数";
    public final static String BUSINESSDETAILS05 = "Businessdetails05#企业详情-关注-点击数";
    public final static String BUSINESSDETAILS06 = "Businessdetails06#企业详情-地图点击数";
    public final static String BUSINESSDETAILS07 = "Businessdetails07#企业详情-工商信息点击数";
    public final static String BUSINESSDETAILS08 = "Businessdetails08#企业详情-企业图谱";
    public final static String BUSINESSDETAILS09 = "Businessdetails09#企业详情-行业分析";
    public final static String BUSINESSDETAILS10 = "Businessdetails10#企业详情-失信信息";
    public final static String BUSINESSDETAILS11 = "Businessdetails11#企业详情-诉讼信息";
    public final static String BUSINESSDETAILS12 = "Businessdetails12#企业详情-对外投资";
    public final static String BUSINESSDETAILS13 = "Businessdetails13#企业详情-股东信息";
    public final static String BUSINESSDETAILS14 = "Businessdetails14#企业详情-企业资讯";
    public final static String BUSINESSDETAILS15 = "Businessdetails15#企业详情-年报信息";
    public final static String BUSINESSDETAILS16 = "Businessdetails16#企业详情-分支机构";
    public final static String BUSINESSDETAILS17 = "Businessdetails17#企业详情-主要成员";
    public final static String BUSINESSDETAILS18 = "Businessdetails18#企业详情-变更记录";
    public final static String BUSINESSDETAILS19 = "Businessdetails19#企业详情-大事记";
    public final static String BUSINESSDETAILS21 = "Businessdetails21#企业详情-行政许可";
    public final static String BUSINESSDETAILS22 = "Businessdetails22#企业详情-风险信息-失信信息";
    public final static String BUSINESSDETAILS23 = "Businessdetails23#企业详情-风险信息-法院信息";
    public final static String BUSINESSDETAILS24 = "Businessdetails24#企业详情-风险信息-法院信息-被执行人";
    public final static String BUSINESSDETAILS25 = "Businessdetails25#企业详情-风险信息-法院信息-失信被执行人";
    public final static String BUSINESSDETAILS26 = "Businessdetails26#企业详情-风险信息-法院信息-裁判文书";
    public final static String BUSINESSDETAILS27 = "Businessdetails27#企业详情-风险信息-法院信息-法院公告";
    public final static String BUSINESSDETAILS28 = "Businessdetails28#企业详情-经营状况-招投标";
    public final static String BUSINESSDETAILS29 = "Businessdetails29#企业详情-经营状况-招聘";
    public final static String BUSINESSDETAILS30 = "Businessdetails30#企业详情-无形资产-专利";
    public final static String BUSINESSDETAILS31 = "Businessdetails31#企业详情-无形资产-商标";
    public final static String BUSINESSDETAILS32 = "Businessdetails32#企业详情-无形资产-软件著作权";
    public final static String BUSINESSDETAILS33 = "Businessdetails33#企业详情-清算信息";
    public final static String DETAIL57 = "Detail57#企业详情页－关注按钮点击数";
    public final static String DETAIL58 = "Detail58#企业详情页－分享按钮点击数";
    public final static String DETAIL59 = "Detail59#企业详情页－刷新按钮点击数";
    public final static String DETAIL60 = "Detail60#企业详情页－地址点击数";
    public final static String DETAIL61 = "Detail61#企业详情页－电话点击数";
    public final static String DETAIL62 = "Detail62#企业详情页－网址点击数";
    public final static String DETAIL63 = "Detail63#企业详情页－工商信息点击数";
    public final static String DETAIL64 = "Detail64#企业详情页－企业图谱点击数";
    public final static String DETAIL65 = "Detail65#企业详情页－行业分析点击数";
    public final static String DETAIL66 = "Detail66#企业详情页－失信信息点击数";
    public final static String DETAIL67 = "Detail67#企业详情页－诉讼信息点击数";
    public final static String DETAIL68 = "Detail68#企业详情页－对外投资点击数";
    public final static String DETAIL69 = "Detail69#企业详情页－股东信息点击数";
    public final static String DETAIL70 = "Detail70#企业详情页－企业资讯点击数";
    public final static String DETAIL71 = "Detail71#企业详情页－年报信息点击数";
    public final static String DETAIL72 = "Detail72#企业详情页－分支机构点击数";
    public final static String DETAIL73 = "Detail73#企业详情页－主要成员点击数";
    public final static String DETAIL74 = "Detail74#企业详情页－变更记录点击数";
    public final static String DETAIL75 = "Detail75#企业详情页－大事记点击数";
    public final static String DETAIL76 = "Detail76#企业详情页－纠错点击数";
    public final static String HELP77 = "Help77#帮助页－常见问题点击数";
    public final static String HELP78 = "Help78#帮助页－意见反馈点击数";
    public final static String HELP79 = "Help79#帮助页－QQ点击数";
    public final static String HELP80 = "Help80#帮助页－QQ群点击数";
    public final static String HELP81 = "Help81#帮助页－邮箱点击数";
    public final static String HELP82 = "Help82#帮助页－电话点击数";
    public final static String HELP83 = "Help83#帮助页－聚信广告点击数";
    public final static String HOME01 = "Home01#首页－名称搜索框点击数";
    public final static String HOME02 = "Home02#首页－主营产品点击数";
    public final static String HOME03 = "Home03#首页－股东高管点击数";
    public final static String HOME04 = "Home04#首页－地址电话点击数";
    public final static String HOME05 = "Home05#首页－企业网址点击数";
    public final static String HOME06 = "Home06#首页－失信查询点击数";
    public final static String HOME07 = "Home07#首页－工资查询点击数";
    public final static String HOME08 = "Home08#首页－轮播图1点击数";
    public final static String HOME09 = "Home09#首页－轮播图2点击数";
    public final static String HOME10 = "Home10#首页－轮播图3点击数";
    public final static String HOME11 = "Home11#首页－轮播图4点击数";
    public final static String HOME12 = "Home12#首页－轮播图5点击数";
    public final static String HOME13 = "Home13#首页－轮播图滑动数";
    public final static String HOME14 = "Home14#首页－热门企业标题点击数";
    public final static String HOME15 = "Home15#首页－热门企业点击数";
    public final static String HOME16 = "Home16#首页－失信榜单标题点击数";
    public final static String HOME17 = "Home17#首页－失信榜单点击数";
    public final static String HOME18 = "Home18#首页－帮助按钮点击数";
    public final static String HOME19 = "Home19#首页－个人按钮点击数";
    public final static String HOME92 = "Home92#首页－热门资讯标题点击数";
    public final static String HOME93 = "Home93#首页－热门资讯点击数";
    public final static String HOME94 = "home94#首页-热门企业标题点击";
    public final static String HOME95 = "home95#首页-新增企业标题点击";
    public final static String HOME96 = "home96#首页-热门企业更多点击";
    public final static String HOME97 = "home97#首页-新增企业更多点击";
    public final static String HOME98 = "home98#首页-新增企业列表点击";
    public final static String HOMEPAGE01 = "Homepage01#首页-企业搜索框点击数";
    public final static String HOMEPAGE02 = "Homepage02#首页-查法人股东点击数";
    public final static String HOMEPAGE03 = "Homepage03#首页-查失信点击数";
    public final static String HOMEPAGE04 = "Homepage04#首页-高级查询点击数";
    public final static String HOMEPAGE05 = "Homepage05#首页-我的关注头";
    public final static String HOMEPAGE06 = "Homepage06#首页-我的关注-列表";
    public final static String HOMEPAGE07 = "Homepage07#首页-热门企业头";
    public final static String HOMEPAGE08 = "Homepage08#首页-热门企业-列表";
    public final static String HOMEPAGE09 = "Homepage09#首页-失信榜单头";
    public final static String HOMEPAGE10 = "Homepage10#首页-失信榜单-列表";
    public final static String HOMEPAGE11 = "Homepage11#首页-数字卡牌";
    public final static String HOMEPAGE12 = "Homepage12#首页-设置按钮点击数";
    public final static String HOMEPAGE13 = "Homepage13#首页-个人中心点击数";
    public final static String HOMEPAGE14 = "Homepage14#首页-滑动次数";
    public final static String HOMEPAGE15 = "Homepage15#首页-背景图点击数字";
    public final static String HOMEPAGE16 = "Homepage16#自定义菜单列表";
    public final static String HOMEPAGE17 = "Homepage17#自定义头部";
    public final static String HOTLIST20 = "Hotlist20#热门企业列表－企业条目点击数";
    public final static String HOTLIST21 = "Hotlist21#失信榜单列表－失信条目点击数";
    public final static String HOTLIST94 = "Hotlist94#热门资讯列表－资讯条目点击数";
    public final static String HOTLIST95 = "Hotlist95#热门资讯HTML页－分享点击数";
    public final static String ME84 = "Me84#个人页－我的关注点击数";
    public final static String ME85 = "Me85#个人页－我的消息点击数";
    public final static String ME86 = "Me86#个人页－修改资料点击数";
    public final static String ME87 = "Me87#个人页－意见反馈点击数";
    public final static String ME88 = "Me88#个人页－推荐点击数";
    public final static String ME89 = "Me89#个人页－推荐－微信点击数";
    public final static String ME90 = "Me90#个人页－推荐－朋友圈点击数";
    public final static String ME91 = "Me91#个人页－推荐－新浪点击数";
    public final static String ME96 = "Me96#个人页－登出账号点击数";
    public final static String NOTICE01 = "Notice01#公告展示数";
    public final static String NOTICE02 = "Notice02#公告关闭点击数";
    public final static String NOTICE03 = "Notice03#公告查看详情点击数";
    public final static String REGISTERED = "registered#注册用户";
    public final static String SEARCH01 = "Search01#搜索页面-右上角搜索贴士按钮";
    public final static String SEARCH02 = "Search02#搜索页面-我的关注-登录按钮";
    public final static String SEARCH03 = "Search03#搜索页面-无搜索记录-查看更多贴士";
    public final static String SEARCH04 = "Search04#搜索页面-搜索区域选择";
    public final static String SEARCH05 = "Search05#搜索页面-提交协助查询";
    public final static String SEARCH06 = "Search06#搜索页面-优化搜索词";
    public final static String SEARCH07 = "Search07#搜索页面-高级检索";
    public final static String SEARCH08 = "Search08#搜索页面-无结果-换个省份按钮";
    public final static String SEARCH09 = "Search09#搜索页面-无结果-多条件按钮";
    public final static String SEARCH10 = "Search10#搜索页面-无结果-优化搜索词";
    public final static String SEARCH11 = "Search11#搜索页面-无结果-提交协助查询";
    public final static String SEARCH12 = "Search12#搜索企业页面-有结果-点击结果";
    public final static String SEARCH13 = "Search13#搜索法人页面-有结果-点击结果";
    public final static String SEARCH14 = "Search14#搜索失信页面-有结果-点击结果";
    public final static String SEARCH15 = "Search15#高级搜索页面-有结果-点击结果";
    public final static String SEARCH16 = "Search16#搜索页面-全网搜索";
    public final static String SEARCH28 = "Search28#失信查询页－有结果－结果点击数";
    public final static String SEARCH29 = "Search29#企业总搜索次数";
    public final static String SEARCH30 = "Search30#企业搜索无结果次数";
    public final static String SEARCH31 = "Search31#名称搜索页－历史搜索点击数";
    public final static String SEARCH32 = "Search32#名称搜索页－热门搜索点击数";
    public final static String SEARCH33 = "Search33#名称搜索页－热词检索点击数";
    public final static String SEARCH34 = "Search34#产品搜索页－历史搜索点击数";
    public final static String SEARCH35 = "Search35#产品搜索页－热门搜索点击数";
    public final static String SEARCH36 = "Search36#产品搜索页－热词检索点击数";
    public final static String SEARCH37 = "Search37#法人搜索页－历史搜索点击数";
    public final static String SEARCH38 = "Search38#法人搜索页－热门搜索点击数";
    public final static String SEARCH39 = "Search39#法人搜索页－热词检索点击数";
    public final static String SEARCH40 = "Search40#地址搜索页－历史搜索点击数";
    public final static String SEARCH41 = "Search41#地址搜索页－热门搜索点击数";
    public final static String SEARCH42 = "Search42#地址搜索页－热词检索点击数";
    public final static String SEARCH43 = "Search43#网址搜索页－历史搜索点击数";
    public final static String SEARCH44 = "Search44#网址搜索页－热门搜索点击数";
    public final static String SEARCH45 = "Search45#网址搜索页－热词检索点击数";
    public final static String SEARCH46 = "Search46#搜索结果页－搜索结果点击数";
    public final static String SEARCH47 = "Search47#搜索结果页－筛选按钮点击数";
    public final static String SEARCH48 = "Search48#搜索结果页－城市筛选点击数";
    public final static String SEARCH49 = "Search49#搜索结果页－省份筛选点击数";
    public final static String SEARCH50 = "Search50#搜索结果页－行业筛选点击数";
    public final static String SEARCH51 = "Search51#搜索结果页－行业筛选点击数";
    public final static String SEARCH52 = "Search52#搜索结果页－注资筛选点击数";
    public final static String SEARCH53 = "Search53#搜索结果页－年限筛选点击数";
    public final static String SEARCH54 = "Search54#搜索结果页－默认排序点击数";
    public final static String SEARCH55 = "Search55#搜索结果页－注资排序点击数";
    public final static String SEARCH56 = "Search56#搜索结果页－时间排序点击数";
    public final static String SETTING01 = "Setting01#设置-更新按钮点击次数";
    public final static String SETTING02 = "Setting02#设置-推荐应用点击次数";
    public final static String SETTING03 = "Setting03#设置-推荐给好友点击次数";
    public final static String SETTING04 = "Setting04#设置-常见问题点击次数";
    public final static String SETTING05 = "Setting05#设置-自定义模块";
    public final static String USER01 = "User01#个人中心-我的关注点击次数";
    public final static String USER02 = "User02#个人中心-系统消息";
    public final static String USER03 = "User03#个人中心-意见反馈";
    public final static String USER04 = "User04#个人中心-自定义模块";

    /**
     * 事件记录
     * @param context
     * @param id 格式为id#标签
     */
    public static void event(Context context, String id) {
//        MobclickAgent.onEvent(context, id.split("#")[0]);
//        StatService.onEvent(context, id.split("#")[0], id.split("#")[1]);
    }

    /**
     * 事件记录
     * @param context
     * @param id 格式为id
     */
    public static void event2(Context context, String id) {
        String value = getId(id); // 格式为id#标签
        if(!TextUtils.isEmpty(value)){
            event(context, value);
        }
    }

    /**
     * 通过反射的方式，根据属性名称获取属性值
     * @param id
     * @return
     */
    private static String getId(String id) {
//        try {
//            Field field = Test.class.getField(id.toUpperCase());
//            return (String) field.get(new EventUtils());
//        } catch (Exception e) {
//            return null;
//        }
        return null;
    }

}
