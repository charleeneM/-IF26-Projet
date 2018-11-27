package fr.utt.if26.pills;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MedicamentPersistance extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "pills.db";
    private static final String TABLE_MEDICAMENT = "medicament";
    private static final String ATTRIBUT_ID_MEDICAMENT = "id_medicament";
    private static final String ATTRIBUT_NOM = "nom";
    private static final String ATTRIBUT_FABRICANT = "fabricant";
    private static final String ATTRIBUT_TYPE = "type";
    private static final String ATTRIBUT_STOCK = "stock";

    public MedicamentPersistance(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
