package com.payconiq.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;

/**
 * Model class of Stock having all required properties
 */
@Getter
@Setter
@Table(name="stock")
@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private long id;
    @Column(name="NAME")
    private String name;
    @Column(name="PRICE")
    private double currentPrice;
    @Column(name="UPDATED_ON")
    @UpdateTimestamp
    private OffsetDateTime lastUpdate;
}
