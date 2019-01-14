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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RappelCheckActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    //Menu
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    ///

    private TextView tv_title;
    private TextView tv_rappel_heure;
    private Button button_check;

    Rappel rappel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rappel_check);

        // Pour le menu
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        ///

        rappel = (Rappel) getIntent().getSerializableExtra("rappel");

        tv_title = (TextView) findViewById(R.id.rappel_check_tv_title);
        tv_rappel_heure = (TextView) findViewById(R.id.rappel_check_tv_heure_value);

        button_check = (Button) findViewById(R.id.rappel_check_button);
        button_check.setOnClickListener(this);

        MedicamentPersistance persistance = new MedicamentPersistance(this, "pills.db", null, 1);
        persistance.initData();

        Medicament med = persistance.getMedicament(rappel.getId_med());

        tv_title.setText("Rappel " + med.getNom());
        tv_rappel_heure.setText(rappel.getHeure());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rappel_check_button:
                this.checkRappel();
                break;
        }
    }


    private void checkRappel(){
        MedicamentPersistance persistance = new MedicamentPersistance(this, "pills.db", null, 1);
        rappel.setStatut(1);
        //changer la valeur de prochain rappel en ajoutant le répétition

        Calendar prochainRappelCalendar = Calendar.getInstance();
        SimpleDateFormat d = new SimpleDateFormat ("dd/MM/yyyy" );

        prochainRappelCalendar.add(Calendar.DATE, rappel.getRepetition());
        String prochainRappel = d.format(prochainRappelCalendar.getTime());
        rappel.setProchain_rappel(prochainRappel);

        persistance.updateRappel(rappel);

        Intent checkRappelShowActivity = new Intent(RappelCheckActivity.this, MainActivity.class);
        startActivity(checkRappelShowActivity);

        Toast.makeText(this, "Le rappel a bien été pris", Toast.LENGTH_LONG).show();
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
        Intent medicamentsActivityIntent = new Intent(RappelCheckActivity.this, MedicineListActivity.class);
        startActivity(medicamentsActivityIntent);
    }

    private void openAujourdhui(){
        Intent aujourdhuiActivityIntent = new Intent(RappelCheckActivity.this, MainActivity.class);
        startActivity(aujourdhuiActivityIntent);
    }

    private void openDonneesPersonnelles(){
        Intent donneesPersoActivityIntent = new Intent(RappelCheckActivity.this, PersonalDataActivity.class);
        startActivity(donneesPersoActivityIntent);
    }

    private void openMesRappels(){
        Intent mesRappelsActivityIntent = new Intent(RappelCheckActivity.this, RappelListActivty.class);
        startActivity(mesRappelsActivityIntent);
    }
}
