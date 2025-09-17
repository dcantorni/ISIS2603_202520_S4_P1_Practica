package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcial1.entities.MercanciaEntity;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(MercanciaService.class)
public class MercanciaServiceTest {
    
    @Autowired
    private MercanciaService mercanciaService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<MercanciaEntity> mercanciaList = new ArrayList<>();
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from MercanciaEntity").executeUpdate();

    }

      private void insertData() {
        for (int i = 0; i < 3; i++) {
            MercanciaEntity mercancia = factory.manufacturePojo(MercanciaEntity.class);
            mercancia.setFechaRecepcion(LocalDateTime.now().minusDays(i + 1));
            mercancia.setCodigoBarras(100L + i);
            mercancia.setCantidadDisponible(20 + i);
            entityManager.persist(mercancia);
            mercanciaList.add(mercancia);
        }
    }

    @Test
    void testCreateMercanciaValid() {
        MercanciaEntity newMercancia = factory.manufacturePojo(MercanciaEntity.class);
        newMercancia.setFechaRecepcion(LocalDateTime.now().minusDays(10));
        
        newMercancia.setNombre("NombrePrueba");
        newMercancia.setCodigoBarras(5000L);
        newMercancia.setCantidadDisponible(20);

        MercanciaEntity result = mercanciaService.createMercancia(newMercancia);

        assertNotNull(result);
        MercanciaEntity entity = entityManager.find(MercanciaEntity.class, result.getId());
        assertEquals(newMercancia.getCodigoBarras(), entity.getCodigoBarras());
        assertEquals(newMercancia.getCantidadDisponible(), entity.getCantidadDisponible());
    }

    @Test
    void testCreateMercanciaFechaPosterior() {
        assertThrows(IllegalArgumentException.class, () -> {
            MercanciaEntity newMercancia = factory.manufacturePojo(MercanciaEntity.class);
            newMercancia.setFechaRecepcion(LocalDateTime.now().plusDays(1));
            newMercancia.setNombre("NombrePrueba");
            newMercancia.setCodigoBarras(5000L);
            newMercancia.setCantidadDisponible(20);
            mercanciaService.createMercancia(newMercancia);
        });
    }
    
}
