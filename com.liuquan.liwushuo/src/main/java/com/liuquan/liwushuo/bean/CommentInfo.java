package com.liuquan.liwushuo.bean;

import java.util.List;

/**
 * Created by PC on 2016/3/29.
 */
public class CommentInfo {

    /**
     * code : 200
     * data : {"comments":[{"content":"蓝魅2可以用嘛","created_at":1456975497,"id":437582,"item_id":1030814,"show":true,"status":1,"user":{"avatar_url":"http://img02.liwushuo.com/avatar/160227/4567b9bd3_a-w180","can_mobile_login":false,"id":1729101,"nickname":"         后来阿","role":0}},{"content":"苹果专用","created_at":1456044130,"id":423971,"item_id":1030814,"replied_comment":{"content":"小米可以用吗？","created_at":1445053504,"id":262172,"item_id":1030814,"show":true,"status":1,"user_id":5862143},"replied_user":{"avatar_url":"http://img03.liwushuo.com/avatar/151229/4efd42363_a-w180","can_mobile_login":false,"id":5862143,"nickname":"无所谓","role":0},"reply_to_id":262172,"show":true,"status":1,"user":{"avatar_url":"http://img01.liwushuo.com/avatar/20160221/obigic45t_i.png-w180","can_mobile_login":false,"id":6753239,"nickname":"ㅎㅎㅎㅎㅎ","role":0}},{"content":"小米可以用吗？","created_at":1445053504,"id":262172,"item_id":1030814,"show":true,"status":1,"user":{"avatar_url":"http://img02.liwushuo.com/avatar/151229/4efd42363_a-w180","can_mobile_login":false,"id":5862143,"nickname":"无所谓","role":0}},{"content":"是任机形吗？","created_at":1443106999,"id":237065,"item_id":1030814,"show":true,"status":1,"user":{"avatar_url":"http://img02.liwushuo.com/avatar/150305/29ee97c1c_a.png-w180","can_mobile_login":false,"id":1490679,"nickname":"海清和雁*^o^*","role":0}},{"content":"不是爱疯可用吗","created_at":1442806809,"id":234140,"item_id":1030814,"show":true,"status":1,"user":{"avatar_url":"http://img02.liwushuo.com/avatar/151206/42c2ea646_a-w180","can_mobile_login":false,"id":5679552,"nickname":"西","role":0}},{"content":"这个壳子超美，我就是这个","created_at":1442065093,"id":225371,"item_id":1030814,"show":true,"status":1,"user":{"avatar_url":"http://img03.liwushuo.com/avatar/150820/7e3d04401_a.png-w180","can_mobile_login":false,"id":5325473,"nickname":"！","role":0}},{"content":"iPhone5可以用吗","created_at":1441960718,"id":223018,"item_id":1030814,"show":true,"status":1,"user":{"avatar_url":"http://img02.liwushuo.com/avatar/20160122/wynmtgk8h_i.png-w180","can_mobile_login":false,"id":5133463,"nickname":"血染长白","role":0}},{"content":"好看","created_at":1440559598,"id":188392,"item_id":1030814,"show":true,"status":1,"user":{"avatar_url":"http://img02.liwushuo.com/avatar/20150407/fytgeu47k_i.png-w180","can_mobile_login":true,"id":1477011,"nickname":"一见如故生万千欢喜心","role":0}}],"paging":{"next_url":"http://api.liwushuo.com/v2/items/1030814/comments?limit=20&offset=20"}}
     * message : OK
     */

