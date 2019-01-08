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
import android.widget.Toast;

public class PersonalDataActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    //Menu
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    ///

    private Button button_consulter_data;
    private Button button_delete_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);

        // Pour le menu
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        ///

        button_consulter_data = (Button) findViewById(R.id.data_button_consulter);
        button_delete_data = (Button) findViewById(R.id.data_button_delete);

        button_consulter_data.setOnClickListener(this);
        button_delete_data.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.data_button_consulter:
                this.consulterAllData();
                break;

            case R.id.data_button_delete:
                this.deleteAllData();
                break;
            default:
                break;
        }

    }

    private void consulterAllData(){
        Intent consulterDataActivityIntent = new Intent(PersonalDataActivity.this, PersonalDataListActivity.class);
        startActivity(consulterDataActivityIntent);
    }

    private void deleteAllData(){
        MedicamentPersistance persistance = new MedicamentPersistance(this, "pills.db", null, 1);
        persistance.deleteAllMedicaments();

        Toast.makeText(this, "Toutes les données ont été supprimées", Toast.LENGTH_LONG).show();
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
        Intent medicamentsActivityIntent = new Intent(PersonalDataActivity.this, MedicineListActivity.class);
        startActivity(medicamentsActivityIntent);
    }

    private void openAujourdhui(){
        Intent aujourdhuiActivityIntent = new Intent(PersonalDataActivity.this, MainActivity.class);
        startActivity(aujourdhuiActivityIntent);
    }

    private void openDonneesPersonnelles(){
        Intent donneesPersoActivityIntent = new Intent(PersonalDataActivity.this, PersonalDataActivity.class);
        startActivity(donneesPersoActivityIntent);
    }
}
