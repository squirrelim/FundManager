package com.example.fundmanager.controller;

import com.example.fundmanager.entity.Fund;
import com.example.fundmanager.entity.Position;
import com.example.fundmanager.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/position")
public class PositonController {
    
    private final PositionService positionService;

    @Autowired
    public PositonController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping
    public List<Position> getPositions() {
        return positionService.getPositions();
    }

    @GetMapping(path = "{PositionId}")
    public Position getPosition(@PathVariable("PositionId") Long id){
        return positionService.getPosition(id);
    }

    @DeleteMapping(path = "{PositionId}")
    public void removePosition(@PathVariable("PositionId") Long id){
        positionService.removePosition(id);
    }

    @PostMapping
    public void addPosition(@RequestBody Position position){
        positionService.addPosition(position);
    }

    @PutMapping(path = "{PositionId}")
    public void updatePosition(@PathVariable("PositionId") Long id, @RequestBody Position position){
        positionService.updatePosition(id, position);
    }
}
