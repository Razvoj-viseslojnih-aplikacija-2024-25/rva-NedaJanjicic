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

import rva.bioskop_app.Rvag1Application; // glavna klasa Spring Boot aplikacije
import rva.models.Film;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Rvag1Application.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FilmControllerIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    int getHighestId() {
        int highestId = 0;
        ResponseEntity<List<Film>> response =
                template.exchange("/film", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Film>>() {});
        if (response.getBody() == null || response.getBody().isEmpty()) return 0;
        for (Film f : response.getBody()) {
            if (f.getId() > highestId) {
                highestId = f.getId();
            }
        }
        return highestId;
    }

    @Test
    @Order(1)
    void testGetAllFilms() {
        ResponseEntity<List<Film>> response =
                template.exchange("/film", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Film>>() {});
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(2)
    void testGetFilmsByNaziv() {
        String naziv = "a"; // deo naziva da bi nešto pronašao
        ResponseEntity<List<Film>> response =
                template.exchange("/film/naziv/" + naziv, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Film>>() {});
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(3)
    void testGetFilmsByZanr() {
        String zanr = "Drama";
        ResponseEntity<List<Film>> response =
                template.exchange("/film/zanr/" + zanr, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Film>>() {});
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(4)
    void testCreateFilm() {
        Film film = new Film();
        film.setNaziv("Test Film");
        film.setRecenzija("Odličan film");
        film.setTrajanje(120);
        film.setZanr("Akcija");

        HttpEntity<Film> entity = new HttpEntity<>(film);
        ResponseEntity<Film> response =
                template.exchange("/film", HttpMethod.POST, entity, Film.class);

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(film.getNaziv(), response.getBody().getNaziv());
    }

    @Test
    @Order(5)
    void testUpdateFilm() {
        int highestId = getHighestId();
        Film film = new Film();
        film.setNaziv("Ažuriran film");
        film.setRecenzija("Izmenjena recenzija");
        film.setTrajanje(140);
        film.setZanr("Komedija");

        HttpEntity<Film> entity = new HttpEntity<>(film);
        ResponseEntity<Film> response =
                template.exchange("/film/" + highestId, HttpMethod.PUT, entity, Film.class);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(film.getNaziv(), response.getBody().getNaziv());
    }

    @Test
    @Order(6)
    void testDeleteFilm() {
        int highestId = getHighestId();

        ResponseEntity<Void> response =
                template.exchange("/film/" + highestId, HttpMethod.DELETE, null, Void.class);

        assertEquals(204, response.getStatusCode().value());
    }
}
