package com.example.fundmanager.service;

import com.example.fundmanager.dao.SecurityRepository;
import com.example.fundmanager.entity.Security;
import com.example.fundmanager.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SecurityService {

    private final SecurityRepository securityRepository;

    @Autowired
    public SecurityService(SecurityRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    public List<Security> getSecurities() {
        return securityRepository.findAll();
    }

    public Security getSecurity(Long id){
        Optional<Security> security = securityRepository.findById(id);
        if(security.isEmpty()){
            throw new SecurityNotFoundException(id);
        }
        return security.get();
    }

    public void removeSecurity(Long id){
        if(securityRepository.existsById(id)){
            securityRepository.deleteById(id);
        }else{
            throw new SecurityNotFoundException(id);
        }
    }

    public void addSecurity(Security security){
        Optional<Security> securityBySymbol = securityRepository.findSecurityBySymbol(security.getSymbol());
        Optional<Security> securityBySecurityId = securityRepository.findSecurityBySecurityId(security.getSecurityId());
        if(securityBySymbol.isPresent() && securityBySecurityId.isPresent()){
            throw new SecurityAlreadyInUseException(security);
        }
        securityRepository.save(security);
    }

    @Transactional
    public void updateSecurity(Long id, Security updatedSecurity){
        if(updatedSecurity.getSecurityId() == null || updatedSecurity.getSymbol() == null){
            throw new IllegalUpdatedSecurityException(updatedSecurity);
        }

        Optional<Security> securityOptional = securityRepository.findById(id);
        if(securityOptional.isEmpty()){
            throw new SecurityNotFoundException(id);
        }

        Security security = securityOptional.get();

        //Check Id
        if(updatedSecurity.getSecurityId() != null && !id.equals(updatedSecurity.getSecurityId())){
            throw new SecurityIdNotMatchingException(id, security);
        }

        //Update Symbol
        if(updatedSecurity.getSymbol() != null &&
        updatedSecurity.getSymbol().length() > 0 &&
        !Objects.equals(updatedSecurity.getSymbol(), security.getSymbol())){

            Optional<Security> securityCheck = securityRepository.findSecurityBySymbol(updatedSecurity.getSymbol());
            if(securityCheck.isPresent()){
                throw new SecurityAlreadyInUseException(updatedSecurity);
            }
        }
        security.setSymbol(updatedSecurity.getSymbol());
    }
}
