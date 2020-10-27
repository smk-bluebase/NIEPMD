package bluebase.in.niepmd;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BookAppointmentFragment extends Fragment {
    Context context;

    String urlGetDoctorInfo = CommonUtils.IP + "/NIEPMD/NIEPMDandroid/patient/get_doctor_info.php";
    String urlBookAppointment = CommonUtils.IP + "/NIEPMD/NIEPMDandroid/patient/book_appointment.php";

    JsonObject jsonObject;
    ProgressDialog progressDialog;

    Spinner specializationSpinner;
    Spinner doctorSpinner;
    TextView consultancyFees;
    TextView date;
    TextView time;

    List<String> specializationList;
    List<String> doctorsList;
    JSONArray doctorJsonArray;

    int mYear, mMonth, mDay, mHour, mMinute;

    int doctorId = -1;
    int consultancyFeesValue;
    String dateValue = "";
    String timeValue = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_appointment, container, false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        height = (int) (height / 1.7);

        ImageView background = view.findViewById(R.id.background);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 200, height);
        background.setLayoutParams(layoutParams);

        specializationSpinner = view.findViewById(R.id.specialization);
        doctorSpinner = view.findViewById(R.id.doctor);
        consultancyFees = view.findViewById(R.id.consultancyFees);
        date = view.findViewById(R.id.date);
        time = view.findViewById(R.id.time);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            String month;
                            if(monthOfYear + 1 < 10){
                                month = "0" + (monthOfYear + 1);
                            }else{
                                month = String.valueOf(monthOfYear + 1);
                            }

                            dateValue = year + "-" + month + "-" + dayOfMonth;
                            date.setText("Date : " + dateValue);
                        }
                    }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String meridiem;
                                if(hourOfDay > 12) {
                                    hourOfDay -= 12;
                                    meridiem = "PM";
                                }else {
                                    meridiem = "AM";
                                }

                                String minuteStr;
                                if(minute < 10){
                                    minuteStr = "0" + minute;
                                }else{
                                    minuteStr = String.valueOf(minute);
                                }

                                timeValue = hourOfDay + ":" + minuteStr + " " + meridiem;
                                time.setText("Time : " + timeValue);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        Button submit = view.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doctorId != -1) {
                    if (dateValue.length() > 0) {
                        if(timeValue.length() > 0) {
                            progressDialog = new ProgressDialog(getContext());
                            progressDialog.setCancelable(false);
                            progressDialog.setMessage("Loading...");
                            progressDialog.show();

                            jsonObject = new JsonObject();
                            jsonObject.addProperty("doctorSpecialization", specializationSpinner.getSelectedItem().toString());
                            jsonObject.addProperty("doctorId", doctorId);
                            jsonObject.addProperty("userId", ((PatientActivity) getActivity()).getId());
                            jsonObject.addProperty("consultancyFees", consultancyFeesValue);
                            jsonObject.addProperty("appointmentDate", dateValue);
                            jsonObject.addProperty("appointmentTime", timeValue);

                            PostBookAppointment postBookAppointment = new PostBookAppointment(context);
                            postBookAppointment.checkServerAvailability(2);
                        }else{
                            Toast.makeText(context, "Select Time", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(context, "Select Date", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Select Doctor", Toast.LENGTH_SHORT).show();
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

        PostGetDoctorInfo postGetDoctorInfo = new PostGetDoctorInfo(context);
        postGetDoctorInfo.checkServerAvailability(2);
    }

    private class PostGetDoctorInfo extends PostRequest{
        public PostGetDoctorInfo(Context context){
            super(context);
        }

        public void serverAvailability(boolean isServerAvailable){
            if(isServerAvailable){
                super.postRequest(urlGetDoctorInfo, new JsonObject());
            }else {
                Toast.makeText(context, "Connection to the server \nnot Available", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }

        public void onFinish(JSONArray jsonArray){
            try{
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);

                specializationList = new ArrayList<String>();
                JSONArray specializationJsonArray = new JSONArray(jsonObject.getString("specialization"));
                for(int i = 0; i < specializationJsonArray.length(); i++){
                    specializationList.add(specializationJsonArray.get(i).toString());
                }
                ArrayAdapter<String> specializationAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, specializationList);
                specializationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                specializationSpinner.setAdapter(specializationAdapter);
                specializationSpinner.setOnItemSelectedListener(new SpecializationSpinnerListener());

               doctorJsonArray  = new JSONArray(jsonObject.getString("doctors"));
            }catch (JSONException e){
                e.printStackTrace();
            }

            progressDialog.dismiss();
        }
    }

    private class SpecializationSpinnerListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            doctorsList = new ArrayList<String>();

            try {
                for (int i = 0; i < doctorJsonArray.length(); i++) {
                    JSONObject doctorObject = (JSONObject) doctorJsonArray.get(i);
                    if(doctorObject.getString("specialization").equals(specializationList.get(position))) {
                        doctorsList.add(doctorObject.getString("doctorName"));
                    }
                }
                ArrayAdapter<String> doctorAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, doctorsList);
                doctorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                doctorSpinner.setAdapter(doctorAdapter);
                doctorSpinner.setOnItemSelectedListener(new DoctorSpinnerListener());
            }catch(JSONException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //Do Nothing!
        }
    }

    private class DoctorSpinnerListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try{
                for(int i = 0; i < doctorJsonArray.length(); i++){
                    JSONObject doctorObject = (JSONObject) doctorJsonArray.get(i);
                    if(doctorObject.getString("doctorName").equals(doctorsList.get(position))){
                        doctorId = doctorObject.getInt("id");
                        consultancyFeesValue = doctorObject.getInt("docFees");
                        consultancyFees.setText("Consultancy Fees : " + consultancyFeesValue);
                        break;
                    }
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //Do Nothing!
        }
    }

    private class PostBookAppointment extends PostRequest{
        public PostBookAppointment(Context context){
            super(context);
        }

        public void serverAvailability(boolean isServerAvailable){
            if(isServerAvailable){
                super.postRequest(urlBookAppointment, jsonObject);
            }else {
                Toast.makeText(context, "Connection to the server \nnot Available", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }

        public void onFinish(JSONArray jsonArray){
            try{
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);

                if(jsonObject.getBoolean("status")){
                    Toast.makeText(context, "Appointment Booked Successfully", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    ((PatientActivity) getActivity()).refreshBookAppointment();
                }else {
                    Toast.makeText(context, "Connection to the server \nnot Available", Toast.LENGTH_SHORT).show();
                }

            }catch (JSONException e){
                e.printStackTrace();
            }

            progressDialog.dismiss();
        }
    }
}
