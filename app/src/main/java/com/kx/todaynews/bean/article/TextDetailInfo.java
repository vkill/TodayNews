package com.kx.todaynews.bean.article;

import java.util.List;

/**
 * 新闻详情的Bean
 */
public class TextDetailInfo {

    private DataBean data;
    private String message;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {

        private int aggr_type;
        private String content;
        private long group_id;
        private int group_source;
        private H5ExtraBean h5_extra;
        private int is_wenda;
        private long item_id;
        private long media_user_id;
        private List<ImageDetailBean> image_detail;
        private List<ThumbImageBean> thumb_image;
        private List<WebpImageDetailBean> webp_image_detail;
        private List<WebpThumbImageBean> webp_thumb_image;

        public int getAggr_type() {
            return aggr_type;
        }

        public void setAggr_type(int aggr_type) {
            this.aggr_type = aggr_type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getGroup_id() {
            return group_id;
        }

        public void setGroup_id(long group_id) {
            this.group_id = group_id;
        }

        public int getGroup_source() {
            return group_source;
        }

        public void setGroup_source(int group_source) {
            this.group_source = group_source;
        }

        public H5ExtraBean getH5_extra() {
            return h5_extra;
        }

        public void setH5_extra(H5ExtraBean h5_extra) {
            this.h5_extra = h5_extra;
        }

        public int getIs_wenda() {
            return is_wenda;
        }

        public void setIs_wenda(int is_wenda) {
            this.is_wenda = is_wenda;
        }

        public long getItem_id() {
            return item_id;
        }

        public void setItem_id(long item_id) {
            this.item_id = item_id;
        }

        public long getMedia_user_id() {
            return media_user_id;
        }

        public void setMedia_user_id(long media_user_id) {
            this.media_user_id = media_user_id;
        }

        public List<ImageDetailBean> getImage_detail() {
            return image_detail;
        }

        public void setImage_detail(List<ImageDetailBean> image_detail) {
            this.image_detail = image_detail;
        }

        public List<ThumbImageBean> getThumb_image() {
            return thumb_image;
        }

        public void setThumb_image(List<ThumbImageBean> thumb_image) {
            this.thumb_image = thumb_image;
        }

        public List<WebpImageDetailBean> getWebp_image_detail() {
            return webp_image_detail;
        }

        public void setWebp_image_detail(List<WebpImageDetailBean> webp_image_detail) {
            this.webp_image_detail = webp_image_detail;
        }

        public List<WebpThumbImageBean> getWebp_thumb_image() {
            return webp_thumb_image;
        }

        public void setWebp_thumb_image(List<WebpThumbImageBean> webp_thumb_image) {
            this.webp_thumb_image = webp_thumb_image;
        }

        public static class H5ExtraBean {
            /**
             * high_quality_flag :
             * is_original : false
             * media : {"auth_info":"人民日报官方账号","avatar_url":"http://p99.pstatp.com/large/b725000102bd7072f33f","can_be_praised":false,"car_author":0,"creator_id":104246645773,"description":"参与、沟通、记录时代。","id":1610946690248711,"master_media_info":"","name":"人民日报","pgc_custom_menu":"","praise_data":{},"show_pgc_component":true,"user_auth_info":"","user_verified":true,"v":false}
             * media_user_id : 104246645773
             * publish_stamp : 1537432120
             * publish_time : 09-20 16:28
             * source : 人民日报
             * src_link : http://mp.weixin.qq.com/s?src=11&timestamp=1537432117&ver=1133&signature=rr2M-ICw*8sB2tAwL51ZlGE8rr6EzDtv0NU*eKt8fQC4K0elrkNyxtSwPvd4Y*WdRTGWkusgD8TwaMdGxt6maNycHeHgLSKO0rz0JH9VJ-JKeAwRgqc8VOgXT7T4mZAV&new=1
             * str_group_id : 6603220675295445512
             * str_item_id : 6603220675295445512
             * title : 中国游客瑞典受辱事件剧情“反转”的背后，究竟藏着什么？
             */

            private String high_quality_flag;
            private boolean is_original;
            private MediaBean media;
            private String media_user_id;
            private String publish_stamp;
            private String publish_time;
            private String source;
            private String src_link;
            private String str_group_id;
            private String str_item_id;
            private String title;

            public String getHigh_quality_flag() {
                return high_quality_flag;
            }

            public void setHigh_quality_flag(String high_quality_flag) {
                this.high_quality_flag = high_quality_flag;
            }

            public boolean isIs_original() {
                return is_original;
            }

            public void setIs_original(boolean is_original) {
                this.is_original = is_original;
            }

            public MediaBean getMedia() {
                return media;
            }

            public void setMedia(MediaBean media) {
                this.media = media;
            }

            public String getMedia_user_id() {
                return media_user_id;
            }

            public void setMedia_user_id(String media_user_id) {
                this.media_user_id = media_user_id;
            }

            public String getPublish_stamp() {
                return publish_stamp;
            }

            public void setPublish_stamp(String publish_stamp) {
                this.publish_stamp = publish_stamp;
            }

            public String getPublish_time() {
                return publish_time;
            }

            public void setPublish_time(String publish_time) {
                this.publish_time = publish_time;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getSrc_link() {
                return src_link;
            }

            public void setSrc_link(String src_link) {
                this.src_link = src_link;
            }

            public String getStr_group_id() {
                return str_group_id;
            }

            public void setStr_group_id(String str_group_id) {
                this.str_group_id = str_group_id;
            }

            public String getStr_item_id() {
                return str_item_id;
            }

            public void setStr_item_id(String str_item_id) {
                this.str_item_id = str_item_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public static class MediaBean {
                /**
                 * auth_info : 人民日报官方账号
                 * avatar_url : http://p99.pstatp.com/large/b725000102bd7072f33f
                 * can_be_praised : false
                 * car_author : 0
                 * creator_id : 104246645773
                 * description : 参与、沟通、记录时代。
                 * id : 1610946690248711
                 * master_media_info :
                 * name : 人民日报
                 * pgc_custom_menu :
                 * praise_data : {}
                 * show_pgc_component : true
                 * user_auth_info :
                 * user_verified : true
                 * v : false
                 */

                private String auth_info;
                private String avatar_url;
                private boolean can_be_praised;
                private int car_author;
                private long creator_id;
                private String description;
                private long id;
                private String master_media_info;
                private String name;
                private String pgc_custom_menu;
                private PraiseDataBean praise_data;
                private boolean show_pgc_component;
                private String user_auth_info;
                private boolean user_verified;
                private boolean v;

                public String getAuth_info() {
                    return auth_info;
                }

                public void setAuth_info(String auth_info) {
                    this.auth_info = auth_info;
                }

                public String getAvatar_url() {
                    return avatar_url;
                }

                public void setAvatar_url(String avatar_url) {
                    this.avatar_url = avatar_url;
                }

                public boolean isCan_be_praised() {
                    return can_be_praised;
                }

                public void setCan_be_praised(boolean can_be_praised) {
                    this.can_be_praised = can_be_praised;
                }

                public int getCar_author() {
                    return car_author;
                }

                public void setCar_author(int car_author) {
                    this.car_author = car_author;
                }

                public long getCreator_id() {
                    return creator_id;
                }

                public void setCreator_id(long creator_id) {
                    this.creator_id = creator_id;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public String getMaster_media_info() {
                    return master_media_info;
                }

                public void setMaster_media_info(String master_media_info) {
                    this.master_media_info = master_media_info;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getPgc_custom_menu() {
                    return pgc_custom_menu;
                }

                public void setPgc_custom_menu(String pgc_custom_menu) {
                    this.pgc_custom_menu = pgc_custom_menu;
                }

                public PraiseDataBean getPraise_data() {
                    return praise_data;
                }

                public void setPraise_data(PraiseDataBean praise_data) {
                    this.praise_data = praise_data;
                }

                public boolean isShow_pgc_component() {
                    return show_pgc_component;
                }

                public void setShow_pgc_component(boolean show_pgc_component) {
                    this.show_pgc_component = show_pgc_component;
                }

                public String getUser_auth_info() {
                    return user_auth_info;
                }

                public void setUser_auth_info(String user_auth_info) {
                    this.user_auth_info = user_auth_info;
                }

                public boolean isUser_verified() {
                    return user_verified;
                }

                public void setUser_verified(boolean user_verified) {
                    this.user_verified = user_verified;
                }

                public boolean isV() {
                    return v;
                }

                public void setV(boolean v) {
                    this.v = v;
                }

                public static class PraiseDataBean {
                }
            }
        }

        public static class ImageDetailBean {
            /**
             * height : 502
             * uri : large/pgc-image/15371811696145d7a14a977
             * url : http://p3.pstatp.com/large/pgc-image/15371811696145d7a14a977
             * url_list : [{"url":"http://p3.pstatp.com/large/pgc-image/15371811696145d7a14a977"},{"url":"http://pb9.pstatp.com/large/pgc-image/15371811696145d7a14a977"},{"url":"http://pb1.pstatp.com/large/pgc-image/15371811696145d7a14a977"}]
             * width : 800
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
                 * url : http://p3.pstatp.com/large/pgc-image/15371811696145d7a14a977
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

        public static class ThumbImageBean {
            /**
             * height : 502
             * uri : thumb/pgc-image/15371811696145d7a14a977
             * url : http://p3.pstatp.com/thumb/pgc-image/15371811696145d7a14a977
             * url_list : [{"url":"http://p3.pstatp.com/thumb/pgc-image/15371811696145d7a14a977"},{"url":"http://pb9.pstatp.com/thumb/pgc-image/15371811696145d7a14a977"},{"url":"http://pb1.pstatp.com/thumb/pgc-image/15371811696145d7a14a977"}]
             * width : 800
             */

            private int height;
            private String uri;
            private String url;
            private int width;
            private List<UrlListBeanX> url_list;

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

            public List<UrlListBeanX> getUrl_list() {
                return url_list;
            }

            public void setUrl_list(List<UrlListBeanX> url_list) {
                this.url_list = url_list;
            }

            public static class UrlListBeanX {
                /**
                 * url : http://p3.pstatp.com/thumb/pgc-image/15371811696145d7a14a977
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

        public static class WebpImageDetailBean {
            /**
             * height : 502
             * uri : details/v0/w640/pgc-image/15371811696145d7a14a977.webp
             * url : http://p3.pstatp.com/details/v0/w640/pgc-image/15371811696145d7a14a977.webp
             * url_list : [{"url":"http://p3.pstatp.com/details/v0/w640/pgc-image/15371811696145d7a14a977.webp"},{"url":"http://pb9.pstatp.com/details/v0/w640/pgc-image/15371811696145d7a14a977.webp"},{"url":"http://pb1.pstatp.com/details/v0/w640/pgc-image/15371811696145d7a14a977.webp"}]
             * width : 800
             */

            private int height;
            private String uri;
            private String url;
            private int width;
            private List<UrlListBeanXX> url_list;

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

            public List<UrlListBeanXX> getUrl_list() {
                return url_list;
            }

            public void setUrl_list(List<UrlListBeanXX> url_list) {
                this.url_list = url_list;
            }

            public static class UrlListBeanXX {
                /**
                 * url : http://p3.pstatp.com/details/v0/w640/pgc-image/15371811696145d7a14a977.webp
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

        public static class WebpThumbImageBean {
            /**
             * height : 502
             * uri : thumb//pgc-image/15371811696145d7a14a977.webp
             * url : http://p3.pstatp.com/thumb/pgc-image/15371811696145d7a14a977.webp
             * url_list : [{"url":"http://p3.pstatp.com/thumb/pgc-image/15371811696145d7a14a977.webp"},{"url":"http://pb9.pstatp.com/thumb/pgc-image/15371811696145d7a14a977.webp"},{"url":"http://pb1.pstatp.com/thumb/pgc-image/15371811696145d7a14a977.webp"}]
             * width : 800
             */

            private int height;
            private String uri;
            private String url;
            private int width;
            private List<UrlListBeanXXX> url_list;

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

            public List<UrlListBeanXXX> getUrl_list() {
                return url_list;
            }

            public void setUrl_list(List<UrlListBeanXXX> url_list) {
                this.url_list = url_list;
            }

            public static class UrlListBeanXXX {
                /**
                 * url : http://p3.pstatp.com/thumb/pgc-image/15371811696145d7a14a977.webp
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
}
