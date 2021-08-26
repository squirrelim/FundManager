package com.example.fundmanager.controller;

import com.example.fundmanager.entity.Fund;
import com.example.fundmanager.entity.Security;
import com.example.fundmanager.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/security")
public class SecurityController {

    private final SecurityService securityService;

    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping
    public ResponseEntity<List<Security>> getSecurities() {
        return new ResponseEntity<>(securityService.getSecurities(), HttpStatus.OK);
    }

    @GetMapping(path = "{SecurityId}")
    public ResponseEntity<Security> getSecurity(@PathVariable("SecurityId") Long id){
        return new ResponseEntity<>(securityService.getSecurity(id), HttpStatus.OK);
    }

    @DeleteMapping(path = "{SecurityId}")
    public ResponseEntity<HttpStatus> removeSecurity(@PathVariable("SecurityId") Long id){
        securityService.removeSecurity(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addSecurity(@RequestBody Security security){
        securityService.addSecurity(security);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "{SecurityId}")
    public ResponseEntity<HttpStatus> updateSecurity(@PathVariable("SecurityId") Long id, @RequestBody Security security){
        securityService.updateSecurity(id, security);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
