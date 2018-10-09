package com.kx.todaynews.bean.article;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 *  详情页底部评论数据Bean
 */
public class ArticleTabCommentsBean {

    private boolean ban_comment;
    private boolean ban_face;
    private int ban_gif_suggest;
    private int ban_pic_comment;
    private int detail_no_comment;
    private int fold_comment_count;
    private int go_topic_detail;
    private GroupBean group;
    private boolean has_more;
    private String message;
    private RepostParamsBean repost_params;
    private int show_add_forum;
    private boolean stable;
    private boolean stick_has_more;
    private int stick_total_number;
    private TabInfoBean tab_info;
    private int total_number;
    private ArrayList<DataBean> data;
    private List<?> stick_comments;

    public boolean isBan_comment() {
        return ban_comment;
    }

    public void setBan_comment(boolean ban_comment) {
        this.ban_comment = ban_comment;
    }

    public boolean isBan_face() {
        return ban_face;
    }

    public void setBan_face(boolean ban_face) {
        this.ban_face = ban_face;
    }

    public int getBan_gif_suggest() {
        return ban_gif_suggest;
    }

    public void setBan_gif_suggest(int ban_gif_suggest) {
        this.ban_gif_suggest = ban_gif_suggest;
    }

    public int getBan_pic_comment() {
        return ban_pic_comment;
    }

    public void setBan_pic_comment(int ban_pic_comment) {
        this.ban_pic_comment = ban_pic_comment;
    }

    public int getDetail_no_comment() {
        return detail_no_comment;
    }

    public void setDetail_no_comment(int detail_no_comment) {
        this.detail_no_comment = detail_no_comment;
    }

    public int getFold_comment_count() {
        return fold_comment_count;
    }

    public void setFold_comment_count(int fold_comment_count) {
        this.fold_comment_count = fold_comment_count;
    }

    public int getGo_topic_detail() {
        return go_topic_detail;
    }

    public void setGo_topic_detail(int go_topic_detail) {
        this.go_topic_detail = go_topic_detail;
    }

    public GroupBean getGroup() {
        return group;
    }

    public void setGroup(GroupBean group) {
        this.group = group;
    }

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RepostParamsBean getRepost_params() {
        return repost_params;
    }

    public void setRepost_params(RepostParamsBean repost_params) {
        this.repost_params = repost_params;
    }

    public int getShow_add_forum() {
        return show_add_forum;
    }

    public void setShow_add_forum(int show_add_forum) {
        this.show_add_forum = show_add_forum;
    }

    public boolean isStable() {
        return stable;
    }

