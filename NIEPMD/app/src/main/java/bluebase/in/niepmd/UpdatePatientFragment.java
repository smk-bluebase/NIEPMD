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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UpdatePatientFragment extends Fragment {
    Context context;

    String urlGetPatientDetails = CommonUtils.IP + "/NIEPMD/NIEPMDandroid/doctor/get_patient_details.php";
    String urlUpdatePatient = CommonUtils.IP + "/NIEPMD/NIEPMDandroid/doctor/update_patient.php";

    ProgressDialog progressDialog;
    JsonObject jsonObject;

    TextView patientName;
    TextView contactNo;
    TextView patientEmail;
    TextView patientAddress;
    TextView patientAge;
    TextView medicalHistory;

    Spinner patientGender;

    ArrayAdapter<CharSequence> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_patient, container, false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        height = (int) (height / 1.7);

        ImageView background = view.findViewById(R.id.background);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 200, height);
        background.setLayoutParams(layoutParams);

        patientGender = view.findViewById(R.id.patientGender);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        patientGender.setAdapter(adapter);

        patientName = view.findViewById(R.id.patientName);
        contactNo = view.findViewById(R.id.contactNo);
        patientEmail = view.findViewById(R.id.patientEmail);
        patientAddress = view.findViewById(R.id.patientAddress);
        patientAge = view.findViewById(R.id.patientAge);
        medicalHistory = view.findViewById(R.id.medicalHistory);

        Button updatePatient = view.findViewById(R.id.updatePatientButton);

        updatePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (patientName.getText().toString().length() > 0) {
                    if (contactNo.getText().toString().length() > 0) {
                        if (patientEmail.getText().toString().length() > 0) {
                            if (patientAddress.getText().toString().length() > 0) {
                                if (patientAge.getText().toString().length() > 0) {
                                    if (medicalHistory.getText().toString().length() > 0) {
                                        progressDialog = new ProgressDialog(getContext());
                                        progressDialog.setCancelable(false);
                                        progressDialog.setMessage("Loading...");
                                        progressDialog.show();

                                        jsonObject = new JsonObject();
                                        jsonObject.addProperty("id", ((DoctorActivity) getActivity()).getPatientId());
                                        jsonObject.addProperty("patientName", patientName.getText().toString());
                                        jsonObject.addProperty("patientContactNo", contactNo.getText().toString());
                                        jsonObject.addProperty("patientEmail", patientEmail.getText().toString());
                                        jsonObject.addProperty("patientGender", patientGender.getSelectedItem().toString());
                                        jsonObject.addProperty("patientAddress", patientAddress.getText().toString());
                                        jsonObject.addProperty("patientAge", patientAge.getText().toString());
                                        jsonObject.addProperty("patientMedicalHistory", medicalHistory.getText().toString());

                                        PostUpdatePatient postUpdatePatient = new PostUpdatePatient(context);
                                        postUpdatePatient.checkServerAvailability(2);
                                    } else {
                                        Toast.makeText(context, "Enter Medical History", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(context, "Enter Patient Age", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(context, "Enter Patient Address", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Enter Patient Email", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Enter Contact No", Toast.LENGTH_SHORT).show();
                    }
                }else{
                        Toast.makeText(context, "Enter Patient Name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context = getContext();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        jsonObject = new JsonObject();
        jsonObject.addProperty("id", ((DoctorActivity) getActivity()).getPatientId());

        PostPatientDetails postPatientDetails = new PostPatientDetails(context);
        postPatientDetails.checkServerAvailability(2);
    }

    private class PostPatientDetails extends PostRequest{
        public PostPatientDetails(Context context){
            super(context);
        }

        public void serverAvailability(boolean isServerAvailable){
            if(isServerAvailable){
                super.postRequest(urlGetPatientDetails, jsonObject);
            }else {
                Toast.makeText(context, "Connection to the server \nnot Available", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }

        public void onFinish(JSONArray jsonArray){
            try{
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);

                patientName.setText(jsonObject.getString("patientName"));
                contactNo.setText(jsonObject.getString("contactNo"));
                patientEmail.setText(jsonObject.getString("patientEmail"));
                patientAddress.setText(jsonObject.getString("patientAddress"));
                patientAge.setText(jsonObject.getString("patientAge"));
                medicalHistory.setText(jsonObject.getString("medicalHistory"));

                patientGender.setSelection(adapter.getPosition(jsonObject.getString("patientGender")));

            }catch(JSONException e){
                e.printStackTrace();
            }

            progressDialog.dismiss();
        }
    }

    private class PostUpdatePatient  extends PostRequest{
        public PostUpdatePatient(Context context){
            super(context);
        }

        public void serverAvailability(boolean isServerAvailable){
            if(isServerAvailable){
                super.postRequest(urlUpdatePatient, jsonObject);
            }else {
                Toast.makeText(context, "Connection to the server \nnot Available", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }

        public void onFinish(JSONArray jsonArray){
            try{
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);

                if(jsonObject.getBoolean("status")){
                    Toast.makeText(context, "Updated Patient Successfully", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    getActivity().getSupportFragmentManager().popBackStack();
                }else {
                    Toast.makeText(context, "Connection to the server \nnot Available", Toast.LENGTH_SHORT).show();
                }
            }catch(JSONException e){
                e.printStackTrace();
            }

            progressDialog.dismiss();
        }
    }

}
