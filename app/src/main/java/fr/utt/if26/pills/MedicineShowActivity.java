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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MedicineShowActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    //Menu
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    ///


    private TextView tv_medicine_nom;
    private TextView tv_medicine_fabricant;
    private TextView tv_medicine_type;
    private TextView tv_medicine_stock;
    private Button button_rappel;
    private Button button_supprimer;
    private Button button_modifier;

    Medicament med;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_show);

        // Pour le menu
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        ///

        med = (Medicament) getIntent().getSerializableExtra("med");

        tv_medicine_nom = (TextView) findViewById(R.id.medicine_show_tv_nom);
        tv_medicine_fabricant = (TextView) findViewById(R.id.medicine_show_tv_fabricant);
        tv_medicine_type = (TextView) findViewById(R.id.medicine_show_tv_type_value);
        tv_medicine_stock = (TextView) findViewById(R.id.medicine_show_tv_stock_value);

        button_rappel = (Button) findViewById(R.id.medicine_show_button_rappel);
        button_supprimer = (Button) findViewById(R.id.medicine_show_button_supprimer);
        button_modifier = (Button) findViewById(R.id.medicine_show_button_modifier);

        button_rappel.setOnClickListener(this);
        button_supprimer.setOnClickListener(this);
        button_modifier.setOnClickListener(this);

        tv_medicine_nom.setText(med.getNom());
        tv_medicine_fabricant.setText(med.getFabricant());
        tv_medicine_type.setText(med.getType());
        String stockMed = (String) String.valueOf(med.getStock());
        tv_medicine_stock.setText(stockMed);

        final ListView listeRappels = (ListView) findViewById(R.id.medecine_show_liste_view_rappels);

        MedicamentPersistance persistance = new MedicamentPersistance(this, "pills.db", null, 1);
        persistance.initData();

        ArrayList<Rappel> dataRappels = persistance.getRappelsFromMedicament(med.getId());

        AdaptateurRappelMedicament adaptateurRappel = new AdaptateurRappelMedicament(this, R.layout.rappel_medicament, dataRappels);
        listeRappels.setAdapter(adaptateurRappel);

        listeRappels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("test","Position=" + position);

                Rappel rappel = (Rappel) listeRappels.getItemAtPosition(position);

                Intent rappelShowActivityIntent = new Intent(MedicineShowActivity.this, RappelShowActivity.class);

                rappelShowActivityIntent.putExtra("rappel", rappel);
                startActivity(rappelShowActivityIntent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.medicine_show_button_rappel:
                this.createRappel();
                break;

            case R.id.medicine_show_button_supprimer:
                this.deleteMedicineShow();
                break;

            case R.id.medicine_show_button_modifier:
                this.updateMedicineShow();
                break;
        }

    }

    private void createRappel(){
        Intent createRappelActivity = new Intent(MedicineShowActivity.this, RappelAddActivity.class);
        createRappelActivity.putExtra("idMed", med.getId());
        startActivity(createRappelActivity);
    }

    private void deleteMedicineShow(){
        MedicamentPersistance persistance = new MedicamentPersistance(this, "pills.db", null, 1);
        persistance.deleteMedicament(med);

        Intent deleteMedicineShowActivity = new Intent(MedicineShowActivity.this, MedicineListActivity.class);
        startActivity(deleteMedicineShowActivity);

        Toast.makeText(this, "Le médicament a bien été supprimé", Toast.LENGTH_LONG).show();
    }

    private void updateMedicineShow(){
        Intent updateMedicineShowActivity = new Intent(MedicineShowActivity.this, MedicineUpdateActivity.class);
        updateMedicineShowActivity.putExtra("med", med);
        startActivity(updateMedicineShowActivity);
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
        Intent medicamentsActivityIntent = new Intent(MedicineShowActivity.this, MedicineListActivity.class);
        startActivity(medicamentsActivityIntent);
    }

    private void openAujourdhui(){
        Intent aujourdhuiActivityIntent = new Intent(MedicineShowActivity.this, MainActivity.class);
        startActivity(aujourdhuiActivityIntent);
    }

    private void openDonneesPersonnelles(){
        Intent donneesPersoActivityIntent = new Intent(MedicineShowActivity.this, PersonalDataActivity.class);
        startActivity(donneesPersoActivityIntent);
    }

    private void openMesRappels(){
        Intent mesRappelsActivityIntent = new Intent(MedicineShowActivity.this, RappelListActivty.class);
        startActivity(mesRappelsActivityIntent);
    }
}
