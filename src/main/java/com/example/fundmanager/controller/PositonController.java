package com.example.fundmanager.controller;

import com.example.fundmanager.entity.Fund;
import com.example.fundmanager.entity.Position;
import com.example.fundmanager.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api/position")
public class PositonController {

    private final PositionService positionService;

    @Autowired
    public PositonController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping
    public ResponseEntity<List<Position>> getPositions() {
        return new ResponseEntity<>(positionService.getPositions(), HttpStatus.OK);
    }

    @GetMapping(path = "{PositionId}")
    public ResponseEntity<Position> getPosition(@PathVariable("PositionId") Long id){
        return new ResponseEntity<>(positionService.getPosition(id), HttpStatus.OK);
    }

    @DeleteMapping(path = "{PositionId}")
    public ResponseEntity<HttpStatus> removePosition(@PathVariable("PositionId") Long id){
        positionService.removePosition(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addPosition(@RequestBody Position position){
        positionService.addPosition(position);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "{PositionId}")
    public ResponseEntity<HttpStatus> updatePosition(@PathVariable("PositionId") Long id, @RequestBody Position position){
        positionService.updatePosition(id, position);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
