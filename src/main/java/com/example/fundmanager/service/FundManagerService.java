package com.example.fundmanager.service;

import com.example.fundmanager.dao.FundManagerRepository;
import com.example.fundmanager.entity.Fund;
import com.example.fundmanager.entity.FundManager;
import com.example.fundmanager.exception.FundNotFoundException;
import com.example.fundmanager.exception.ManagerAlreadyInUseException;
import com.example.fundmanager.exception.ManagerIdNotMatchingException;
import com.example.fundmanager.exception.ManagerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FundManagerService {

    private final FundManagerRepository fundManagerRepository;

    @Autowired
    public FundManagerService(FundManagerRepository fundManagerRepository) {
        this.fundManagerRepository = fundManagerRepository;
    }

    public List<FundManager> getManagers() {
        return fundManagerRepository.findAll();
    }

    public FundManager getManager(Long id) {
        Optional<FundManager> manager = fundManagerRepository.findById(id);
        if(manager.isEmpty()){
            throw new ManagerNotFoundException(id);
        }
        return manager.get();
    }

    public void removeManager(Long id) {
        if(fundManagerRepository.existsById(id)){
            fundManagerRepository.deleteById(id);
        }else{
            throw new ManagerNotFoundException(id);
        }
    }

    public void addManager(FundManager manager) {
        Optional<FundManager> managerByName = fundManagerRepository.findFundManagerByName(manager.getFirstName(), manager.getLastName());
        Optional<FundManager> managerByEmployeeId = fundManagerRepository.findFundManagerByEmployeeId(manager.getEmployeeId());
        if(managerByName.isPresent() || managerByEmployeeId.isPresent()){
            throw new ManagerAlreadyInUseException(manager);
        }
        for(Fund fund: manager.getFunds()){
            fund.setManager(manager);
        }
        fundManagerRepository.save(manager);
    }

    @Transactional
    public void updateManager(Long id, FundManager updatedManager) {
        Optional<FundManager> fundManagerOptional = fundManagerRepository.findById(id);
        if(fundManagerOptional.isEmpty()){
            throw new ManagerNotFoundException(id);
        }

        FundManager fundManager = fundManagerOptional.get();

        //Check Id
        if(updatedManager.getEmployeeId() != null && !id.equals(updatedManager.getEmployeeId())){
            throw new ManagerIdNotMatchingException(id, updatedManager);
        }

        if(updatedManager.getFirstName() != null && updatedManager.getFirstName().length()>0 &&
                updatedManager.getLastName() != null && updatedManager.getLastName().length()>0){
            Optional<FundManager> managerCheck = fundManagerRepository.findFundManagerByName(
                    updatedManager.getFirstName(),
                    updatedManager.getLastName());
            if(managerCheck.isPresent()){
                throw new ManagerAlreadyInUseException(updatedManager);
            }

            //Update First Name
            if(!Objects.equals(updatedManager.getFirstName(), fundManager.getFirstName())){
                fundManager.setFirstName(updatedManager.getFirstName());
            }

            //Update Last Name
            if(!Objects.equals(updatedManager.getLastName(), fundManager.getLastName())){
                fundManager.setLastName(updatedManager.getLastName());
            }
        }

    }
}
