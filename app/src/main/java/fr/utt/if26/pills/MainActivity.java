package fr.utt.if26.pills;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bouton à enlever une fois que le menu sera créé
        button = (Button) findViewById(R.id.button_medicaments);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
            Intent medicamentsActivityIntent = new Intent(MainActivity.this, MedicineListActivity.class);
            startActivity(medicamentsActivityIntent);

    }
}
