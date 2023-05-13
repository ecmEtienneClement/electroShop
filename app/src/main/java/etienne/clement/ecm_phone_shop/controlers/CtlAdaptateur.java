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

import java.util.Locale;

import etienne.clement.ecm_phone_shop.R;
import etienne.clement.ecm_phone_shop.models.Product;

/**
 * created by ETIENNE CLEMENT MBAYE on 19-03-2022.
 */
public class CtlAdaptateur extends ArrayAdapter<Product> {
    public CtlAdaptateur(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View m_view;
        LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        m_view = mInflater.inflate(R.layout.model_liste_product, null);
        Product currentPhone = getItem(position);
        //Recuperation des elements de ma vue
        TextView m_NomPhone = m_view.findViewById(R.id.txtNameProduit);
        TextView m_VersionPhone = m_view.findViewById(R.id.txtCateProduit);
        TextView m_PrixPhone = m_view.findViewById(R.id.txtPrixProduit);
        TextView m_StockPhone = m_view.findViewById(R.id.txtStocProduit);
        ImageView imgPhone = m_view.findViewById(R.id.imgProduit);

        //Mise en place des valeure dans les champs txtView

        if (currentPhone.getMarque().equalsIgnoreCase("Téléphone Portable")) {
            imgPhone.setImageResource(R.drawable.images);
        } else if (currentPhone.getMarque().equalsIgnoreCase("Tablette")) {
            imgPhone.setImageResource(R.drawable.imgtab);
        } else {
            imgPhone.setImageResource(R.drawable.ordi);

        }
        m_NomPhone.setText(currentPhone.getNom().toUpperCase(Locale.ROOT));
        m_VersionPhone.setText(currentPhone.getMarque());
        m_PrixPhone.setText(currentPhone.getPrix().toString() + " FCFA");
        m_StockPhone.setText(currentPhone.getStock().toString());
        return m_view;
    }
}
