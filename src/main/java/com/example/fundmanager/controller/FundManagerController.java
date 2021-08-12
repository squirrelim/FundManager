package com.example.fundmanager.controller;

import com.example.fundmanager.entity.Fund;
import com.example.fundmanager.entity.FundManager;
import com.example.fundmanager.service.FundManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
public class FundManagerController {

    private final FundManagerService fundManagerService;

    @Autowired
    public FundManagerController(FundManagerService fundManagerService) {
        this.fundManagerService = fundManagerService;
    }

    @GetMapping
    public List<FundManager> getManagers() {
        return fundManagerService.getManagers();
    }

    @GetMapping(path = "{ManagerId}")
    public FundManager getManager(@PathVariable("ManagerId") Long id){
        return fundManagerService.getManager(id);
    }

    @DeleteMapping(path = "{ManagerId}")
    public void removeManager(@PathVariable("ManagerId") Long id){
        fundManagerService.removeManager(id);
    }

    @PostMapping
    public void addManager(@RequestBody FundManager manager){
        fundManagerService.addManager(manager);
    }

    @PutMapping(path = "{ManagerId}")
    public void updateManager(@PathVariable("ManagerId") Long id, @RequestBody FundManager manager){
        fundManagerService.updateManager(id, manager);
    }
}
