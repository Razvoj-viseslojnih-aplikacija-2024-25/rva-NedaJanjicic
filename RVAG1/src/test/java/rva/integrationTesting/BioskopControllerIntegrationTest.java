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

import rva.bioskop_app.Rvag1Application;
import rva.models.Bioskop;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Rvag1Application.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BioskopControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    int getHighestId() {
        int highestId = 0;
        ResponseEntity<List<Bioskop>> response =
                template.exchange("/bioskop", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Bioskop>>() {});
        if (response.getBody() == null || response.getBody().isEmpty()) return highestId;
        for (Bioskop b : response.getBody()) {
            if (highestId < b.getId()) highestId = b.getId();
        }
        return highestId;
    }

    @Test
    @Order(1)
    void testGetAllBioskops() {
        ResponseEntity<List<Bioskop>> response =
                template.exchange("/bioskop", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Bioskop>>() {});
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(!response.getBody().isEmpty());
    }

    @Test
    @Order(2)
    void testGetBioskopsByNaziv() {
        String naziv = "Arena";
        ResponseEntity<List<Bioskop>> response =
                template.exchange("/bioskop/naziv/" + naziv, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Bioskop>>() {});
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        if (!response.getBody().isEmpty()) {
            assertTrue(response.getBody().get(0).getNaziv().contains(naziv));
        }
    }

    @Test
    @Order(3)
    void testGetBioskopById() {
        int id = 1;
        ResponseEntity<Bioskop> response =
                template.exchange("/bioskop/" + id, HttpMethod.GET, null, Bioskop.class);

        if (response.getStatusCode().value() == 404) {
            System.out.println("Bioskop sa ID " + id + " ne postoji u bazi — preskačemo test.");
            return;
        }

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    @Order(4)
    void testCreateBioskop() {
        Bioskop bioskop = new Bioskop();
        bioskop.setNaziv("POST TEST BIOSKOP");
        bioskop.setAdresa("Novi Sad");

        HttpEntity<Bioskop> entity = new HttpEntity<>(bioskop);

        ResponseEntity<Bioskop> response =
                template.exchange("/bioskop", HttpMethod.POST, entity, Bioskop.class);

        int highestId = getHighestId();

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(bioskop.getNaziv(), response.getBody().getNaziv());
        assertEquals(bioskop.getAdresa(), response.getBody().getAdresa());
    }

    @Test
    @Order(5)
    void testUpdateBioskop() {
        int highestId = getHighestId();
        Bioskop bioskop = new Bioskop();
        bioskop.setNaziv("PUT TEST BIOSKOP");
        bioskop.setAdresa("Beograd");

        HttpEntity<Bioskop> entity = new HttpEntity<>(bioskop);

        ResponseEntity<Bioskop> response =
                template.exchange("/bioskop/" + highestId, HttpMethod.PUT, entity, Bioskop.class);

        if (response.getStatusCode().value() == 404) {
            fail("Nije pronađen bioskop sa ID: " + highestId);
        }

        assertEquals(200, response.getStatusCode().value());
        assertEquals(bioskop.getNaziv(), response.getBody().getNaziv());
    }

    @Test
    @Order(6)
    void testDeleteBioskop() {
        int highestId = getHighestId();

        ResponseEntity<String> response =
                template.exchange("/bioskop/" + highestId, HttpMethod.DELETE, null, String.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        ResponseEntity<String> getResponse =
                template.exchange("/bioskop/" + highestId, HttpMethod.GET, null, String.class);

        assertEquals(404, getResponse.getStatusCode().value());
    }
}
