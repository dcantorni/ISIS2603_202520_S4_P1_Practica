package co.edu.uniandes.dse.parcial1.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.MercanciaEntity;
import co.edu.uniandes.dse.parcial1.entities.UbicacionBodegaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
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
    public void addMercanciaBodega(Long  mercanciaId, Long ubicacionBodegaId) throws IllegalOperationException, EntityNotFoundException{
        Optional<MercanciaEntity> mercanciaOPT = mercanciaRepository.findById(mercanciaId);
        if (mercanciaOPT.isEmpty())
            throw new EntityNotFoundException("Mercancia no encontrada");

        Optional<UbicacionBodegaEntity> ubicacionBodegaOPT = ubicacionBodegaRepository.findById(ubicacionBodegaId);
        if (ubicacionBodegaOPT.isEmpty())
            throw new EntityNotFoundException("UbicacionBodega no encontrada");

        MercanciaEntity mercancia = mercanciaOPT.get();
        UbicacionBodegaEntity ubicacionBodega = ubicacionBodegaOPT.get();

        if (ubicacionBodega.getMercancias().contains(mercancia))
            throw new IllegalOperationException("Mercancia ya está en esta bodega");
        
        mercancia.setUbicacionBodega(ubicacionBodega);
        mercanciaRepository.save(mercancia);    
    }

}
