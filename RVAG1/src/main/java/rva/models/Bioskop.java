package rva.models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Bioskop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "BIOSKOP_ID_GEN", sequenceName = "BIOSKOP_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BIOSKOP_ID_GEN")
    private int id;

    private String naziv;
    private String adresa;

    @OneToMany(mappedBy = "bioskop")
    @JsonIgnore
    private List<Sala> sale;

    public Bioskop() {}

    public Bioskop(int id, String naziv, String adresa) {
        this.id = id;
        this.naziv = naziv;
        this.adresa = adresa;
    }

    // getters i setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNaziv() { return naziv; }
    public void setNaziv(String naziv) { this.naziv = naziv; }
    public String getAdresa() { return adresa; }
    public void setAdresa(String adresa) { this.adresa = adresa; }
    public List<Sala> getSale() { return sale; }
    public void setSale(List<Sala> sale) { this.sale = sale; }
}
