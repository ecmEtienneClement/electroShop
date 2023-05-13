package etienne.clement.ecm_phone_shop.controlers;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;

import etienne.clement.ecm_phone_shop.R;
import etienne.clement.ecm_phone_shop.models.Product;

/**
 * created by ETIENNE CLEMENT MBAYE on 22-03-2022.
 */
public class CtlForme extends ArrayAdapter<Product> {
    ArrayList<String> listCategorie = new ArrayList<>();
    AutoCompleteTextView mAutoCompleteTextViewOptionCategorie;
    ArrayAdapter<String> adaptateurListCate;
    TextView nomProduct, prixProduct, versionProduct, ramProduct, stockageProduct, autoProduct, stockProduct;
    String txtNomProduct;
    String txtCateProduct = "";
    int txtIdProduct, txtPrixProduct, txtVersionProduct, txtRamProduct, txtStockageProduct, txtAutoProduct, txtStockProduct;

    Context context;
    boolean isAdd;
    DBHelper bd;
    Product mProduct;

    public CtlForme(@NonNull Context context, int resource, boolean isAdd) {
        super(context, resource);
        this.context = context;
        this.isAdd = isAdd;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View mview;
        LayoutInflater mLayoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mview = mLayoutInflater.inflate(R.layout.modef_form, null);
        ImageView mimageView = mview.findViewById(R.id.imageViewAddUpp);
        nomProduct = mview.findViewById(R.id.nomProduct);
        mAutoCompleteTextViewOptionCategorie = mview.findViewById(R.id.cateProductSelect);
        prixProduct = mview.findViewById(R.id.prixProduct);
        versionProduct = mview.findViewById(R.id.versionProduct);
        ramProduct = mview.findViewById(R.id.ramProduct);
        stockageProduct = mview.findViewById(R.id.stockageProduct);
        autoProduct = mview.findViewById(R.id.autoProduct);
        stockProduct = mview.findViewById(R.id.stockProduct);

        Product mproduct = getItem(position);
        txtIdProduct = mproduct.getId();
        listCategorie.add("Ordinateur Portable");
        listCategorie.add("Tablette");
        listCategorie.add("Téléphone Portable");
        adaptateurListCate = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, listCategorie);
        mAutoCompleteTextViewOptionCategorie.setAdapter(adaptateurListCate);

        mAutoCompleteTextViewOptionCategorie.setOnItemClickListener((parent1, view, position1, id) -> {

            txtCateProduct = (String) parent1.getItemAtPosition(position1);
        });

        if (mproduct.getMarque().equalsIgnoreCase("Téléphone Portable")) {
            mimageView.setImageResource(R.drawable.images);
        } else if (mproduct.getMarque().equalsIgnoreCase("Tablette")) {
            mimageView.setImageResource(R.drawable.imgtab);
        } else if(mproduct.getMarque().equalsIgnoreCase("Ordinateur Portable" )) {
            mimageView.setImageResource(R.drawable.ordi);
        }else{
            mimageView.setImageResource(R.drawable.images02);
        }

        nomProduct.setText(mproduct.getNom());
        // mAutoCompleteTextViewOptionCategorie.setText(mproduct.getMarque());
        prixProduct.setText(mproduct.getPrix().toString());
        versionProduct.setText(mproduct.getVersion().toString());
        ramProduct.setText(mproduct.getRam().toString());
        stockageProduct.setText(mproduct.getStockage().toString());
        autoProduct.setText(mproduct.getAutonomie().toString());
        stockProduct.setText(mproduct.getStock().toString());

