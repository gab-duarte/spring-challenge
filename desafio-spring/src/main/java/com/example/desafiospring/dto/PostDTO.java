package com.example.desafiospring.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDTO {

    private Long userId;
    private Long post_id;
    private LocalDate date;
    private ProductDTO detail;
    @NotNull(message = "Category is mandatory")
    private Integer category;
    @NotNull(message = "Price is mandatory")
    private Double price;
    private Boolean hasPromo;
    private Double discount;

    public PostDTO(Long userId, Long post_id, LocalDate date, ProductDTO detail, Integer category, Double price, Boolean hasPromo, Double discount) {
        this.userId = userId;
        this.post_id = post_id;
        this.date = date;
        this.detail = detail;
        this.category = category;
        this.price = price;
        this.hasPromo = hasPromo;
        this.discount = discount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPost_id() {
        return post_id;
    }

    public void setPost_id(Long post_id) {
        this.post_id = post_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ProductDTO getDetail() {
        return detail;
    }

    public void setDetail(ProductDTO detail) {
        this.detail = detail;
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
}
