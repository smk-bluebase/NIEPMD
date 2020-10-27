package bluebase.in.niepmd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

public class DoctorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    int id;
    int patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        Toolbar toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        drawer = findViewById(R.id.doctor_drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        ImageView backButton = headerView.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        if(savedInstanceState == null) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("myProfileDoctor")
                    .replace(R.id.fragment_container, new MyProfileDoctorFragment(), "myProfileDoctor")
                    .commit();
            navigationView.setCheckedItem(R.id.nav_my_profile);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.nav_my_profile:
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("myProfileDoctor")
                        .replace(R.id.fragment_container, new MyProfileDoctorFragment(), "myProfileDoctor")
                        .commit();
                break;

            case R.id.nav_appointment_history:
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("appointmentHistoryDoctor")
                        .replace(R.id.fragment_container, new AppointmentHistoryDoctorFragment(), "appointmentHistoryDoctor")
                        .commit();
                break;

            case R.id.nav_patient:
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("patient")
                        .replace(R.id.fragment_container, new PatientFragment(), "patient")
                        .commit();
                break;

            case R.id.nav_search:
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("search")
                        .replace(R.id.fragment_container, new SearchFragment(), "search")
                        .commit();
                break;

            case R.id.nav_physiotherapy_form:
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("physiotherapyForm")
                        .replace(R.id.fragment_container, new PhysiotherapyFormFragment(), "physiotherapyForm")
                        .commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public int getId(){
        return id;
    }

    public void refreshAppointmentHistory(){
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("appointmentHistoryDoctor")
                .replace(R.id.fragment_container, new AppointmentHistoryDoctorFragment(), "appointmentHistoryDoctor")
                .commit();
    }

    public void setPatientId(int patientId){
        this.patientId = patientId;
    }

    public int getPatientId(){
        return patientId;
    }

    public void refreshPhysiotherapyForm(){
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("physiotherapyForm")
                .replace(R.id.fragment_container, new PhysiotherapyFormFragment(), "physiotherapyForm")
                .commit();
    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            int fragmentsInStack = getSupportFragmentManager().getBackStackEntryCount();

            if (fragmentsInStack > 1) {
                getSupportFragmentManager().popBackStack();
            }else {
                Intent intent = new Intent(DoctorActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

}