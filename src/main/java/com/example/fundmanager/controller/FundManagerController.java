package com.example.fundmanager.controller;

import com.example.fundmanager.entity.Fund;
import com.example.fundmanager.entity.FundManager;
import com.example.fundmanager.service.FundManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api/manager")
public class FundManagerController {

    private final FundManagerService fundManagerService;

    @Autowired
    public FundManagerController(FundManagerService fundManagerService) {
        this.fundManagerService = fundManagerService;
    }

    @GetMapping
    public ResponseEntity<List<FundManager>> getManagers() {
        return new ResponseEntity<>(fundManagerService.getManagers(), HttpStatus.OK);
    }

    @GetMapping(path = "{ManagerId}")
    public ResponseEntity<FundManager> getManager(@PathVariable("ManagerId") Long id){
        return new ResponseEntity<>(fundManagerService.getManager(id), HttpStatus.OK);
    }

    @DeleteMapping(path = "{ManagerId}")
    public ResponseEntity<HttpStatus> removeManager(@PathVariable("ManagerId") Long id){
        fundManagerService.removeManager(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addManager(@RequestBody FundManager manager){
        fundManagerService.addManager(manager);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "{ManagerId}")
    public ResponseEntity<HttpStatus> updateManager(@PathVariable("ManagerId") Long id, @RequestBody FundManager manager){
        fundManagerService.updateManager(id, manager);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
