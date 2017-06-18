package me.bobaikato.app.report;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Author: Bobai Kato
 * Date: 6/18/17
 * Twitter, Instagram, Github, GitLab: @BobaiKato
 * Email: bobai.Kato@gmail.com
 */

public class ReportListAdapter extends ArrayAdapter<Report> {

    private List<Report> reports;


    public ReportListAdapter(Context context, int resource, List<Report> objects) {
        super(context, resource, objects);
        reports = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.mr_chooser_list_item, parent, false);
        }
        Report report = reports.get(position);


        return super.getView(position, convertView, parent);

    }
}
