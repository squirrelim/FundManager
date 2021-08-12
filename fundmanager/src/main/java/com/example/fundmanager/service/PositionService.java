package com.example.fundmanager.service;

import com.example.fundmanager.dao.PositionRepository;
import com.example.fundmanager.entity.Position;
import com.example.fundmanager.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PositionService {
    private final PositionRepository positionRepository;

    @Autowired
    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public List<Position> getPositions(){
        return positionRepository.findAll();
    }

    public Position getPosition(Long id){
        Optional<Position> position = positionRepository.findById(id);
        if(position.isEmpty()){
            throw new PositionNotFoundException(id);
        }
        return position.get();
    }

    public void removePosition(Long id){
        if(positionRepository.existsById(id)){
            positionRepository.deleteById(id);
        }else{
            throw new PositionNotFoundException(id);
        }
    }

    public void addPosition(Position position){
        Optional<Position> existingPosition = positionRepository.findPositions(
                position.getSecurityId(),
                position.getQuantity(),
                position.getDatePurchased());
        Optional<Position> positionsByPositionId = positionRepository.findPositionsByPositionId(position.getPositionId());

        if(existingPosition.isPresent() && positionsByPositionId.isPresent()){
            throw new PositionAlreadyInUseException(position);
        }

        positionRepository.save(position);
    }

    @Transactional
    public void updatePosition(Long id, Position updatedPosition){
        if(updatedPosition.getPositionId() == null ||
                updatedPosition.getSecurityId() ==null ||
                updatedPosition.getQuantity() == null ||
                updatedPosition.getDatePurchased() == null){
            throw new IllegalUpdatedPositionException(updatedPosition);
        }

        Optional<Position> positionOptional = positionRepository.findById(id);
        if(positionOptional.isEmpty()){
            throw new PositionNotFoundException(id);
        }

        Position position = positionOptional.get();

        //Check Id
        if(updatedPosition.getPositionId() != null && !id.equals(updatedPosition.getPositionId())){
            throw new PositionIdNotMatchingException(id, position);
        }

        if(updatedPosition.getSecurityId() != null &&
                updatedPosition.getQuantity() != null && updatedPosition.getQuantity() > 0 &&
                updatedPosition.getDatePurchased() !=null) {

            Optional<Position> positionCheck = positionRepository.findPositions(updatedPosition.getSecurityId(),
                    updatedPosition.getQuantity(),
                    updatedPosition.getDatePurchased());
            if(positionCheck.isPresent()){
                throw new PositionAlreadyInUseException(updatedPosition);
            }

            //Update Security Id
            if(!updatedPosition.getSecurityId().equals(position.getSecurityId())){
                position.setSecurityId(updatedPosition.getSecurityId());
            }

            //Update Quantity
            if(!updatedPosition.getQuantity().equals(position.getQuantity())){
                position.setQuantity(updatedPosition.getQuantity());
            }

            //Update Date Purchased
            if(!updatedPosition.getDatePurchased().equals(position.getDatePurchased())){
                position.setDatePurchased(updatedPosition.getDatePurchased());
            }
        }

    }
}
