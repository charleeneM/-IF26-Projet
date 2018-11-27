package fr.utt.if26.pills;

public class Medicament {
    private Integer id; // à voir si vraiment utile ici
    private String nom;
    private String fabricant;
    private Enum type; // déclaration à corriger
    private Double stock;

    public Medicament() {
        this.id = 0;
        this.nom = "nom";
        this.fabricant = "fabricant";
        //this.type = "type";
        this.stock = 0.0;
    }

    public Medicament(Integer id, String nom, String fabricant, Enum type, Double stock) {
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

    public Enum getType() {
        return type;
    }

    public void setType(Enum type) {
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
