package com.example.fundmanager.controller;

import com.example.fundmanager.entity.Fund;
import com.example.fundmanager.entity.Security;
import com.example.fundmanager.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/security")
public class SecurityController {

    private final SecurityService securityService;

    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping
    public List<Security> getSecurities() {
        return securityService.getSecurities();
    }

    @GetMapping(path = "{SecurityId}")
    public Security getSecurity(@PathVariable("SecurityId") Long id){
        return securityService.getSecurity(id);
    }

    @DeleteMapping(path = "{SecurityId}")
    public void removeSecurity(@PathVariable("SecurityId") Long id){
        securityService.removeSecurity(id);
    }

    @PostMapping
    public void addSecurity(@RequestBody Security security){
        securityService.addSecurity(security);
    }

    @PutMapping(path = "{SecurityId}")
    public void updateSecurity(@PathVariable("SecurityId") Long id, @RequestBody Security security){
        securityService.updateSecurity(id, security);
    }
}
