package bluebase.in.niepmd;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import java.util.Iterator;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AppointmentHistoryFragment extends Fragment {
    Context context;
    JsonObject jsonObject;
    String urlAppointmentHistory = CommonUtils.IP + "/NIEPMD/NIEPMDandroid/patient/appointment_history.php";
    String urlCancelAppointment = CommonUtils.IP + "/NIEPMD/NIEPMDandroid/patient/cancel_appointment.php";

    ProgressDialog progressDialog;

    ListView appointmentHistoryListView;
    Dialog dialog;
    int appointmentId;

    public ArrayList<AppointmentHistoryPatientItems> itemsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment_history, container, false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        height = (int) (height / 1.7);

        ImageView background = view.findViewById(R.id.background);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 200, height);
        background.setLayoutParams(layoutParams);

        appointmentHistoryListView = view.findViewById(R.id.appointmentHistoryListView);

        appointmentHistoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Iterator i = itemsList.iterator();
                int j = 0;

                while(i.hasNext()){
                    if(j == position) {
                        AppointmentHistoryPatientItems item = (AppointmentHistoryPatientItems) i.next();
                        appointmentId = item.getId();
                    }
                    if (i.hasNext())
                        i.next();
                    j++;
                }

                dialog = new Dialog(context);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.cancel_appointment);

                Button ok = dialog.findViewById(R.id.ok);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog = new ProgressDialog(context);
                        progressDialog.setCancelable(false);
                        progressDialog.setMessage("Loading...");
                        progressDialog.show();

                        jsonObject = new JsonObject();
                        jsonObject.addProperty("id", appointmentId);

                        PostCancelAppointment postCancelAppointment = new PostCancelAppointment(context);
                        postCancelAppointment.checkServerAvailability(2);

                        dialog.dismiss();
                    }
                });

                Button cancel = dialog.findViewById(R.id.cancel);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
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
        jsonObject.addProperty("userId", ((PatientActivity) getActivity()).getId());

        PostAppointmentHistory postAppointmentHistory = new PostAppointmentHistory(getContext());
        postAppointmentHistory.checkServerAvailability(2);
    }

    private class PostAppointmentHistory extends PostRequest{
        public PostAppointmentHistory(Context context){
            super(context);
        }

        public void serverAvailability(boolean isServerAvailable){
            if(isServerAvailable){
                super.postRequest(urlAppointmentHistory, jsonObject);
            }else {
                Toast.makeText(context, "Connection to the server \nnot Available", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }

        public void onFinish(JSONArray jsonArray){
            try {
                itemsList.clear();

                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                    AppointmentHistoryPatientItems item = new AppointmentHistoryPatientItems();

                    item.setId(jsonObject.getInt("appointmentId"));
                    item.setDoctorName(jsonObject.getString("doctorName"));
                    item.setDoctorSpecialization(jsonObject.getString("doctorSpecialization"));
                    item.setConsultancyFees(jsonObject.getString("consultancyFees"));
                    item.setAppointmentDate(jsonObject.getString("appointmentDate"));
                    item.setAppointmentTime(jsonObject.getString("appointmentTime"));
                    item.setPostingDate(jsonObject.getString("postingDate"));
                    item.setUserStatus(jsonObject.getString("userStatus"));
                    item.setDoctorStatus(jsonObject.getString("doctorStatus"));

                    itemsList.add(item);
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }

            AppointmentHistoryPatientAdapter appointmentHistoryPatientAdapter = new AppointmentHistoryPatientAdapter(context, itemsList);
            appointmentHistoryListView.setAdapter(appointmentHistoryPatientAdapter);

            progressDialog.dismiss();
        }
    }

    private class PostCancelAppointment extends PostRequest{
        public PostCancelAppointment(Context context){
            super(context);
        }

        public void serverAvailability(boolean isServerAvailable){
            if(isServerAvailable){
                super.postRequest(urlCancelAppointment, jsonObject);
            }else{
                Toast.makeText(context, "Connection to the server \nnot Available", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }

        public void onFinish(JSONArray jsonArray){
            try{
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);

                if(jsonObject1.getBoolean("status")){
                    Toast.makeText(context, "Appointment cancelled successfully", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    ((PatientActivity) getActivity()).refreshAppointmentHistory();
                }else{
                    Toast.makeText(context, "Connection to the server \nnot Available", Toast.LENGTH_SHORT).show();
                }
            }catch(JSONException e){
                e.printStackTrace();
            }

            progressDialog.dismiss();
        }
    }

}
