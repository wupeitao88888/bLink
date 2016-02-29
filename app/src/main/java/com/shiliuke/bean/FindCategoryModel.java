package com.shiliuke.bean;

import java.util.ArrayList;

/**
 * Created by wangzhi on 15/12/23.
 */
public class FindCategoryModel extends BaseModel {

    private ArrayList<FindCategoryModelResult> datas;

    public ArrayList<FindCategoryModelResult> getDatas() {
        return datas;
    }

    public class FindCategoryModelResult {
        private String class_id;
        private String class_name;

        public String getClass_name() {
            return class_name;
        }
    }

}
