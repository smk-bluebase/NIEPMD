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

public class MyProfileFragment extends Fragment {
    Context context;

    TextView name;
    TextView gender;
    TextView email;
    TextView address;
    TextView city;
    TextView profileRegDate;
    TextView profileLastUpdationDate;

    ProgressDialog progressDialog;

    String urlMyProfile = CommonUtils.IP + "/NIEPMD/NIEPMDandroid/patient/my_profile.php";

    JsonObject jsonObject;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myprofile, container, false);

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
                        .addToBackStack("myProfileEntry")
                        .replace(R.id.fragment_container, new MyProfileEntryFragment(), "myProfileEntry")
                        .commit();
            }
        });

        name = view.findViewById(R.id.nameValue);
        gender = view.findViewById(R.id.genderValue);
        email = view.findViewById(R.id.emailValue);
        address = view.findViewById(R.id.addressValue);
        city = view.findViewById(R.id.cityValue);
        profileRegDate = view.findViewById(R.id.profileRegDateValue);
        profileLastUpdationDate = view.findViewById(R.id.profileLastUpdationDateValue);

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
                gender.setText(jsonObject.getString("gender"));
                email.setText(jsonObject.getString("email"));
                address.setText(jsonObject.getString("address"));
                city.setText(jsonObject.getString("city"));
                profileRegDate.setText(jsonObject.getString("regDate"));
                profileLastUpdationDate.setText(jsonObject.getString("updationDate"));

            }catch(JSONException e){
                e.printStackTrace();
            }

            progressDialog.dismiss();
        }
    }
}
