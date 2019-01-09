package com.jusfoun.jusfouninquire.net.model;

import java.io.Serializable;
import java.util.List;

/**
 * 获取问答列表
 *
 * @时间 2017/11/8
 * @作者 LiuGuangDan
 */

public class QuestResultModel extends BaseModel implements Serializable {


    public List<QuestionlistBean> questionlist;

    public static class QuestionlistBean {
        /**
         * title : 你的问题得到解决了么？
         * questionid : 1
         * answerlist : [{"answerid":"1","content":"解决了"},{"answerid":"2","content":"未解决了"}]
         */

        public String title;
        public String questionid;
        public List<AnswerlistBean> answerlist;

        public static class AnswerlistBean {
            /**
             * answerid : 1
             * content : 解决了
             */

            public String answerid;
            public String content;
        }
    }
}