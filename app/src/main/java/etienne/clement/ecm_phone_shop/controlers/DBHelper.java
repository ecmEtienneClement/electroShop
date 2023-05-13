package etienne.clement.ecm_phone_shop.controlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.widget.Toast;


import etienne.clement.ecm_phone_shop.models.HistoriqueModel;
import etienne.clement.ecm_phone_shop.models.Product;

/**
 * created by ETIENNE CLEMENT MBAYE on 22-03-2022.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "bdProduct.sqlite", null, 6


        );

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table Product(id INTEGER primary key AUTOINCREMENT , name TEXT  ,marque TEXT,prix INTEGER,version INTEGER,ram INTEGER,stockage INTEGER,autonomie INTEGER,stock INTEGER) ");
        db.execSQL("create Table Historique(id INTEGER primary key AUTOINCREMENT,dateAchat TEXT,nomClient TEXT,prenomClient TEXT,addressClient TEXT,numeroClient INTERGER, name TEXT  ,marque TEXT,prix INTEGER,version INTEGER,ram INTEGER,stockage INTEGER,autonomie INTEGER) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists Product");
        db.execSQL("drop Table if exists Historique");
        onCreate(db);
    }

    /**
     * @return cursor
     */
    public Cursor getAllProduct() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from Product ", null);


    }

    /**
     * @param mproduct produit
     * @return cursor
     */
    public Cursor getSelectedProduct(Product mproduct) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from Product where id=?  ", new String[]{mproduct.getId().toString()});

    }

    /**
     * @return cursor
     */
    public Cursor getLastProduct() {
        return getAllProduct();
    }

    /**
     *
     * @param categorie cate
     * @return cur
     */
    public Cursor getProductByCate(String categorie) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from Product where marque=?  ", new String[]{categorie});

    }

    /**
     * @param mProduct produit
     * @param mContext context
     * @return boolean
     */
    public boolean savedProduct(Product mProduct, Context mContext) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", mProduct.getNom());
        cv.put("marque", mProduct.getMarque());
        cv.put("prix", mProduct.getPrix());
        cv.put("version", mProduct.getVersion());
        cv.put("ram", mProduct.getRam());
        cv.put("stockage", mProduct.getStockage());
        cv.put("autonomie", mProduct.getAutonomie());
        cv.put("stock", mProduct.getStock());
        long rs = db.insert("Product", null, cv);
        if (rs == -1) {
            Toast.makeText(mContext, "Erreur d'enregistrement de votre : " + mProduct.getMarque(), Toast.LENGTH_LONG).show();
            db.close();
            return false;
        } else {
            db.close();
            Toast.makeText(mContext, "Votre : " + mProduct.getMarque() + " : " + mProduct.getNom() + " a été bien enregistrez", Toast.LENGTH_LONG).show();
            return true;

        }

    }

    /**
     * @param mProduct product
     * @param mContext context
     * @return boollean
     */
    public boolean updateProduct(Product mProduct, Context mContext) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", mProduct.getId());
        cv.put("name", mProduct.getNom());
        cv.put("marque", mProduct.getMarque());
        cv.put("prix", mProduct.getPrix());
        cv.put("version", mProduct.getVersion());
        cv.put("ram", mProduct.getRam());
        cv.put("stockage", mProduct.getStockage());
        cv.put("autonomie", mProduct.getAutonomie());
        cv.put("stock", mProduct.getStock());
        Cursor mCursor = db.rawQuery("select * from Product where id = ?", new String[]{mProduct.getId().toString()});
        if (mCursor.getCount() > 0) {
            long rs = db.update("Product", cv, "id=?", new String[]{mProduct.getId().toString()});
            if (rs == -1) {
                Toast.makeText(mContext, "Erreur de modification de votre : " + mProduct.getMarque(), Toast.LENGTH_LONG).show();
                mCursor.close();
                return false;
            } else {
                Toast.makeText(mContext, "La modification de votre : " + mProduct.getMarque() + " : " + mProduct.getNom() + " a été bien enregistrez", Toast.LENGTH_LONG).show();
                mCursor.close();
                return true;

            }
        } else {
            Toast.makeText(mContext, "Erreur inattendu ce nom du produit : " + mProduct.getMarque() + " n'existe pas", Toast.LENGTH_LONG).show();
            mCursor.close();
            return false;
        }

    }

    /**
     * @param mProduct produit
     * @param mContext context
     * @return boolean
     */
    public boolean deleteProduct(Product mProduct, Context mContext) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor mCursor = db.rawQuery("select * from Product where name = ?", new String[]{mProduct.getNom()});
        if (mCursor.getCount() > 0) {
            long rs = db.delete("Product", "id=?", new String[]{mProduct.getId().toString()});
            if (rs == -1) {
                Toast.makeText(mContext, "Erreur de suppression de votre : " + mProduct.getMarque(), Toast.LENGTH_LONG).show();
                mCursor.close();
                return false;
            } else {
                Toast.makeText(mContext, "La suppression de votre : " + mProduct.getMarque() + " : " + mProduct.getNom() + " a été bien effectuer", Toast.LENGTH_LONG).show();
                mCursor.close();
                return true;
            }
        } else {
            Toast.makeText(mContext, "Erreur inattendu ce nom du produit : " + mProduct.getMarque() + " n'existe pas", Toast.LENGTH_LONG).show();
            mCursor.close();
            return false;
        }

    }

    public void deleteAllProduct(Context mContext) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("drop Table if exists Product");
        db.execSQL("create Table Product(id INTEGER primary key AUTOINCREMENT , name TEXT  ,marque TEXT,prix INTEGER,version INTEGER,ram INTEGER,stockage INTEGER,autonomie INTEGER,stock INTEGER) ");

        Toast.makeText(mContext, "Tout les produits ont été bien supprimés ", Toast.LENGTH_LONG).show();


    }

    /**
     * @return cursor
     */
    public Cursor getAllHistorique() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from Historique", null);
    }

    /***
     *
     * @param mhistoriqueModel historique model
     * @param mContext context
     * @return boolean
     */
    public boolean saveHistorique(HistoriqueModel mhistoriqueModel, Context mContext) {

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("dateAchat", mhistoriqueModel.getDate());
        cv.put("nomClient", mhistoriqueModel.getNomClient());
        cv.put("prenomClient", mhistoriqueModel.getPrenomClient());
        cv.put("addressClient", mhistoriqueModel.getAddressClient());
        cv.put("numeroClient", mhistoriqueModel.getNumeroClient());
        cv.put("name", mhistoriqueModel.getNom());
        cv.put("marque", mhistoriqueModel.getMarque());
        cv.put("prix", mhistoriqueModel.getPrix());
        cv.put("version", mhistoriqueModel.getVersion());
        cv.put("ram", mhistoriqueModel.getRam());
        cv.put("stockage", mhistoriqueModel.getStockage());
        cv.put("autonomie", mhistoriqueModel.getAutonomie());

        long rs = db.insert("Historique", null, cv);
        if (rs == -1) {
            Toast.makeText(mContext, "Erreur d'enregistrement de votre achat: ", Toast.LENGTH_LONG).show();
            return false;
        } else {
            Toast.makeText(mContext, "Votre achat a été bien effectuer: ", Toast.LENGTH_LONG).show();
            return true;
        }

    }

    /**
     * @param mContext context
     */
    public boolean deleteAllHistorique(Context mContext) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("drop Table if exists Historique");
        db.execSQL("create Table Historique(id INTEGER primary key AUTOINCREMENT,dateAchat TEXT,nomClient TEXT,prenomClient TEXT,addressClient TEXT,numeroClient INTERGER, name TEXT  ,marque TEXT,prix INTEGER,version INTEGER,ram INTEGER,stockage INTEGER,autonomie INTEGER) ");

        Toast.makeText(mContext, "Historique d'achat supprimer ", Toast.LENGTH_LONG).show();

        return true;
    }
}
