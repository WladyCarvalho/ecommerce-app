package com.expressive.springbootecommerce.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory category;

    @Column(name="sku")
    private String sku;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="unit_price")
    private BigDecimal unitPrice;

    @Column(name="image_url")
    private String imgUrl;

    @Column(name="active")
    private boolean active;

    @Column(name="units_in_stock")
    private int unitStock;

    @CreationTimestamp
    @Column(name="date_created")
    private  Date dateCreated;

    @UpdateTimestamp
    @Column(name="last_updated")
    private Date lastUpdated;
}
