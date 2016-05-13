package com.smkpgri2.alaska.adapater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smkpgri2.alaska.activity.R;
import com.smkpgri2.alaska.entity.Complaint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by smkpgri2 on 12/05/16.
 */
public class ComplaintAdapter extends BaseAdapter {
    private List<Complaint> complaints = new ArrayList<Complaint>();
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");

    private Context context;

    private LayoutInflater inflater;


    public ComplaintAdapter(Context context, List<Complaint> newses){
        this.context = context;
        this.complaints = complaints;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return complaints.size();
    }

    @Override
    public Object getItem(int position) {
        return complaints.get(position);
    }

    @Override
    public long getItemId(int id) {
        return 0;
    }

    public void addNews(List<Complaint> categories){
        this.complaints.addAll(categories);
        notifyDataSetChanged();
    }

    public void clear(){
        complaints.clear();
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if(view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.adapter_complaint, viewGroup, false);

            holder.name = (TextView)view.findViewById(R.id.text_title);
            holder.description = (TextView)view.findViewById(R.id.textContent);
            holder.date = (TextView)view.findViewById(R.id.text_date);
            holder.category = (TextView) view.findViewById(R.id.text_category);

            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        holder.name.setText(complaints.get(position).getTitle());
        holder.description.setText(complaints.get(position).getMessage());
        holder.date.setText(dateFormat.format(complaints.get(position).getLogInformation().getCreateDate()));
        holder.category.setText(complaints.get(position).getCategory().getName());

        return view;
    }


    private class ViewHolder {
        public TextView name;
        public TextView description;
        public TextView date;
        public TextView category;
    }
}

