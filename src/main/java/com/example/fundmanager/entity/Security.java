package com.example.fundmanager.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@Table
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "securityId")
public class Security {

    /**
     * A security can appear in many positions in many investment funds
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long securityId;

    @Column(nullable = false)
    private String symbol;

    public Security() {
    }

    public Security(String symbol) {
        this.symbol = symbol;
    }

    public Security(Long securityId, String symbol) {
        this.securityId = securityId;
        this.symbol = symbol;
    }

    public Long getSecurityId() {
        return securityId;
    }

    public void setSecurityId(Long securityId) {
        this.securityId = securityId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "Security{" +
                "securityId=" + securityId +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
