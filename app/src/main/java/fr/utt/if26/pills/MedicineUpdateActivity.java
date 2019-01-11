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

public class MedicineUpdateActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    //Menu
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    ///

    private EditText et_medicine_nom;
    private EditText et_medicine_type;
    private EditText et_medicine_stock;
    private EditText et_medicine_fabricant;
    private Button button_valider;

    Medicament med;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_add);

        // Pour le menu
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        ///

        med = (Medicament) getIntent().getSerializableExtra("med");

        et_medicine_nom = (EditText) findViewById(R.id.medicine_add_et_nom);
        et_medicine_type = (EditText) findViewById(R.id.medicine_add_et_type);
        et_medicine_stock = (EditText) findViewById(R.id.medicine_add_et_stock);
        et_medicine_fabricant = (EditText) findViewById(R.id.medicine_add_et_fabricant);
        button_valider = (Button) findViewById(R.id.medicine_add_button_valider);

        et_medicine_nom.setText(med.getNom());
        et_medicine_fabricant.setText(med.getFabricant());
        et_medicine_type.setText(med.getType());
        String stockMed = (String) String.valueOf(med.getStock());
        et_medicine_stock.setText(stockMed);

        button_valider.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String nomMed = (String) et_medicine_nom.getText().toString();
        //a voir suivant le type de type
        String typeMed = (String) et_medicine_type.getText().toString();

        String stockMedIntermediaire = (String) et_medicine_stock.getText().toString();
        Double stockMed = 0.0;
        if(!stockMedIntermediaire.isEmpty()){
            stockMed = (Double) Double.parseDouble(String.valueOf(stockMedIntermediaire));
        }
        String fabricantMed = (String) et_medicine_fabricant.getText().toString();

        //Vérifie que le nom du médicament a été renseigné
        if(!nomMed.isEmpty()){
            med.setNom(nomMed);
            med.setFabricant(fabricantMed);
            med.setType(typeMed);
            med.setStock(stockMed);

            MedicamentPersistance persistance = new MedicamentPersistance(this, "pills.db", null, 1);
            persistance.updateMedicament(med);

            Intent medicineUpdateActivityIntent = new Intent(MedicineUpdateActivity.this, MedicineListActivity.class);
            startActivity(medicineUpdateActivityIntent);

            Toast.makeText(this, "Le médicament a bien été modifié", Toast.LENGTH_LONG).show();
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
        Intent medicamentsActivityIntent = new Intent(MedicineUpdateActivity.this, MedicineListActivity.class);
        startActivity(medicamentsActivityIntent);
    }

    private void openAujourdhui(){
        Intent aujourdhuiActivityIntent = new Intent(MedicineUpdateActivity.this, MainActivity.class);
        startActivity(aujourdhuiActivityIntent);
    }

    private void openDonneesPersonnelles(){
        Intent donneesPersoActivityIntent = new Intent(MedicineUpdateActivity.this, PersonalDataActivity.class);
        startActivity(donneesPersoActivityIntent);
    }

    private void openMesRappels(){
        Intent mesRappelsActivityIntent = new Intent(MedicineUpdateActivity.this, RappelListActivty.class);
        startActivity(mesRappelsActivityIntent);
    }
}
