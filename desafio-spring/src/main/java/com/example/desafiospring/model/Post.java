package com.example.desafiospring.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Post implements Comparable<Post> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;

    @ManyToOne
    private Seller seller;

    private final LocalDate date = LocalDate.now();

    @OneToOne
    private Product product;
    private Integer category;
    private Double price;
    private Boolean hasPromo;
    private Double discount;

    public Post(Long postId, Seller seller, Product product, Integer category, Double price, Boolean hasPromo, Double discount) {
        this.postId = postId;
        this.seller = seller;
        this.product = product;
        this.category = category;
        this.price = price;
        this.hasPromo = hasPromo;
        this.discount = discount;
    }

    public Post(Long postId, Seller seller, Product product, Integer category, Double price) {
        this.postId = postId;
        this.seller = seller;
        this.product = product;
        this.category = category;
        this.price = price;
    }

    public Post() {

    }

    public Long getPostId() {
        return postId;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public LocalDate getDate() {
        return date;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getHasPromo() {
        return hasPromo;
    }

    public void setHasPromo(Boolean hasPromo) {
        this.hasPromo = hasPromo;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", seller=" + seller +
                ", date=" + date +
                ", product=" + product +
                ", category=" + category +
                ", price=" + price +
                ", hasPromo=" + hasPromo +
                ", discount=" + discount +
                '}';
    }

    @Override
    public int compareTo(Post o) {
        return this.getDate().compareTo(o.getDate());
    }
}
