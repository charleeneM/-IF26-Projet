package fr.utt.if26.pills;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RappelAddActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    //Menu
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    ///

    private EditText et_rappel_heure;
    private EditText et_rappel_repetition;
    private Button button_rappel_valider;

    Integer idMed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rappel_add);

        // Pour le menu
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        ///

        idMed = getIntent().getIntExtra("idMed", 0);

        et_rappel_heure = (EditText) findViewById(R.id.rappel_add_et_heure);
        et_rappel_repetition = (EditText) findViewById(R.id.rappel_add_et_repetition);

        button_rappel_valider = (Button) findViewById(R.id.rappel_add_button);
        button_rappel_valider.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Rappel rappel = new Rappel();

        String heureRappel = (String) et_rappel_heure.getText().toString();
        String repetitionRappelIntermediare = et_rappel_repetition.getText().toString();


        Integer repetitionRappel = 1;
        if (!repetitionRappelIntermediare.isEmpty()) {
            repetitionRappel = (Integer) Integer.parseInt(repetitionRappelIntermediare);
        }

        if (!heureRappel.isEmpty()){
            rappel.setHeure(heureRappel);
            rappel.setRepetition(repetitionRappel);
            rappel.setId_med(idMed);

            MedicamentPersistance persistance = new MedicamentPersistance(this, "pills.db", null, 1);
            persistance.addRappel(rappel);

            Intent rappelAddActivityIntent = new Intent(RappelAddActivity.this, MedicineListActivity.class);
            startActivity(rappelAddActivityIntent);

            Toast.makeText(this, "Le rappel a bien été ajouté", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Veuillez renseigner les champs obligatoires", Toast.LENGTH_LONG).show();
        }

    }


    // ---------------------
    // CONFIGURATION - MENU
    // ---------------------

    // 1 - Configure Toolbar
    private void configureToolBar(){
        this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }

    // 2 - Configure Drawer Layout
    private void configureDrawerLayout(){
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 3 - Configure NavigationView
    private void configureNavigationView(){
        this.navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // 4 - Handle Navigation Item Click
        int id = item.getItemId();

        switch (id){
            case R.id.nav_aujourdhui :
                this.openAujourdhui();
                break;
            case R.id.nav_medicaments:
                this.openMesMedicaments();
                break;
            case R.id.nav_rappels:
                this.openMesRappels();
                break;
            case R.id.nav_donnees_perso:
                this.openDonneesPersonnelles();
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void openMesMedicaments(){
        Intent medicamentsActivityIntent = new Intent(RappelAddActivity.this, MedicineListActivity.class);
        startActivity(medicamentsActivityIntent);
    }

    private void openAujourdhui(){
        Intent aujourdhuiActivityIntent = new Intent(RappelAddActivity.this, MainActivity.class);
        startActivity(aujourdhuiActivityIntent);
    }

    private void openDonneesPersonnelles(){
        Intent donneesPersoActivityIntent = new Intent(RappelAddActivity.this, PersonalDataActivity.class);
        startActivity(donneesPersoActivityIntent);
    }

    private void openMesRappels(){
        Intent mesRappelsActivityIntent = new Intent(RappelAddActivity.this, RappelListActivty.class);
        startActivity(mesRappelsActivityIntent);
    }
}

