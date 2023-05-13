package etienne.clement.ecm_phone_shop.controlers;

/**
 * created by ETIENNE CLEMENT MBAYE on 19-03-2022.
 */
public class CtlReqBd {
    public static CtlReqBd instance = null;

    //Mise en place du singleton
    public static final CtlReqBd getInstance() {

        if (CtlReqBd.instance == null) {
            CtlReqBd.instance = new CtlReqBd();
        }
        return instance;
    }

    //Mise en place des req...
}
