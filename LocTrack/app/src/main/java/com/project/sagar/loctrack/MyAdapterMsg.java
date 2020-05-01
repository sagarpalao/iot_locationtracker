package com.project.sagar.loctrack;

import static com.project.sagar.loctrack.CommonUtilities.TAG;

import java.util.ArrayList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.*;
import com.google.android.gms.location.places.*;
import com.google.android.gms.common.api.*;


public class MyAdapterMsg extends RecyclerView.Adapter<MyAdapterMsg.ViewHolder> {
    private ArrayList<MsgD> mDataset;
    static int pos;
    ImageView mimgloc;
    GoogleApiClient mGoogleApiClient;
    Thread worker;
    android.content.Context mContext;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtloc;
        public TextView txtmsg;
        public ImageView imgloc, imgdel, imgedit;


        public ViewHolder(View v) {
            super(v);
            txtloc = (TextView) v.findViewById(R.id.txtplace);
            txtmsg = (TextView) v.findViewById(R.id.txtmsg);


        }
    }

    public void add(int position, MsgD item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapterMsg(ArrayList<MsgD> myDataset, GoogleApiClient client, android.content.Context context) {
        mDataset = myDataset;
        mGoogleApiClient=client;
        mContext=context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapterMsg.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_msg, parent, false);
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
        String loc=mDataset.get(position).txtloc;
        String msg=mDataset.get(position).txtmsg;
        String placeId=mDataset.get(position).placeid;
        //String imgurl=mDataset.get(position).imgurl;

        if(loc!=null && msg!=null && placeId!=null) {
            holder.txtloc.setText(loc);
            holder.txtmsg.setText(msg);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }



}