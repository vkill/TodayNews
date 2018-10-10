package com.kx.todaynews.bean.article;

import java.util.List;

/**
 * Created by admin on 2018/10/9.
 */
public class ArticleReplyListBean {

    /**
     * ban_face : false
     * data : {"data":[{"content":"出事了？","content_rich_span":"","create_time":1539086942,"digg_count":6,"forward_count":0,"group":{"content":"","content_rich_span":"","is_video":false,"user_id":24324846370,"user_name":"重庆道士"},"has_author_digg":0,"id":1613849622579214,"is_owner":false,"reply_to_comment":{"author_badge":[],"content_rich_span":"","id":1613849195344936,"is_followed":null,"is_following":null,"is_owner":false,"is_pgc_author":false,"text":"20亿那是邓亚萍，不是他","user_auth_info":"","user_id":74448269728,"user_name":"qzuser2318697","user_relation":0,"user_verified":false,"verified_reason":""},"repost_params":{"cover_url":"http://p9.pstatp.com/thumb/5677/4633951464","fw_id":6610307875329802765,"fw_id_type":4,"opt_id":1613849622579214,"opt_id_type":3,"repost_type":211,"title":"薄熙来原秘书案子，近期再有消息"},"text":"出事了？","user":{"author_badge":[],"avatar_url":"http://p1.pstatp.com/thumb/2bd700179a7cda85841c","description":"","interact_style":0,"is_blocked":null,"is_blocking":null,"is_followed":null,"is_following":null,"is_pgc_author":false,"name":"咕嘟咕嘟八戒","screen_name":"咕嘟咕嘟八戒","user_auth_info":"","user_decoration":null,"user_id":9076865274,"user_relation":0,"user_verified":false,"verified_reason":""},"user_digg":false}],"group_id":6610307875329802765,"has_more":true,"hot_comments":[],"id":1613847242317831,"offset":20,"stick_has_more":false,"stick_total_number":0,"total_count":91}
     * message : success
     * stable : true
     */
    private List<String> diggListImages;
    private boolean ban_face;
    private DataBeanX data;
    private String message;
    private boolean stable;

    public List<String> getDiggListImages() {
        return diggListImages;
    }

    public void setDiggListImages(List<String> diggListImages) {
        this.diggListImages = diggListImages;
    }

    public boolean isBan_face() {
        return ban_face;
    }

