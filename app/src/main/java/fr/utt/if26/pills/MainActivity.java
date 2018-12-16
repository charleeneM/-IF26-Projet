package fr.utt.if26.pills;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bouton à enlever une fois que le menu sera créé
        button = (Button) findViewById(R.id.button_medicaments);
        button.setOnClickListener(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        Intent medicamentsActivityIntent = new Intent(MainActivity.this, MedicineListActivity.class);
        startActivity(medicamentsActivityIntent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        System.out.println("On create options Menu");
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_aujourdhui :
                System.out.println("Switch aujourd'hui");
                openMenuAujourdhui();
                return true;
            case R.id.menu_medicament :
                System.out.println("Switch mes medicaments");
                openMenuMesMedicaments();
                return true;
            case R.id.menu_rappels :
                openMenuMesRappels();
                return true;
            default :

        }
        if (toggle.onOptionsItemSelected(item)) {
           return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openMenuMesRappels() {
    }

    private void openMenuMesMedicaments() {
        Intent medicamentsActivityIntent = new Intent(MainActivity.this, MedicineListActivity.class);
        startActivity(medicamentsActivityIntent);
    }

    private void openMenuAujourdhui() {
        Intent aujourdhuiActivityIntent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(aujourdhuiActivityIntent);
    }



}
