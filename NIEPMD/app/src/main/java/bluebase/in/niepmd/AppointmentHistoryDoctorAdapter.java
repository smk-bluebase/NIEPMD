package bluebase.in.niepmd;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class AppointmentHistoryDoctorAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<AppointmentHistoryDoctorItems> items;

    public AppointmentHistoryDoctorAdapter(Context context, ArrayList<AppointmentHistoryDoctorItems> items){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.appointment_history_doctor_list, parent, false);
        }

        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.parseColor("#F5F5F5"));
        }else{
            convertView.setBackgroundColor(Color.parseColor("#D3D3D3"));
        }

        AppointmentHistoryDoctorItems currentItem = (AppointmentHistoryDoctorItems) getItem(position);

        TextView patientName = convertView.findViewById(R.id.patientName);
        TextView doctorSpecialization = convertView.findViewById(R.id.doctorSpecialization);
        TextView consultancyFees = convertView.findViewById(R.id.consultancyFees);
        TextView appointmentTime = convertView.findViewById(R.id.appointmentTime);
        TextView bookingTime = convertView.findViewById(R.id.bookingTime);
        TextView currentStatus = convertView.findViewById(R.id.currentStatus);

        patientName.setText(currentItem.getPatientName());
        doctorSpecialization.setText(currentItem.getDoctorSpecialization());
        consultancyFees.setText(currentItem.getConsultancyFees());
        appointmentTime.setText(currentItem.getAppointmentDate() + " / " + currentItem.getAppointmentTime());
        bookingTime.setText(currentItem.getPostingDate());

        int userStatus = Integer.parseInt(currentItem.getUserStatus());
        int doctorStatus = Integer.parseInt(currentItem.getDoctorStatus());

        if(userStatus == 1 && doctorStatus == 1){
            currentStatus.setText("Active");
        }else if(userStatus == 0 && doctorStatus == 1){
            currentStatus.setText("Cancel by you");
        }else if(userStatus == 1 && doctorStatus == 0){
            currentStatus.setText("Cancel by Doctor");
        }else {
            currentStatus.setText("Cancelled");
        }

        return convertView;
    }
}