    private int code;
    /**
     * comments : [{"content":"蓝魅2可以用嘛","created_at":1456975497,"id":437582,"item_id":1030814,"show":true,"status":1,"user":{"avatar_url":"http://img02.liwushuo.com/avatar/160227/4567b9bd3_a-w180","can_mobile_login":false,"id":1729101,"nickname":"         后来阿","role":0}},{"content":"苹果专用","created_at":1456044130,"id":423971,"item_id":1030814,"replied_comment":{"content":"小米可以用吗？","created_at":1445053504,"id":262172,"item_id":1030814,"show":true,"status":1,"user_id":5862143},"replied_user":{"avatar_url":"http://img03.liwushuo.com/avatar/151229/4efd42363_a-w180","can_mobile_login":false,"id":5862143,"nickname":"无所谓","role":0},"reply_to_id":262172,"show":true,"status":1,"user":{"avatar_url":"http://img01.liwushuo.com/avatar/20160221/obigic45t_i.png-w180","can_mobile_login":false,"id":6753239,"nickname":"ㅎㅎㅎㅎㅎ","role":0}},{"content":"小米可以用吗？","created_at":1445053504,"id":262172,"item_id":1030814,"show":true,"status":1,"user":{"avatar_url":"http://img02.liwushuo.com/avatar/151229/4efd42363_a-w180","can_mobile_login":false,"id":5862143,"nickname":"无所谓","role":0}},{"content":"是任机形吗？","created_at":1443106999,"id":237065,"item_id":1030814,"show":true,"status":1,"user":{"avatar_url":"http://img02.liwushuo.com/avatar/150305/29ee97c1c_a.png-w180","can_mobile_login":false,"id":1490679,"nickname":"海清和雁*^o^*","role":0}},{"content":"不是爱疯可用吗","created_at":1442806809,"id":234140,"item_id":1030814,"show":true,"status":1,"user":{"avatar_url":"http://img02.liwushuo.com/avatar/151206/42c2ea646_a-w180","can_mobile_login":false,"id":5679552,"nickname":"西","role":0}},{"content":"这个壳子超美，我就是这个","created_at":1442065093,"id":225371,"item_id":1030814,"show":true,"status":1,"user":{"avatar_url":"http://img03.liwushuo.com/avatar/150820/7e3d04401_a.png-w180","can_mobile_login":false,"id":5325473,"nickname":"！","role":0}},{"content":"iPhone5可以用吗","created_at":1441960718,"id":223018,"item_id":1030814,"show":true,"status":1,"user":{"avatar_url":"http://img02.liwushuo.com/avatar/20160122/wynmtgk8h_i.png-w180","can_mobile_login":false,"id":5133463,"nickname":"血染长白","role":0}},{"content":"好看","created_at":1440559598,"id":188392,"item_id":1030814,"show":true,"status":1,"user":{"avatar_url":"http://img02.liwushuo.com/avatar/20150407/fytgeu47k_i.png-w180","can_mobile_login":true,"id":1477011,"nickname":"一见如故生万千欢喜心","role":0}}]
     * paging : {"next_url":"http://api.liwushuo.com/v2/items/1030814/comments?limit=20&offset=20"}
     */

    private DataEntity data;
    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static class DataEntity {
        /**
         * next_url : http://api.liwushuo.com/v2/items/1030814/comments?limit=20&offset=20
         */

        private PagingEntity paging;
        /**
         * content : 蓝魅2可以用嘛
         * created_at : 1456975497
         * id : 437582
         * item_id : 1030814
         * show : true
         * status : 1
         * user : {"avatar_url":"http://img02.liwushuo.com/avatar/160227/4567b9bd3_a-w180","can_mobile_login":false,"id":1729101,"nickname":"         后来阿","role":0}
         */

        private List<CommentsEntity> comments;

        public void setPaging(PagingEntity paging) {
            this.paging = paging;
        }

        public void setComments(List<CommentsEntity> comments) {
            this.comments = comments;
        }

        public PagingEntity getPaging() {
            return paging;
        }

        public List<CommentsEntity> getComments() {
            return comments;
        }

        public static class PagingEntity {
            private String next_url;

            public void setNext_url(String next_url) {
                this.next_url = next_url;
            }

            public String getNext_url() {
                return next_url;
            }
        }

        public static class CommentsEntity {
            private String content;
            private int created_at;
            private int id;
            private int item_id;
            private boolean show;
            private int status;
            /**
             * avatar_url : http://img02.liwushuo.com/avatar/160227/4567b9bd3_a-w180
             * can_mobile_login : false
             * id : 1729101
             * nickname :          后来阿
             * role : 0
             */

            private UserEntity user;

            public void setContent(String content) {
                this.content = content;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setItem_id(int item_id) {
                this.item_id = item_id;
            }

            public void setShow(boolean show) {
                this.show = show;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public void setUser(UserEntity user) {
                this.user = user;
            }

            public String getContent() {
                return content;
            }

            public int getCreated_at() {
                return created_at;
            }

            public int getId() {
                return id;
            }

            public int getItem_id() {
                return item_id;
            }

            public boolean isShow() {
                return show;
            }

            public int getStatus() {
                return status;
            }

            public UserEntity getUser() {
                return user;
            }

            public static class UserEntity {
                private String avatar_url;
                private boolean can_mobile_login;
                private int id;
                private String nickname;
                private int role;

                public void setAvatar_url(String avatar_url) {
                    this.avatar_url = avatar_url;
                }

                public void setCan_mobile_login(boolean can_mobile_login) {
                    this.can_mobile_login = can_mobile_login;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public void setRole(int role) {
                    this.role = role;
                }

                public String getAvatar_url() {
                    return avatar_url;
                }

                public boolean isCan_mobile_login() {
                    return can_mobile_login;
                }

                public int getId() {
                    return id;
                }

                public String getNickname() {
                    return nickname;
                }

                public int getRole() {
                    return role;
                }
            }
        }
    }
}