    public void setBan_face(boolean ban_face) {
        this.ban_face = ban_face;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStable() {
        return stable;
    }

    public void setStable(boolean stable) {
        this.stable = stable;
    }

    public static class DataBeanX {
        /**
         * data : [{"content":"出事了？","content_rich_span":"","create_time":1539086942,"digg_count":6,"forward_count":0,"group":{"content":"","content_rich_span":"","is_video":false,"user_id":24324846370,"user_name":"重庆道士"},"has_author_digg":0,"id":1613849622579214,"is_owner":false,"reply_to_comment":{"author_badge":[],"content_rich_span":"","id":1613849195344936,"is_followed":null,"is_following":null,"is_owner":false,"is_pgc_author":false,"text":"20亿那是邓亚萍，不是他","user_auth_info":"","user_id":74448269728,"user_name":"qzuser2318697","user_relation":0,"user_verified":false,"verified_reason":""},"repost_params":{"cover_url":"http://p9.pstatp.com/thumb/5677/4633951464","fw_id":6610307875329802765,"fw_id_type":4,"opt_id":1613849622579214,"opt_id_type":3,"repost_type":211,"title":"薄熙来原秘书案子，近期再有消息"},"text":"出事了？","user":{"author_badge":[],"avatar_url":"http://p1.pstatp.com/thumb/2bd700179a7cda85841c","description":"","interact_style":0,"is_blocked":null,"is_blocking":null,"is_followed":null,"is_following":null,"is_pgc_author":false,"name":"咕嘟咕嘟八戒","screen_name":"咕嘟咕嘟八戒","user_auth_info":"","user_decoration":null,"user_id":9076865274,"user_relation":0,"user_verified":false,"verified_reason":""},"user_digg":false}]
         * group_id : 6610307875329802765
         * has_more : true
         * hot_comments : []
         * id : 1613847242317831
         * offset : 20
         * stick_has_more : false
         * stick_total_number : 0
         * total_count : 91
         */

        private long group_id;
        private boolean has_more;
        private long id;
        private int offset;
        private boolean stick_has_more;
        private int stick_total_number;
        private int total_count;
        private List<DataBean> data;
        private List<?> hot_comments;

        public long getGroup_id() {
            return group_id;
        }

        public void setGroup_id(long group_id) {
            this.group_id = group_id;
        }

        public boolean isHas_more() {
            return has_more;
        }

        public void setHas_more(boolean has_more) {
            this.has_more = has_more;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public boolean isStick_has_more() {
            return stick_has_more;
        }

        public void setStick_has_more(boolean stick_has_more) {
            this.stick_has_more = stick_has_more;
        }

        public int getStick_total_number() {
            return stick_total_number;
        }

        public void setStick_total_number(int stick_total_number) {
            this.stick_total_number = stick_total_number;
        }

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public List<?> getHot_comments() {
            return hot_comments;
        }

        public void setHot_comments(List<?> hot_comments) {
            this.hot_comments = hot_comments;
        }

        public static class DataBean {
            /**
             * content : 出事了？
             * content_rich_span :
             * create_time : 1539086942
             * digg_count : 6
             * forward_count : 0
             * group : {"content":"","content_rich_span":"","is_video":false,"user_id":24324846370,"user_name":"重庆道士"}
             * has_author_digg : 0
             * id : 1613849622579214
             * is_owner : false
             * reply_to_comment : {"author_badge":[],"content_rich_span":"","id":1613849195344936,"is_followed":null,"is_following":null,"is_owner":false,"is_pgc_author":false,"text":"20亿那是邓亚萍，不是他","user_auth_info":"","user_id":74448269728,"user_name":"qzuser2318697","user_relation":0,"user_verified":false,"verified_reason":""}
             * repost_params : {"cover_url":"http://p9.pstatp.com/thumb/5677/4633951464","fw_id":6610307875329802765,"fw_id_type":4,"opt_id":1613849622579214,"opt_id_type":3,"repost_type":211,"title":"薄熙来原秘书案子，近期再有消息"}
             * text : 出事了？
             * user : {"author_badge":[],"avatar_url":"http://p1.pstatp.com/thumb/2bd700179a7cda85841c","description":"","interact_style":0,"is_blocked":null,"is_blocking":null,"is_followed":null,"is_following":null,"is_pgc_author":false,"name":"咕嘟咕嘟八戒","screen_name":"咕嘟咕嘟八戒","user_auth_info":"","user_decoration":null,"user_id":9076865274,"user_relation":0,"user_verified":false,"verified_reason":""}
             * user_digg : false
             */

            private String content;
            private String content_rich_span;
            private long create_time;
            private int digg_count;
            private int forward_count;
            private GroupBean group;
            private int has_author_digg;
            private long id;
            private boolean is_owner;
            private ReplyToCommentBean reply_to_comment;
            private RepostParamsBean repost_params;
            private String text;
            private UserBean user;
            private boolean user_digg;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getContent_rich_span() {
                return content_rich_span;
            }

            public void setContent_rich_span(String content_rich_span) {
                this.content_rich_span = content_rich_span;
            }

            public long getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public int getDigg_count() {
                return digg_count;
            }

            public void setDigg_count(int digg_count) {
                this.digg_count = digg_count;
            }

            public int getForward_count() {
                return forward_count;
            }

            public void setForward_count(int forward_count) {
                this.forward_count = forward_count;
            }

            public GroupBean getGroup() {
                return group;
            }

            public void setGroup(GroupBean group) {
                this.group = group;
            }

            public int getHas_author_digg() {
                return has_author_digg;
            }

            public void setHas_author_digg(int has_author_digg) {
                this.has_author_digg = has_author_digg;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public boolean isIs_owner() {
                return is_owner;
            }

            public void setIs_owner(boolean is_owner) {
                this.is_owner = is_owner;
            }

            public ReplyToCommentBean getReply_to_comment() {
                return reply_to_comment;
            }

            public void setReply_to_comment(ReplyToCommentBean reply_to_comment) {
                this.reply_to_comment = reply_to_comment;
            }

            public RepostParamsBean getRepost_params() {
                return repost_params;
            }

            public void setRepost_params(RepostParamsBean repost_params) {
                this.repost_params = repost_params;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public boolean isUser_digg() {
                return user_digg;
            }

            public void setUser_digg(boolean user_digg) {
                this.user_digg = user_digg;
            }

            public static class GroupBean {
                /**
                 * content :
                 * content_rich_span :
                 * is_video : false
                 * user_id : 24324846370
                 * user_name : 重庆道士
                 */

                private String content;
                private String content_rich_span;
                private boolean is_video;
                private long user_id;
                private String user_name;

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getContent_rich_span() {
                    return content_rich_span;
                }

                public void setContent_rich_span(String content_rich_span) {
                    this.content_rich_span = content_rich_span;
                }

                public boolean isIs_video() {
                    return is_video;
                }

                public void setIs_video(boolean is_video) {
                    this.is_video = is_video;
                }

                public long getUser_id() {
                    return user_id;
                }

                public void setUser_id(long user_id) {
                    this.user_id = user_id;
                }

                public String getUser_name() {
                    return user_name;
                }

                public void setUser_name(String user_name) {
                    this.user_name = user_name;
                }
            }

            public static class ReplyToCommentBean {
                /**
                 * author_badge : []
                 * content_rich_span :
                 * id : 1613849195344936
                 * is_followed : null
                 * is_following : null
                 * is_owner : false
                 * is_pgc_author : false
                 * text : 20亿那是邓亚萍，不是他
                 * user_auth_info :
                 * user_id : 74448269728
                 * user_name : qzuser2318697
                 * user_relation : 0
                 * user_verified : false
                 * verified_reason :
                 */

                private String content_rich_span;
                private long id;
                private Object is_followed;
                private Object is_following;
                private boolean is_owner;
                private boolean is_pgc_author;
                private String text;
                private String user_auth_info;
                private long user_id;
                private String user_name;
                private int user_relation;
                private boolean user_verified;
                private String verified_reason;
                private List<?> author_badge;

                public String getContent_rich_span() {
                    return content_rich_span;
                }

                public void setContent_rich_span(String content_rich_span) {
                    this.content_rich_span = content_rich_span;
                }

                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public Object getIs_followed() {
                    return is_followed;
                }

                public void setIs_followed(Object is_followed) {
                    this.is_followed = is_followed;
                }

                public Object getIs_following() {
                    return is_following;
                }

                public void setIs_following(Object is_following) {
                    this.is_following = is_following;
                }

                public boolean isIs_owner() {
                    return is_owner;
                }

                public void setIs_owner(boolean is_owner) {
                    this.is_owner = is_owner;
                }

                public boolean isIs_pgc_author() {
                    return is_pgc_author;
                }

                public void setIs_pgc_author(boolean is_pgc_author) {
                    this.is_pgc_author = is_pgc_author;
                }

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public String getUser_auth_info() {
                    return user_auth_info;
                }

                public void setUser_auth_info(String user_auth_info) {
                    this.user_auth_info = user_auth_info;
                }

                public long getUser_id() {
                    return user_id;
                }

                public void setUser_id(long user_id) {
                    this.user_id = user_id;
                }

                public String getUser_name() {
                    return user_name;
                }

                public void setUser_name(String user_name) {
                    this.user_name = user_name;
                }

                public int getUser_relation() {
                    return user_relation;
                }

                public void setUser_relation(int user_relation) {
                    this.user_relation = user_relation;
                }

                public boolean isUser_verified() {
                    return user_verified;
                }

                public void setUser_verified(boolean user_verified) {
                    this.user_verified = user_verified;
                }

                public String getVerified_reason() {
                    return verified_reason;
                }

                public void setVerified_reason(String verified_reason) {
                    this.verified_reason = verified_reason;
                }

                public List<?> getAuthor_badge() {
                    return author_badge;
                }

                public void setAuthor_badge(List<?> author_badge) {
                    this.author_badge = author_badge;
                }
            }

            public static class RepostParamsBean {
                /**
                 * cover_url : http://p9.pstatp.com/thumb/5677/4633951464
                 * fw_id : 6610307875329802765
                 * fw_id_type : 4
                 * opt_id : 1613849622579214
                 * opt_id_type : 3
                 * repost_type : 211
                 * title : 薄熙来原秘书案子，近期再有消息
                 */

                private String cover_url;
                private long fw_id;
                private int fw_id_type;
                private long opt_id;
                private int opt_id_type;
                private int repost_type;
                private String title;

                public String getCover_url() {
                    return cover_url;
                }

                public void setCover_url(String cover_url) {
                    this.cover_url = cover_url;
                }

                public long getFw_id() {
                    return fw_id;
                }

                public void setFw_id(long fw_id) {
                    this.fw_id = fw_id;
                }

                public int getFw_id_type() {
                    return fw_id_type;
                }

                public void setFw_id_type(int fw_id_type) {
                    this.fw_id_type = fw_id_type;
                }

                public long getOpt_id() {
                    return opt_id;
                }

                public void setOpt_id(long opt_id) {
                    this.opt_id = opt_id;
                }

                public int getOpt_id_type() {
                    return opt_id_type;
                }

                public void setOpt_id_type(int opt_id_type) {
                    this.opt_id_type = opt_id_type;
                }

                public int getRepost_type() {
                    return repost_type;
                }

                public void setRepost_type(int repost_type) {
                    this.repost_type = repost_type;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }
            }

            public static class UserBean {
                /**
                 * author_badge : []
                 * avatar_url : http://p1.pstatp.com/thumb/2bd700179a7cda85841c
                 * description :
                 * interact_style : 0
                 * is_blocked : null
                 * is_blocking : null
                 * is_followed : null
                 * is_following : null
                 * is_pgc_author : false
                 * name : 咕嘟咕嘟八戒
                 * screen_name : 咕嘟咕嘟八戒
                 * user_auth_info :
                 * user_decoration : null
                 * user_id : 9076865274
                 * user_relation : 0
                 * user_verified : false
                 * verified_reason :
                 */

                private String avatar_url;
                private String description;
                private int interact_style;
                private Object is_blocked;
                private Object is_blocking;
                private Object is_followed;
                private Object is_following;
                private boolean is_pgc_author;
                private String name;
                private String screen_name;
                private String user_auth_info;
                private Object user_decoration;
                private long user_id;
                private int user_relation;
                private boolean user_verified;
                private String verified_reason;
                private List<?> author_badge;

                public String getAvatar_url() {
                    return avatar_url;
                }

                public void setAvatar_url(String avatar_url) {
                    this.avatar_url = avatar_url;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public int getInteract_style() {
                    return interact_style;
                }

                public void setInteract_style(int interact_style) {
                    this.interact_style = interact_style;
                }

                public Object getIs_blocked() {
                    return is_blocked;
                }

                public void setIs_blocked(Object is_blocked) {
                    this.is_blocked = is_blocked;
                }

                public Object getIs_blocking() {
                    return is_blocking;
                }

                public void setIs_blocking(Object is_blocking) {
                    this.is_blocking = is_blocking;
                }

                public Object getIs_followed() {
                    return is_followed;
                }

                public void setIs_followed(Object is_followed) {
                    this.is_followed = is_followed;
                }

                public Object getIs_following() {
                    return is_following;
                }

                public void setIs_following(Object is_following) {
                    this.is_following = is_following;
                }

                public boolean isIs_pgc_author() {
                    return is_pgc_author;
                }

                public void setIs_pgc_author(boolean is_pgc_author) {
                    this.is_pgc_author = is_pgc_author;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getScreen_name() {
                    return screen_name;
                }

                public void setScreen_name(String screen_name) {
                    this.screen_name = screen_name;
                }

                public String getUser_auth_info() {
                    return user_auth_info;
                }

                public void setUser_auth_info(String user_auth_info) {
                    this.user_auth_info = user_auth_info;
                }

                public Object getUser_decoration() {
                    return user_decoration;
                }

                public void setUser_decoration(Object user_decoration) {
                    this.user_decoration = user_decoration;
                }

                public long getUser_id() {
                    return user_id;
                }

                public void setUser_id(long user_id) {
                    this.user_id = user_id;
                }

                public int getUser_relation() {
                    return user_relation;
                }

                public void setUser_relation(int user_relation) {
                    this.user_relation = user_relation;
                }

                public boolean isUser_verified() {
                    return user_verified;
                }

                public void setUser_verified(boolean user_verified) {
                    this.user_verified = user_verified;
                }

                public String getVerified_reason() {
                    return verified_reason;
                }

                public void setVerified_reason(String verified_reason) {
                    this.verified_reason = verified_reason;
                }

                public List<?> getAuthor_badge() {
                    return author_badge;
                }

                public void setAuthor_badge(List<?> author_badge) {
                    this.author_badge = author_badge;
                }
            }
        }
    }
}
