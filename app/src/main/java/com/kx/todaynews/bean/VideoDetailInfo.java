package com.kx.todaynews.bean;

import java.util.List;

/**
 * Created by admin on 2018/9/19.
 */
public class VideoDetailInfo {

    /**
     * detail_video_large_image : {"height":326,"uri":"video1609/pgc-image/15373410870720e328fac7a","url":"http://p9.pstatp.com/video1609/pgc-image/15373410870720e328fac7a","url_list":[{"url":"http://p9.pstatp.com/video1609/pgc-image/15373410870720e328fac7a"},{"url":"http://pb1.pstatp.com/video1609/pgc-image/15373410870720e328fac7a"},{"url":"http://pb3.pstatp.com/video1609/pgc-image/15373410870720e328fac7a"}],"width":580}
     * direct_play : 1
     * group_flags : 32832
     * show_pgc_subscribe : 1
     * video_id : v020049b0000begvccl8n75ngioq81d0
     * video_preloading_flag : 1
     * video_type : 0
     * video_watch_count : 5055182
     * video_watching_count : 0
     */

    private DetailVideoLargeImageBean detail_video_large_image;
    private int direct_play;
    private int group_flags;
    private int show_pgc_subscribe;
    private String video_id;
    private int video_preloading_flag;
    private int video_type;
    private int video_watch_count;
    private int video_watching_count;

    public DetailVideoLargeImageBean getDetail_video_large_image() {
        return detail_video_large_image;
    }

    public void setDetail_video_large_image(DetailVideoLargeImageBean detail_video_large_image) {
        this.detail_video_large_image = detail_video_large_image;
    }

    public int getDirect_play() {
        return direct_play;
    }

    public void setDirect_play(int direct_play) {
        this.direct_play = direct_play;
    }

    public int getGroup_flags() {
        return group_flags;
    }

    public void setGroup_flags(int group_flags) {
        this.group_flags = group_flags;
    }

    public int getShow_pgc_subscribe() {
        return show_pgc_subscribe;
    }

    public void setShow_pgc_subscribe(int show_pgc_subscribe) {
        this.show_pgc_subscribe = show_pgc_subscribe;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public int getVideo_preloading_flag() {
        return video_preloading_flag;
    }

    public void setVideo_preloading_flag(int video_preloading_flag) {
        this.video_preloading_flag = video_preloading_flag;
    }

    public int getVideo_type() {
        return video_type;
    }

    public void setVideo_type(int video_type) {
        this.video_type = video_type;
    }

    public int getVideo_watch_count() {
        return video_watch_count;
    }

    public void setVideo_watch_count(int video_watch_count) {
        this.video_watch_count = video_watch_count;
    }

    public int getVideo_watching_count() {
        return video_watching_count;
    }

    public void setVideo_watching_count(int video_watching_count) {
        this.video_watching_count = video_watching_count;
    }

    public static class DetailVideoLargeImageBean {
        /**
         * height : 326
         * uri : video1609/pgc-image/15373410870720e328fac7a
         * url : http://p9.pstatp.com/video1609/pgc-image/15373410870720e328fac7a
         * url_list : [{"url":"http://p9.pstatp.com/video1609/pgc-image/15373410870720e328fac7a"},{"url":"http://pb1.pstatp.com/video1609/pgc-image/15373410870720e328fac7a"},{"url":"http://pb3.pstatp.com/video1609/pgc-image/15373410870720e328fac7a"}]
         * width : 580
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
             * url : http://p9.pstatp.com/video1609/pgc-image/15373410870720e328fac7a
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
}
