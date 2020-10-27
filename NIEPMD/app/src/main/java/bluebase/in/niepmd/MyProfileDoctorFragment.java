package bluebase.in.niepmd;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyProfileDoctorFragment extends Fragment {
    Context context;

    String urlMyProfile = CommonUtils.IP + "/NIEPMD/NIEPMDandroid/doctor/my_profile.php";

    ProgressDialog progressDialog;
    JsonObject jsonObject;

    TextView doctorName;
    TextView doctorSpecialization;
    TextView email;
    TextView clinicAddress;
    TextView consultancyFees;
    TextView contactNo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile_doctor, container, false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        height = (int) (height / 1.7);

        ImageView background = view.findViewById(R.id.background);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 200, height);
        background.setLayoutParams(layoutParams);

        ImageView editButton = view.findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("myProfileEntryDoctor")
                        .replace(R.id.fragment_container, new MyProfileEntryDoctorFragment(), "newProfileEntryDoctor")
                        .commit();
            }
        });

        doctorName = view.findViewById(R.id.doctorNameValue);
        doctorSpecialization = view.findViewById(R.id.doctorSpecializationValue);
        email = view.findViewById(R.id.emailValue);
        clinicAddress = view.findViewById(R.id.clinicAddressValue);
        consultancyFees = view.findViewById(R.id.consultancyFeesValue);
        contactNo = view.findViewById(R.id.contactNoValue);

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
        jsonObject.addProperty("id", ((DoctorActivity) getActivity()).getId());

        PostMyProfile postMyProfile = new PostMyProfile(context);
        postMyProfile.checkServerAvailability(2);
    }

    private class PostMyProfile extends PostRequest{
        public PostMyProfile(Context context){
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
                doctorSpecialization.setText(doctorDetailsObject.getString("doctorSpecialization"));
                email.setText(doctorDetailsObject.getString("docEmail"));
                clinicAddress.setText(doctorDetailsObject.getString("address"));
                consultancyFees.setText(doctorDetailsObject.getString("consultancyFees"));
                contactNo.setText(doctorDetailsObject.getString("contactNo"));

            }catch(JSONException e){
                e.printStackTrace();
            }

            progressDialog.dismiss();
        }
    }
}