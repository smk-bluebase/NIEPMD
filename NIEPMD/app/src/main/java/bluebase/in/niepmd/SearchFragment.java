package bluebase.in.niepmd;

import android.app.Dialog;
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
import android.widget.SearchView;
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

public class SearchFragment extends Fragment {
    Context context;

    String urlSearch = CommonUtils.IP + "/NIEPMD/NIEPMDandroid/doctor/search.php";

    JsonObject jsonObject;

    SearchView search;

    ListView searchListView;
    public ArrayList<SearchItems> itemsList = new ArrayList<>();

    Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        height = (int) (height / 1.7);

        ImageView background = view.findViewById(R.id.background);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 200, height);
        background.setLayoutParams(layoutParams);

        search = view.findViewById(R.id.search);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return false;
            }
        });

        searchListView = view.findViewById(R.id.searchListView);

        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Iterator i = itemsList.iterator();
                int j = 0;

                while(i.hasNext()){
                    if(j == position) {
                        SearchItems item = (SearchItems) i.next();
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
                                .addToBackStack("medicalDetails")
                                .replace(R.id.fragment_container, new MedicalHistoryDetailsDoctorFragment(), "medicalDetails")
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
    }

    public void search(String query){
        if(query.equals("")) {
            query = null;
        }

        jsonObject = new JsonObject();
        jsonObject.addProperty("query", query);

        PostSearch postSearch = new PostSearch(context);
        postSearch.checkServerAvailability(1);
    }

    private class PostSearch extends PostRequest{
        public PostSearch(Context context){
            super(context);
        }

        public void serverAvailability(boolean isServerAvailable){
            if(isServerAvailable){
                super.postRequest(urlSearch, jsonObject);
            }else {
                Toast.makeText(context, "Connection to the server \nnot Available", Toast.LENGTH_SHORT).show();
            }
        }

        public void onFinish(JSONArray jsonArray){
            try{
                itemsList.clear();

                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                    SearchItems item = new SearchItems();

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

            SearchAdapter adapter = new SearchAdapter(context, itemsList);
            searchListView.setAdapter(adapter);
        }
    }
}
