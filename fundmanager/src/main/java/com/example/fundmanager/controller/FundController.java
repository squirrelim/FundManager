package com.example.fundmanager.controller;

import com.example.fundmanager.entity.Fund;
import com.example.fundmanager.entity.FundManager;
import com.example.fundmanager.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fund")
public class FundController {

    private final FundService fundService;

    @Autowired
    public FundController(FundService fundService) {
        this.fundService = fundService;
    }

    @GetMapping
    public List<Fund> getFunds() {
        return fundService.getFunds();
    }

    @GetMapping(path = "{FundId}")
    public Fund getFund(@PathVariable("FundId") Long id){
        return fundService.getFund(id);
    }

    @DeleteMapping(path = "{FundId}")
    public void removeFund(@PathVariable("FundId") Long id){
        fundService.removeFund(id);
    }

    @PostMapping
    public void addFund(@RequestBody Fund fund){
        fundService.addFund(fund);
    }

    @PutMapping(path = "{FundId}")
    public void updateFund(@PathVariable("FundId") Long id, @RequestBody Fund fund){
        fundService.updateFund(id, fund);
    }

}
