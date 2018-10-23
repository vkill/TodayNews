package com.kx.todaynews.module.video;

public class VideoInfo {

    /**
     * code : 0
     * message : success
     * video_info : {"code":0,"data":{"auto_definition":"360p","enable_ssl":false,"media_type":"video","poster_url":"http://p3.pstatp.com/origin/101300008d0015883d1e7","status":10,"user_id":"toutiao","validate":"","video_duration":196.25,"video_id":"v020041d0000bf79tgclbum1rmmdp20g","video_list":{"video_1":{"backup_url_1":"aHR0cDovL3Y3LnBzdGF0cC5jb20vZjg5NzYwNzVjZDU4NTQ1MmZhNmQ3YWNlMjdiNTMxYmUvNWJjZWQzYzAvdmlkZW8vbS8yMjBjYTIxNTE4MzBlYmU0NDNlOTAxNDYwZGYxOTgyM2EzMzExNWRlMTg2MDAwMDhiNjQ2NGJjN2JiNC8=","bitrate":147951,"codec_type":"h265","definition":"360p","encrypt":false,"file_hash":"94c2e6bdd765145ba05069e4b1eb148c","logo_type":"xigua","main_url":"aHR0cDovL3Y5OS10dC5peGlndWEuY29tLzUwNTcyMjA4ZDM5NDUyN2ExMzQxNjBiN2RmYjNhZGZiLzViY2VkM2MwL3ZpZGVvL20vMjIwY2EyMTUxODMwZWJlNDQzZTkwMTQ2MGRmMTk4MjNhMzMxMTVkZTE4NjAwMDA4YjY0NjRiYzdiYjQv","preload_interval":45,"preload_max_step":10,"preload_min_step":5,"preload_size":327680,"quality":"normal","size":5328036,"socket_buffer":3328740,"spade_a":"","user_video_proxy":1,"vheight":360,"vtype":"mp4","vwidth":640},"video_2":{"backup_url_1":"aHR0cDovL3Y3LnBzdGF0cC5jb20vNTlkODRmYThkNmM3OTdiZThhZWE5YTIzMTM5YWFmNjUvNWJjZWQzYzAvdmlkZW8vbS8yMjAzNzg2ODIzNDQyOTU0YzQ2OTNmZDYwNGVjODhkODA0MDExNWRlNWYyMDAwMDlkMzEwMjVmZmFjOS8=","bitrate":219868,"codec_type":"h265","definition":"480p","encrypt":false,"file_hash":"b5279e87887825b6e87f9c0b6c5dca9d","logo_type":"xigua","main_url":"aHR0cDovL3Y5OS10dC5peGlndWEuY29tL2YyZjJkMmVhODI0NGRkZTEzZDZjMzVlYTIzYjY5OTIxLzViY2VkM2MwL3ZpZGVvL20vMjIwMzc4NjgyMzQ0Mjk1NGM0NjkzZmQ2MDRlYzg4ZDgwNDAxMTVkZTVmMjAwMDA5ZDMxMDI1ZmZhYzkv","preload_interval":45,"preload_max_step":10,"preload_min_step":5,"preload_size":327680,"quality":"normal","size":7092167,"socket_buffer":4946940,"spade_a":"","user_video_proxy":1,"vheight":480,"vtype":"mp4","vwidth":854},"video_3":{"backup_url_1":"aHR0cDovL3Y3LnBzdGF0cC5jb20vZmRkZThiN2Q5OWU3NzAzZGIwYzQ5ODU2MmU0NmFlOGMvNWJjZWQzYzAvdmlkZW8vbS8yMjBhODNlOWZmZWNmNDE0MTIyYTEwM2Y2MzUxMWEyODFkZDExNWUwYzRlMDAwMDFkZjc1OWMyYmViNC8=","bitrate":474147,"codec_type":"h265","definition":"720p","encrypt":false,"file_hash":"20040f3bff0bad4e4025560bc6eadbac","logo_type":"xigua","main_url":"aHR0cDovL3Y5OS10dC5peGlndWEuY29tL2Q2OWJjZmI2NDNmYTI3YWJlZGUxNjM1YzE2MzViYjNmLzViY2VkM2MwL3ZpZGVvL20vMjIwYTgzZTlmZmVjZjQxNDEyMmExMDNmNjM1MTFhMjgxZGQxMTVlMGM0ZTAwMDAxZGY3NTljMmJlYjQv","preload_interval":45,"preload_max_step":10,"preload_min_step":5,"preload_size":327680,"quality":"normal","size":13329628,"socket_buffer":10668240,"spade_a":"","user_video_proxy":1,"vheight":720,"vtype":"mp4","vwidth":1280}}},"message":"success","total":3}
     */

