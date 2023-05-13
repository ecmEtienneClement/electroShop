package etienne.clement.ecm_phone_shop.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import etienne.clement.ecm_phone_shop.R;
import etienne.clement.ecm_phone_shop.controlers.CtlAdaptateurDetails;
import etienne.clement.ecm_phone_shop.controlers.CtlNavigation;
import etienne.clement.ecm_phone_shop.controlers.DBHelper;
import etienne.clement.ecm_phone_shop.controlers.EnumerateursConstantes;
import etienne.clement.ecm_phone_shop.models.Product;

public class MainActivityDetailsPhone extends AppCompatActivity {

    ArrayList<String> listMenu = new ArrayList<>();
    ArrayList<String> listOption = new ArrayList<>();
    ArrayAdapter<String> adaptateurMenu;
    ArrayAdapter<String> adaptateurOption;
    CtlAdaptateurDetails mCtlAdaptateurDetails;
    ListView mListViewDetails;
    AutoCompleteTextView mAutoCompleteTextViewMenu;
    AutoCompleteTextView mAutoCompleteTextViewOption;
    Product phoneDetails;
    CtlNavigation m_CtlNavigation;
    DBHelper bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_details_phone);
        getSupportActionBar().hide();
        //Instanciation ctl
        m_CtlNavigation = CtlNavigation.getInstance();
        //Serialisation de mes elements de la vue
        mAutoCompleteTextViewOption = findViewById(R.id.auto_complete);
        mAutoCompleteTextViewMenu = findViewById(R.id.cateProductSelect);
        mListViewDetails = findViewById(R.id.listePhoneDetails);
        //Liste Menu
        listMenu.add("Acceuil");
        listMenu.add("Ajouter Produit");
        listMenu.add("Historique vente");
        listMenu.add("A propos");
        //Liste Option
        listOption.add("Acheter");
        listOption.add("Modifier");
        listOption.add("Supprimer");

        adaptateurMenu = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listMenu);
        adaptateurOption = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listOption);

        mAutoCompleteTextViewMenu.setAdapter(adaptateurMenu);
        mAutoCompleteTextViewOption.setAdapter(adaptateurOption);
        //Recuperation de intent
        Intent mIntent = getIntent();
        if (mIntent != null) {
            if (mIntent.hasExtra(EnumerateursConstantes.PHONE_DETAILS.toString())) {
                phoneDetails = (Product) mIntent.getSerializableExtra(EnumerateursConstantes.PHONE_DETAILS.toString());
                mCtlAdaptateurDetails = new CtlAdaptateurDetails(getApplicationContext(), 0);
                mListViewDetails.setAdapter(mCtlAdaptateurDetails);
                mCtlAdaptateurDetails.add(phoneDetails);
            }
        } else {
            Toast.makeText(getApplicationContext(), "Une erreur s'est produite", Toast.LENGTH_SHORT).show();
        }

        //Traitement du clique du nav
        mAutoCompleteTextViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product phoneParam = new Product(0, "", "", 0, 0, 0, 0, 0, 0);
                String m_value = (String) parent.getItemAtPosition(position);
                m_CtlNavigation.navigationMenu(MainActivityDetailsPhone.this, m_value, phoneParam);
                finish();
            }

        });
        //Traitement du clique du option
        mAutoCompleteTextViewOption.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String m_value = (String) parent.getItemAtPosition(position);
                if (m_value == "Supprimer") {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivityDetailsPhone.this);
                    mBuilder.setTitle("CONFIRMATION");
                    mBuilder.setMessage("Confirmez-vous la suppression de ce produit ?");
                    mBuilder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            bd = new DBHelper(MainActivityDetailsPhone.this);
                            if (bd.deleteProduct(phoneDetails, MainActivityDetailsPhone.this)) {
                                m_CtlNavigation.navigationMenu(MainActivityDetailsPhone.this, m_value, phoneDetails);
                                finish();
                            }

                        }
                    });
                    mBuilder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    mBuilder.show();
                } else {
                    m_CtlNavigation.navigationMenu(MainActivityDetailsPhone.this, m_value, phoneDetails);
                    finish();
                }

            }

        });
    }


}

