package com.project.sagar.loctrack;

import java.util.ArrayList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyAdapterNo extends RecyclerView.Adapter<MyAdapterNo.ViewHolder> {
    private ArrayList<PlaceD> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtplace;
        public TextView txtaddr;

        public ViewHolder(View v) {
            super(v);
            txtplace = (TextView) v.findViewById(R.id.notextPlace);
            txtaddr = (TextView) v.findViewById(R.id.notextAddress);
        }
    }

    public void add(int position, PlaceD item){
        mDataset.add(position,item );
        notifyItemInserted(position);
    }

    public void remove(String item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapterNo(ArrayList<PlaceD> myDataset)
    {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapterNo.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_itemno, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
// - replace the contents of the view with that element
        //final String name = mDataset.get(position);

        holder.txtplace.setText(mDataset.get(position).place);
        holder.txtaddr.setText(mDataset.get(position).addr);

    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}