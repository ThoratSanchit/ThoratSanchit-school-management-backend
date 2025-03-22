package com.jwt.implementation.model;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "fee_categories")
public class FeeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "description")
    private String description;
}