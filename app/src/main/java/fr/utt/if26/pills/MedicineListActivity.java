package fr.utt.if26.pills;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MedicineListActivity extends AppCompatActivity implements View.OnClickListener {
    Button bouton_ajout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);

        bouton_ajout = (Button) findViewById(R.id.medicine_list_button_ajouter);
        bouton_ajout.setOnClickListener(this);

        final ListView listeMedicaments = (ListView) findViewById(R.id.medicine_list_view);

        MedicamentPersistance persistance = new MedicamentPersistance(this, "pills.db", null, 1);
        persistance.initData();

        ArrayList<Medicament> medicaments = persistance.getAllMedicaments();

        AdaptateurMedicament adapteur = new AdaptateurMedicament(this, R.layout.medicine, medicaments);
        listeMedicaments.setAdapter(adapteur);

        listeMedicaments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("test","Position=" + position);

                Medicament med = (Medicament) listeMedicaments.getItemAtPosition(position);
                String nom = med.getNom();
                String fabricant = med.getFabricant();
                String type = med.getType();
                Double stock = med.getStock();

                Intent medicineShowActivityIntent = new Intent(MedicineListActivity.this, MedicineShowActivity.class);
                medicineShowActivityIntent.putExtra("nomMed", nom);
                medicineShowActivityIntent.putExtra("fabricantMed", fabricant);
                medicineShowActivityIntent.putExtra("typeMed", type);
                medicineShowActivityIntent.putExtra("stockMed", stock);
                startActivity(medicineShowActivityIntent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent MedicineAddActivityIntent = new Intent(MedicineListActivity.this, MedicineAddActivity.class);
        startActivity(MedicineAddActivityIntent);
    }
}
