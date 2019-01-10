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
import android.widget.TextView;
import android.widget.Toast;

public class RappelShowActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    //Menu
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    ///

    private TextView tv_title;
    private TextView tv_rappel_heure;
    private TextView tv_rappel_repetition;
    private Button button_delete;
    private Button button_update;

    Rappel rappel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rappel_show);

        // Pour le menu
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        ///

        rappel = (Rappel) getIntent().getSerializableExtra("rappel");

        tv_title = (TextView) findViewById(R.id.rappel_show_tv_title);
        tv_rappel_heure = (TextView) findViewById(R.id.rappel_show_tv_heure_value);
        tv_rappel_repetition = (TextView) findViewById(R.id.rappel_show_tv_repetition_value);

        button_delete = (Button) findViewById(R.id.rappel_show_button_supprimer);
        button_update = (Button) findViewById(R.id.rappel_show_button_modifier);
        button_update.setOnClickListener(this);
        button_delete.setOnClickListener(this);

        MedicamentPersistance persistance = new MedicamentPersistance(this, "pills.db", null, 1);
        persistance.initData();

        Medicament med = persistance.getMedicament(rappel.getId_med());

        tv_title.setText("Rappel " + med.getNom());
        tv_rappel_heure.setText(rappel.getHeure());
        tv_rappel_repetition.setText(String.valueOf(rappel.getRepetition()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rappel_show_button_supprimer:
                this.deleteRappel();
                break;

            case R.id.rappel_show_button_modifier:
                this.updateRappel();
                break;
        }
    }


    private void deleteRappel(){
        MedicamentPersistance persistance = new MedicamentPersistance(this, "pills.db", null, 1);
        persistance.deleteRappel(rappel);

        Intent deleteRappelShowActivity = new Intent(RappelShowActivity.this, MedicineListActivity.class);
        startActivity(deleteRappelShowActivity);

        Toast.makeText(this, "Le rappel a bien été supprimé", Toast.LENGTH_LONG).show();
    }

    private void updateRappel(){
        Intent updateRappelShowActivity = new Intent(RappelShowActivity.this, RappelUpdateActivity.class);
        updateRappelShowActivity.putExtra("rappel", rappel);
        startActivity(updateRappelShowActivity);
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
        Intent medicamentsActivityIntent = new Intent(RappelShowActivity.this, MedicineListActivity.class);
        startActivity(medicamentsActivityIntent);
    }

    private void openAujourdhui(){
        Intent aujourdhuiActivityIntent = new Intent(RappelShowActivity.this, MainActivity.class);
        startActivity(aujourdhuiActivityIntent);
    }

    private void openDonneesPersonnelles(){
        Intent donneesPersoActivityIntent = new Intent(RappelShowActivity.this, PersonalDataActivity.class);
        startActivity(donneesPersoActivityIntent);
    }
}
