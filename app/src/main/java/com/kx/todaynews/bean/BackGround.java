package com.kx.todaynews.bean;

/**
 * Created by admin on 2018/9/23.
 */
public class BackGround {

    private VideoBean video;

    public VideoBean getVideo() {
        return video;
    }

    public void setVideo(VideoBean video) {
        this.video = video;
    }

    public static class VideoBean {
        /**
         * covers : http://p3.pstatp.com/origin/pgc-image/15376607763445e438ad4d8
         * id : 19e895f726cb444f80e724002e735dfe
         */

        private String covers;
        private String id;

        public String getCovers() {
            return covers;
        }

        public void setCovers(String covers) {
            this.covers = covers;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
