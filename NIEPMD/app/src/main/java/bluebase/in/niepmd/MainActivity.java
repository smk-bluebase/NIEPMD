package bluebase.in.niepmd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText email;
    EditText password;

    Context context = this;

    String urlLogin = CommonUtils.IP + "/NIEPMD/NIEPMDandroid/login.php";
    String urlForgotPassword = CommonUtils.IP + "/NIEPMD/NIEPMDandroid/forgotpasswordgenerator.php";

    JsonObject jsonObject;

    ProgressDialog progressDialog;
    Dialog dialog;

    RadioGroup doctorOrPatient;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        height = (int) (height / 1.7);

        ImageView background = findViewById(R.id.background);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 200, height);
        background.setLayoutParams(layoutParams);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        email.setText("mohanakrishnan.s@bluebase.in");
        password.setText("adminp");

        doctorOrPatient = findViewById(R.id.doctorOrPatient);

        Button login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().length() > 0) {
                    if(password.getText().toString().length() > 0) {
                        progressDialog = new ProgressDialog(context);
                        progressDialog.setCancelable(false);
                        progressDialog.setMessage("Loading...");
                        progressDialog.show();

                        radioButton = doctorOrPatient.findViewById(doctorOrPatient.getCheckedRadioButtonId());

                        jsonObject = new JsonObject();
                        jsonObject.addProperty("email", email.getText().toString());

                        MD5 md5 = new MD5();

                        jsonObject.addProperty("password", md5.getMd5(password.getText().toString()));

                        if(radioButton.getText().toString().equals("Doctor")) {
                            jsonObject.addProperty("doctorOrPatient", 1);
                        }else{
                            jsonObject.addProperty("doctorOrPatient", 0);
                        }

                        PostLogin postLogin = new PostLogin(context);
                        postLogin.checkServerAvailability(2);
                    }else {
                        Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView signUp = findViewById(R.id.signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });

        TextView forgotPassword = findViewById(R.id.forgotPassword);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(context);
                dialog.setCancelable(true);

                dialog.setContentView(R.layout.forgot_password);

                final EditText email = dialog.findViewById(R.id.email);

                final RadioGroup doctorOrPatient = dialog.findViewById(R.id.doctorOrPatient);

                Button ok = dialog.findViewById(R.id.ok);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(email.getText().toString().length() > 0) {
                            progressDialog = new ProgressDialog(context);
                            progressDialog.setCancelable(false);
                            progressDialog.setMessage("Loading...");
                            progressDialog.show();

                            RadioButton radioButton = doctorOrPatient.findViewById(doctorOrPatient.getCheckedRadioButtonId());

                            jsonObject = new JsonObject();
                            jsonObject.addProperty("email", email.getText().toString());
                            if(radioButton.getText().toString().equals("Doctor")) {
                                jsonObject.addProperty("doctorOrPatient", 1);
                            }else{
                                jsonObject.addProperty("doctorOrPatient", 0);
                            }

                            PostForgotPassword postForgotPassword = new PostForgotPassword(context);
                            postForgotPassword.checkServerAvailability(2);
                        }else{
                            Toast.makeText(context, "Enter Username", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.show();
            }
        });
    }

    private class PostLogin extends PostRequest{
        public PostLogin(Context context){
            super(context);
        }

        public void serverAvailability(boolean isServerAvailable){
            if(isServerAvailable){
                super.postRequest(urlLogin, jsonObject);
            }else {
                Toast.makeText(context, "Connection to the server \nnot Available", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }

        public void onFinish(JSONArray jsonArray){
            try {
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);

                if(jsonObject.getBoolean("status")){
                    if(jsonObject.getInt("doctororpatient") == 1){
                        Intent i = new Intent(MainActivity.this, DoctorActivity.class);
                        i.putExtra("id", jsonObject.getInt("id"));
                        startActivity(i);
                    }else if(jsonObject.getInt("doctororpatient") == 0){
                        Intent i = new Intent(MainActivity.this, PatientActivity.class);
                        i.putExtra("id", jsonObject.getInt("id"));
                        startActivity(i);
                    }
                }else {
                    Toast.makeText(context, "Username or Password \n\t\t\t\t\t\t not correct", Toast.LENGTH_SHORT).show();
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialog.dismiss();
        }
    }

    private class PostForgotPassword extends PostRequest{
        public PostForgotPassword(Context context){
            super(context);
        }

        public void serverAvailability(boolean isServerAvailable){
            if(isServerAvailable){
                super.postRequest(urlForgotPassword, jsonObject);
            }else{
                Toast.makeText(context, "Connection to the server \nnot Available", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }

        public void onFinish(JSONArray jsonArray) {
            try {
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);

                if (jsonObject.getBoolean("status")) {
                    Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "Username not valid", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

}