    public void setStable(boolean stable) {
        this.stable = stable;
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

    public TabInfoBean getTab_info() {
        return tab_info;
    }

    public void setTab_info(TabInfoBean tab_info) {
        this.tab_info = tab_info;
    }

    public int getTotal_number() {
        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public List<?> getStick_comments() {
        return stick_comments;
    }

    public void setStick_comments(List<?> stick_comments) {
        this.stick_comments = stick_comments;
    }

    public static class GroupBean {
        /**
         * content :
         * content_rich_span :
         * is_video : false
         * user_id : 3242684112
         * user_name : 海外网
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

    public static class RepostParamsBean {
        /**
         * cover_url : http://p3.pstatp.com/list/300x300/pgc-image/153760015637814b843d0b7
         * fw_id : 6603942533846270472
         * fw_id_type : 4
         * fw_user_id : 3242684112
         * repost_type : 211
         * schema :
         * title : 广深港高铁香港段开通仪式举行，林郑月娥：通车意义何止那26公里？
         */

        private String cover_url;
        private long fw_id;
        private int fw_id_type;
        private long fw_user_id;
        private int repost_type;
        private String schema;
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

        public long getFw_user_id() {
            return fw_user_id;
        }

        public void setFw_user_id(long fw_user_id) {
            this.fw_user_id = fw_user_id;
        }

        public int getRepost_type() {
            return repost_type;
        }

        public void setRepost_type(int repost_type) {
            this.repost_type = repost_type;
        }

        public String getSchema() {
            return schema;
        }

        public void setSchema(String schema) {
            this.schema = schema;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class TabInfoBean {
        /**
         * current_tab_index : 0
         * tabs : ["热度","时间"]
         */

        private int current_tab_index;
        private List<String> tabs;

        public int getCurrent_tab_index() {
            return current_tab_index;
        }

        public void setCurrent_tab_index(int current_tab_index) {
            this.current_tab_index = current_tab_index;
        }

        public List<String> getTabs() {
            return tabs;
        }

        public void setTabs(List<String> tabs) {
            this.tabs = tabs;
        }
    }

    public static class DataBean {
        /**
         * ad : null
         * cell_type : 1
         * comment : {"author_badge":[],"bury_count":0,"content_rich_span":"","create_time":1537603034,"digg_count":25,"forward_count":1,"has_author_digg":0,"id":1612293639215133,"interact_style":0,"is_blocked":0,"is_blocking":0,"is_followed":0,"is_following":0,"is_pgc_author":0,"large_image_list":[],"media_info":{"avatar_url":"","name":""},"platform":"feifei","remark_name":"","reply_count":4,"reply_list":[],"score":0.9737772472852234,"text":"一家人的高铁","thumb_image_list":[],"user_auth_info":"","user_bury":0,"user_decoration":"","user_digg":0,"user_id":56037151868,"user_name":"烧饼歌","user_profile_image_url":"http://p9.pstatp.com/thumb/16aa000be7ae5f1d56af","user_relation":0,"user_verified":true,"verified_reason":"广州烧饼文化传播有限公司总经理"}
         */

        private Object ad;
        private int cell_type;
        private CommentBean comment;

        public Object getAd() {
            return ad;
        }

        public void setAd(Object ad) {
            this.ad = ad;
        }

        public int getCell_type() {
            return cell_type;
        }

        public void setCell_type(int cell_type) {
            this.cell_type = cell_type;
        }

        public CommentBean getComment() {
            return comment;
        }

        public void setComment(CommentBean comment) {
            this.comment = comment;
        }

        public static class CommentBean implements Parcelable{
            /**
             * author_badge : []
             * bury_count : 0
             * content_rich_span :
             * create_time : 1537603034
             * digg_count : 25
             * forward_count : 1
             * has_author_digg : 0
             * id : 1612293639215133
             * interact_style : 0
             * is_blocked : 0
             * is_blocking : 0
             * is_followed : 0
             * is_following : 0
             * is_pgc_author : 0
             * large_image_list : []
             * media_info : {"avatar_url":"","name":""}
             * platform : feifei
             * remark_name :
             * reply_count : 4
             * reply_list : []
             * score : 0.9737772472852234
             * text : 一家人的高铁
             * thumb_image_list : []
             * user_auth_info :
             * user_bury : 0
             * user_decoration :
             * user_digg : 0
             * user_id : 56037151868
             * user_name : 烧饼歌
             * user_profile_image_url : http://p9.pstatp.com/thumb/16aa000be7ae5f1d56af
             * user_relation : 0
             * user_verified : true
             * verified_reason : 广州烧饼文化传播有限公司总经理
             */

            private int bury_count;
            private String content_rich_span;
            private long create_time;
            private int digg_count;
            private int forward_count;
            private int has_author_digg;
            private long id;
            private int interact_style;
            private int is_blocked;
            private int is_blocking;
            private int is_followed;
            private int is_following;
            private int is_pgc_author;
            private MediaInfoBean media_info;
            private String platform;
            private String remark_name;
            private int reply_count;
            private double score;
            private String text;
            private String user_auth_info;
            private int user_bury;
            private String user_decoration;
            private int user_digg;
            private long user_id;
            private String user_name;
            private String user_profile_image_url;
            private int user_relation;
            private boolean user_verified;
            private String verified_reason;
            private List<?> author_badge;
            private List<?> large_image_list;
            private List<?> reply_list;
            private List<?> thumb_image_list;

            protected CommentBean(Parcel in) {
                bury_count = in.readInt();
                content_rich_span = in.readString();
                create_time = in.readLong();
                digg_count = in.readInt();
                forward_count = in.readInt();
                has_author_digg = in.readInt();
                id = in.readLong();
                interact_style = in.readInt();
                is_blocked = in.readInt();
                is_blocking = in.readInt();
                is_followed = in.readInt();
                is_following = in.readInt();
                is_pgc_author = in.readInt();
                media_info = in.readParcelable(MediaInfoBean.class.getClassLoader());
                platform = in.readString();
                remark_name = in.readString();
                reply_count = in.readInt();
                score = in.readDouble();
                text = in.readString();
                user_auth_info = in.readString();
                user_bury = in.readInt();
                user_decoration = in.readString();
                user_digg = in.readInt();
                user_id = in.readLong();
                user_name = in.readString();
                user_profile_image_url = in.readString();
                user_relation = in.readInt();
                user_verified = in.readByte() != 0;
                verified_reason = in.readString();
            }

            public static final Creator<CommentBean> CREATOR = new Creator<CommentBean>() {
                @Override
                public CommentBean createFromParcel(Parcel in) {
                    return new CommentBean(in);
                }

                @Override
                public CommentBean[] newArray(int size) {
                    return new CommentBean[size];
                }
            };

            public int getBury_count() {
                return bury_count;
            }

            public void setBury_count(int bury_count) {
                this.bury_count = bury_count;
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

            public void setCreate_time(long create_time) {
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

            public int getInteract_style() {
                return interact_style;
            }

            public void setInteract_style(int interact_style) {
                this.interact_style = interact_style;
            }

            public int getIs_blocked() {
                return is_blocked;
            }

            public void setIs_blocked(int is_blocked) {
                this.is_blocked = is_blocked;
            }

            public int getIs_blocking() {
                return is_blocking;
            }

            public void setIs_blocking(int is_blocking) {
                this.is_blocking = is_blocking;
            }

            public int getIs_followed() {
                return is_followed;
            }

            public void setIs_followed(int is_followed) {
                this.is_followed = is_followed;
            }

            public int getIs_following() {
                return is_following;
            }

            public void setIs_following(int is_following) {
                this.is_following = is_following;
            }

            public int getIs_pgc_author() {
                return is_pgc_author;
            }

            public void setIs_pgc_author(int is_pgc_author) {
                this.is_pgc_author = is_pgc_author;
            }

            public MediaInfoBean getMedia_info() {
                return media_info;
            }

            public void setMedia_info(MediaInfoBean media_info) {
                this.media_info = media_info;
            }

            public String getPlatform() {
                return platform;
            }

            public void setPlatform(String platform) {
                this.platform = platform;
            }

            public String getRemark_name() {
                return remark_name;
            }

            public void setRemark_name(String remark_name) {
                this.remark_name = remark_name;
            }

            public int getReply_count() {
                return reply_count;
            }

            public void setReply_count(int reply_count) {
                this.reply_count = reply_count;
            }

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
                this.score = score;
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

            public int getUser_bury() {
                return user_bury;
            }

            public void setUser_bury(int user_bury) {
                this.user_bury = user_bury;
            }

            public String getUser_decoration() {
                return user_decoration;
            }

            public void setUser_decoration(String user_decoration) {
                this.user_decoration = user_decoration;
            }

            public int getUser_digg() {
                return user_digg;
            }

            public void setUser_digg(int user_digg) {
                this.user_digg = user_digg;
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

            public String getUser_profile_image_url() {
                return user_profile_image_url;
            }

            public void setUser_profile_image_url(String user_profile_image_url) {
                this.user_profile_image_url = user_profile_image_url;
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

            public List<?> getLarge_image_list() {
                return large_image_list;
            }

            public void setLarge_image_list(List<?> large_image_list) {
                this.large_image_list = large_image_list;
            }

            public List<?> getReply_list() {
                return reply_list;
            }

            public void setReply_list(List<?> reply_list) {
                this.reply_list = reply_list;
            }

            public List<?> getThumb_image_list() {
                return thumb_image_list;
            }

            public void setThumb_image_list(List<?> thumb_image_list) {
                this.thumb_image_list = thumb_image_list;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(bury_count);
                dest.writeString(content_rich_span);
                dest.writeLong(create_time);
                dest.writeInt(digg_count);
                dest.writeInt(forward_count);
                dest.writeInt(has_author_digg);
                dest.writeLong(id);
                dest.writeInt(interact_style);
                dest.writeInt(is_blocked);
                dest.writeInt(is_blocking);
                dest.writeInt(is_followed);
                dest.writeInt(is_following);
                dest.writeInt(is_pgc_author);
                dest.writeParcelable(media_info, flags);
                dest.writeString(platform);
                dest.writeString(remark_name);
                dest.writeInt(reply_count);
                dest.writeDouble(score);
                dest.writeString(text);
                dest.writeString(user_auth_info);
                dest.writeInt(user_bury);
                dest.writeString(user_decoration);
                dest.writeInt(user_digg);
                dest.writeLong(user_id);
                dest.writeString(user_name);
                dest.writeString(user_profile_image_url);
                dest.writeInt(user_relation);
                dest.writeByte((byte) (user_verified ? 1 : 0));
                dest.writeString(verified_reason);
            }

            public static class MediaInfoBean implements Parcelable{
                /**
                 * avatar_url :
                 * name :
                 */

                private String avatar_url;
                private String name;

                protected MediaInfoBean(Parcel in) {
                    avatar_url = in.readString();
                    name = in.readString();
                }

                public static final Creator<MediaInfoBean> CREATOR = new Creator<MediaInfoBean>() {
                    @Override
                    public MediaInfoBean createFromParcel(Parcel in) {
                        return new MediaInfoBean(in);
                    }

                    @Override
                    public MediaInfoBean[] newArray(int size) {
                        return new MediaInfoBean[size];
                    }
                };

                public String getAvatar_url() {
                    return avatar_url;
                }

                public void setAvatar_url(String avatar_url) {
                    this.avatar_url = avatar_url;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(avatar_url);
                    dest.writeString(name);
                }
            }
        }
    }
}
