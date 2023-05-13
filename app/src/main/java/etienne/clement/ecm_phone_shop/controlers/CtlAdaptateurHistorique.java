package etienne.clement.ecm_phone_shop.controlers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import etienne.clement.ecm_phone_shop.R;
import etienne.clement.ecm_phone_shop.models.HistoriqueModel;


/**
 * created by ETIENNE CLEMENT MBAYE on 22-03-2022.
 */
public class CtlAdaptateurHistorique  extends ArrayAdapter<HistoriqueModel> {
    public CtlAdaptateurHistorique(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View mViewAdaptateur;
        LayoutInflater mLayoutInflater= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewAdaptateur= mLayoutInflater.inflate(R.layout.model_historique,null);
        HistoriqueModel currentHistorique = getItem(position);
        //Recuperation des elements de la vue

        TextView dateAchat = mViewAdaptateur.findViewById(R.id.txtDate);

        TextView nomClient = mViewAdaptateur.findViewById(R.id.nomClient);
        TextView prenomClient = mViewAdaptateur.findViewById(R.id.prenomClient);
        TextView numeroClient = mViewAdaptateur.findViewById(R.id.numeroClient);
        TextView addressClient = mViewAdaptateur.findViewById(R.id.addressClient);

        TextView nom = mViewAdaptateur.findViewById(R.id.txtNomDetails);
        TextView marque = mViewAdaptateur.findViewById(R.id.txtMarqueDetails);
        TextView prix = mViewAdaptateur.findViewById(R.id.txtPrixDetails);
        TextView version = mViewAdaptateur.findViewById(R.id.txtVersionDetails);
        TextView ram = mViewAdaptateur.findViewById(R.id.txtRamDetails);
        TextView stockage = mViewAdaptateur.findViewById(R.id.txtStockageDetails);
        TextView autonomie = mViewAdaptateur.findViewById(R.id.txtAutonomiDetails);


        //Mise en place des valeur de la vue

        dateAchat.setText(currentHistorique.getDate().toString().toLowerCase() );
        nomClient.setText(currentHistorique.getNomClient().toUpperCase());
        prenomClient.setText(currentHistorique.getPrenomClient().toUpperCase());
        numeroClient.setText(currentHistorique.getNumeroClient().toString() );
        addressClient.setText(currentHistorique.getAddressClient());


        nom.setText(currentHistorique.getNom().toUpperCase());
        marque.setText(currentHistorique.getMarque().toUpperCase());
        prix.setText(currentHistorique.getPrix().toString() + " FCFA");
        version.setText(currentHistorique.getVersion().toString());
        ram.setText(currentHistorique.getRam().toString() + " GB");
        stockage.setText(currentHistorique.getStockage().toString() + " GB");
        autonomie.setText(currentHistorique.getAutonomie().toString() + " Heures");
        return  mViewAdaptateur;
    }
}
