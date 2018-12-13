package fr.utt.if26.pills;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MedicamentPersistance extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "pills.db";
    private static final String TABLE_MEDICAMENT = "medicament";
    private static final String ATTRIBUT_ID_MEDICAMENT = "id_medicament";
    private static final String ATTRIBUT_NOM = "nom";
    private static final String ATTRIBUT_FABRICANT = "fabricant";
    private static final String ATTRIBUT_TYPE = "type";
    private static final String ATTRIBUT_STOCK = "stock";

    final String table_medicament_create =
            "CREATE TABLE IF NOT EXISTS " + TABLE_MEDICAMENT + "(" +
                    ATTRIBUT_ID_MEDICAMENT + " INTEGER primary key AUTOINCREMENT," +
                    ATTRIBUT_NOM + " TEXT not null, " +
                    ATTRIBUT_FABRICANT + " TEXT, " +
                    ATTRIBUT_TYPE + " TEXT, " +
                    ATTRIBUT_STOCK + " REAL" +
                    ")";

    final String table_medicament_delete =
            "DROP TABLE IF EXISTS " + TABLE_MEDICAMENT;

    public MedicamentPersistance(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(table_medicament_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(table_medicament_delete);
        onCreate(db);
    }

    public void addMedicament(Medicament m) {
        ContentValues values = new ContentValues();

        SQLiteDatabase database = this.getWritableDatabase();

        values.put(ATTRIBUT_ID_MEDICAMENT, m.getId()); //vérifier pour l'auto incrementation
        values.put(ATTRIBUT_NOM, m.getNom());
        values.put(ATTRIBUT_FABRICANT, m.getFabricant());
        values.put(ATTRIBUT_TYPE, m.getType());
        values.put(ATTRIBUT_STOCK, m.getStock());

        //System.out.println(values);
        database.insert(TABLE_MEDICAMENT, null, values);
        database.close();
    }


    public void initData() {
        if (this.getAllMedicaments().isEmpty()) {
            this.onUpgrade(this.getWritableDatabase(), 1, 2);

            //pas d'initialisation dans notre cas
            /*
            ArrayList<Module> modules = new Cursus().getModules();
            for (Module module : modules) {
                addModule(module);
            }*/

            //Tests
            Medicament med1 = new Medicament(null,"Med1", "test", "Pillule", 25.0);
            Medicament med2 = new Medicament(null,"Med2", "fab", "Sirop", 33.5);
            Medicament med3 = new Medicament(null,"Med3", "boiron", "granules", 100.0);

            addMedicament(med1);
            addMedicament(med2);
            addMedicament(med3);
        }
    }

    public void deleteMedicament(Medicament m) {
        String query = "DELETE FROM " + TABLE_MEDICAMENT + " WHERE " + ATTRIBUT_ID_MEDICAMENT + " = ?";

        System.out.println(query);

        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(query, new Integer[]{m.getId()});

        database.close();
    }

    public void updateMedicament(Medicament m) {
        ContentValues values = new ContentValues();
        System.out.println("Medicament à modifier : " + m);

        SQLiteDatabase database = this.getWritableDatabase();

        values.put(ATTRIBUT_NOM, m.getNom());
        values.put(ATTRIBUT_FABRICANT, m.getFabricant());
        values.put(ATTRIBUT_TYPE, m.getType());
        values.put(ATTRIBUT_STOCK, m.getStock());

        String selection = ATTRIBUT_ID_MEDICAMENT + " LIKE ?";
        String id_med = (String) String.valueOf(m.getId());
        String[] selectionArgs = {id_med};

        database.update(TABLE_MEDICAMENT, values, selection, selectionArgs);

    }

    public Medicament getMedicament(Integer key) {
        Medicament medicament = new Medicament();

        String query = "SELECT * FROM " + TABLE_MEDICAMENT + " WHERE " + ATTRIBUT_ID_MEDICAMENT + " = ?";

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, new String[]{(String.valueOf(key))});

        while (cursor.moveToNext()) {
            medicament.setId(cursor.getInt(0));
            medicament.setNom(cursor.getString(1));
            medicament.setFabricant(cursor.getString(2));
            medicament.setType(cursor.getString(3));
            medicament.setStock(cursor.getDouble(4));
        }

        database.close();
        cursor.close();

        return medicament;
    }

    public ArrayList<Medicament> getAllMedicaments() {
        ArrayList<Medicament> listeMedicaments = new ArrayList();

        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MEDICAMENT;
        Cursor cursor = database.rawQuery(query, null);

        while(cursor.moveToNext()){
            Medicament medicament = new Medicament();
            medicament.setId(cursor.getInt(0));
            medicament.setNom(cursor.getString(1));
            medicament.setFabricant(cursor.getString(2));
            medicament.setType(cursor.getString(3));
            medicament.setStock(cursor.getDouble(4));

            listeMedicaments.add(medicament);
        }
        database.close();
        return listeMedicaments;
    }

}

