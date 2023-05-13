package etienne.clement.ecm_phone_shop.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import etienne.clement.ecm_phone_shop.R;
import etienne.clement.ecm_phone_shop.controlers.CtlAdaptateurHistorique;
import etienne.clement.ecm_phone_shop.controlers.CtlNavigation;
import etienne.clement.ecm_phone_shop.controlers.DBHelper;
import etienne.clement.ecm_phone_shop.models.HistoriqueModel;
import etienne.clement.ecm_phone_shop.models.Product;

public class Historique extends AppCompatActivity {
    private String dateAchat, nomClient, prenomClient, addressClient, nomProduct, cateProduct;

    private Integer numeroClient, prixProduct, versionProduct, ramProduct, stockageProduct, autoProduct, stockProduct;

    ArrayList<String> listMenu = new ArrayList<>();
    ArrayList<String> listOption = new ArrayList<>();
    ArrayList<HistoriqueModel> listHistorique = new ArrayList<>();
    ArrayAdapter<String> adaptateurMenu;
    ArrayAdapter<String> adaptateurOption;
    ListView mListViewDetails;
    AutoCompleteTextView mAutoCompleteTextViewMenu;
    AutoCompleteTextView mAutoCompleteTextViewOption;
    CtlAdaptateurHistorique mCtlAdaptateurHistorique;
    CtlNavigation m_CtlNavigation;
    DBHelper db;
    TextView totalHist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);
        getSupportActionBar().hide();
        totalHist = findViewById(R.id.totalProduct);

        db = new DBHelper(this);
        //Recuperation des produits dans la base de donnee
        Cursor mCursor = db.getAllHistorique();

        if (mCursor.getCount() > 0) {
            totalHist.setText(String.valueOf(mCursor.getCount()));
            while (mCursor.moveToNext()) {
                dateAchat = mCursor.getString(1);
                nomClient = mCursor.getString(2);
                prenomClient = mCursor.getString(3);
                addressClient = mCursor.getString(4);
                numeroClient = Integer.valueOf(mCursor.getString(5));
                nomProduct = mCursor.getString(6);
                cateProduct = mCursor.getString(7);
                prixProduct = Integer.valueOf(mCursor.getString(8));
                versionProduct = Integer.valueOf(mCursor.getString(9));
                ramProduct = Integer.valueOf(mCursor.getString(10));
                stockageProduct = Integer.valueOf(mCursor.getString(11));
                autoProduct = Integer.valueOf(mCursor.getString(12));
                HistoriqueModel currentHistorique = new HistoriqueModel(dateAchat, nomClient, prenomClient, numeroClient, addressClient, nomProduct, cateProduct, ramProduct, stockageProduct, autoProduct, prixProduct, versionProduct);
                listHistorique.add(currentHistorique);
            }

        } else {
            totalHist.setText("0");
            AlertDialog.Builder builder = new AlertDialog.Builder(Historique.this);
            builder.setTitle("INFO");
            builder.setMessage("Désoler aucun achat de produit n'est effectuer");
            builder.show();
        }

        m_CtlNavigation = CtlNavigation.getInstance();
        //Serialisation de mes elements de la vue
        mCtlAdaptateurHistorique = new CtlAdaptateurHistorique(getApplicationContext(), 0);
        mAutoCompleteTextViewOption = findViewById(R.id.auto_complete);
        mAutoCompleteTextViewMenu = findViewById(R.id.cateProductSelect);
        mListViewDetails = findViewById(R.id.listePhoneDetails);

        Product phoneParam = new Product(0, "", "", 0, 0, 0, 0, 0, 0);

        //Liste Menu
        listMenu.add("Acceuil");
        listMenu.add("Ajouter Produit");
        listMenu.add("A propos");
        //Liste Option
        listOption.add("Effacer Historique");

        adaptateurMenu = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listMenu);
        adaptateurOption = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listOption);

        mAutoCompleteTextViewMenu.setAdapter(adaptateurMenu);
        mAutoCompleteTextViewOption.setAdapter(adaptateurOption);
        mListViewDetails.setAdapter(mCtlAdaptateurHistorique);
        Collections.reverse(listHistorique);
        mCtlAdaptateurHistorique.addAll(listHistorique);


        //Traitement du clique du nav
        mAutoCompleteTextViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String m_value = (String) parent.getItemAtPosition(position);
                m_CtlNavigation.navigationMenu(Historique.this, m_value, phoneParam);
                finish();
            }

        });
        //Traitement du clique du option
        mAutoCompleteTextViewOption.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(Historique.this);
                mBuilder.setTitle("CONFIRMATION");
                mBuilder.setMessage("Confirmez-vous la suppression de l'historique ?");
                mBuilder.setPositiveButton("Oui", (dialog, which) -> {
                    DBHelper db = new DBHelper(Historique.this);
                    if (db.deleteAllHistorique(Historique.this)) {
                        //  String m_value = (String) parent.getItemAtPosition(position);
                        m_CtlNavigation.navigationMenu(Historique.this, "Supprimer", phoneParam);
                        finish();
                    }
                });
                mBuilder.setNegativeButton("Non", (dialog, which) -> {

                });
                mBuilder.show();


            }

        });
    }
}
