package bluebase.in.niepmd;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ManagePatientsFragment extends Fragment {
    Context context;

    String urlManagePatients = CommonUtils.IP  + "/NIEPMD/NIEPMDandroid/doctor/manage_patients.php";

    ProgressDialog progressDialog;
    JsonObject jsonObject;

    ListView managePatientsListView;

    public ArrayList<ManagePatientsItems> itemsList = new ArrayList<>();

    Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_patients, container, false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        height = (int) (height / 1.7);

        ImageView background = view.findViewById(R.id.background);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 200, height);
        background.setLayoutParams(layoutParams);

        managePatientsListView = view.findViewById(R.id.managePatientsListView);

        managePatientsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Iterator i = itemsList.iterator();
                int j = 0;

                while(i.hasNext()){
                    if(j == position) {
                        ManagePatientsItems item = (ManagePatientsItems) i.next();
                        ((DoctorActivity) getActivity()).setPatientId(item.getId());
                        break;
                    }
                    if (i.hasNext())
                        i.next();
                    j++;
                }

                dialog = new Dialog(context);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.manage_patients_options);

                Button updatePatient = dialog.findViewById(R.id.updatePatient);

                updatePatient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .addToBackStack("updatePatient")
                                .replace(R.id.fragment_container, new UpdatePatientFragment(), "updatePatient")
                                .commit();
                    }
                });

                Button medicalDetails = dialog.findViewById(R.id.medicalDetails);

                medicalDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .addToBackStack("medicalDetailsDoctor")
                                .replace(R.id.fragment_container, new MedicalHistoryDetailsDoctorFragment(), "medicalDetailsDoctor")
                                .commit();
                    }
                });

                dialog.show();
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
        jsonObject.addProperty("docId", ((DoctorActivity) getActivity()).getId());

        PostManagePatients postManagePatients = new PostManagePatients(context);
        postManagePatients.checkServerAvailability(2);
    }

    private class PostManagePatients extends PostRequest{
        public PostManagePatients(Context context){
            super(context);
        }

        public void serverAvailability(boolean isServerAvailable){
            if(isServerAvailable){
                super.postRequest(urlManagePatients, jsonObject);
            }else{
                Toast.makeText(context, "Connection to the server \nnot Available", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }

        public void onFinish(JSONArray jsonArray){
            try{
                itemsList.clear();

                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                    ManagePatientsItems item = new ManagePatientsItems();

                    item.setId(jsonObject.getInt("id"));
                    item.setPatientName(jsonObject.getString("patientName"));
                    item.setContactNo(jsonObject.getString("contactNo"));
                    item.setPatientGender(jsonObject.getString("patientGender"));
                    item.setCreationDate(jsonObject.getString("creationDate"));
                    item.setUpdationDate(jsonObject.getString("updationDate"));

                    itemsList.add(item);
                }
            }catch(JSONException e){
                e.printStackTrace();
            }

            ManagePatientsAdapter adapter = new ManagePatientsAdapter(context, itemsList);
            managePatientsListView.setAdapter(adapter);

            progressDialog.dismiss();
        }
    }

}
