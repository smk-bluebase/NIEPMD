package bluebase.in.niepmd;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class BodyVitalsHistoryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<BodyVitalsHistoryItems> items;

    public BodyVitalsHistoryAdapter(Context context, ArrayList<BodyVitalsHistoryItems> items){
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
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.body_vitals_list, parent, false);
        }

        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.parseColor("#F5F5F5"));
        }else{
            convertView.setBackgroundColor(Color.parseColor("#D3D3D3"));
        }

         BodyVitalsHistoryItems item = (BodyVitalsHistoryItems) getItem(position);

        TextView bloodPressure = convertView.findViewById(R.id.bloodPressure);
        TextView weight = convertView.findViewById(R.id.weight);
        TextView bloodSugar = convertView.findViewById(R.id.bloodSugar);
        TextView bodyTemperature = convertView.findViewById(R.id.bodyTemperature);
        TextView medicalPrescription = convertView.findViewById(R.id.medicalPrescription);
        TextView visitDate = convertView.findViewById(R.id.visitDate);

        bloodPressure.setText(item.getBloodPressure());
        weight.setText(item.getWeight());
        bloodSugar.setText(item.getBloodSugar());
        bodyTemperature.setText(item.getBodyTemperature());
        medicalPrescription.setText(item.getMedicalPrescription());
        visitDate.setText(item.getVisitDate());

        return convertView;
    }
}
