package fr.utt.if26.pills;

import java.io.Serializable;
import java.util.Date;

public class Rappel implements Serializable {
    private Integer id_rappel;
    private Integer id_med;
    private String heure;
    private Integer repetition;
    private Integer statut; //uniquement 0 ou 1
    private String dernier_rappel;

    public Rappel() {
        this.id_rappel = null;
        this.id_med = null;
        this.heure = null;
        this.repetition = 1;
        this.statut = 0;
        this.dernier_rappel = null;
    }

    public Rappel(Integer id_rappel, Integer id_med, String heure, Integer repetition, Integer statut, String dernier_rappel) {
        this.id_rappel = id_rappel;
        this.id_med = id_med;
        this.heure = heure;
        this.repetition = repetition;
        this.statut = statut;
        this.dernier_rappel = dernier_rappel;
    }

    public Integer getId_rappel() {
        return id_rappel;
    }

    public void setId_rappel(Integer id_rappel) {
        this.id_rappel = id_rappel;
    }

    public Integer getId_med() {
        return id_med;
    }

    public void setId_med(Integer id_med) {
        this.id_med = id_med;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public Integer getRepetition() {
        return repetition;
    }

    public void setRepetition(Integer repetition) {
        this.repetition = repetition;
    }

    public Integer getStatut() {
        return statut;
    }

    public void setStatut(Integer statut) {
        this.statut = statut;
    }

    public String getDernier_rappel() {
        return dernier_rappel;
    }

    public void setDernier_rappel(String dernier_rappel) {
        this.dernier_rappel = dernier_rappel;
    }

    @Override
    public String toString() {
        return "Rappel{" +
                "id_rappel=" + id_rappel +
                ", id_med=" + id_med +
                ", heure=" + heure +
                ", repetition=" + repetition +
                ", statut=" + statut +
                ", dernier_rappel=" + dernier_rappel +
                '}';
    }
}

