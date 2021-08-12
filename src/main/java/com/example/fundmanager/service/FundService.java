package com.example.fundmanager.service;

import com.example.fundmanager.dao.FundRepository;
import com.example.fundmanager.entity.Fund;
import com.example.fundmanager.entity.FundManager;
import com.example.fundmanager.entity.Position;
import com.example.fundmanager.exception.FundAlreadyInUseException;
import com.example.fundmanager.exception.FundIdNotMatchingException;
import com.example.fundmanager.exception.FundNotFoundException;
import com.example.fundmanager.exception.IllegalUpdatedFundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FundService {

    private final FundRepository fundRepository;
    private final FundManagerService fundManagerService;
    private final PositionService positionService;

    @Autowired
    public FundService(FundRepository fundRepository, FundManagerService fundManagerService, PositionService positionService) {
        this.fundRepository = fundRepository;
        this.fundManagerService = fundManagerService;
        this.positionService = positionService;
    }

    public List<Fund> getFunds() {
        return fundRepository.findAll();
    }

    public Fund getFund(Long id){
        Optional<Fund> fund = fundRepository.findById(id);
        if(fund.isEmpty()){
            throw new FundNotFoundException(id);
        }
        return fund.get();
    }

    public void removeFund(Long id){
        if(fundRepository.existsById(id)){
            fundRepository.deleteById(id);
        }else{
            throw new FundNotFoundException(id);
        }
    }

    public void addFund(Fund fund){
        Optional<Fund> fundByName = fundRepository.findFundByName(fund.getName());
        if(fundByName.isPresent()){
            throw new FundAlreadyInUseException(fund);
        }

        // Get real manager references.
        FundManager manager = fundManagerService.getManager(fund.getManager().getEmployeeId());

        // Get real position references.
        List<Position> positions = fund
                .getPositions()
                .stream()
                .map(p -> positionService.getPosition(p.getPositionId()))
                .collect(Collectors.toList());

        fund.setManager(manager);
        fund.setPositions(positions);
        fundRepository.save(fund);
    }

    @Transactional
    public void updateFund(Long id, Fund updatedFund){
        if(updatedFund.getFundId() == null || updatedFund.getName() == null){
            throw new IllegalUpdatedFundException(updatedFund);
        }

        Optional<Fund> fundOptional = fundRepository.findById(id);
        if(fundOptional.isEmpty()){
            throw new FundNotFoundException(id);
        }
        Fund fund = fundOptional.get();

        //Check Id
        if(updatedFund.getFundId() != null && !id.equals(updatedFund.getFundId())){
            throw new FundIdNotMatchingException(id, fund);
        }

        //Update Name
        if(updatedFund.getName() != null &&
        updatedFund.getName().length() > 0 &&
        !updatedFund.getName().equals(fund.getName())){

            Optional<Fund> fundCheck = fundRepository.findFundByName(updatedFund.getName());
            if(fundCheck.isPresent()){
                throw new FundAlreadyInUseException(updatedFund);
            }
            fund.setName(updatedFund.getName());
        }
    }

}
