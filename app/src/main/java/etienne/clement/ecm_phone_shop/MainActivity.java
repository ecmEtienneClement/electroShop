package etienne.clement.ecm_phone_shop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;

import etienne.clement.ecm_phone_shop.controlers.CtlAdaptateur;
import etienne.clement.ecm_phone_shop.controlers.CtlNavigation;
import etienne.clement.ecm_phone_shop.controlers.DBHelper;
import etienne.clement.ecm_phone_shop.controlers.EnumerateursConstantes;
import etienne.clement.ecm_phone_shop.models.Product;
import etienne.clement.ecm_phone_shop.views.Historique;
import etienne.clement.ecm_phone_shop.views.MainActivityDetailsPhone;

public class MainActivity extends AppCompatActivity {
    TextView txtTotalProduct;
    private String nomProduct, cateProduct;
    private Integer idProduct, prixProduct, versionProduct, ramProduct, stockageProduct, autoProduct, stockProduct;
    ArrayList<Product> listProduct = new ArrayList<>();
    ArrayList<Product> listProductReversed = new ArrayList<>();
    ArrayList<String> listMarque = new ArrayList<>();
    ArrayList<String> listMenu = new ArrayList<>();
    CtlAdaptateur adaptateurPhone;
    ArrayAdapter<String> adaptateurMarquePhone;
    ArrayAdapter<String> adaptateurMenu;
    AutoCompleteTextView mAutoCompleteTextViewMarque;
    AutoCompleteTextView mAutoCompleteTextViewMenu;
    ListView mListViewPhone;
    CtlNavigation m_CtlNavigation;
    DBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        m_CtlNavigation = CtlNavigation.getInstance();
        //Serialisation de mes elements de la vue
        mListViewPhone = findViewById(R.id.listePhone);
        txtTotalProduct = findViewById(R.id.totalProduct);
        mAutoCompleteTextViewMarque = findViewById(R.id.auto_complete);
        mAutoCompleteTextViewMenu = findViewById(R.id.cateProductSelect);
        // Initialisation liste bd
        iniList();
        listMenu.add("Ajouter Produit");
        listMenu.add("Historique vente");
        listMenu.add("Effacer tout les produits");
        listMenu.add("A propos");

        listMarque.add("All");
        listMarque.add("Ordinateur Portable");
        listMarque.add("Tablette");
        listMarque.add("Téléphone Portable");

