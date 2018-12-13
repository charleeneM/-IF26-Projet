package fr.utt.if26.pills;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MedicineUpdateActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_medicine_nom;
    private EditText et_medicine_type;
    private EditText et_medicine_stock;
    private EditText et_medicine_fabricant;
    private Button button_valider;

    Medicament med;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_add);

        med = (Medicament) getIntent().getSerializableExtra("med");

        et_medicine_nom = (EditText) findViewById(R.id.medicine_add_et_nom);
        et_medicine_type = (EditText) findViewById(R.id.medicine_add_et_type);
        et_medicine_stock = (EditText) findViewById(R.id.medicine_add_et_stock);
        et_medicine_fabricant = (EditText) findViewById(R.id.medicine_add_et_fabricant);
        button_valider = (Button) findViewById(R.id.medicine_add_button_valider);

        et_medicine_nom.setText(med.getNom());
        et_medicine_fabricant.setText(med.getFabricant());
        et_medicine_type.setText(med.getType());
        String stockMed = (String) String.valueOf(med.getStock());
        et_medicine_stock.setText(stockMed);

        button_valider.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String nomMed = (String) et_medicine_nom.getText().toString();
        //a voir suivant le type de type
        String typeMed = (String) et_medicine_type.getText().toString();
        Double stockMed = (Double) Double.parseDouble(String.valueOf(et_medicine_stock.getText()));
        String fabricantMed = (String) et_medicine_fabricant.getText().toString();

        med.setNom(nomMed);
        med.setFabricant(fabricantMed);
        med.setType(typeMed);
        med.setStock(stockMed);

        MedicamentPersistance persistance = new MedicamentPersistance(this, "pills.db", null, 1);
        persistance.updateMedicament(med);

        Intent medicineUpdateActivityIntent = new Intent(MedicineUpdateActivity.this, MedicineListActivity.class);
        startActivity(medicineUpdateActivityIntent);

        Toast.makeText(this, "Le médicament a bien été modifié", Toast.LENGTH_LONG).show();
    }
}
