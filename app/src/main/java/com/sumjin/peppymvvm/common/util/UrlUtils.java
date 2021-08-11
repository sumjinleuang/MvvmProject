package com.sumjin.peppymvvm.common.util;

public class UrlUtils {

    public static String createCirclePagerUrl(String username, int page) {
        //?username=test1&page=1
        if (username.length()>0){
            return "DynamicInfoList?username="+username+"&page="+page;
        }
        return "DynamicInfoList?page="+page;
    }

    public static String getOnSellPageUrl(int page) {
        return "onSell/"+page;
    }

    public static String getCoverPath(String pict_url) {
        if (pict_url.startsWith("http") || pict_url.startsWith("https")) {
            return pict_url;
        } else {
            return "https:" + pict_url;
        }
    }
}
