package co.edu.uniandes.dse.parcial1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.MercanciaEntity;
import co.edu.uniandes.dse.parcial1.entities.UbicacionBodegaEntity;
import co.edu.uniandes.dse.parcial1.repositories.MercanciaRepository;
import co.edu.uniandes.dse.parcial1.repositories.UbicacionBodegaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MercanciaUbicacionBodegaService {

    @Autowired
    MercanciaRepository mercanciaRepository;

    @Autowired
    UbicacionBodegaRepository ubicacionBodegaRepository;

    @Transactional
    public void addMercanciaBodega(MercanciaEntity mercancia, UbicacionBodegaEntity ubicacionBodega){
        

        mercancia.setUbicacionBodega(ubicacionBodega);
        mercanciaRepository.save(mercancia);    
    }

}
