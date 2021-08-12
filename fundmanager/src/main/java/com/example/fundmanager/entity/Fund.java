package com.example.fundmanager.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "fundId")
public class Fund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fundId;

    @Column(nullable = false)
    private String name;

    /**
     * Investment funds are managed by a single fund manager
     *
     * Each fund has a set of positions
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id")
    @JsonIdentityReference(alwaysAsId = true)
    private FundManager manager;

    public Fund() {
    }

    public Fund(String name, FundManager manager) {
        this.name = name;
        this.manager = manager;
    }

    public Fund(Long fundId, String name, FundManager manager) {
        this.fundId = fundId;
        this.name = name;
        this.manager = manager;
    }

    public Long getFundId() {
        return fundId;
    }

    public void setFundId(Long fundId) {
        this.fundId = fundId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FundManager getManager() {
        return manager;
    }

    public void setManager(FundManager manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "Fund{" +
                "fundId=" + fundId +
                ", name='" + name + '\'' +
                ", manager=" + manager +
                '}';
    }

}
