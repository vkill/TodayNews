package com.kx.todaynews.bean;

import java.util.List;

/**
 * Created by admin on 2018/9/19.
 */
public class MiddleImage {

    /**
     * height : 202
     * uri : list/pgc-image/15373410870720e328fac7a
     * url : http://p9.pstatp.com/list/300x196/pgc-image/15373410870720e328fac7a.webp
     * url_list : [{"url":"http://p9.pstatp.com/list/300x196/pgc-image/15373410870720e328fac7a.webp"},{"url":"http://pb1.pstatp.com/list/300x196/pgc-image/15373410870720e328fac7a.webp"},{"url":"http://pb3.pstatp.com/list/300x196/pgc-image/15373410870720e328fac7a.webp"}]
     * width : 360
     */

    private int height;
    private String uri;
    private String url;
    private int width;
    private List<UrlListBean> url_list;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public List<UrlListBean> getUrl_list() {
        return url_list;
    }

    public void setUrl_list(List<UrlListBean> url_list) {
        this.url_list = url_list;
    }

    public static class UrlListBean {
        /**
         * url : http://p9.pstatp.com/list/300x196/pgc-image/15373410870720e328fac7a.webp
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
