package com.example.fundmanager.controller;

import com.example.fundmanager.dto.FundDto;
import com.example.fundmanager.entity.Fund;
import com.example.fundmanager.entity.FundManager;
import com.example.fundmanager.entity.Position;
import com.example.fundmanager.service.FundManagerService;
import com.example.fundmanager.service.FundService;
import com.example.fundmanager.service.PositionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/fund")
public class FundController {

    private final FundService fundService;
    private final ModelMapper modelMapper;

    @Autowired
    public FundController(FundService fundService, ModelMapper modelMapper) {
        this.fundService = fundService;
        this.modelMapper = modelMapper;

        // Configure mapper to use Id for manager, and to skip positions.
        this.modelMapper.typeMap(Fund.class, FundDto.class).addMappings(
                mapper -> {
                    mapper.map(src -> src.getManager().getEmployeeId(), FundDto::setManager);
                    mapper.skip(FundDto::setPositions);
        });

        // Skip the manager and positions.
        this.modelMapper.typeMap(FundDto.class, Fund.class).addMappings(
                mapper -> {
                    mapper.skip(Fund::setManager);
                    mapper.skip(Fund::setPositions);
                });
    }

    @GetMapping
    public List<FundDto> getFunds() {
        return fundService.getFunds()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "{FundId}")
    public FundDto getFund(@PathVariable("FundId") Long id){
        return convertToDto(fundService.getFund(id));
    }

    @DeleteMapping(path = "{FundId}")
    public void removeFund(@PathVariable("FundId") Long id){
        fundService.removeFund(id);
    }

    @PostMapping
    public void addFund(@RequestBody FundDto fund){
        fundService.addFund(convertToEntity(fund));
    }

    @PutMapping(path = "{FundId}")
    public void updateFund(@PathVariable("FundId") Long id, @RequestBody FundDto fund){
        fundService.updateFund(id, convertToEntity(fund));
    }

    private FundDto convertToDto(Fund fund) {
        FundDto fundDto = modelMapper.map(fund, FundDto.class);

        // Manually convert positions.
        List<Long> positionIds = fund.getPositions()
                .stream()
                .map(p -> p.getPositionId())
                .collect(Collectors.toList());
        fundDto.setPositions(positionIds);

        return fundDto;
    }

    private Fund convertToEntity(FundDto fundDto) {
        Fund fund = modelMapper.map(fundDto, Fund.class);

        // Create manager object and set the id.
        // The FundService should handle if this is correct or not.
        FundManager manager = new FundManager();
        manager.setEmployeeId(fundDto.getManager());

        // Convert the list of ids into a list of Positions.
        List<Position> positions = fundDto
                .getPositions()
                .stream()
                .map(pId -> new Position(pId, null, null, null, null))
                .collect(Collectors.toList());

        fund.setManager(manager);
        fund.setPositions(positions);

        return fund;
    }
}

