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

public class MyProfileEntryFragment extends Fragment {
    Context context;

    ProgressDialog progressDialog;

    String urlMyProfile = CommonUtils.IP + "/NIEPMD/NIEPMDandroid/patient/my_profile.php";
    String urlMyProfileEntry = CommonUtils.IP + "/NIEPMD/NIEPMDandroid/patient/my_profile_entry.php";

    JsonObject jsonObject;

    TextView name;
    Spinner gender;
    TextView email;
    TextView address;
    TextView city;

    ArrayAdapter<CharSequence> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myprofile_entry, container, false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        height = (int) (height / 1.7);

        ImageView background = view.findViewById(R.id.background);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 200, height);
        background.setLayoutParams(layoutParams);

        gender =  view.findViewById(R.id.gender);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapter);

        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        address = view.findViewById(R.id.address);
        city = view.findViewById(R.id.city);

        Button update = view.findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().length() > 0) {
                    if(email.getText().toString().length() > 0) {
                        if(address.getText().toString().length() > 0) {
                            if(city.getText().toString().length() > 0) {
                                progressDialog = new ProgressDialog(getContext());
                                progressDialog.setCancelable(false);
                                progressDialog.setMessage("Loading...");
                                progressDialog.show();

                                jsonObject = new JsonObject();
                                jsonObject.addProperty("id", ((PatientActivity) getActivity()).getId());
                                jsonObject.addProperty("name", name.getText().toString());
                                jsonObject.addProperty("email", email.getText().toString());
                                jsonObject.addProperty("gender", gender.getSelectedItem().toString().toLowerCase());
                                jsonObject.addProperty("address", address.getText().toString());
                                jsonObject.addProperty("city", city.getText().toString());

                                PostProfileEntry postProfileEntry = new PostProfileEntry(context);
                                postProfileEntry.checkServerAvailability(2);
                            }else{
                                Toast.makeText(context, "Enter City", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(context, "Enter Address", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(context, "Enter Email", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Enter Name", Toast.LENGTH_SHORT).show();
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

        PostMyProfile postMyProfile = new PostMyProfile(context);
        postMyProfile.checkServerAvailability(2);
    }

    private class PostMyProfile extends PostRequest{
        public PostMyProfile(Context context){
            super(context);
        }

        public void serverAvailability(boolean isSeverAvailable){
            if(isSeverAvailable){
                super.postRequest(urlMyProfile, jsonObject);
            }else {
                Toast.makeText(context, "Connection to the server \nnot Available", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }

        public void onFinish(JSONArray jsonArray){
            try{
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);

                name.setText(jsonObject.getString("fullName"));

                String genderValue = "Male";

                if(jsonObject.getString("gender").toLowerCase().equals("male")) genderValue = "Male";
                else if(jsonObject.getString("gender").toLowerCase().equals("female")) genderValue = "Female";
                else if(jsonObject.getString("gender").toLowerCase().equals("other")) genderValue = "Other";

                gender.setSelection(adapter.getPosition(genderValue));

                email.setText(jsonObject.getString("email"));
                address.setText(jsonObject.getString("address"));
                city.setText(jsonObject.getString("city"));

            }catch(JSONException e){
                e.printStackTrace();
            }

            progressDialog.dismiss();
        }
    }

    private class PostProfileEntry extends PostRequest{
        public PostProfileEntry(Context context){
            super(context);
        }

        public void serverAvailability(boolean isServerAvailable){
            if(isServerAvailable){
                super.postRequest(urlMyProfileEntry, jsonObject);
            }else{
                Toast.makeText(context, "Connection to the server \nnot Available", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }

        public void onFinish(JSONArray jsonArray){
            try{
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);

                if(jsonObject.getBoolean("status")){
                    Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
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
