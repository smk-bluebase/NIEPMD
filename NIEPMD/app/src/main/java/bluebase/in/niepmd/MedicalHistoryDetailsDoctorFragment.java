package bluebase.in.niepmd;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MedicalHistoryDetailsDoctorFragment extends Fragment {
    Context context;

    String urlMedicalHistoryDetails = CommonUtils.IP + "/NIEPMD/NIEPMDandroid/doctor/medical_history_details.php";

    ProgressDialog progressDialog;
    JsonObject jsonObject;

    public ArrayList<BodyVitalsHistoryItems> itemsList = new ArrayList<>();
    ListView bodyVitalsListView;

    TextView patientName;
    TextView patientEmail;
    TextView phone;
    TextView patientAddress;
    TextView patientGender;
    TextView patientAge;
    TextView patientMedicalHistory;
    TextView patientRegDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medical_history_details_doctor, container, false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        height = (int) (height / 1.7);

        ImageView background = view.findViewById(R.id.background);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 200, height);
        background.setLayoutParams(layoutParams);

        patientName = view.findViewById(R.id.patientName);
        patientEmail = view.findViewById(R.id.patientEmail);
        phone = view.findViewById(R.id.phone);
        patientAddress = view.findViewById(R.id.patientAddress);
        patientGender = view.findViewById(R.id.patientGender);
        patientAge = view.findViewById(R.id.patientAge);
        patientMedicalHistory = view.findViewById(R.id.patientMedicalHistory);
        patientRegDate = view.findViewById(R.id.patientRegDate);

        bodyVitalsListView = view.findViewById(R.id.bodyVitalsHistoryListView);

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
        jsonObject.addProperty("viewId", ((DoctorActivity) getActivity()).getPatientId());

        PostMedicalHistoryDetails postMedicalHistoryDetails = new PostMedicalHistoryDetails(context);
        postMedicalHistoryDetails.checkServerAvailability(2);
    }

    private class PostMedicalHistoryDetails extends PostRequest{
        public PostMedicalHistoryDetails(Context context){
            super(context);
        }

        public void serverAvailability(boolean isServerAvailable){
            if(isServerAvailable){
                super.postRequest(urlMedicalHistoryDetails, jsonObject);
            }else{
                Toast.makeText(context, "Connection to the server \nnot Available", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }

        public void onFinish(JSONArray jsonArray){
            try{
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);

                JSONObject patientDetailsObject = new JSONObject(jsonObject.getString("tblpatient"));

                patientName.setText(patientDetailsObject.getString("patientName"));
                patientEmail.setText(patientDetailsObject.getString("patientEmail"));
                phone.setText(patientDetailsObject.getString("patientContactNo"));
                patientAddress.setText(patientDetailsObject.getString("patientAddress"));
                patientGender.setText(patientDetailsObject.getString("patientGender"));
                patientAge.setText(patientDetailsObject.getString("patientAge"));
                patientMedicalHistory.setText(patientDetailsObject.getString("patientMedicalHistory"));
                patientRegDate.setText(patientDetailsObject.getString("creationDate"));

                JSONArray bodyVitalsHistoryObject = new JSONArray(jsonObject.getString("tblmedicalhistory"));

                for(int i = 0; i < bodyVitalsHistoryObject.length(); i++){
                    JSONObject entryObject = (JSONObject) bodyVitalsHistoryObject.get(i);

                    BodyVitalsHistoryItems item = new BodyVitalsHistoryItems();

                    item.setBloodPressure(entryObject.getString("bloodPressure"));
                    item.setWeight(entryObject.getString("weight"));
                    item.setBloodSugar(entryObject.getString("bloodSugar"));
                    item.setBodyTemperature(entryObject.getString("temperature"));
                    item.setMedicalPrescription(entryObject.getString("medicalPrescription"));
                    item.setVisitDate(entryObject.getString("creationDate"));

                    itemsList.add(item);
                }

            }catch(JSONException e){
                e.printStackTrace();
            }

            BodyVitalsHistoryAdapter adapter = new BodyVitalsHistoryAdapter(context, itemsList);
            bodyVitalsListView.setAdapter(adapter);

            setListViewHeightBasedOnChildren(bodyVitalsListView);

            progressDialog.dismiss();
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
