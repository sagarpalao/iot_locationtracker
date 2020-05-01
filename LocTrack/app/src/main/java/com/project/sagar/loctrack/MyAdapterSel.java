package com.project.sagar.loctrack;

/**
 * Created by SAGAR on 1/2/2016.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapterSel extends ArrayAdapter<PlaceD> {

    private final java.util.ArrayList<PlaceD> mData;
    android.app.Activity context;


    static class ViewHolder {
        public TextView txtplace;
        public TextView txtaddr;
    }


    public MyAdapterSel(android.app.Activity context, java.util.ArrayList<PlaceD> myDataset) {
        super(context, com.project.sagar.loctrack.R.layout.list_itemsel, myDataset);
        this.context = context;
        this.mData = myDataset;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(com.project.sagar.loctrack.R.layout.list_itemsel, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.txtplace = (TextView) rowView.findViewById(com.project.sagar.loctrack.R.id.selPlace);
            viewHolder.txtaddr = (android.widget.TextView) rowView.findViewById(com.project.sagar.loctrack.R.id.selAddress);
            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.txtplace = (TextView) rowView.findViewById(com.project.sagar.loctrack.R.id.selPlace);
        holder.txtaddr = (TextView) rowView.findViewById(com.project.sagar.loctrack.R.id.selAddress);
        holder.txtplace.setText(mData.get(position).place);
        holder.txtaddr.setText(mData.get(position).addr);

        return rowView;
    }
}

