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
import android.widget.ListView;

import java.util.ArrayList;

public class PersonalDataListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //Menu
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    ///

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data_list);

        // Pour le menu
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        ///


        final ListView listeDataMedicaments = (ListView) findViewById(R.id.data_consulter_list_view);
        final ListView listeDataRappels = (ListView) findViewById(R.id.data_consulter_list_rappel_view);

        MedicamentPersistance persistance = new MedicamentPersistance(this, "pills.db", null, 1);
        persistance.initData();

        ArrayList<Medicament> dataMedicaments = persistance.getAllMedicaments();
        ArrayList<Rappel> dataRappels = persistance.getAllRappels();

        AdaptateurDataMedicament adapteur = new AdaptateurDataMedicament(this, R.layout.data_medicine, dataMedicaments);
        listeDataMedicaments.setAdapter(adapteur);
        AdaptateurDataRappel adaptateurRappel = new AdaptateurDataRappel(this, R.layout.data_rappel, dataRappels);
        listeDataRappels.setAdapter(adaptateurRappel);
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
        Intent medicamentsActivityIntent = new Intent(PersonalDataListActivity.this, MedicineListActivity.class);
        startActivity(medicamentsActivityIntent);
    }

    private void openAujourdhui(){
        Intent aujourdhuiActivityIntent = new Intent(PersonalDataListActivity.this, MainActivity.class);
        startActivity(aujourdhuiActivityIntent);
    }

    private void openDonneesPersonnelles(){
        Intent donneesPersoActivityIntent = new Intent(PersonalDataListActivity.this, PersonalDataActivity.class);
        startActivity(donneesPersoActivityIntent);
    }

    private void openMesRappels(){
        Intent mesRappelsActivityIntent = new Intent(PersonalDataListActivity.this, RappelListActivty.class);
        startActivity(mesRappelsActivityIntent);
    }

}
