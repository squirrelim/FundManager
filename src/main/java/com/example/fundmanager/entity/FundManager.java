package com.example.fundmanager.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;


@Entity
@Table
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "employeeId")
public class FundManager {

    /**
     * Ensure at least one field has the @Id annotation: this will be the primary key in the database.
     */
    @Id

    /**
     * Use @GeneratedValue annotation as well for the database to generate the Id.
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    /**
     * Each fund manager manages many investment funds
     *  1.declare relationship
     *  2.configure the foreign key
     *      name = the foreign key
     *      referencedColumnName = refer to the name of the primary key
     */
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

//    @OneToMany(targetEntity = Fund.class)
//    @JoinColumn(name = "employeeId", referencedColumnName = "id")
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
    @JsonIdentityReference(alwaysAsId = true)
    private List<Fund> funds;

    public FundManager() {

    }

    public FundManager(String firstName, String lastName, List<Fund> funds) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.funds = funds;
    }


    public FundManager(Long employeeId, String firstName, String lastName, List<Fund> funds) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.funds = funds;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public List<Fund> getFunds() {
        return funds;
    }

    public void setFunds(List<Fund> funds) {
        this.funds = funds;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "FundManager{" +
                "employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", funds=" + funds +
                '}';
    }
}
