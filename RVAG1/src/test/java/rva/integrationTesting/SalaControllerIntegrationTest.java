package rva.integrationTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import rva.bioskop_app.Rvag1Application; // glavna Spring Boot klasa
import rva.models.Sala;
import rva.models.Bioskop;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Rvag1Application.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SalaControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    int getHighestId() {
        int highestId = 0;
        ResponseEntity<List<Sala>> response =
                template.exchange("/sala", HttpMethod.GET, null, new ParameterizedTypeReference<List<Sala>>() {});

        if (response.getBody().isEmpty()) return highestId;
        for (Sala s : response.getBody()) {
            if (s.getId() > highestId) {
                highestId = s.getId();
            }
        }
        return highestId;
    }

    @Test
    @Order(1)
    void testGetAllSalas() {
        ResponseEntity<List<Sala>> response =
                template.exchange("/sala", HttpMethod.GET, null, new ParameterizedTypeReference<List<Sala>>() {});
        assertEquals(200, response.getStatusCode().value());
        assertTrue(!response.getBody().isEmpty());
    }

    @Test
    @Order(2)
    void testGetSalaByKapacitet() {
        int minKapacitet = 10;
        ResponseEntity<List<Sala>> response =
                template.exchange("/sala/kapacitet/" + minKapacitet, HttpMethod.GET, null, new ParameterizedTypeReference<List<Sala>>() {});
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(3)
    void testGetSalaById() {
        int id = 1;
        ResponseEntity<Sala> response = null;
        try {
            response = template.exchange("/sala/" + id, HttpMethod.GET, null, Sala.class);
        } catch (RestClientException e) {
            fail("No data found with primary key: " + id);
        }

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    @Order(4)
    void testCreateSala() {
        Sala sala = new Sala();
        sala.setKapacitet(50);
        sala.setBrojRedova(5);

        Bioskop bioskop = new Bioskop();
        bioskop.setId(1); // postavi neki postojeći bioskop
        sala.setBioskop(bioskop);

        HttpEntity<Sala> entity = new HttpEntity<>(sala);
        ResponseEntity<Sala> response = template.exchange("/sala", HttpMethod.POST, entity, Sala.class);

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(sala.getKapacitet(), response.getBody().getKapacitet());
    }

    @Test
    @Order(5)
    void testUpdateSala() {
        int highestId = getHighestId();

        Sala sala = new Sala();
        sala.setKapacitet(60);
        sala.setBrojRedova(6);

        Bioskop bioskop = new Bioskop();
        bioskop.setId(1);
        sala.setBioskop(bioskop);

        HttpEntity<Sala> entity = new HttpEntity<>(sala);
        ResponseEntity<Sala> response = template.exchange("/sala/" + highestId, HttpMethod.PUT, entity, Sala.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(sala.getKapacitet(), response.getBody().getKapacitet());
    }

    @Test
    @Order(6)
    void testDeleteSala() {
        int highestId = getHighestId();

        ResponseEntity<Void> response = template.exchange("/sala/" + highestId, HttpMethod.DELETE, null, Void.class);
        assertEquals(204, response.getStatusCode().value());

        ResponseEntity<Sala> getResponse = template.exchange("/sala/" + highestId, HttpMethod.GET, null, Sala.class);
        assertEquals(404, getResponse.getStatusCode().value());
    }
}
