package com.example.fundmanager.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "positionId")
public class Position {

    /**
     * Ensure at least one field has the @Id annotation: this will be the primary key in the database.
     */
    @Id

    /**
     * Use @GeneratedValue annotation as well for the database to generate the Id.
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long positionId;

    @Column(nullable = false)
    private Long securityId;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private LocalDate datePurchased;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fund_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Fund fund;

    /**
     * A position is a combination of a security, a quantity and a date
     */

    public Position() {
    }

    public Position(Long securityId, Long quantity, LocalDate datePurchased, Fund fund) {
        this.securityId = securityId;
        this.quantity = quantity;
        this.datePurchased = datePurchased;
        this.fund = fund;
    }

    public Position(Long positionId, Long securityId, Long quantity, LocalDate datePurchased, Fund fund) {
        this.positionId = positionId;
        this.securityId = securityId;
        this.quantity = quantity;
        this.datePurchased = datePurchased;
        this.fund = fund;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Long getSecurityId() {
        return securityId;
    }

    public void setSecurityId(Long securityId) {
        this.securityId = securityId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public LocalDate getDatePurchased() {
        return datePurchased;
    }

    public void setDatePurchased(LocalDate datePurchased) {
        this.datePurchased = datePurchased;
    }

    public Fund getFund() {
        return fund;
    }

    public void setFund(Fund fund) {
        this.fund = fund;
    }

    @Override
    public String toString() {
        return "Position{" +
                "positionId=" + positionId +
                ", securityId=" + securityId +
                ", quantity=" + quantity +
                ", datePurchased=" + datePurchased +
                ", fund=" + fund +
                '}';
    }
}
