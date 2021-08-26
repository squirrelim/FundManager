package com.example.fundmanager.controller;

import com.example.fundmanager.entity.Fund;
import com.example.fundmanager.entity.FundManager;
import com.example.fundmanager.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/fund")
public class FundController {

    private final FundService fundService;

    @Autowired
    public FundController(FundService fundService) {
        this.fundService = fundService;
    }

    @GetMapping
    public ResponseEntity<List<Fund>> getFunds() {
        return new ResponseEntity<>(fundService.getFunds(), HttpStatus.OK);
    }

    @GetMapping(path = "{FundId}")
    public ResponseEntity<Fund> getFund(@PathVariable("FundId") Long id){
        return new ResponseEntity<>(fundService.getFund(id), HttpStatus.OK);
    }

    @DeleteMapping(path = "{FundId}")
    public ResponseEntity<HttpStatus> removeFund(@PathVariable("FundId") Long id){
        fundService.removeFund(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addFund(@RequestBody Fund fund){
        fundService.addFund(fund);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "{FundId}")
    public ResponseEntity<HttpStatus> updateFund(@PathVariable("FundId") Long id, @RequestBody Fund fund){
        fundService.updateFund(id, fund);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
