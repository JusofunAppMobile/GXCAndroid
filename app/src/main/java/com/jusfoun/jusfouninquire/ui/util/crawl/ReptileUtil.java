package com.jusfoun.jusfouninquire.ui.util.crawl;

/**
 * @author zhaoyapeng
 * @version create time:17/7/3116:01
 * @Email zyp@jusfoun.com
 * @Description ${}
 */
public class ReptileUtil {
    private   ReptileRequestQueue reptileRequestQueue;
    private static ReptileUtil reptileUtil;
    private ReptileUtil(){
        reptileRequestQueue = new ReptileRequestQueue();
        reptileRequestQueue.start();
    }

    public static ReptileUtil getInatance() {
      if(reptileUtil==null){
          reptileUtil = new ReptileUtil();
      }
      return  reptileUtil;
    }

    public void add(BaseReptileRequest reptileRequest){
        reptileRequestQueue.add(reptileRequest);
    }

    public void reSet(){
        reptileRequestQueue.reSet();
    }
}
