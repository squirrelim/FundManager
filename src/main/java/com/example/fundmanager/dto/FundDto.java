package com.example.fundmanager.dto;

import java.util.List;

public class FundDto {

    private Long id;
    private String name;
    private Long manager;
    private List<Long> positions;

    public FundDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getManager() {
        return manager;
    }

    public void setManager(Long manager) {
        this.manager = manager;
    }

    public List<Long> getPositions() {
        return positions;
    }

    public void setPositions(List<Long> positions) {
        this.positions = positions;
    }
}
