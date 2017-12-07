package ren.jieshu.jieshuren.entity;

import java.io.Serializable;

/**
 * Author: shinianPan on 2017/10/30.
 * email : snow_psn@163.com
 */

public class DoubanBook implements Serializable {

    private String subtitle;
    private String pubdate;
    private String origin_title;
    private String image;
    private Images images;
    private String binding;
    private String catalog;
    private String pages;
    private String id;
    private String publisher;
    private String isbn10;
    private String isbn13;
    private String title;
    private String summary;
    private String author[];

    private int is_apply;





    public int getIs_apply() {
        return is_apply;
    }
    public void setIs_apply(int is_apply) {
        this.is_apply = is_apply;
    }
    private String author_intro;



    public String getAuthor_intro() {
        return author_intro;
    }
    public void setAuthor_intro(String author_intro) {
        this.author_intro = author_intro;
    }
    private String price;

    private Rating rating;





    public Rating getRating() {
        return rating;
    }
    public void setRating(Rating rating) {
        this.rating = rating;
    }
    public Images getImages() {
        return images;
    }
    public void setImages(Images images) {
        this.images = images;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String[] getAuthor() {
        return author;
    }
    public void setAuthor(String[] author) {
        this.author = author;
    }
    public String getSubtitle() {
        return subtitle;
    }
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
    public String getPubdate() {
        return pubdate;
    }
    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }
    public String getOrigin_title() {
        return origin_title;
    }
    public void setOrigin_title(String origin_title) {
        this.origin_title = origin_title;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getBinding() {
        return binding;
    }
    public void setBinding(String binding) {
        this.binding = binding;
    }
    public String getCatalog() {
        return catalog;
    }
    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }
    public String getPages() {
        return pages;
    }
    public void setPages(String pages) {
        this.pages = pages;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public String getIsbn10() {
        return isbn10;
    }
    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }
    public String getIsbn13() {
        return isbn13;
    }
    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
}
