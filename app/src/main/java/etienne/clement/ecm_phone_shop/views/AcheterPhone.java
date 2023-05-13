package etienne.clement.ecm_phone_shop.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import etienne.clement.ecm_phone_shop.R;
import etienne.clement.ecm_phone_shop.controlers.CtlFormeAchat;
import etienne.clement.ecm_phone_shop.controlers.CtlNavigation;
import etienne.clement.ecm_phone_shop.controlers.EnumerateursConstantes;
import etienne.clement.ecm_phone_shop.models.Product;

public class AcheterPhone extends AppCompatActivity {
    ArrayList<String> listMenu = new ArrayList<>();
    ArrayList<String> listOption = new ArrayList<>();
    ArrayAdapter<String> adaptateurMenu;
    ArrayAdapter<String> adaptateurOption;
    CtlFormeAchat mCtlFormeAchat;
    ListView mListViewForm;
    AutoCompleteTextView mAutoCompleteTextViewMenu;
    AutoCompleteTextView mAutoCompleteTextViewOption;

    CtlNavigation m_CtlNavigation;
    Product phoneDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acheter_phone);
        Objects.requireNonNull(getSupportActionBar()).hide();

        m_CtlNavigation = CtlNavigation.getInstance();
        mAutoCompleteTextViewMenu = findViewById(R.id.cateProductSelect);
        mAutoCompleteTextViewOption = findViewById(R.id.auto_complete);
        mListViewForm = findViewById(R.id.listformachat);
        mCtlFormeAchat = new CtlFormeAchat(this, 0);

        //Liste Menu
        listMenu.add("Acceuil");
        listMenu.add("Ajouter Produit");
        listMenu.add("Historique vente");
        listMenu.add("A propos");

        //Liste Option
        listOption.add("Valider");
        listOption.add("Effacer");
        listOption.add("Annuler");


        Intent mIntent = getIntent();
        if (mIntent != null) {
            if (mIntent.hasExtra(EnumerateursConstantes.PHONE_DETAILS.toString())) {
                phoneDetails = (Product) mIntent.getSerializableExtra(EnumerateursConstantes.PHONE_DETAILS.toString());

            }
        } else {
            Toast.makeText(getApplicationContext(), "Une erreur s'est produite", Toast.LENGTH_SHORT).show();
        }


        adaptateurMenu = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listMenu);
        mAutoCompleteTextViewMenu.setAdapter(adaptateurMenu);
        adaptateurOption = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listOption);
        mAutoCompleteTextViewOption.setAdapter(adaptateurOption);
        mListViewForm.setAdapter(mCtlFormeAchat);
        mCtlFormeAchat.add("");


        //Traitement du clique du nav
        mAutoCompleteTextViewMenu.setOnItemClickListener((parent, view, position, id) -> {
            Product phoneParam = new Product(0, "", "", 0, 0, 0, 0, 0, 0);

            String m_value = (String) parent.getItemAtPosition(position);
            m_CtlNavigation.navigationMenu(AcheterPhone.this, m_value, phoneParam);
            finish();
        });
        //Traitement du clique des option
        mAutoCompleteTextViewOption.setOnItemClickListener((parent, view, position, id) -> {
            String m_value = (String) parent.getItemAtPosition(position);
            if (m_value.equals("Valider")) {

                if (validation()) {
                    m_CtlNavigation.navigationMenu(AcheterPhone.this, "historiqueAchat", phoneDetails);
                    finish();

                }
            } else if (m_value.equals("Effacer")) {
                effacer();
            } else {
                m_CtlNavigation.navigationMenu(AcheterPhone.this, "RetourAcheter", phoneDetails);
                finish();
            }
        });
    }
    //Evenement btn Valider

    private boolean validation() {
        if (mCtlFormeAchat.add(phoneDetails)) {
            phoneDetails.setStock(phoneDetails.getStock() - 1);
            return mCtlFormeAchat.updateStock(phoneDetails);
        }
        return false;
    }

    //Evenement du btn effacer
    private void effacer() {
        mCtlFormeAchat.effacer();

    }
}