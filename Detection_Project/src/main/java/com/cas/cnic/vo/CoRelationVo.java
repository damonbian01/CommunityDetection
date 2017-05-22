package com.cas.cnic.vo;

/**
 * Created by ouxuan on 2017/5/22.
 */
public class CoRelationVo {
    /**
     * 合著关系id
     */
    private int id;
    /**
     * 合著关系的作者id
     */
    private int userId;
    /**
     * 合著关系的文章id
     */
    private int articleId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
}
