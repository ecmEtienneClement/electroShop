package etienne.clement.ecm_phone_shop.models;

import java.util.Date;

/**
 * created by ETIENNE CLEMENT MBAYE on 22-03-2022.
 */
public class HistoriqueModel {

    //Mise en place des proprietes
    private String date;
    private String nomClient, prenomClient, addressClient, nom, marque;
    private Integer ram, stockage, autonomie, prix, version, numeroClient;

    /**
     * @param date
     * @param nomClient
     * @param prenomClient
     * @param addressClient
     * @param nom
     * @param marque
     * @param ram
     * @param stockage
     * @param autonomie
     * @param prix
     * @param version
     * @param numeroClient
     */
    public HistoriqueModel(String date, String nomClient, String prenomClient, Integer numeroClient, String addressClient, String nom, String marque, Integer ram, Integer stockage, Integer autonomie, Integer prix, Integer version) {
        this.date = date;
        this.nomClient = nomClient;
        this.prenomClient = prenomClient;
        this.numeroClient = numeroClient;
        this.addressClient = addressClient;
        this.nom = nom;
        this.marque = marque;
        this.ram = ram;
        this.stockage = stockage;
        this.autonomie = autonomie;
        this.prix = prix;
        this.version = version;

    }

    public String getDate() {
        return date;
    }

    public String getNomClient() {
        return nomClient;
    }

    public String getPrenomClient() {
        return prenomClient;
    }

    public String getAddressClient() {
        return addressClient;
    }

    public String getNom() {
        return nom;
    }

    public String getMarque() {
        return marque;
    }

    public Integer getRam() {
        return ram;
    }

    public Integer getStockage() {
        return stockage;
    }

    public Integer getAutonomie() {
        return autonomie;
    }

    public Integer getPrix() {
        return prix;
    }

    public Integer getVersion() {
        return version;
    }

    public Integer getNumeroClient() {
        return numeroClient;
    }
}
