package fr.utt.if26.pills;

import java.io.Serializable;

public class Medicament implements Serializable {
    private Integer id;
    private String nom;
    private String fabricant;
    private String type; // déclaration à corriger
    private Double stock;

    public Medicament() {
        this.id = null;
        this.nom = "nom";
        this.fabricant = "fabricant";
        this.type = "type";
        this.stock = 0.0;
    }

    public Medicament(Integer id, String nom, String fabricant, String type, Double stock) {
        this.id = id;
        this.nom = nom;
        this.fabricant = fabricant;
        this.type = type;
        this.stock = stock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getFabricant() {
        return fabricant;
    }

    public void setFabricant(String fabricant) {
        this.fabricant = fabricant;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Medicament{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", fabricant='" + fabricant + '\'' +
                ", type=" + type +
                ", stock=" + stock +
                '}';
    }
}
