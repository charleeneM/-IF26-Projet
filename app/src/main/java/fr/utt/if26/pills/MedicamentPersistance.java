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

    //Table medicament
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

    final String table_medicament_delete_all =
            "DELETE FROM " + TABLE_MEDICAMENT;

    //Table rappel
    private static final String TABLE_RAPPEL= "rappel";
    private static final String ATTRIBUT_ID_RAPPEL = "id_rappel";
    private static final String ATTRIBUT_ID_MED = "id_med";
    private static final String ATTRIBUT_HEURE = "heure";
    private static final String ATTRIBUT_REPETITION = "repetition";
    private static final String ATTRIBUT_STATUT = "statut";
    private static final String ATTRIBUT_PROCHAIN_RAPPEL = "prochain_rappel";

    final String table_rappel_create =
            "CREATE TABLE IF NOT EXISTS " + TABLE_RAPPEL + "(" +
                    ATTRIBUT_ID_RAPPEL + " INTEGER primary key AUTOINCREMENT," +
                    ATTRIBUT_ID_MED + " INTEGER not null, " +
                    ATTRIBUT_HEURE + " TEXT, " +  //Heure au format HH:MM
                    ATTRIBUT_REPETITION + " INTEGER DEFAULT 1, " +
                    ATTRIBUT_STATUT + " INTEGER DEFAULT 0, " + //Booléen : 0 pour false et 1 pour true
                    ATTRIBUT_PROCHAIN_RAPPEL + " TEXT, " + //Date au format "DD/MM/YYYY"
                    " FOREIGN KEY(" + ATTRIBUT_ID_MED + ") REFERENCES " + TABLE_MEDICAMENT + "(" + ATTRIBUT_ID_MEDICAMENT + ") " +
            ")";

    final String table_rappel_delete =
            "DROP TABLE IF EXISTS " + TABLE_RAPPEL;

    final String table_rappel_delete_all =
            "DELETE FROM " + TABLE_RAPPEL;

    ///////

    public MedicamentPersistance(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(table_medicament_create);
        db.execSQL(table_rappel_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(table_rappel_delete);
        db.execSQL(table_medicament_delete);
        onCreate(db);
    }

    public void addMedicament(Medicament m) {
        ContentValues values = new ContentValues();

        SQLiteDatabase database = this.getWritableDatabase();

        values.put(ATTRIBUT_ID_MEDICAMENT, m.getId());
        values.put(ATTRIBUT_NOM, m.getNom());
        values.put(ATTRIBUT_FABRICANT, m.getFabricant());
        values.put(ATTRIBUT_TYPE, m.getType());
        values.put(ATTRIBUT_STOCK, m.getStock());

        //System.out.println(values);
        database.insert(TABLE_MEDICAMENT, null, values);
        database.close();
    }

    public void addRappel(Rappel r) {
        ContentValues values = new ContentValues();

        SQLiteDatabase database = this.getWritableDatabase();

        values.put(ATTRIBUT_ID_RAPPEL, r.getId_rappel());
        values.put(ATTRIBUT_ID_MED, r.getId_med());
        values.put(ATTRIBUT_HEURE, r.getHeure());
        values.put(ATTRIBUT_REPETITION, r.getRepetition());
        values.put(ATTRIBUT_STATUT, r.getStatut());
        values.put(ATTRIBUT_PROCHAIN_RAPPEL, r.getProchain_rappel());

        System.out.println("----------------- Values du rappel " + values);
        database.insert(TABLE_RAPPEL, null, values);
        database.close();
    }


    public void initData() {
        if (this.getAllMedicaments().isEmpty()) {
            this.onUpgrade(this.getWritableDatabase(), 1, 2);

            //Tests
            /*
            Medicament med1 = new Medicament(null,"Med1", "test", "Pillule", 25.0);
            Medicament med2 = new Medicament(null,"Med2", "fab", "Sirop", 33.5);
            Medicament med3 = new Medicament(null,"Med3", "boiron", "granules", 100.0);

            addMedicament(med1);
            addMedicament(med2);
            addMedicament(med3);
            */
        }
    }

    public void deleteAllMedicaments(){
        this.deleteAllRappels();
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(table_medicament_delete_all);

        db.close();
    }

    public void deleteAllRappels(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(table_rappel_delete_all);

        db.close();
    }

    public void deleteMedicament(Medicament m) {
        this.deleteRappelFromMedicament(m);
        String query = "DELETE FROM " + TABLE_MEDICAMENT + " WHERE " + ATTRIBUT_ID_MEDICAMENT + " = ?";

        System.out.println(query);

        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(query, new Integer[]{m.getId()});

        database.close();
    }

    public void deleteRappelFromMedicament(Medicament m) {
        String query = "DELETE FROM " + TABLE_RAPPEL + " WHERE " + ATTRIBUT_ID_MED + " = ?";

        System.out.println(query);

        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(query, new Integer[]{m.getId()});

        database.close();
    }

    public void deleteRappel(Rappel r) {
        String query = "DELETE FROM " + TABLE_RAPPEL + " WHERE " + ATTRIBUT_ID_RAPPEL + " = ?";

        System.out.println(query);

        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(query, new Integer[]{r.getId_rappel()});

        database.close();
    }

    public void updateRappel(Rappel r) {
        ContentValues values = new ContentValues();

        SQLiteDatabase database = this.getWritableDatabase();

        System.out.println("Rappel à modifier : " + r);

        values.put(ATTRIBUT_ID_RAPPEL, r.getId_rappel());
        values.put(ATTRIBUT_ID_MED, r.getId_med());
        values.put(ATTRIBUT_HEURE, r.getHeure());
        values.put(ATTRIBUT_REPETITION, r.getRepetition());
        values.put(ATTRIBUT_STATUT, r.getStatut());
        values.put(ATTRIBUT_PROCHAIN_RAPPEL, r.getProchain_rappel());

        String selection = ATTRIBUT_ID_RAPPEL + " LIKE ?";
        String id_rappel = (String) String.valueOf(r.getId_rappel());
        String[] selectionArgs = {id_rappel};

        database.update(TABLE_RAPPEL, values, selection, selectionArgs);

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

    public Rappel getRappel(Integer id_rappel) {
        Rappel rappel = new Rappel();

        String query = "SELECT * FROM " + TABLE_RAPPEL + " WHERE " + ATTRIBUT_ID_RAPPEL+ " = ?";

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, new String[]{(String.valueOf(id_rappel))});

        while (cursor.moveToNext()) {
            rappel.setId_rappel(cursor.getInt(0));
            rappel.setId_med(cursor.getInt(1));;
            rappel.setHeure(cursor.getString(2));
            rappel.setRepetition(cursor.getInt(3));
            rappel.setStatut(cursor.getInt(4));
            rappel.setProchain_rappel(cursor.getString(5));
        }

        database.close();
        cursor.close();

        return rappel;
    }

    public ArrayList<Rappel> getRappelsFromMedicament(Integer id_med) {
        ArrayList<Rappel> listeRappels = new ArrayList();

        String query = "SELECT * FROM " + TABLE_RAPPEL + " WHERE " + ATTRIBUT_ID_MED+ " = ?";

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, new String[]{(String.valueOf(id_med))});

        while (cursor.moveToNext()) {
            Rappel rappel = new Rappel();

            rappel.setId_rappel(cursor.getInt(0));
            rappel.setId_med(cursor.getInt(1));;
            rappel.setHeure(cursor.getString(2));
            rappel.setRepetition(cursor.getInt(3));
            rappel.setStatut(cursor.getInt(4));
            rappel.setProchain_rappel(cursor.getString(5));

            listeRappels.add(rappel);
        }

        database.close();
        cursor.close();

        return listeRappels;
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

    public ArrayList<Rappel> getAllRappels() {
        ArrayList<Rappel> listeRappels = new ArrayList();

        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_RAPPEL;
        Cursor cursor = database.rawQuery(query, null);

        while(cursor.moveToNext()){
            Rappel rappel = new Rappel();

            rappel.setId_rappel(cursor.getInt(0));
            rappel.setId_med(cursor.getInt(1));;
            rappel.setHeure(cursor.getString(2));
            rappel.setRepetition(cursor.getInt(3));
            rappel.setStatut(cursor.getInt(4));
            rappel.setProchain_rappel(cursor.getString(5));

            listeRappels.add(rappel);
        }
        database.close();
        return listeRappels;
    }
}