    private int code;
    private String message;
    private VideoInfoBean video_info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public VideoInfoBean getVideo_info() {
        return video_info;
    }

    public void setVideo_info(VideoInfoBean video_info) {
        this.video_info = video_info;
    }

    public static class VideoInfoBean {
        /**
         * code : 0
         * data : {"auto_definition":"360p","enable_ssl":false,"media_type":"video","poster_url":"http://p3.pstatp.com/origin/101300008d0015883d1e7","status":10,"user_id":"toutiao","validate":"","video_duration":196.25,"video_id":"v020041d0000bf79tgclbum1rmmdp20g","video_list":{"video_1":{"backup_url_1":"aHR0cDovL3Y3LnBzdGF0cC5jb20vZjg5NzYwNzVjZDU4NTQ1MmZhNmQ3YWNlMjdiNTMxYmUvNWJjZWQzYzAvdmlkZW8vbS8yMjBjYTIxNTE4MzBlYmU0NDNlOTAxNDYwZGYxOTgyM2EzMzExNWRlMTg2MDAwMDhiNjQ2NGJjN2JiNC8=","bitrate":147951,"codec_type":"h265","definition":"360p","encrypt":false,"file_hash":"94c2e6bdd765145ba05069e4b1eb148c","logo_type":"xigua","main_url":"aHR0cDovL3Y5OS10dC5peGlndWEuY29tLzUwNTcyMjA4ZDM5NDUyN2ExMzQxNjBiN2RmYjNhZGZiLzViY2VkM2MwL3ZpZGVvL20vMjIwY2EyMTUxODMwZWJlNDQzZTkwMTQ2MGRmMTk4MjNhMzMxMTVkZTE4NjAwMDA4YjY0NjRiYzdiYjQv","preload_interval":45,"preload_max_step":10,"preload_min_step":5,"preload_size":327680,"quality":"normal","size":5328036,"socket_buffer":3328740,"spade_a":"","user_video_proxy":1,"vheight":360,"vtype":"mp4","vwidth":640},"video_2":{"backup_url_1":"aHR0cDovL3Y3LnBzdGF0cC5jb20vNTlkODRmYThkNmM3OTdiZThhZWE5YTIzMTM5YWFmNjUvNWJjZWQzYzAvdmlkZW8vbS8yMjAzNzg2ODIzNDQyOTU0YzQ2OTNmZDYwNGVjODhkODA0MDExNWRlNWYyMDAwMDlkMzEwMjVmZmFjOS8=","bitrate":219868,"codec_type":"h265","definition":"480p","encrypt":false,"file_hash":"b5279e87887825b6e87f9c0b6c5dca9d","logo_type":"xigua","main_url":"aHR0cDovL3Y5OS10dC5peGlndWEuY29tL2YyZjJkMmVhODI0NGRkZTEzZDZjMzVlYTIzYjY5OTIxLzViY2VkM2MwL3ZpZGVvL20vMjIwMzc4NjgyMzQ0Mjk1NGM0NjkzZmQ2MDRlYzg4ZDgwNDAxMTVkZTVmMjAwMDA5ZDMxMDI1ZmZhYzkv","preload_interval":45,"preload_max_step":10,"preload_min_step":5,"preload_size":327680,"quality":"normal","size":7092167,"socket_buffer":4946940,"spade_a":"","user_video_proxy":1,"vheight":480,"vtype":"mp4","vwidth":854},"video_3":{"backup_url_1":"aHR0cDovL3Y3LnBzdGF0cC5jb20vZmRkZThiN2Q5OWU3NzAzZGIwYzQ5ODU2MmU0NmFlOGMvNWJjZWQzYzAvdmlkZW8vbS8yMjBhODNlOWZmZWNmNDE0MTIyYTEwM2Y2MzUxMWEyODFkZDExNWUwYzRlMDAwMDFkZjc1OWMyYmViNC8=","bitrate":474147,"codec_type":"h265","definition":"720p","encrypt":false,"file_hash":"20040f3bff0bad4e4025560bc6eadbac","logo_type":"xigua","main_url":"aHR0cDovL3Y5OS10dC5peGlndWEuY29tL2Q2OWJjZmI2NDNmYTI3YWJlZGUxNjM1YzE2MzViYjNmLzViY2VkM2MwL3ZpZGVvL20vMjIwYTgzZTlmZmVjZjQxNDEyMmExMDNmNjM1MTFhMjgxZGQxMTVlMGM0ZTAwMDAxZGY3NTljMmJlYjQv","preload_interval":45,"preload_max_step":10,"preload_min_step":5,"preload_size":327680,"quality":"normal","size":13329628,"socket_buffer":10668240,"spade_a":"","user_video_proxy":1,"vheight":720,"vtype":"mp4","vwidth":1280}}}
         * message : success
         * total : 3
         */

