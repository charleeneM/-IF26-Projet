package fr.utt.if26.pills;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView maListe = (ListView) findViewById(R.id.module_liste_lv);
        MedicamentPersistance persistance = new MedicamentPersistance(this, "pills.db", null, 1);
        persistance.initData();

        Medicament med4 = new Medicament(null, "Med4", "blabla", "comprimé", null);
        persistance.addMedicament(med4);
        ArrayList<Medicament> medicaments = persistance.getAllMedicaments();

        Adaptateur adapteur = new Adaptateur(this, R.layout.medicament, medicaments);
        maListe.setAdapter(adapteur);
    }
}
