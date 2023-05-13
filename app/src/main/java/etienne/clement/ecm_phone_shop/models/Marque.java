package etienne.clement.ecm_phone_shop.models;

/**
 * created by ETIENNE CLEMENT MBAYE on 19-03-2022.
 */
public class Marque {
    //Mise en place de la propriete
    private String marque;

    /**
     * @param marque
     */
    public Marque(String marque) {
        this.marque = marque;
    }
    //Mise en place du getteur

    public String getMarque() {
        return marque;
    }
}
