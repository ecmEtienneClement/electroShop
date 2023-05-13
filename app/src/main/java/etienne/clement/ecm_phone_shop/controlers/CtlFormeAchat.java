package etienne.clement.ecm_phone_shop.controlers;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Date;

import etienne.clement.ecm_phone_shop.R;
import etienne.clement.ecm_phone_shop.models.HistoriqueModel;
import etienne.clement.ecm_phone_shop.models.Product;

/**
 * created by ETIENNE CLEMENT MBAYE on 22-03-2022.
 */
public class CtlFormeAchat extends ArrayAdapter<String> {
    TextView nomClient, prenomClient, numeroClient, addressClient;
    String txtNomClient, txtPrenomClient, txtAddressClient;
    int txtnumeroClient;

    Context context;
    boolean isAdd;
    DBHelper bd;
    HistoriqueModel mHistoriqueModel;

    public CtlFormeAchat(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.isAdd = isAdd;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View mview;
        LayoutInflater mLayoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mview = mLayoutInflater.inflate(R.layout.modef_form_achat, null);
        ImageView mimageView = mview.findViewById(R.id.imageViewAddUpp);
        nomClient = mview.findViewById(R.id.nomClient);
        prenomClient = mview.findViewById(R.id.prenomClient);
        numeroClient = mview.findViewById(R.id.numeroClient);
        addressClient = mview.findViewById(R.id.addressClient);

        mimageView.setImageResource(R.drawable.images02);

        return mview;

    }

    private boolean validateForm() {
        AlertDialog.Builder rappelleCour = new AlertDialog.Builder(context);

        //Netoyage
        txtNomClient = nomClient.getText().toString().trim();
        txtPrenomClient = prenomClient.getText().toString().trim();
        txtAddressClient = addressClient.getText().toString();
        txtnumeroClient = Integer.parseInt(numeroClient.getText().toString().trim());

        if (txtNomClient.length() < 2 || txtPrenomClient.length() < 2 || txtAddressClient.length() < 2) {
            rappelleCour.setTitle("ERREUR !");
            rappelleCour.setMessage("Veillez bien verifier les données saisis");
            rappelleCour.show();
            return false;
        }
        if (String.valueOf(txtnumeroClient).length() < 9) {
            rappelleCour.setTitle("ERREUR !");
            rappelleCour.setMessage("Numéro de téléphone incorecte");
            rappelleCour.show();
            return false;
        }

        return true;
    }

    public boolean add(Product mProduct) {
        if (validateForm()) {
            bd = new DBHelper(context);
            mHistoriqueModel = new HistoriqueModel(new Date().toString(), txtNomClient, txtPrenomClient, txtnumeroClient, txtAddressClient, mProduct.getNom(), mProduct.getMarque(), mProduct.getRam(), mProduct.getStockage(), mProduct.getAutonomie(), mProduct.getPrix(), mProduct.getVersion());
            return bd.saveHistorique(mHistoriqueModel, context);
        } else {
            return false;
        }
    }
    public boolean updateStock(Product mProduct) {
        if (validateForm()) {
            bd = new DBHelper(context);
            return bd.updateProduct(mProduct, context);
        } else {
            return false;
        }
    }


    public void effacer() {
        nomClient.setText("");
        prenomClient.setText("");
        numeroClient.setText(String.valueOf(0));
        addressClient.setText("");

    }

}
