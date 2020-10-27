package bluebase.in.niepmd;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class SearchAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SearchItems> items;

    public SearchAdapter(Context context, ArrayList<SearchItems> items){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.search_list, parent, false);
        }

        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.parseColor("#F5F5F5"));
        }else{
            convertView.setBackgroundColor(Color.parseColor("#D3D3D3"));
        }

        SearchItems currentItem = (SearchItems) getItem(position);

        TextView patientName = convertView.findViewById(R.id.patientName);
        TextView contactNo = convertView.findViewById(R.id.contactNo);
        TextView patientGender = convertView.findViewById(R.id.patientGender);
        TextView creationDate = convertView.findViewById(R.id.creationDate);
        TextView updationDate = convertView.findViewById(R.id.updationDate);

        patientName.setText(currentItem.getPatientName());
        contactNo.setText(currentItem.getContactNo());
        patientGender.setText(currentItem.getPatientGender());
        creationDate.setText(currentItem.getCreationDate());
        updationDate.setText(currentItem.getUpdationDate());

        return convertView;
    }
}
