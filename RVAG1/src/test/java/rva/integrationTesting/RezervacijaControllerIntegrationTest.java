package rva.integrationTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
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

import rva.bioskop_app.Rvag1Application; // glavna klasa Spring Boot aplikacije
import rva.models.Rezervacija;
import rva.models.Film;
import rva.models.Sala;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Rvag1Application.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RezervacijaControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    int getHighestId() {
        int highestId = 0;
        ResponseEntity<List<Rezervacija>> response =
                template.exchange("/rezervacija", HttpMethod.GET, null, new ParameterizedTypeReference<List<Rezervacija>>() {});
        if (response.getBody().isEmpty()) return highestId;
        for (Rezervacija r : response.getBody()) {
            if (highestId < r.getId()) highestId = r.getId();
        }
        return highestId;
    }

    @Test
    @Order(1)
    void testGetAllRezervacijas() {
        ResponseEntity<List<Rezervacija>> response =
                template.exchange("/rezervacija", HttpMethod.GET, null, new ParameterizedTypeReference<List<Rezervacija>>() {});
        assertEquals(200, response.getStatusCode().value());
        assertTrue(!response.getBody().isEmpty());
    }

    @Test
    @Order(2)
    void testGetRezervacijaById() {
        int id = 1;
        ResponseEntity<Rezervacija> response = null;
        try {
            response = template.exchange("/rezervacija/" + id, HttpMethod.GET, null, Rezervacija.class);
        } catch (RestClientException e) {
            fail("No data found with primary key: " + id);
        }
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    @Order(3)
    void testCreateRezervacija() {
        Rezervacija r = new Rezervacija();
        r.setBrojOsoba(3);
        r.setCenaKarti(450);
        r.setDatum(new Date());
        r.setPlaceno(true);
        Film f = new Film();
        f.setId(1);
        r.setFilm(f);
        Sala s = new Sala();
        s.setId(1);
        r.setSala(s);

        HttpEntity<Rezervacija> entity = new HttpEntity<>(r);
        ResponseEntity<Rezervacija> response =
                template.exchange("/rezervacija", HttpMethod.POST, entity, Rezervacija.class);

        int highestId = getHighestId();

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(r.getBrojOsoba(), response.getBody().getBrojOsoba());
        assertEquals(r.getCenaKarti(), response.getBody().getCenaKarti());
        assertEquals(r.isPlaceno(), response.getBody().isPlaceno());
    }

    @Test
    @Order(4)
    void testUpdateRezervacija() {
        int highestId = getHighestId();
        Rezervacija r = new Rezervacija();
        r.setBrojOsoba(5);
        r.setCenaKarti(700);
        r.setDatum(new Date());
        r.setPlaceno(false);
        Film f = new Film();
        f.setId(1);
        r.setFilm(f);
        Sala s = new Sala();
        s.setId(1);
        r.setSala(s);

        HttpEntity<Rezervacija> entity = new HttpEntity<>(r);
        ResponseEntity<Rezervacija> response =
                template.exchange("/rezervacija/" + highestId, HttpMethod.PUT, entity, Rezervacija.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(r.getBrojOsoba(), response.getBody().getBrojOsoba());
        assertEquals(r.getCenaKarti(), response.getBody().getCenaKarti());
        assertEquals(r.isPlaceno(), response.getBody().isPlaceno());
    }

    @Test
    @Order(5)
    void testDeleteRezervacija() {
        int highestId = getHighestId();
        ResponseEntity<Void> response =
                template.exchange("/rezervacija/" + highestId, HttpMethod.DELETE, null, Void.class);

        assertEquals(204, response.getStatusCode().value());
    }
}
