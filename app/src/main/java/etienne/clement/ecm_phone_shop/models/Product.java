package etienne.clement.ecm_phone_shop.models;

import java.io.Serializable;

/**
 * created by ETIENNE CLEMENT MBAYE on 19-03-2022.
 */
public class Product implements Serializable {
    //Mise en place des proprietes
    private String nom, marque;
    private Integer id, ram, stockage, autonomie, prix, version, stock;

    /**
     *
     */

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * @param nom
     * @param marque
     * @param prix
     * @param version
     * @param ram
     * @param stockage
     * @param autonomie
     */
    /*
    public Product(String nom, String marque, Integer prix, Integer version, Integer ram, Integer stockage, Integer autonomie, Integer stock) {
        this.nom = nom;
        this.marque = marque;
        this.prix = prix;
        this.version = version;
        this.ram = ram;
        this.stockage = stockage;
        this.autonomie = autonomie;
        this.stock = stock;


    }
*/
    public Product(Integer id, String nom, String marque, Integer prix, Integer version, Integer ram, Integer stockage, Integer autonomie, Integer stock) {
        this.nom = nom;
        this.marque = marque;
        this.id = id;
        this.ram = ram;
        this.stockage = stockage;
        this.autonomie = autonomie;
        this.prix = prix;
        this.version = version;
        this.stock = stock;
    }
//Mise en place de getteur


    public Integer getId() {
        return id;
    }

    public Integer getStock() {
        return stock;
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
}
