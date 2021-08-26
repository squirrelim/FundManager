package com.example.fundmanager.service;

import com.example.fundmanager.dao.FundRepository;
import com.example.fundmanager.entity.Fund;
import com.example.fundmanager.exception.FundAlreadyInUseException;
import com.example.fundmanager.exception.FundIdNotMatchingException;
import com.example.fundmanager.exception.FundNotFoundException;
import com.example.fundmanager.exception.IllegalUpdatedFundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FundService {

    private final FundRepository fundRepository;

    @Autowired
    public FundService(FundRepository fundRepository) {
        this.fundRepository = fundRepository;
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
        updatedFund.getName().length() > 0){
            fund.setName(updatedFund.getName());
        }

        //Update Manager ID
        if(updatedFund.getEmployeeId() != null &&
                updatedFund.getEmployeeId() > 0){
            fund.setEmployeeId(updatedFund.getEmployeeId());
        }
    }

}
