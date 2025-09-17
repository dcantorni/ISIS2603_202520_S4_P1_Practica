package co.edu.uniandes.dse.parcial1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.UbicacionBodegaEntity;
import co.edu.uniandes.dse.parcial1.repositories.UbicacionBodegaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UbicacionBodegaService {

    
    @Autowired
    private UbicacionBodegaRepository ubicacionBodegaRepository;

    @Transactional
    public UbicacionBodegaEntity createUbicacionBodega(UbicacionBodegaEntity ubicacionBodega){
        

    if (ubicacionBodega.getNumeroEstante() <= 0){
        throw new IllegalArgumentException("El número de estante debe ser un valor positivo.");
    }
        return ubicacionBodegaRepository.save(ubicacionBodega);
    }

}
