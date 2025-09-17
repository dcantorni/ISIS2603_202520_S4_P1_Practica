package co.edu.uniandes.dse.parcial1.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class MercanciaEntity extends BaseEntity{
    @PodamExclude
    @ManyToOne
    private UbicacionBodegaEntity ubicacionBodega;

    private String nombre;
    private Long codigoBarras;
    private LocalDateTime fechaRecepcion;
    private Integer cantidadDisponible;
    
}
