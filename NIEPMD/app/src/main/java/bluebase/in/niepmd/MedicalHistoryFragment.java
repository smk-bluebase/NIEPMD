package bluebase.in.niepmd;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import java.util.Iterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MedicalHistoryFragment extends Fragment {
    Context context;

    String urlMedicalHistory = CommonUtils.IP + "/NIEPMD/NIEPMDandroid/patient/medical_history.php";

    ProgressDialog progressDialog;
    JsonObject jsonObject;

    public ArrayList<MedicalHistoryPatientItems> itemsList = new ArrayList<>();
    ListView medicalHistoryListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medical_history, container, false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        height = (int) (height / 1.7);

        ImageView background = view.findViewById(R.id.background);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 200, height);
        background.setLayoutParams(layoutParams);

        medicalHistoryListView = view.findViewById(R.id.medicalHistoryListView);
        
        medicalHistoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Iterator i = itemsList.iterator();
                int j = 0;

                while(i.hasNext()){
                    if(j == position){
                        MedicalHistoryPatientItems item = (MedicalHistoryPatientItems) i.next();
                        ((PatientActivity) getActivity()).setViewId(item.getId());
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .addToBackStack("medicalHistoryDetails")
                                .replace(R.id.fragment_container, new MedicalHistoryDetailsFragment(), "medicalHistoryDetails")
                                .commit();
                        break;
                    }

                    if(i.hasNext())
                        i.next();
                    j++;
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
        jsonObject.addProperty("id", ((PatientActivity) getActivity()).getId());

        PostMedicalHistory postMedicalHistory = new PostMedicalHistory(context);
        postMedicalHistory.checkServerAvailability(2);

    }

    private class PostMedicalHistory extends PostRequest{
        public PostMedicalHistory(Context context){
            super(context);
        }

        public void serverAvailability(boolean isServerAvailable){
            if(isServerAvailable){
                super.postRequest(urlMedicalHistory, jsonObject);
            }else {
                Toast.makeText(context, "Connection to the server \nnot Available", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }

        public void onFinish(JSONArray jsonArray){
            try{
                itemsList.clear();

                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                    MedicalHistoryPatientItems item = new MedicalHistoryPatientItems();

                    item.setId(jsonObject.getInt("id"));
                    item.setPatientname(jsonObject.getString("patientName"));
                    item.setPatientcontactno(jsonObject.getString("patientContactNo"));
                    item.setPatientgender(jsonObject.getString("patientGender"));
                    item.setCreationdate(jsonObject.getString("creationDate"));
                    item.setUpdationdate(jsonObject.getString("updationDate"));
                    
                    itemsList.add(item);
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
            
            MedicalHistoryPatientsAdapter adapter = new MedicalHistoryPatientsAdapter(context, itemsList);
            medicalHistoryListView.setAdapter(adapter);

            progressDialog.dismiss();
        }
    }

}