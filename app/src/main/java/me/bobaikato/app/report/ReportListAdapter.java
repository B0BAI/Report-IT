package me.bobaikato.app.report;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Author: Bobai Kato
 * Date: 6/18/17
 * Twitter, Instagram, Github, GitLab: @BobaiKato
 * Email: bobai.Kato@gmail.com
 */

public class ReportListAdapter extends ArrayAdapter<Report> {
    private ArrayList<Report> reports;

    public ReportListAdapter(Context context, int resource, ArrayList<Report> objects) {
        super(context, resource, objects);
        reports = objects;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.mr_chooser_list_item, parent, false);
        }
        Report report = reports.get(position);
        //TextView address = (TextView) convertView.findViewById(R.id.report_add);

        //address.setText(report.getADDRESS());
        return convertView;

    }
}