        return mview;

    }

    private boolean validateForm() {
        AlertDialog.Builder rappelleCour = new AlertDialog.Builder(context);

        //Netoyage
        txtNomProduct = nomProduct.getText().toString().trim();
        // txtCateProduct = cateProduct.getText().toString().trim();
        txtPrixProduct = Integer.parseInt(prixProduct.getText().toString());
        txtVersionProduct = Integer.parseInt(versionProduct.getText().toString().trim());
        txtRamProduct = Integer.parseInt(ramProduct.getText().toString().trim());
        txtStockageProduct = Integer.parseInt(stockageProduct.getText().toString().trim());
        txtAutoProduct = Integer.parseInt(autoProduct.getText().toString().trim());
        txtStockProduct = Integer.parseInt(stockProduct.getText().toString().trim());
        if (txtNomProduct.length() < 2) {
            rappelleCour.setTitle("ERREUR !");
            rappelleCour.setMessage("Le nom du produit saisi est trop court");
            rappelleCour.show();
            return false;
        }
        if (txtCateProduct.length() < 2) {
            rappelleCour.setTitle("ERREUR !");
            rappelleCour.setMessage("Veillez choisir une catégorie pour cet produit");
            rappelleCour.show();
            return false;
        }
        if (txtPrixProduct < 5000) {
            rappelleCour.setTitle("ERREUR !");
            rappelleCour.setMessage("Le prix du produit saisi est tres faible");
            rappelleCour.show();
            return false;
        }
        if (txtVersionProduct < 2000) {
            rappelleCour.setTitle("ERREUR !");
            rappelleCour.setMessage("La version du produit saisi est tres ancienne");
            rappelleCour.show();
            return false;
        }
        if (txtAutoProduct == 0 || txtRamProduct == 0 || txtStockProduct == 0 || txtStockageProduct == 0) {
            rappelleCour.setTitle("ERREUR !");
            rappelleCour.setMessage("Veillez bien verifier les données saisis");
            rappelleCour.show();
            return false;
        }

        return true;
    }

    public boolean add() {
        if (validateForm()) {
            bd = new DBHelper(context);
            mProduct = new Product(txtIdProduct, txtNomProduct, txtCateProduct, txtPrixProduct, txtVersionProduct, txtRamProduct, txtStockageProduct, txtAutoProduct, txtStockProduct);

            return bd.savedProduct(mProduct, context);
        } else {
            return false;
        }
    }

    public boolean update() {
        if (validateForm()) {
            bd = new DBHelper(context);
            mProduct = new Product(txtIdProduct, txtNomProduct, txtCateProduct, txtPrixProduct, txtVersionProduct, txtRamProduct, txtStockageProduct, txtAutoProduct, txtStockProduct);
            return bd.updateProduct(mProduct, context);
        } else {
            return false;
        }
    }

    public Product getSelectProduct() {
        Product mProductSelected = null;
        String nomProduct, cateProduct;
        Integer idProduct, prixProduct, versionProduct, ramProduct, stockageProduct, autoProduct, stockProduct;
        bd = new DBHelper(context);

        Cursor mCursor = bd.getSelectedProduct(mProduct);
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
            mProductSelected = new Product(idProduct, nomProduct, cateProduct, prixProduct, versionProduct, ramProduct, stockageProduct, autoProduct, stockProduct);
            return mProductSelected;
        }
        return mProductSelected;
    }

    public Product getLastProduct() {
        Product mProductSelected;
        String nomProduct, cateProduct;
        Integer idProduct, prixProduct, versionProduct, ramProduct, stockageProduct, autoProduct, stockProduct;
        bd = new DBHelper(context);

        Cursor mCursor = bd.getLastProduct();
        mCursor.moveToLast();
        idProduct = Integer.valueOf(mCursor.getString(0));
        nomProduct = mCursor.getString(1);
        cateProduct = mCursor.getString(2);
        prixProduct = Integer.valueOf(mCursor.getString(3));
        versionProduct = Integer.valueOf(mCursor.getString(4));
        ramProduct = Integer.valueOf(mCursor.getString(5));
        stockageProduct = Integer.valueOf(mCursor.getString(6));
        autoProduct = Integer.valueOf(mCursor.getString(7));
        stockProduct = Integer.valueOf(mCursor.getString(8));
        mProductSelected = new Product(idProduct, nomProduct, cateProduct, prixProduct, versionProduct, ramProduct, stockageProduct, autoProduct, stockProduct);
        return mProductSelected;

    }

    public void effacer() {
        nomProduct.setText("");
        // cateProduct.setText("");
        prixProduct.setText(String.valueOf(0));
        versionProduct.setText(String.valueOf(0));
        ramProduct.setText(String.valueOf(0));
        stockageProduct.setText(String.valueOf(0));
        autoProduct.setText(String.valueOf(0));
        stockProduct.setText(String.valueOf(0));
    }

}
