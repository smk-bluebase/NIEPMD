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

public class PatientActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    int id;
    int viewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        Toolbar toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        drawer = findViewById(R.id.patient_drawer_layout);

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
                    .addToBackStack("myProfile")
                    .replace(R.id.fragment_container, new MyProfileFragment(), "myProfile")
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
                        .addToBackStack("myProfile")
                        .replace(R.id.fragment_container, new MyProfileFragment(), "myProfile")
                        .commit();
                break;

            case R.id.nav_book_appointment:
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("bookAppointment")
                        .replace(R.id.fragment_container, new BookAppointmentFragment(), "bookAppointment")
                        .commit();
                break;

            case R.id.nav_appointment_history:
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("appointmentHistory")
                        .replace(R.id.fragment_container, new AppointmentHistoryFragment(), "appointmentHistory")
                        .commit();
                break;

            case R.id.nav_medical_history:
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("medicalHistory")
                        .replace(R.id.fragment_container, new MedicalHistoryFragment(), "medicalHistory")
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
                .addToBackStack("appointmentHistory")
                .replace(R.id.fragment_container, new AppointmentHistoryFragment(), "appointmentHistory")
                .commit();
    }

    public void refreshBookAppointment(){
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("bookAppointment")
                .replace(R.id.fragment_container, new BookAppointmentFragment(), "bookAppointment")
                .commit();
    }

    public int getViewId(){
        return viewId;
    }

    public void setViewId(int viewId){
        this.viewId = viewId;
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
                Intent intent = new Intent(PatientActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
