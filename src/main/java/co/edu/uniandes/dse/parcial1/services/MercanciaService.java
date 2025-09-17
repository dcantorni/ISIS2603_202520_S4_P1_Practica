package co.edu.uniandes.dse.parcial1.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.MercanciaEntity;
import co.edu.uniandes.dse.parcial1.repositories.MercanciaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MercanciaService {
    
      @Autowired
    private MercanciaRepository mercanciaRepository;

    @Transactional
    public MercanciaEntity createMercancia(MercanciaEntity mercancia){
        LocalDateTime fechaActual = LocalDateTime.now();
    
    if (mercanciaRepository.findbycodigoBarras(mercancia.getCodigoBarras()) != null) {
            throw new IllegalArgumentException("No se puede registrar una mercancía sin código de barras único");
        }

    if (mercancia.getFechaRecepcion().isBefore(fechaActual)){
        throw new IllegalArgumentException("La fecha de recepción no puede ser posterior a la fecha actual.");
    }
    if (mercancia.getNombre() == null || mercancia.getNombre().isBlank()){
        throw new IllegalArgumentException("El nombre de la mercancía es obligatorio y no puede estar vacío.");
    }
    

        return mercanciaRepository.save(mercancia); 
    }
}
