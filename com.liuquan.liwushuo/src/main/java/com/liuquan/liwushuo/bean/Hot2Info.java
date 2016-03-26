package com.liuquan.liwushuo.bean;

import java.util.List;

/**
 * Created by PC on 2016/3/25.
 */
public class Hot2Info {

    /**
     * code : 200
     * data : {"authentic":{"desc":"精品正选，假货额外赔付100%","url":"http://www.liwushuo.com/items/authentic/desc"},"category_id":7,"comments_count":7,"cover_image_url":"http://img02.liwushuo.com/image/160107/7ffsehwpy.jpg-w720","cover_webp_url":"http://img02.liwushuo.com/image/160107/7ffsehwpy.jpg?imageView2/0/w/720/q/85/format/webp","created_at":1452223068,"description":"手动翻动的日历，靓丽的糖果色，让每天都有好心情，摆放在桌面上，不仅是台历，也是一款很好的装饰品。每天自己去翻动它，让日子过的更有意义，更真实。","detail_html":"","editor_id":1024,"favorited":false,"favorites_count":3408,"id":1047476,"image_urls":["http://img03.liwushuo.com/image/160107/7ffsehwpy.jpg-w720"],"liked":false,"likes_count":3408,"name":"糖果色办公家居日用桌面台历","post_ids":[],"price":"60.42","purchase_id":"21081236016","purchase_shop_id":"68996670","purchase_status":1,"purchase_type":2,"purchase_url":"http://s.click.taobao.com/t?sche=liwushuo&e=m%3D2%26s%3DkRqAtAx%2B%2FfocQipKwQzePOeEDrYVVa64yK8Cckff7TVRAdhuF14FMc5Fc7KCqFUjMMgx22UI05atgmtnxDX9deVMA5qBABUs5mPg1WiM1P5OS0OzHKBZzW1e2y4p13L5dr06RTYsuTCZ%2F6zV%2FRv7ZYn29e9fI627xgxdTc00KD8%3D","shares_count":41,"source":{"button_title":"去天猫购买","name":"天猫","page_title":"商品详情","type":"tmall"},"subcategory_id":53,"updated_at":1452223068,"url":"http://www.liwushuo.com/items/1047476","webp_urls":["http://img03.liwushuo.com/image/160107/7ffsehwpy.jpg?imageView2/0/w/720/q/85/format/webp"]}
     * message : OK
     */

    private int code;
    /**
     * authentic : {"desc":"精品正选，假货额外赔付100%","url":"http://www.liwushuo.com/items/authentic/desc"}
     * category_id : 7
     * comments_count : 7
     * cover_image_url : http://img02.liwushuo.com/image/160107/7ffsehwpy.jpg-w720
     * cover_webp_url : http://img02.liwushuo.com/image/160107/7ffsehwpy.jpg?imageView2/0/w/720/q/85/format/webp
     * created_at : 1452223068
     * description : 手动翻动的日历，靓丽的糖果色，让每天都有好心情，摆放在桌面上，不仅是台历，也是一款很好的装饰品。每天自己去翻动它，让日子过的更有意义，更真实。
     * detail_html :
     * editor_id : 1024
     * favorited : false
     * favorites_count : 3408
     * id : 1047476
     * image_urls : ["http://img03.liwushuo.com/image/160107/7ffsehwpy.jpg-w720"]
     * liked : false
     * likes_count : 3408
     * name : 糖果色办公家居日用桌面台历
     * post_ids : []
     * price : 60.42
     * purchase_id : 21081236016
     * purchase_shop_id : 68996670
     * purchase_status : 1
     * purchase_type : 2
     * purchase_url : http://s.click.taobao.com/t?sche=liwushuo&e=m%3D2%26s%3DkRqAtAx%2B%2FfocQipKwQzePOeEDrYVVa64yK8Cckff7TVRAdhuF14FMc5Fc7KCqFUjMMgx22UI05atgmtnxDX9deVMA5qBABUs5mPg1WiM1P5OS0OzHKBZzW1e2y4p13L5dr06RTYsuTCZ%2F6zV%2FRv7ZYn29e9fI627xgxdTc00KD8%3D
     * shares_count : 41
     * source : {"button_title":"去天猫购买","name":"天猫","page_title":"商品详情","type":"tmall"}
     * subcategory_id : 53
     * updated_at : 1452223068
     * url : http://www.liwushuo.com/items/1047476
     * webp_urls : ["http://img03.liwushuo.com/image/160107/7ffsehwpy.jpg?imageView2/0/w/720/q/85/format/webp"]
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
         * desc : 精品正选，假货额外赔付100%
         * url : http://www.liwushuo.com/items/authentic/desc
         */

