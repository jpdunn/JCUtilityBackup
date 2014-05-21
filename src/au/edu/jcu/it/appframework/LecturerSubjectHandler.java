package au.edu.jcu.it.appframework;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class LecturerSubjectHandler extends ArrayAdapter<LecturerSubjectItem> {
	
	private final Context context;
    private final ArrayList<LecturerSubjectItem> itemsArrayList;

    public LecturerSubjectHandler(Context context, ArrayList<LecturerSubjectItem> itemsArrayList) {

        super(context, R.layout.lecturesubjectlist, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater 
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.lecturesubjectlist, parent, false);
        TextView labelView = (TextView) rowView.findViewById(R.id.label);

        labelView.setText(itemsArrayList.get(position).getTitle());
        
        Button roster = (Button) rowView.findViewById(R.id.viewAttendanceButton);
        roster.setTag(itemsArrayList.get(position).getTitle().toString());
        
        Button addAttendance = (Button) rowView.findViewById(R.id.attendanceButton);
        addAttendance.setTag(itemsArrayList.get(position).getTitle().toString());
        
        Button getAttendance = (Button) rowView.findViewById(R.id.viewA);
        getAttendance.setTag(itemsArrayList.get(position).getTitle().toString());

        // 5. retrn rowView
        return rowView;
    }

}
