package fr.utt.if26.pills;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MedicineAddActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_medicine_nom;
    private EditText et_medicine_type;
    private EditText et_medicine_stock;
    private EditText et_medicine_fabricant;
    private Button button_valider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_add);

        //to do : voir si les editText ont des valeurs (en cas de modification) tester si Intent a des paramètres

        et_medicine_nom = (EditText) findViewById(R.id.medicine_add_et_nom);
        et_medicine_type = (EditText) findViewById(R.id.medicine_add_et_type);
        et_medicine_stock = (EditText) findViewById(R.id.medicine_add_et_stock);
        et_medicine_fabricant = (EditText) findViewById(R.id.medicine_add_et_fabricant);
        button_valider = (Button) findViewById(R.id.medicine_add_button_valider);

        button_valider.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String nomMed = (String) et_medicine_nom.getText().toString();
        //a voir suivant le type de type
        String typeMed = (String) et_medicine_type.getText().toString();
        //caster en float
        String stockMed = (String) et_medicine_stock.getText().toString();
        String fabricantMed = (String) et_medicine_fabricant.getText().toString();

        //To do : persistance du médicament dans la table

        Intent medicineAddActivityIntent = new Intent(MedicineAddActivity.this, MainActivity.class);
        startActivity(medicineAddActivityIntent);

        Toast.makeText(this, "Le médicament a bien été ajouté", Toast.LENGTH_LONG).show();
    }
}
