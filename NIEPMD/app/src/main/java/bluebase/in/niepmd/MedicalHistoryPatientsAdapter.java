package bluebase.in.niepmd;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class MedicalHistoryPatientsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MedicalHistoryPatientItems> items;

    public MedicalHistoryPatientsAdapter(Context context, ArrayList<MedicalHistoryPatientItems> items){
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.medical_history_list, parent, false);
        }

        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.parseColor("#F5F5F5"));
        }else{
            convertView.setBackgroundColor(Color.parseColor("#D3D3D3"));
        }

        MedicalHistoryPatientItems items = (MedicalHistoryPatientItems) getItem(position);

        TextView patientName = convertView.findViewById(R.id.patientName);
        TextView patientContactNo = convertView.findViewById(R.id.patientContactNo);
        TextView patientGender = convertView.findViewById(R.id.patientGender);
        TextView creationDate = convertView.findViewById(R.id.creationDate);
        TextView updationDate = convertView.findViewById(R.id.updationDate);

        patientName.setText(items.getPatientname());
        patientContactNo.setText(items.getPatientcontactno());
        patientGender.setText(items.getPatientgender());
        creationDate.setText(items.getCreationdate());
        updationDate.setText(items.getUpdationdate());

        return convertView;
    }
}