        notifyUpdateList();
        adaptateurMarquePhone = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listMarque);
        adaptateurMenu = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listMenu);
        mAutoCompleteTextViewMenu.setAdapter(adaptateurMenu);
        mAutoCompleteTextViewMarque.setAdapter(adaptateurMarquePhone);

        /*
         * PARTIE DES EVENEMENTS
         * */
        mAutoCompleteTextViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        //Traitement du clique du nav
        mAutoCompleteTextViewMenu.setOnItemClickListener((parent, view, position, id) -> {
            if (String.valueOf(parent.getItemAtPosition(position)).equalsIgnoreCase("Effacer tout les produits")) {
                android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                mBuilder.setTitle("CONFIRMATION");
                mBuilder.setMessage("Confirmez-vous la suppression des produits ?");
                mBuilder.setPositiveButton("Oui", (dialog, which) -> {
                    db = new DBHelper(MainActivity.this);
                    db.deleteAllProduct(MainActivity.this);
                    listProduct.clear();
                    notifyUpdateList();
                                });
                mBuilder.setNegativeButton("Non", (dialog, which) -> {

                });
                mBuilder.show();


            } else {
                Product phoneParam = new Product(0, "", "", 0, 0, 0, 0, 0, 0);

                String m_value = (String) parent.getItemAtPosition(position);
                m_CtlNavigation.navigationMenu(MainActivity.this, m_value, phoneParam);
                finish();
            }


        });
        mAutoCompleteTextViewMarque.setOnItemClickListener((parent, view, position, id) -> {
            if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("All")) {
                listProduct.clear();
                iniList();
                notifyUpdateList();

            } else {
                filterList(parent.getItemAtPosition(position).toString());
            }

        });

        //Evenement clique des listes phone

        mListViewPhone.setOnItemClickListener((parent, view, position, id) -> {

            RotateAnimation rotateAnimation = new RotateAnimation(0, 360);
            rotateAnimation.setDuration(400);
            view.startAnimation(rotateAnimation);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Product phoneSelected = (Product) parent.getItemAtPosition(position);
                    //.........
                    Intent pageDetailsPhone = new Intent(MainActivity.this, MainActivityDetailsPhone.class);
                    //avec serialisable........
                    pageDetailsPhone.putExtra(EnumerateursConstantes.PHONE_DETAILS.toString(), phoneSelected);
                    startActivity(pageDetailsPhone);
                    finish();
                }
            }, 500);


        });
    }


    private void iniList() {
        db = new DBHelper(this);
        //Recuperation des produits dans la base de donnee
        Cursor mCursor = db.getAllProduct();
        txtTotalProduct.setText(String.valueOf(mCursor.getCount()));
        if (mCursor.getCount() > 0) {
            while (mCursor.moveToNext()) {
                idProduct = Integer.valueOf(mCursor.getString(0));
                nomProduct = mCursor.getString(1);
                cateProduct = mCursor.getString(2);
                prixProduct = Integer.valueOf(mCursor.getString(3));
                versionProduct = Integer.valueOf(mCursor.getString(4));
                ramProduct = Integer.valueOf(mCursor.getString(5));
                stockageProduct = Integer.valueOf(mCursor.getString(6));
                autoProduct = Integer.valueOf(mCursor.getString(7));
                stockProduct = Integer.valueOf(mCursor.getString(8));
                Product currentProduct = new Product(idProduct, nomProduct, cateProduct, prixProduct, versionProduct, ramProduct, stockageProduct, autoProduct, stockProduct);
                listProduct.add(currentProduct);
            }

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("INFO");
            builder.setMessage("Désoler aucun produit n'est enregistrez");
            builder.show();
        }
    }

    private void filterList(String mcateProduct) {
        db = new DBHelper(this);
        //Recuperation des produits dans la base de donnee
        Cursor mCursor = db.getProductByCate(mcateProduct);
        txtTotalProduct.setText(String.valueOf(mCursor.getCount()));
        if (mCursor.getCount() > 0) {
            listProduct.clear();
            while (mCursor.moveToNext()) {
                idProduct = Integer.valueOf(mCursor.getString(0));
                nomProduct = mCursor.getString(1);
                cateProduct = mCursor.getString(2);
                prixProduct = Integer.valueOf(mCursor.getString(3));
                versionProduct = Integer.valueOf(mCursor.getString(4));
                ramProduct = Integer.valueOf(mCursor.getString(5));
                stockageProduct = Integer.valueOf(mCursor.getString(6));
                autoProduct = Integer.valueOf(mCursor.getString(7));
                stockProduct = Integer.valueOf(mCursor.getString(8));
                Product currentProduct = new Product(idProduct, nomProduct, cateProduct, prixProduct, versionProduct, ramProduct, stockageProduct, autoProduct, stockProduct);
                listProduct.add(currentProduct);
            }
            notifyUpdateList();
        } else {
            listProduct.clear();
            notifyUpdateList();
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("INFO");
            builder.setMessage("Désoler aucun produit de cette cathégorie n'est enregistrez");
            builder.show();
        }
    }

    private void notifyUpdateList() {
        Collections.reverse(listProduct);
        adaptateurPhone = new CtlAdaptateur(getApplicationContext(), 0);
        mListViewPhone.setAdapter(adaptateurPhone);
        adaptateurPhone.addAll(listProduct);
    }
}
/*
*
* RotateAnimation rotateAnimation = new RotateAnimation(0, 360);
        rotateAnimation.setDuration(400);
        view.startAnimation(rotateAnimation);
        * */