        private AuthenticEntity authentic;
        private int category_id;
        private int comments_count;
        private String cover_image_url;
        private String cover_webp_url;
        private int created_at;
        private String description;
        private String detail_html;
        private int editor_id;
        private boolean favorited;
        private int favorites_count;
        private int id;
        private boolean liked;
        private int likes_count;
        private String name;
        private String price;
        private String purchase_id;
        private String purchase_shop_id;
        private int purchase_status;
        private int purchase_type;
        private String purchase_url;
        private int shares_count;
        /**
         * button_title : 去天猫购买
         * name : 天猫
         * page_title : 商品详情
         * type : tmall
         */

        private SourceEntity source;
        private int subcategory_id;
        private int updated_at;
        private String url;
        private List<String> image_urls;
        private List<?> post_ids;
        private List<String> webp_urls;

        public void setAuthentic(AuthenticEntity authentic) {
            this.authentic = authentic;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public void setComments_count(int comments_count) {
            this.comments_count = comments_count;
        }

        public void setCover_image_url(String cover_image_url) {
            this.cover_image_url = cover_image_url;
        }

        public void setCover_webp_url(String cover_webp_url) {
            this.cover_webp_url = cover_webp_url;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setDetail_html(String detail_html) {
            this.detail_html = detail_html;
        }

        public void setEditor_id(int editor_id) {
            this.editor_id = editor_id;
        }

        public void setFavorited(boolean favorited) {
            this.favorited = favorited;
        }

        public void setFavorites_count(int favorites_count) {
            this.favorites_count = favorites_count;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setLiked(boolean liked) {
            this.liked = liked;
        }

        public void setLikes_count(int likes_count) {
            this.likes_count = likes_count;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setPurchase_id(String purchase_id) {
            this.purchase_id = purchase_id;
        }

        public void setPurchase_shop_id(String purchase_shop_id) {
            this.purchase_shop_id = purchase_shop_id;
        }

        public void setPurchase_status(int purchase_status) {
            this.purchase_status = purchase_status;
        }

        public void setPurchase_type(int purchase_type) {
            this.purchase_type = purchase_type;
        }

        public void setPurchase_url(String purchase_url) {
            this.purchase_url = purchase_url;
        }

        public void setShares_count(int shares_count) {
            this.shares_count = shares_count;
        }

        public void setSource(SourceEntity source) {
            this.source = source;
        }

        public void setSubcategory_id(int subcategory_id) {
            this.subcategory_id = subcategory_id;
        }

        public void setUpdated_at(int updated_at) {
            this.updated_at = updated_at;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setImage_urls(List<String> image_urls) {
            this.image_urls = image_urls;
        }

        public void setPost_ids(List<?> post_ids) {
            this.post_ids = post_ids;
        }

        public void setWebp_urls(List<String> webp_urls) {
            this.webp_urls = webp_urls;
        }

        public AuthenticEntity getAuthentic() {
            return authentic;
        }

        public int getCategory_id() {
            return category_id;
        }

        public int getComments_count() {
            return comments_count;
        }

        public String getCover_image_url() {
            return cover_image_url;
        }

        public String getCover_webp_url() {
            return cover_webp_url;
        }

        public int getCreated_at() {
            return created_at;
        }

        public String getDescription() {
            return description;
        }

        public String getDetail_html() {
            return detail_html;
        }

        public int getEditor_id() {
            return editor_id;
        }

        public boolean isFavorited() {
            return favorited;
        }

        public int getFavorites_count() {
            return favorites_count;
        }

        public int getId() {
            return id;
        }

        public boolean isLiked() {
            return liked;
        }

        public int getLikes_count() {
            return likes_count;
        }

        public String getName() {
            return name;
        }

        public String getPrice() {
            return price;
        }

        public String getPurchase_id() {
            return purchase_id;
        }

        public String getPurchase_shop_id() {
            return purchase_shop_id;
        }

        public int getPurchase_status() {
            return purchase_status;
        }

        public int getPurchase_type() {
            return purchase_type;
        }

        public String getPurchase_url() {
            return purchase_url;
        }

        public int getShares_count() {
            return shares_count;
        }

        public SourceEntity getSource() {
            return source;
        }

        public int getSubcategory_id() {
            return subcategory_id;
        }

        public int getUpdated_at() {
            return updated_at;
        }

        public String getUrl() {
            return url;
        }

        public List<String> getImage_urls() {
            return image_urls;
        }

        public List<?> getPost_ids() {
            return post_ids;
        }

        public List<String> getWebp_urls() {
            return webp_urls;
        }

        public static class AuthenticEntity {
            private String desc;
            private String url;

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getDesc() {
                return desc;
            }

            public String getUrl() {
                return url;
            }
        }

        public static class SourceEntity {
            private String button_title;
            private String name;
            private String page_title;
            private String type;

            public void setButton_title(String button_title) {
                this.button_title = button_title;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setPage_title(String page_title) {
                this.page_title = page_title;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getButton_title() {
                return button_title;
            }

            public String getName() {
                return name;
            }

            public String getPage_title() {
                return page_title;
            }

            public String getType() {
                return type;
            }
        }
    }
}
