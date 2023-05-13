package etienne.clement.ecm_phone_shop.controlers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import etienne.clement.ecm_phone_shop.R;
import etienne.clement.ecm_phone_shop.models.Product;

/**
 * created by ETIENNE CLEMENT MBAYE on 20-03-2022.
 */
public class CtlAdaptateurDetails extends ArrayAdapter<Product> {

    Product mPhone;

    public CtlAdaptateurDetails(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View mViewAdaptateur;
        LayoutInflater mLayoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mViewAdaptateur = mLayoutInflater.inflate(R.layout.model_details_phone, null);
        mPhone = getItem(position);
        //Recuperation des elements de la vue
        TextView nom = mViewAdaptateur.findViewById(R.id.txtNomDetails);
        TextView marque = mViewAdaptateur.findViewById(R.id.txtMarqueDetails);
        TextView prix = mViewAdaptateur.findViewById(R.id.txtPrixDetails);
        TextView version = mViewAdaptateur.findViewById(R.id.txtVersionDetails);
        TextView ram = mViewAdaptateur.findViewById(R.id.txtRamDetails);
        TextView stockage = mViewAdaptateur.findViewById(R.id.txtStockageDetails);
        TextView autonomie = mViewAdaptateur.findViewById(R.id.txtAutonomiDetails);
        TextView stock = mViewAdaptateur.findViewById(R.id.txtStockDetails);
        ImageView m_imageView = mViewAdaptateur.findViewById(R.id.imageViewDetails);

        //Mise en place des valeur de la vue
        if (mPhone.getMarque().equalsIgnoreCase("Téléphone Portable")) {
            m_imageView.setImageResource(R.drawable.images );
        } else if (mPhone.getMarque().equalsIgnoreCase("Tablette")) {
            m_imageView.setImageResource(R.drawable.imgtab);
        } else {
            m_imageView.setImageResource(R.drawable.ordi);

        }
        nom.setText(mPhone.getNom().toUpperCase());
        marque.setText(mPhone.getMarque());
        prix.setText(mPhone.getPrix().toString() + " FCFA");
        version.setText(mPhone.getVersion().toString());
        ram.setText(mPhone.getRam().toString() + " GB");
        stockage.setText(mPhone.getStockage().toString() + " GB");
        autonomie.setText(mPhone.getAutonomie().toString() + " Heures");
        stock.setText(mPhone.getStock().toString());

        return mViewAdaptateur;
    }
}
