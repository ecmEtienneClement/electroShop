package etienne.clement.ecm_phone_shop.controlers;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import etienne.clement.ecm_phone_shop.MainActivity;
import etienne.clement.ecm_phone_shop.models.Product;
import etienne.clement.ecm_phone_shop.views.AcheterPhone;
import etienne.clement.ecm_phone_shop.views.AddAndUpdatePhone;
import etienne.clement.ecm_phone_shop.views.Apropos;
import etienne.clement.ecm_phone_shop.views.Historique;
import etienne.clement.ecm_phone_shop.views.MainActivityDetailsPhone;

/**
 * created by ETIENNE CLEMENT MBAYE on 20-03-2022.
 */
public class CtlNavigation {
    public static CtlNavigation m_CtlNavigation = null;

    public static final CtlNavigation getInstance() {

        if (CtlNavigation.m_CtlNavigation == null) {
            CtlNavigation.m_CtlNavigation = new CtlNavigation();
        }
        return m_CtlNavigation;
    }

    public void navigationMenu(Context context, String value, Product phoneParam) {
        Intent mIntent;
        switch (value) {
            case "Acceuil":
                mIntent = new Intent(context, MainActivity.class);
                context.startActivity(mIntent);
                //   finish();
                break;
            case "Historique vente":
                mIntent = new Intent(context, Historique.class);
                context.startActivity(mIntent);
                //   finish();
                break;
            case "A propos":
                mIntent = new Intent(context, Apropos.class);
                context.startActivity(mIntent);
                break;
            case "Acheter":
                mIntent = new Intent(context, AcheterPhone.class);
                mIntent.putExtra(EnumerateursConstantes.PHONE_DETAILS.toString(), phoneParam);
                context.startActivity(mIntent);
                //   finish();
                break;
            case "Modifier":
                mIntent = new Intent(context, AddAndUpdatePhone.class);
                mIntent.putExtra(EnumerateursConstantes.PHONE_DETAILS.toString(), phoneParam);
                context.startActivity(mIntent);

                break;
            case "Supprimer":
                mIntent = new Intent(context, MainActivity.class);
                context.startActivity(mIntent);
                break;
            case "Ajouter Produit":
                mIntent = new Intent(context, AddAndUpdatePhone.class);
                mIntent.putExtra(EnumerateursConstantes.PHONE_DETAILS.toString(), phoneParam);
                mIntent.putExtra(EnumerateursConstantes.PHONE_ADD.toString(), "add");
                context.startActivity(mIntent);
                break;
            case "RetourModif":
                mIntent = new Intent(context, MainActivityDetailsPhone.class);
                mIntent.putExtra(EnumerateursConstantes.PHONE_DETAILS.toString(), phoneParam);
                context.startActivity(mIntent);

                break;
            case "historiqueAchat":
                mIntent = new Intent(context, Historique.class);
                context.startActivity(mIntent);
                break;
            case "RetourAcheter":
                mIntent = new Intent(context, MainActivityDetailsPhone.class);
                mIntent.putExtra(EnumerateursConstantes.PHONE_DETAILS.toString(), phoneParam);
                context.startActivity(mIntent);

                break;

        }
    }


}
