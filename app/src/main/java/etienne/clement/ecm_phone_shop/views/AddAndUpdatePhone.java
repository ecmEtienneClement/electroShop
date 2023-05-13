package etienne.clement.ecm_phone_shop.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import etienne.clement.ecm_phone_shop.R;
import etienne.clement.ecm_phone_shop.controlers.CtlForme;
import etienne.clement.ecm_phone_shop.controlers.CtlNavigation;
import etienne.clement.ecm_phone_shop.controlers.EnumerateursConstantes;
import etienne.clement.ecm_phone_shop.models.Product;

public class AddAndUpdatePhone extends AppCompatActivity {
    ArrayList<String> listMenu = new ArrayList<>();
    ArrayList<String> listOption = new ArrayList<>();
    ArrayAdapter<String> adaptateurMenu;
    ArrayAdapter<String> adaptateurOption;
    CtlForme mCtlForme;
    //TextView inpNom, inpMarque, inpPrix, inpVersion, inpRam, inpStockage, inpAutonomie, inpStock;

    AutoCompleteTextView mAutoCompleteTextViewMenu;
    AutoCompleteTextView mAutoCompleteTextViewOption;
    ListView mListViewForm;
    CtlNavigation m_CtlNavigation;
    Product phoneDetails;
    boolean isAdd = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_update_phone);
        getSupportActionBar().hide();

        mListViewForm = findViewById(R.id.listViewForm);
        m_CtlNavigation = CtlNavigation.getInstance();
        mAutoCompleteTextViewMenu = findViewById(R.id.cateProductSelect);
        mAutoCompleteTextViewOption = findViewById(R.id.auto_complete);


        //Liste Menu
        listMenu.add("Acceuil");
        listMenu.add("Ajouter Produit");
        listMenu.add("Historique vente");
        listMenu.add("A propos");

        //Liste Option
        listOption.add("Valider");
        listOption.add("Effacer");
        listOption.add("Annuler");

        if (mIntent != null) {
            if (mIntent.hasExtra(EnumerateursConstantes.PHONE_DETAILS.toString())) {
                phoneDetails = (Product) mIntent.getSerializableExtra(EnumerateursConstantes.PHONE_DETAILS.toString());

            }
            if (mIntent.hasExtra(EnumerateursConstantes.PHONE_ADD.toString())) {
                isAdd = true;
                listMenu.remove(1);
            }
        } else {
            Toast.makeText(getApplicationContext(), "Une erreur s'est produite", Toast.LENGTH_SHORT).show();
        }
//Instanciation ctl
        //Ici le constructeur apres la verification de isAdd pour savoir si le user add ou update
        mCtlForme = new CtlForme(this, 0, isAdd);
        adaptateurMenu = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listMenu);
        mAutoCompleteTextViewMenu.setAdapter(adaptateurMenu);
        adaptateurOption = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listOption);
        mAutoCompleteTextViewOption.setAdapter(adaptateurOption);
        mListViewForm.setAdapter(mCtlForme);
        mCtlForme.addAll(phoneDetails);

        //Traitement du clique du nav
        mAutoCompleteTextViewMenu.setOnItemClickListener((parent, view, position, id) -> {
            Product phoneParam = new Product(0, "", "", 0, 0, 0, 0, 0, 0);

            String m_value = (String) parent.getItemAtPosition(position);
            m_CtlNavigation.navigationMenu(AddAndUpdatePhone.this, m_value, phoneParam);
            finish();
        });
        //Traitement du clique du nav
        mAutoCompleteTextViewOption.setOnItemClickListener((parent, view, position, id) -> {
            String m_value = (String) parent.getItemAtPosition(position);
            if (m_value.equals("Valider")) {
                if (validation()) {
                    if (isAdd) {
                        m_CtlNavigation.navigationMenu(AddAndUpdatePhone.this, "RetourModif", mCtlForme.getLastProduct());

                    } else {
                        m_CtlNavigation.navigationMenu(AddAndUpdatePhone.this, "RetourModif", mCtlForme.getSelectProduct());

                    }
                }
            } else if (m_value.equals("Effacer")) {
                effacer();
            } else {
                if (!isAdd) {
                    m_CtlNavigation.navigationMenu(AddAndUpdatePhone.this, "RetourModif", phoneDetails);
                } else {
                    m_CtlNavigation.navigationMenu(AddAndUpdatePhone.this, "Acceuil", phoneDetails);
                }
                finish();
            }
        });
    }

    //Evenement btn Valider

    private boolean validation() {
        if (isAdd) {
            return mCtlForme.add();
        } else {
            return mCtlForme.update();
        }

    }

    //Evenement du btn effacer
    private void effacer() {
        mCtlForme.effacer();
    }
}