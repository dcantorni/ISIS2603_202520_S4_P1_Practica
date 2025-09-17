package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcial1.entities.UbicacionBodegaEntity;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(UbicacionBodegaService.class)
public class UbicacionBodegaServiceTest {

    @Autowired
    private UbicacionBodegaService ubicacionBodegaService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<UbicacionBodegaEntity> ubicacionBodegaList = new ArrayList<>();
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from UbicacionBodegaEntity").executeUpdate();

    }

      private void insertData() {
        for (int i = 0; i < 3; i++) {
            UbicacionBodegaEntity bodega = factory.manufacturePojo(UbicacionBodegaEntity.class);
            bodega.setNumeroEstante(1+i);
            bodega.setCanasta(1+i);
            bodega.setPesoMax(1+i);
            entityManager.persist(bodega);
            ubicacionBodegaList.add(bodega);
        }
    }

    @Test
    void testCreateUbivacionBodegaValid() {
        UbicacionBodegaEntity newBodega = factory.manufacturePojo(UbicacionBodegaEntity.class);
        newBodega.setNumeroEstante(16);
        newBodega.setCanasta(30);
        newBodega.setPesoMax(19);

        UbicacionBodegaEntity result = ubicacionBodegaService.createUbicacionBodega(newBodega);

        assertNotNull(result);
        UbicacionBodegaEntity entity = entityManager.find(UbicacionBodegaEntity.class, result.getId());
        assertEquals(newBodega.getNumeroEstante(), entity.getNumeroEstante());
        assertEquals(newBodega.getPesoMax(), entity.getPesoMax());
    }

    @Test
    void testCreateMercanciaFechaPosterior() {
        assertThrows(IllegalArgumentException.class, () -> {
            UbicacionBodegaEntity newBodega = factory.manufacturePojo(UbicacionBodegaEntity.class);
            newBodega.setNumeroEstante(-16);
            newBodega.setCanasta(30);
            newBodega.setPesoMax(19);
            ubicacionBodegaService.createUbicacionBodega(newBodega);
        });
    }
    
}
