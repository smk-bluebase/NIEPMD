package bluebase.in.niepmd;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyProfileEntryDoctorFragment extends Fragment {
    Context context;

    String urlMyProfile = CommonUtils.IP + "/NIEPMD/NIEPMDandroid/doctor/my_profile.php";
    String urlMyProfileEntry = CommonUtils.IP + "/NIEPMD/NIEPMDandroid/doctor/update_profile.php";

    ProgressDialog progressDialog;
    JsonObject jsonObject;

    TextView doctorName;
    Spinner doctorSpecialization;
    TextView email;
    TextView clinicAddress;
    TextView consultancyFees;
    TextView contactNo;

    ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile_entry_doctor, container, false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        height = (int) (height / 1.7);

        ImageView background = view.findViewById(R.id.background);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 200, height);
        background.setLayoutParams(layoutParams);

        doctorSpecialization =  view.findViewById(R.id.doctorSpecializationSpinner);

        doctorName = view.findViewById(R.id.doctorName);
        email = view.findViewById(R.id.email);
        clinicAddress = view.findViewById(R.id.clinicAddress);
        consultancyFees = view.findViewById(R.id.consultancyFees);
        contactNo = view.findViewById(R.id.contactNo);

        Button update = view.findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doctorName.getText().toString().length() > 0) {
                    if(doctorSpecialization.getSelectedItem().toString().length() > 0) {
                        if(email.getText().toString().length() > 0) {
                            if(clinicAddress.getText().toString().length() > 0) {
                                if(consultancyFees.getText().toString().length() > 0) {
                                    if(contactNo.getText().toString().length() > 0) {
                                        progressDialog = new ProgressDialog(getContext());
                                        progressDialog.setCancelable(false);
                                        progressDialog.setMessage("Loading...");
                                        progressDialog.show();

                                        jsonObject = new JsonObject();
                                        jsonObject.addProperty("id", ((DoctorActivity) getActivity()).getId());
                                        jsonObject.addProperty("doctorName", doctorName.getText().toString());
                                        jsonObject.addProperty("doctorSpecialization", doctorSpecialization.getSelectedItem().toString());
                                        jsonObject.addProperty("doctorEmail", email.getText().toString());
                                        jsonObject.addProperty("clinicAddress", clinicAddress.getText().toString());
                                        jsonObject.addProperty("consultancyFees", consultancyFees.getText().toString());
                                        jsonObject.addProperty("contactNo", contactNo.getText().toString());

                                        PostMyProfileDoctorEntry postMyProfileDoctorEntry = new PostMyProfileDoctorEntry(context);
                                        postMyProfileDoctorEntry.checkServerAvailability(2);
                                    }else{
                                        Toast.makeText(context, "Enter Contact No", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(context, "Enter Consultancy Fees", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(context, "Enter Clinic Address", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(context, "Enter Email", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(context, "Enter Doctor Specialization", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Enter Doctor Name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context= getContext();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        jsonObject = new JsonObject();
        jsonObject.addProperty("id", ((DoctorActivity) getActivity()).getId());

        PostMyProfileDoctor postMyProfileDoctor = new PostMyProfileDoctor(context);
        postMyProfileDoctor.checkServerAvailability(2);
    }

    private class PostMyProfileDoctor extends PostRequest{
        public PostMyProfileDoctor(Context context){
            super(context);
        }

        public void serverAvailability(boolean isServerAvailable){
            if(isServerAvailable){
                super.postRequest(urlMyProfile, jsonObject);
            }else {
                Toast.makeText(context, "Connection to the server \nnot Available", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }

        public void onFinish(JSONArray jsonArray){
            try{
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);

                JSONObject doctorDetailsObject = new JSONObject(jsonObject.getString("doctorDetails"));

                doctorName.setText(doctorDetailsObject.getString("doctorName"));
                email.setText(doctorDetailsObject.getString("docEmail"));
                clinicAddress.setText(doctorDetailsObject.getString("address"));
                consultancyFees.setText(doctorDetailsObject.getString("consultancyFees"));
                contactNo.setText(doctorDetailsObject.getString("contactNo"));

                JSONArray specializationArray = new JSONArray(jsonObject.getString("specialization"));

                List<String> specializationList = new ArrayList<String>();

                for(int i = 0; i < specializationArray.length(); i++){
                    specializationList.add(specializationArray.getString(i));
                }

                adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, specializationList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                doctorSpecialization.setAdapter(adapter);

                doctorSpecialization.setSelection(adapter.getPosition(doctorDetailsObject.getString("doctorSpecialization")));

            }catch(JSONException e){
                e.printStackTrace();
            }

            progressDialog.dismiss();
        }
    }

    private class PostMyProfileDoctorEntry extends PostRequest{
        public PostMyProfileDoctorEntry(Context context){
            super(context);
        }

        public void serverAvailability(boolean isServerAvailable){
            if(isServerAvailable){
                super.postRequest(urlMyProfileEntry, jsonObject);
            }else {
                Toast.makeText(context, "Connection to the server \nnot Available", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }

        public void onFinish(JSONArray jsonArray){
            try{
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);

                if(jsonObject.getBoolean("status")){
                    Toast.makeText(context, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    getActivity().getSupportFragmentManager().popBackStack();
                }else {
                    Toast.makeText(context, "Profile not Updated", Toast.LENGTH_SHORT).show();
                }

            }catch(JSONException e){
                e.printStackTrace();
            }

            progressDialog.dismiss();
        }
    }
}