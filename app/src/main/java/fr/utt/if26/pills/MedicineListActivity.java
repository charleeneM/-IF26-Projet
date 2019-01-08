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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MedicineListActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    //Menu
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    ///


    Button bouton_ajout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);

        // Pour le menu
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        ///

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

                Intent medicineShowActivityIntent = new Intent(MedicineListActivity.this, MedicineShowActivity.class);

                medicineShowActivityIntent.putExtra("med", med);
                startActivity(medicineShowActivityIntent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent MedicineAddActivityIntent = new Intent(MedicineListActivity.this, MedicineAddActivity.class);
        startActivity(MedicineAddActivityIntent);
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
        Intent medicamentsActivityIntent = new Intent(MedicineListActivity.this, MedicineListActivity.class);
        startActivity(medicamentsActivityIntent);
    }

    private void openAujourdhui(){
        Intent aujourdhuiActivityIntent = new Intent(MedicineListActivity.this, MainActivity.class);
        startActivity(aujourdhuiActivityIntent);
    }

    private void openDonneesPersonnelles(){
        Intent donneesPersoActivityIntent = new Intent(MedicineListActivity.this, PersonalDataActivity.class);
        startActivity(donneesPersoActivityIntent);
    }
}
