package com.cas.cnic.vo;

/**
 * Created by ouxuan on 2017/5/22.
 */
public class ArticleVo {
    /**
     * 文章编号
     */
    private int id;
    /**
     * 文章名称
     */
    private String articleTitle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }
}
