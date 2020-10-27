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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PhysiotherapyFormFragment extends Fragment {
    Context context;

    String urlPhysiotherapyForm = CommonUtils.IP + "/NIEPMD/NIEPMDandroid/doctor/physiotherapy_form.php";

    ProgressDialog progressDialog;
    JsonObject jsonObject;

    TextView patientName;
    TextView patientAge;
    TextView diagnosis;
    Spinner gender;
    Spinner postureSpecialization;
    TextView gait;
    Spinner muscleOneSpecialization;
    TextView involuntaryMuscle;
    Spinner nutrionalStatusOfMuscles;
    TextView bulkAndGirthOfMuscles;
    TextView jointRangeOfMotion;
    TextView musclePower;
    Spinner contractureAndDeformity;
    TextView limpLengthDiscrepancy;
    TextView developmentReflexesMoro;
    TextView galant;
    TextView rooting;
    TextView sucking;
    TextView atnr;
    TextView sntr;
    TextView extensorThrust;
    TextView tonyLabryinthReflex;
    TextView righitingReflection;
    TextView eqilibriumReaction;
    TextView balanceReaction;
    TextView unilateral;

    CheckBox supine;
    CheckBox prone;
    CheckBox sitting;
    CheckBox standing;
    CheckBox running;
    CheckBox rolling;
    CheckBox commingToSitting;
    CheckBox crawling;
    CheckBox walking;
    CheckBox standingOnLeg;
    CheckBox climbingUp;
    CheckBox climbingDown;
    CheckBox sideWays;
    CheckBox backwards;
    CheckBox verticals;
    CheckBox upwards;

    ArrayAdapter<CharSequence> genderAdapter;
    ArrayAdapter<CharSequence> postSpecializationAdapter;
    ArrayAdapter<CharSequence> muscleOneSpecializationAdapter;
    ArrayAdapter<CharSequence> nutrionalStatusOfMusclesAdapter;
    ArrayAdapter<CharSequence> contractureAndDeformityAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_physiotherapy_form, container, false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        height = (int) (height / 1.7);

        ImageView background = view.findViewById(R.id.background);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 200, height);
        background.setLayoutParams(layoutParams);

        gender = view.findViewById(R.id.gender);
        genderAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.gender, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(genderAdapter);

        postureSpecialization = view.findViewById(R.id.postureSpecialization);
        postSpecializationAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.posture, android.R.layout.simple_spinner_item);
        postSpecializationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        postureSpecialization.setAdapter(postSpecializationAdapter);

        muscleOneSpecialization = view.findViewById(R.id.muscleOneSpecialization);
        muscleOneSpecializationAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.muscleOne, android.R.layout.simple_spinner_item);
        muscleOneSpecializationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        muscleOneSpecialization.setAdapter(muscleOneSpecializationAdapter);

        nutrionalStatusOfMuscles = view.findViewById(R.id.nutrionalStatusOfMuscles);
        nutrionalStatusOfMusclesAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.nutrionalStatusOfMuscles, android.R.layout.simple_spinner_item);
        nutrionalStatusOfMusclesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nutrionalStatusOfMuscles.setAdapter(nutrionalStatusOfMusclesAdapter);

        contractureAndDeformity = view.findViewById(R.id.contractureAndDeformity);
        contractureAndDeformityAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.contractureAndDeformity, android.R.layout.simple_spinner_item);
        contractureAndDeformityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contractureAndDeformity.setAdapter(contractureAndDeformityAdapter);

        patientName = view.findViewById(R.id.patientName);
        patientAge = view.findViewById(R.id.patientAge);
        diagnosis = view.findViewById(R.id.diagnosis);
        gait = view.findViewById(R.id.gait);
        involuntaryMuscle = view.findViewById(R.id.involuntaryMovements);
        bulkAndGirthOfMuscles = view.findViewById(R.id.bulkAndGirthOfMuscles);
        jointRangeOfMotion = view.findViewById(R.id.jointRangeOfMotion);
        musclePower = view.findViewById(R.id.musclePower);
        limpLengthDiscrepancy = view.findViewById(R.id.limpLengthDiscrepancy);
        developmentReflexesMoro = view.findViewById(R.id.developmentReflexesMoro);
        galant = view.findViewById(R.id.galant);
        rooting = view.findViewById(R.id.rooting);
        sucking = view.findViewById(R.id.sucking);
        atnr = view.findViewById(R.id.atnr);
        sntr = view.findViewById(R.id.sntr);
        extensorThrust = view.findViewById(R.id.extensorThrust);
        tonyLabryinthReflex = view.findViewById(R.id.tonyLabryinthReflex);
        righitingReflection = view.findViewById(R.id.righitingReflection);
        eqilibriumReaction = view.findViewById(R.id.eqilibriumReaction);
        balanceReaction = view.findViewById(R.id.balanceReaction);
        unilateral = view.findViewById(R.id.unilateral);

        supine = view.findViewById(R.id.supine);
        prone = view.findViewById(R.id.prone);
        sitting = view.findViewById(R.id.sitting);
        standing = view.findViewById(R.id.standing);
        running = view.findViewById(R.id.running);
        rolling = view.findViewById(R.id.rolling);
        commingToSitting = view.findViewById(R.id.commingToSitting);
        crawling = view.findViewById(R.id.crawling);
        walking = view.findViewById(R.id.walking);
        standingOnLeg = view.findViewById(R.id.standingOnLeg);
        climbingUp = view.findViewById(R.id.climbingUp);
        climbingDown = view.findViewById(R.id.climbingDown);
        sideWays = view.findViewById(R.id.sideWays);
        backwards = view.findViewById(R.id.backwards);
        verticals = view.findViewById(R.id.verticals);
        upwards = view.findViewById(R.id.upwards);

        Button add = view.findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(patientName.getText().toString().length() > 0) {
                    if (patientAge.getText().toString().length() > 0) {
                        if (diagnosis.getText().toString().length() > 0) {
                            if (gait.getText().toString().length() > 0) {
                                if (involuntaryMuscle.getText().toString().length() > 0) {
                                    if (bulkAndGirthOfMuscles.getText().toString().length() > 0) {
                                        if (jointRangeOfMotion.getText().toString().length() > 0) {
                                            if (musclePower.getText().toString().length() > 0) {
                                                if (limpLengthDiscrepancy.getText().toString().length() > 0) {
                                                    if (developmentReflexesMoro.getText().toString().length() > 0) {
                                                        if (galant.getText().toString().length() > 0) {
                                                            if (rooting.getText().toString().length() > 0) {
                                                                if (sucking.getText().toString().length() > 0) {
                                                                    if (atnr.getText().toString().length() > 0) {
                                                                        if (sntr.getText().toString().length() > 0) {
                                                                            if (extensorThrust.getText().toString().length() > 0) {
                                                                                if (tonyLabryinthReflex.getText().toString().length() > 0) {
                                                                                    if (righitingReflection.getText().toString().length() > 0) {
                                                                                        if (eqilibriumReaction.getText().toString().length() > 0) {
                                                                                            if (balanceReaction.getText().toString().length() > 0) {
                                                                                                if (unilateral.getText().toString().length() > 0) {
                                                                                                    progressDialog = new ProgressDialog(getContext());
                                                                                                    progressDialog.setCancelable(false);
                                                                                                    progressDialog.setMessage("Loading...");
                                                                                                    progressDialog.show();

                                                                                                    jsonObject = new JsonObject();
                                                                                                    jsonObject.addProperty("docId", ((DoctorActivity) getActivity()).getId());
                                                                                                    jsonObject.addProperty("patientName", patientName.getText().toString());
                                                                                                    jsonObject.addProperty("patientAge", patientAge.getText().toString());
                                                                                                    jsonObject.addProperty("diagnosis", diagnosis.getText().toString());
                                                                                                    jsonObject.addProperty("gait", gait.getText().toString());
                                                                                                    jsonObject.addProperty("involuntaryMuscle", involuntaryMuscle.getText().toString());
                                                                                                    jsonObject.addProperty("bulkAndGirthOfMuscles", bulkAndGirthOfMuscles.getText().toString());
                                                                                                    jsonObject.addProperty("jointRangeOfMotion", jointRangeOfMotion.getText().toString());
                                                                                                    jsonObject.addProperty("musclePower", musclePower.getText().toString());
                                                                                                    jsonObject.addProperty("limpLengthDiscrepancy", limpLengthDiscrepancy.getText().toString());
                                                                                                    jsonObject.addProperty("developmentReflexesMoro", developmentReflexesMoro.getText().toString());
                                                                                                    jsonObject.addProperty("galant", galant.getText().toString());
                                                                                                    jsonObject.addProperty("rooting", rooting.getText().toString());
                                                                                                    jsonObject.addProperty("sucking", sucking.getText().toString());
                                                                                                    jsonObject.addProperty("atnr", atnr.getText().toString());
                                                                                                    jsonObject.addProperty("sntr", sntr.getText().toString());
                                                                                                    jsonObject.addProperty("extensorThrust", extensorThrust.getText().toString());
                                                                                                    jsonObject.addProperty("tonyLabryinthReflex", tonyLabryinthReflex.getText().toString());
                                                                                                    jsonObject.addProperty("righitingReflection", righitingReflection.getText().toString());
                                                                                                    jsonObject.addProperty("eqilibriumReaction", eqilibriumReaction.getText().toString());
                                                                                                    jsonObject.addProperty("balanceReaction", balanceReaction.getText().toString());
                                                                                                    jsonObject.addProperty("unilateral", unilateral.getText().toString());

                                                                                                    if (supine.isChecked()) {
                                                                                                        jsonObject.addProperty("supine", 1);
                                                                                                    } else {
                                                                                                        jsonObject.addProperty("supine", 0);
                                                                                                    }

                                                                                                    if (prone.isChecked()) {
                                                                                                        jsonObject.addProperty("prone", 1);
                                                                                                    } else {
                                                                                                        jsonObject.addProperty("prone", 0);
                                                                                                    }

                                                                                                    if (sitting.isChecked()) {
                                                                                                        jsonObject.addProperty("sitting", 1);
                                                                                                    } else {
                                                                                                        jsonObject.addProperty("sitting", 0);
                                                                                                    }

                                                                                                    if (standing.isChecked()) {
                                                                                                        jsonObject.addProperty("standing", 1);
                                                                                                    } else {
                                                                                                        jsonObject.addProperty("standing", 0);
                                                                                                    }

                                                                                                    if (running.isChecked()) {
                                                                                                        jsonObject.addProperty("running", 1);
                                                                                                    } else {
                                                                                                        jsonObject.addProperty("running", 0);
                                                                                                    }

                                                                                                    if (rolling.isChecked()) {
                                                                                                        jsonObject.addProperty("rolling", 1);
                                                                                                    } else {
                                                                                                        jsonObject.addProperty("rolling", 0);
                                                                                                    }

                                                                                                    if (commingToSitting.isChecked()) {
                                                                                                        jsonObject.addProperty("commingToSitting", 1);
                                                                                                    } else {
                                                                                                        jsonObject.addProperty("commingToSitting", 0);
                                                                                                    }

                                                                                                    if (crawling.isChecked()) {
                                                                                                        jsonObject.addProperty("crawling", 1);
                                                                                                    } else {
                                                                                                        jsonObject.addProperty("crawling", 0);
                                                                                                    }

                                                                                                    if (walking.isChecked()) {
                                                                                                        jsonObject.addProperty("walking", 1);
                                                                                                    } else {
                                                                                                        jsonObject.addProperty("walking", 0);
                                                                                                    }

                                                                                                    if (standingOnLeg.isChecked()) {
                                                                                                        jsonObject.addProperty("standingOnLeg", 1);
                                                                                                    } else {
                                                                                                        jsonObject.addProperty("standingOnLeg", 0);
                                                                                                    }

                                                                                                    if (climbingUp.isChecked()) {
                                                                                                        jsonObject.addProperty("climbingUp", 1);
                                                                                                    } else {
                                                                                                        jsonObject.addProperty("climbingUp", 0);
                                                                                                    }

                                                                                                    if (climbingDown.isChecked()) {
                                                                                                        jsonObject.addProperty("climbingDown", 1);
                                                                                                    } else {
                                                                                                        jsonObject.addProperty("climbingDown", 0);
                                                                                                    }

                                                                                                    if (sideWays.isChecked()) {
                                                                                                        jsonObject.addProperty("sideWays", 1);
                                                                                                    } else {
                                                                                                        jsonObject.addProperty("sideWays", 0);
                                                                                                    }

                                                                                                    if (backwards.isChecked()) {
                                                                                                        jsonObject.addProperty("backwards", 1);
                                                                                                    } else {
                                                                                                        jsonObject.addProperty("backwards", 0);
                                                                                                    }

                                                                                                    if (verticals.isChecked()) {
                                                                                                        jsonObject.addProperty("verticals", 1);
                                                                                                    } else {
                                                                                                        jsonObject.addProperty("verticals", 0);
                                                                                                    }

                                                                                                    if (upwards.isChecked()) {
                                                                                                        jsonObject.addProperty("upwards", 1);
                                                                                                    } else {
                                                                                                        jsonObject.addProperty("upwards", 0);
                                                                                                    }

                                                                                                    jsonObject.addProperty("gender", gender.getSelectedItem().toString());
                                                                                                    jsonObject.addProperty("posture", postureSpecialization.getSelectedItem().toString());
                                                                                                    jsonObject.addProperty("muscle", muscleOneSpecialization.getSelectedItem().toString());
                                                                                                    jsonObject.addProperty("nutrionalStatusOfMuscles", nutrionalStatusOfMuscles.getSelectedItem().toString());
                                                                                                    jsonObject.addProperty("contractureAndDeformity", contractureAndDeformity.getSelectedItem().toString());

                                                                                                    PostPhysiotherapyForm postPhysiotherapyForm = new PostPhysiotherapyForm(context);
                                                                                                    postPhysiotherapyForm.checkServerAvailability(2);
                                                                                                }else{
                                                                                                    Toast.makeText(context, "Enter Unilateral", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            }else{
                                                                                                Toast.makeText(context, "Enter Balance Reaction", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        }else{
                                                                                            Toast.makeText(context, "Enter Eqilibrium Reaction", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                    }else{
                                                                                        Toast.makeText(context, "Enter Righiting Reflection", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }else{
                                                                                    Toast.makeText(context, "Enter Tony Labryinth Reflex", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            }else{
                                                                                Toast.makeText(context, "Enter Extensor Thrust", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }else{
                                                                            Toast.makeText(context, "Enter Symmetrical Tonic Neck Reflex", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }else{
                                                                        Toast.makeText(context, "Enter Asymmetrical Tonic Neck Reflex", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }else{
                                                                    Toast.makeText(context, "Enter Sucking", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }else{
                                                                Toast.makeText(context, "Enter Rooting", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }else{
                                                            Toast.makeText(context, "Enter Galant", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }else{
                                                        Toast.makeText(context, "Enter Moro", Toast.LENGTH_SHORT).show();
                                                    }
                                                }else{
                                                    Toast.makeText(context, "Enter Limp Length Discrepancy", Toast.LENGTH_SHORT).show();
                                                }
                                            }else{
                                                Toast.makeText(context, "Enter Muscle Power", Toast.LENGTH_SHORT).show();
                                            }
                                        }else{
                                            Toast.makeText(context, "Enter Joint Range Of Motion", Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        Toast.makeText(context, "Enter Bulk and Girth of Muscles", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(context, "Enter Involuntary Muscles", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(context, "Enter Gait", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(context, "Enter Diagnosis", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(context, "Enter Patient Age", Toast.LENGTH_SHORT).show();
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
    }

    private class PostPhysiotherapyForm extends PostRequest{
        public PostPhysiotherapyForm(Context context){
            super(context);
        }

        public void serverAvailability(boolean isServerAvailable){
            if(isServerAvailable){
                super.postRequest(urlPhysiotherapyForm, jsonObject);
            }else {
                Toast.makeText(context, "Connection to the server \nnot Available", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }

        public void onFinish(JSONArray jsonArray){
            try{
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);

                if(jsonObject.getBoolean("status")){
                    Toast.makeText(context, "Added Form Successfully", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    ((DoctorActivity) getActivity()).refreshPhysiotherapyForm();
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
