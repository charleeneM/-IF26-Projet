package fr.utt.if26.pills;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MedicineShowActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_medicine_nom;
    private TextView tv_medicine_fabricant;
    private TextView tv_medicine_type;
    private TextView tv_medicine_stock;
    private Button button_rappel;
    private Button button_supprimer;
    private Button button_modifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_show);

        tv_medicine_nom = (TextView) findViewById(R.id.medicine_show_tv_nom);
        tv_medicine_fabricant = (TextView) findViewById(R.id.medicine_show_tv_fabricant);
        tv_medicine_type = (TextView) findViewById(R.id.medicine_show_tv_type);
        tv_medicine_stock = (TextView) findViewById(R.id.medicine_show_tv_stock);

        button_rappel = (Button) findViewById(R.id.medicine_show_button_rappel);
        button_supprimer = (Button) findViewById(R.id.medicine_show_button_supprimer);
        button_modifier = (Button) findViewById(R.id.medicine_show_button_modifier);

        button_rappel.setOnClickListener(this);
        button_supprimer.setOnClickListener(this);
        button_modifier.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.medicine_show_button_rappel:
                break;

            case R.id.medicine_show_button_supprimer:
                break;

            case R.id.medicine_show_button_modifier:
                break;
        }

    }
}