        private int code;
        private DataBean data;
        private String message;
        private int total;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

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

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public static class DataBean {
            /**
             * auto_definition : 360p
             * enable_ssl : false
             * media_type : video
             * poster_url : http://p3.pstatp.com/origin/101300008d0015883d1e7
             * status : 10
             * user_id : toutiao
             * validate :
             * video_duration : 196.25
             * video_id : v020041d0000bf79tgclbum1rmmdp20g
             * video_list : {"video_1":{"backup_url_1":"aHR0cDovL3Y3LnBzdGF0cC5jb20vZjg5NzYwNzVjZDU4NTQ1MmZhNmQ3YWNlMjdiNTMxYmUvNWJjZWQzYzAvdmlkZW8vbS8yMjBjYTIxNTE4MzBlYmU0NDNlOTAxNDYwZGYxOTgyM2EzMzExNWRlMTg2MDAwMDhiNjQ2NGJjN2JiNC8=","bitrate":147951,"codec_type":"h265","definition":"360p","encrypt":false,"file_hash":"94c2e6bdd765145ba05069e4b1eb148c","logo_type":"xigua","main_url":"aHR0cDovL3Y5OS10dC5peGlndWEuY29tLzUwNTcyMjA4ZDM5NDUyN2ExMzQxNjBiN2RmYjNhZGZiLzViY2VkM2MwL3ZpZGVvL20vMjIwY2EyMTUxODMwZWJlNDQzZTkwMTQ2MGRmMTk4MjNhMzMxMTVkZTE4NjAwMDA4YjY0NjRiYzdiYjQv","preload_interval":45,"preload_max_step":10,"preload_min_step":5,"preload_size":327680,"quality":"normal","size":5328036,"socket_buffer":3328740,"spade_a":"","user_video_proxy":1,"vheight":360,"vtype":"mp4","vwidth":640},"video_2":{"backup_url_1":"aHR0cDovL3Y3LnBzdGF0cC5jb20vNTlkODRmYThkNmM3OTdiZThhZWE5YTIzMTM5YWFmNjUvNWJjZWQzYzAvdmlkZW8vbS8yMjAzNzg2ODIzNDQyOTU0YzQ2OTNmZDYwNGVjODhkODA0MDExNWRlNWYyMDAwMDlkMzEwMjVmZmFjOS8=","bitrate":219868,"codec_type":"h265","definition":"480p","encrypt":false,"file_hash":"b5279e87887825b6e87f9c0b6c5dca9d","logo_type":"xigua","main_url":"aHR0cDovL3Y5OS10dC5peGlndWEuY29tL2YyZjJkMmVhODI0NGRkZTEzZDZjMzVlYTIzYjY5OTIxLzViY2VkM2MwL3ZpZGVvL20vMjIwMzc4NjgyMzQ0Mjk1NGM0NjkzZmQ2MDRlYzg4ZDgwNDAxMTVkZTVmMjAwMDA5ZDMxMDI1ZmZhYzkv","preload_interval":45,"preload_max_step":10,"preload_min_step":5,"preload_size":327680,"quality":"normal","size":7092167,"socket_buffer":4946940,"spade_a":"","user_video_proxy":1,"vheight":480,"vtype":"mp4","vwidth":854},"video_3":{"backup_url_1":"aHR0cDovL3Y3LnBzdGF0cC5jb20vZmRkZThiN2Q5OWU3NzAzZGIwYzQ5ODU2MmU0NmFlOGMvNWJjZWQzYzAvdmlkZW8vbS8yMjBhODNlOWZmZWNmNDE0MTIyYTEwM2Y2MzUxMWEyODFkZDExNWUwYzRlMDAwMDFkZjc1OWMyYmViNC8=","bitrate":474147,"codec_type":"h265","definition":"720p","encrypt":false,"file_hash":"20040f3bff0bad4e4025560bc6eadbac","logo_type":"xigua","main_url":"aHR0cDovL3Y5OS10dC5peGlndWEuY29tL2Q2OWJjZmI2NDNmYTI3YWJlZGUxNjM1YzE2MzViYjNmLzViY2VkM2MwL3ZpZGVvL20vMjIwYTgzZTlmZmVjZjQxNDEyMmExMDNmNjM1MTFhMjgxZGQxMTVlMGM0ZTAwMDAxZGY3NTljMmJlYjQv","preload_interval":45,"preload_max_step":10,"preload_min_step":5,"preload_size":327680,"quality":"normal","size":13329628,"socket_buffer":10668240,"spade_a":"","user_video_proxy":1,"vheight":720,"vtype":"mp4","vwidth":1280}}
             */

            private String auto_definition;
            private boolean enable_ssl;
            private String media_type;
            private String poster_url;
            private int status;
            private String user_id;
            private String validate;
            private double video_duration;
            private String video_id;
            private VideoListBean video_list;

            public String getAuto_definition() {
                return auto_definition;
            }

            public void setAuto_definition(String auto_definition) {
                this.auto_definition = auto_definition;
            }

            public boolean isEnable_ssl() {
                return enable_ssl;
            }

            public void setEnable_ssl(boolean enable_ssl) {
                this.enable_ssl = enable_ssl;
            }

            public String getMedia_type() {
                return media_type;
            }

            public void setMedia_type(String media_type) {
                this.media_type = media_type;
            }

            public String getPoster_url() {
                return poster_url;
            }

            public void setPoster_url(String poster_url) {
                this.poster_url = poster_url;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getValidate() {
                return validate;
            }

            public void setValidate(String validate) {
                this.validate = validate;
            }

            public double getVideo_duration() {
                return video_duration;
            }

            public void setVideo_duration(double video_duration) {
                this.video_duration = video_duration;
            }

            public String getVideo_id() {
                return video_id;
            }

            public void setVideo_id(String video_id) {
                this.video_id = video_id;
            }

            public VideoListBean getVideo_list() {
                return video_list;
            }

            public void setVideo_list(VideoListBean video_list) {
                this.video_list = video_list;
            }

            public static class VideoListBean {
                /**
                 * video_1 : {"backup_url_1":"aHR0cDovL3Y3LnBzdGF0cC5jb20vZjg5NzYwNzVjZDU4NTQ1MmZhNmQ3YWNlMjdiNTMxYmUvNWJjZWQzYzAvdmlkZW8vbS8yMjBjYTIxNTE4MzBlYmU0NDNlOTAxNDYwZGYxOTgyM2EzMzExNWRlMTg2MDAwMDhiNjQ2NGJjN2JiNC8=","bitrate":147951,"codec_type":"h265","definition":"360p","encrypt":false,"file_hash":"94c2e6bdd765145ba05069e4b1eb148c","logo_type":"xigua","main_url":"aHR0cDovL3Y5OS10dC5peGlndWEuY29tLzUwNTcyMjA4ZDM5NDUyN2ExMzQxNjBiN2RmYjNhZGZiLzViY2VkM2MwL3ZpZGVvL20vMjIwY2EyMTUxODMwZWJlNDQzZTkwMTQ2MGRmMTk4MjNhMzMxMTVkZTE4NjAwMDA4YjY0NjRiYzdiYjQv","preload_interval":45,"preload_max_step":10,"preload_min_step":5,"preload_size":327680,"quality":"normal","size":5328036,"socket_buffer":3328740,"spade_a":"","user_video_proxy":1,"vheight":360,"vtype":"mp4","vwidth":640}
                 * video_2 : {"backup_url_1":"aHR0cDovL3Y3LnBzdGF0cC5jb20vNTlkODRmYThkNmM3OTdiZThhZWE5YTIzMTM5YWFmNjUvNWJjZWQzYzAvdmlkZW8vbS8yMjAzNzg2ODIzNDQyOTU0YzQ2OTNmZDYwNGVjODhkODA0MDExNWRlNWYyMDAwMDlkMzEwMjVmZmFjOS8=","bitrate":219868,"codec_type":"h265","definition":"480p","encrypt":false,"file_hash":"b5279e87887825b6e87f9c0b6c5dca9d","logo_type":"xigua","main_url":"aHR0cDovL3Y5OS10dC5peGlndWEuY29tL2YyZjJkMmVhODI0NGRkZTEzZDZjMzVlYTIzYjY5OTIxLzViY2VkM2MwL3ZpZGVvL20vMjIwMzc4NjgyMzQ0Mjk1NGM0NjkzZmQ2MDRlYzg4ZDgwNDAxMTVkZTVmMjAwMDA5ZDMxMDI1ZmZhYzkv","preload_interval":45,"preload_max_step":10,"preload_min_step":5,"preload_size":327680,"quality":"normal","size":7092167,"socket_buffer":4946940,"spade_a":"","user_video_proxy":1,"vheight":480,"vtype":"mp4","vwidth":854}
                 * video_3 : {"backup_url_1":"aHR0cDovL3Y3LnBzdGF0cC5jb20vZmRkZThiN2Q5OWU3NzAzZGIwYzQ5ODU2MmU0NmFlOGMvNWJjZWQzYzAvdmlkZW8vbS8yMjBhODNlOWZmZWNmNDE0MTIyYTEwM2Y2MzUxMWEyODFkZDExNWUwYzRlMDAwMDFkZjc1OWMyYmViNC8=","bitrate":474147,"codec_type":"h265","definition":"720p","encrypt":false,"file_hash":"20040f3bff0bad4e4025560bc6eadbac","logo_type":"xigua","main_url":"aHR0cDovL3Y5OS10dC5peGlndWEuY29tL2Q2OWJjZmI2NDNmYTI3YWJlZGUxNjM1YzE2MzViYjNmLzViY2VkM2MwL3ZpZGVvL20vMjIwYTgzZTlmZmVjZjQxNDEyMmExMDNmNjM1MTFhMjgxZGQxMTVlMGM0ZTAwMDAxZGY3NTljMmJlYjQv","preload_interval":45,"preload_max_step":10,"preload_min_step":5,"preload_size":327680,"quality":"normal","size":13329628,"socket_buffer":10668240,"spade_a":"","user_video_proxy":1,"vheight":720,"vtype":"mp4","vwidth":1280}
                 */

                private Video1Bean video_1;
                private Video2Bean video_2;
                private Video3Bean video_3;

                public Video1Bean getVideo_1() {
                    return video_1;
                }

                public void setVideo_1(Video1Bean video_1) {
                    this.video_1 = video_1;
                }

                public Video2Bean getVideo_2() {
                    return video_2;
                }

                public void setVideo_2(Video2Bean video_2) {
                    this.video_2 = video_2;
                }

                public Video3Bean getVideo_3() {
                    return video_3;
                }

                public void setVideo_3(Video3Bean video_3) {
                    this.video_3 = video_3;
                }

                public static class Video1Bean {
                    /**
                     * backup_url_1 : aHR0cDovL3Y3LnBzdGF0cC5jb20vZjg5NzYwNzVjZDU4NTQ1MmZhNmQ3YWNlMjdiNTMxYmUvNWJjZWQzYzAvdmlkZW8vbS8yMjBjYTIxNTE4MzBlYmU0NDNlOTAxNDYwZGYxOTgyM2EzMzExNWRlMTg2MDAwMDhiNjQ2NGJjN2JiNC8=
                     * bitrate : 147951
                     * codec_type : h265
                     * definition : 360p
                     * encrypt : false
                     * file_hash : 94c2e6bdd765145ba05069e4b1eb148c
                     * logo_type : xigua
                     * main_url : aHR0cDovL3Y5OS10dC5peGlndWEuY29tLzUwNTcyMjA4ZDM5NDUyN2ExMzQxNjBiN2RmYjNhZGZiLzViY2VkM2MwL3ZpZGVvL20vMjIwY2EyMTUxODMwZWJlNDQzZTkwMTQ2MGRmMTk4MjNhMzMxMTVkZTE4NjAwMDA4YjY0NjRiYzdiYjQv
                     * preload_interval : 45
                     * preload_max_step : 10
                     * preload_min_step : 5
                     * preload_size : 327680
                     * quality : normal
                     * size : 5328036
                     * socket_buffer : 3328740
                     * spade_a :
                     * user_video_proxy : 1
                     * vheight : 360
                     * vtype : mp4
                     * vwidth : 640
                     */

                    private String backup_url_1;
                    private int bitrate;
                    private String codec_type;
                    private String definition;
                    private boolean encrypt;
                    private String file_hash;
                    private String logo_type;
                    private String main_url;
                    private int preload_interval;
                    private int preload_max_step;
                    private int preload_min_step;
                    private int preload_size;
                    private String quality;
                    private int size;
                    private int socket_buffer;
                    private String spade_a;
                    private int user_video_proxy;
                    private int vheight;
                    private String vtype;
                    private int vwidth;

                    public String getBackup_url_1() {
                        return backup_url_1;
                    }

                    public void setBackup_url_1(String backup_url_1) {
                        this.backup_url_1 = backup_url_1;
                    }

                    public int getBitrate() {
                        return bitrate;
                    }

                    public void setBitrate(int bitrate) {
                        this.bitrate = bitrate;
                    }

                    public String getCodec_type() {
                        return codec_type;
                    }

                    public void setCodec_type(String codec_type) {
                        this.codec_type = codec_type;
                    }

                    public String getDefinition() {
                        return definition;
                    }

                    public void setDefinition(String definition) {
                        this.definition = definition;
                    }

                    public boolean isEncrypt() {
                        return encrypt;
                    }

                    public void setEncrypt(boolean encrypt) {
                        this.encrypt = encrypt;
                    }

                    public String getFile_hash() {
                        return file_hash;
                    }

                    public void setFile_hash(String file_hash) {
                        this.file_hash = file_hash;
                    }

                    public String getLogo_type() {
                        return logo_type;
                    }

                    public void setLogo_type(String logo_type) {
                        this.logo_type = logo_type;
                    }

                    public String getMain_url() {
                        return main_url;
                    }

                    public void setMain_url(String main_url) {
                        this.main_url = main_url;
                    }

                    public int getPreload_interval() {
                        return preload_interval;
                    }

                    public void setPreload_interval(int preload_interval) {
                        this.preload_interval = preload_interval;
                    }

                    public int getPreload_max_step() {
                        return preload_max_step;
                    }

                    public void setPreload_max_step(int preload_max_step) {
                        this.preload_max_step = preload_max_step;
                    }

                    public int getPreload_min_step() {
                        return preload_min_step;
                    }

                    public void setPreload_min_step(int preload_min_step) {
                        this.preload_min_step = preload_min_step;
                    }

                    public int getPreload_size() {
                        return preload_size;
                    }

                    public void setPreload_size(int preload_size) {
                        this.preload_size = preload_size;
                    }

                    public String getQuality() {
                        return quality;
                    }

                    public void setQuality(String quality) {
                        this.quality = quality;
                    }

                    public int getSize() {
                        return size;
                    }

                    public void setSize(int size) {
                        this.size = size;
                    }

                    public int getSocket_buffer() {
                        return socket_buffer;
                    }

                    public void setSocket_buffer(int socket_buffer) {
                        this.socket_buffer = socket_buffer;
                    }

                    public String getSpade_a() {
                        return spade_a;
                    }

                    public void setSpade_a(String spade_a) {
                        this.spade_a = spade_a;
                    }

                    public int getUser_video_proxy() {
                        return user_video_proxy;
                    }

                    public void setUser_video_proxy(int user_video_proxy) {
                        this.user_video_proxy = user_video_proxy;
                    }

                    public int getVheight() {
                        return vheight;
                    }

                    public void setVheight(int vheight) {
                        this.vheight = vheight;
                    }

                    public String getVtype() {
                        return vtype;
                    }

                    public void setVtype(String vtype) {
                        this.vtype = vtype;
                    }

                    public int getVwidth() {
                        return vwidth;
                    }

                    public void setVwidth(int vwidth) {
                        this.vwidth = vwidth;
                    }
                }

                public static class Video2Bean {
                    /**
                     * backup_url_1 : aHR0cDovL3Y3LnBzdGF0cC5jb20vNTlkODRmYThkNmM3OTdiZThhZWE5YTIzMTM5YWFmNjUvNWJjZWQzYzAvdmlkZW8vbS8yMjAzNzg2ODIzNDQyOTU0YzQ2OTNmZDYwNGVjODhkODA0MDExNWRlNWYyMDAwMDlkMzEwMjVmZmFjOS8=
                     * bitrate : 219868
                     * codec_type : h265
                     * definition : 480p
                     * encrypt : false
                     * file_hash : b5279e87887825b6e87f9c0b6c5dca9d
                     * logo_type : xigua
                     * main_url : aHR0cDovL3Y5OS10dC5peGlndWEuY29tL2YyZjJkMmVhODI0NGRkZTEzZDZjMzVlYTIzYjY5OTIxLzViY2VkM2MwL3ZpZGVvL20vMjIwMzc4NjgyMzQ0Mjk1NGM0NjkzZmQ2MDRlYzg4ZDgwNDAxMTVkZTVmMjAwMDA5ZDMxMDI1ZmZhYzkv
                     * preload_interval : 45
                     * preload_max_step : 10
                     * preload_min_step : 5
                     * preload_size : 327680
                     * quality : normal
                     * size : 7092167
                     * socket_buffer : 4946940
                     * spade_a :
                     * user_video_proxy : 1
                     * vheight : 480
                     * vtype : mp4
                     * vwidth : 854
                     */

                    private String backup_url_1;
                    private int bitrate;
                    private String codec_type;
                    private String definition;
                    private boolean encrypt;
                    private String file_hash;
                    private String logo_type;
                    private String main_url;
                    private int preload_interval;
                    private int preload_max_step;
                    private int preload_min_step;
                    private int preload_size;
                    private String quality;
                    private int size;
                    private int socket_buffer;
                    private String spade_a;
                    private int user_video_proxy;
                    private int vheight;
                    private String vtype;
                    private int vwidth;

                    public String getBackup_url_1() {
                        return backup_url_1;
                    }

                    public void setBackup_url_1(String backup_url_1) {
                        this.backup_url_1 = backup_url_1;
                    }

                    public int getBitrate() {
                        return bitrate;
                    }

                    public void setBitrate(int bitrate) {
                        this.bitrate = bitrate;
                    }

                    public String getCodec_type() {
                        return codec_type;
                    }

                    public void setCodec_type(String codec_type) {
                        this.codec_type = codec_type;
                    }

                    public String getDefinition() {
                        return definition;
                    }

                    public void setDefinition(String definition) {
                        this.definition = definition;
                    }

                    public boolean isEncrypt() {
                        return encrypt;
                    }

                    public void setEncrypt(boolean encrypt) {
                        this.encrypt = encrypt;
                    }

                    public String getFile_hash() {
                        return file_hash;
                    }

                    public void setFile_hash(String file_hash) {
                        this.file_hash = file_hash;
                    }

                    public String getLogo_type() {
                        return logo_type;
                    }

                    public void setLogo_type(String logo_type) {
                        this.logo_type = logo_type;
                    }

                    public String getMain_url() {
                        return main_url;
                    }

                    public void setMain_url(String main_url) {
                        this.main_url = main_url;
                    }

                    public int getPreload_interval() {
                        return preload_interval;
                    }

                    public void setPreload_interval(int preload_interval) {
                        this.preload_interval = preload_interval;
                    }

                    public int getPreload_max_step() {
                        return preload_max_step;
                    }

                    public void setPreload_max_step(int preload_max_step) {
                        this.preload_max_step = preload_max_step;
                    }

                    public int getPreload_min_step() {
                        return preload_min_step;
                    }

                    public void setPreload_min_step(int preload_min_step) {
                        this.preload_min_step = preload_min_step;
                    }

                    public int getPreload_size() {
                        return preload_size;
                    }

                    public void setPreload_size(int preload_size) {
                        this.preload_size = preload_size;
                    }

                    public String getQuality() {
                        return quality;
                    }

                    public void setQuality(String quality) {
                        this.quality = quality;
                    }

                    public int getSize() {
                        return size;
                    }

                    public void setSize(int size) {
                        this.size = size;
                    }

                    public int getSocket_buffer() {
                        return socket_buffer;
                    }

                    public void setSocket_buffer(int socket_buffer) {
                        this.socket_buffer = socket_buffer;
                    }

                    public String getSpade_a() {
                        return spade_a;
                    }

                    public void setSpade_a(String spade_a) {
                        this.spade_a = spade_a;
                    }

                    public int getUser_video_proxy() {
                        return user_video_proxy;
                    }

                    public void setUser_video_proxy(int user_video_proxy) {
                        this.user_video_proxy = user_video_proxy;
                    }

                    public int getVheight() {
                        return vheight;
                    }

                    public void setVheight(int vheight) {
                        this.vheight = vheight;
                    }

                    public String getVtype() {
                        return vtype;
                    }

                    public void setVtype(String vtype) {
                        this.vtype = vtype;
                    }

                    public int getVwidth() {
                        return vwidth;
                    }

                    public void setVwidth(int vwidth) {
                        this.vwidth = vwidth;
                    }
                }

                public static class Video3Bean {
                    /**
                     * backup_url_1 : aHR0cDovL3Y3LnBzdGF0cC5jb20vZmRkZThiN2Q5OWU3NzAzZGIwYzQ5ODU2MmU0NmFlOGMvNWJjZWQzYzAvdmlkZW8vbS8yMjBhODNlOWZmZWNmNDE0MTIyYTEwM2Y2MzUxMWEyODFkZDExNWUwYzRlMDAwMDFkZjc1OWMyYmViNC8=
                     * bitrate : 474147
                     * codec_type : h265
                     * definition : 720p
                     * encrypt : false
                     * file_hash : 20040f3bff0bad4e4025560bc6eadbac
                     * logo_type : xigua
                     * main_url : aHR0cDovL3Y5OS10dC5peGlndWEuY29tL2Q2OWJjZmI2NDNmYTI3YWJlZGUxNjM1YzE2MzViYjNmLzViY2VkM2MwL3ZpZGVvL20vMjIwYTgzZTlmZmVjZjQxNDEyMmExMDNmNjM1MTFhMjgxZGQxMTVlMGM0ZTAwMDAxZGY3NTljMmJlYjQv
                     * preload_interval : 45
                     * preload_max_step : 10
                     * preload_min_step : 5
                     * preload_size : 327680
                     * quality : normal
                     * size : 13329628
                     * socket_buffer : 10668240
                     * spade_a :
                     * user_video_proxy : 1
                     * vheight : 720
                     * vtype : mp4
                     * vwidth : 1280
                     */

                    private String backup_url_1;
                    private int bitrate;
                    private String codec_type;
                    private String definition;
                    private boolean encrypt;
                    private String file_hash;
                    private String logo_type;
                    private String main_url;
                    private int preload_interval;
                    private int preload_max_step;
                    private int preload_min_step;
                    private int preload_size;
                    private String quality;
                    private int size;
                    private int socket_buffer;
                    private String spade_a;
                    private int user_video_proxy;
                    private int vheight;
                    private String vtype;
                    private int vwidth;

                    public String getBackup_url_1() {
                        return backup_url_1;
                    }

                    public void setBackup_url_1(String backup_url_1) {
                        this.backup_url_1 = backup_url_1;
                    }

                    public int getBitrate() {
                        return bitrate;
                    }

                    public void setBitrate(int bitrate) {
                        this.bitrate = bitrate;
                    }

                    public String getCodec_type() {
                        return codec_type;
                    }

                    public void setCodec_type(String codec_type) {
                        this.codec_type = codec_type;
                    }

                    public String getDefinition() {
                        return definition;
                    }

                    public void setDefinition(String definition) {
                        this.definition = definition;
                    }

                    public boolean isEncrypt() {
                        return encrypt;
                    }

                    public void setEncrypt(boolean encrypt) {
                        this.encrypt = encrypt;
                    }

                    public String getFile_hash() {
                        return file_hash;
                    }

                    public void setFile_hash(String file_hash) {
                        this.file_hash = file_hash;
                    }

                    public String getLogo_type() {
                        return logo_type;
                    }

                    public void setLogo_type(String logo_type) {
                        this.logo_type = logo_type;
                    }

                    public String getMain_url() {
                        return main_url;
                    }

                    public void setMain_url(String main_url) {
                        this.main_url = main_url;
                    }

                    public int getPreload_interval() {
                        return preload_interval;
                    }

                    public void setPreload_interval(int preload_interval) {
                        this.preload_interval = preload_interval;
                    }

                    public int getPreload_max_step() {
                        return preload_max_step;
                    }

                    public void setPreload_max_step(int preload_max_step) {
                        this.preload_max_step = preload_max_step;
                    }

                    public int getPreload_min_step() {
                        return preload_min_step;
                    }

                    public void setPreload_min_step(int preload_min_step) {
                        this.preload_min_step = preload_min_step;
                    }

                    public int getPreload_size() {
                        return preload_size;
                    }

                    public void setPreload_size(int preload_size) {
                        this.preload_size = preload_size;
                    }

                    public String getQuality() {
                        return quality;
                    }

                    public void setQuality(String quality) {
                        this.quality = quality;
                    }

                    public int getSize() {
                        return size;
                    }

                    public void setSize(int size) {
                        this.size = size;
                    }

                    public int getSocket_buffer() {
                        return socket_buffer;
                    }

                    public void setSocket_buffer(int socket_buffer) {
                        this.socket_buffer = socket_buffer;
                    }

                    public String getSpade_a() {
                        return spade_a;
                    }

                    public void setSpade_a(String spade_a) {
                        this.spade_a = spade_a;
                    }

                    public int getUser_video_proxy() {
                        return user_video_proxy;
                    }

                    public void setUser_video_proxy(int user_video_proxy) {
                        this.user_video_proxy = user_video_proxy;
                    }

                    public int getVheight() {
                        return vheight;
                    }

                    public void setVheight(int vheight) {
                        this.vheight = vheight;
                    }

                    public String getVtype() {
                        return vtype;
                    }

                    public void setVtype(String vtype) {
                        this.vtype = vtype;
                    }

                    public int getVwidth() {
                        return vwidth;
                    }

                    public void setVwidth(int vwidth) {
                        this.vwidth = vwidth;
                    }
                }
            }
        }
    }
}
