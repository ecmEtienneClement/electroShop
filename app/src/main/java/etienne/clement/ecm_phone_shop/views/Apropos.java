package etienne.clement.ecm_phone_shop.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import java.util.ArrayList;

import etienne.clement.ecm_phone_shop.MainActivity;
import etienne.clement.ecm_phone_shop.R;
import etienne.clement.ecm_phone_shop.controlers.CtlApropos;
import etienne.clement.ecm_phone_shop.controlers.CtlNavigation;
import etienne.clement.ecm_phone_shop.controlers.DBHelper;
import etienne.clement.ecm_phone_shop.models.Product;

public class Apropos extends AppCompatActivity {
    AutoCompleteTextView mAutoCompleteTextViewMenu;
    ArrayAdapter<String> adaptateurMenu;
    ArrayList<String> listMenu = new ArrayList<>();
    CtlNavigation m_CtlNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apropos);
        getSupportActionBar().hide();
        m_CtlNavigation = CtlNavigation.getInstance();
        mAutoCompleteTextViewMenu = findViewById(R.id.cateProductSelect);
        ListView listView = findViewById(R.id.listviewpropo);
        ArrayList<String> list = new ArrayList<>();
        CtlApropos mCtlApropos = new CtlApropos(this, 0);
        list.add(" ");

        listView.setAdapter(mCtlApropos);
        mCtlApropos.addAll(list);
        listMenu.add("Acceuil");
        listMenu.add("Ajouter Produit");
        listMenu.add("Historique vente");


        adaptateurMenu = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listMenu);
        mAutoCompleteTextViewMenu.setAdapter(adaptateurMenu);
        mAutoCompleteTextViewMenu.setOnItemClickListener((parent, view, position, id) -> {

                Product phoneParam = new Product(0, "", "", 0, 0, 0, 0, 0, 0);

                String m_value = (String) parent.getItemAtPosition(position);
                m_CtlNavigation.navigationMenu(Apropos.this, m_value, phoneParam);
                finish();



        });

    }
}