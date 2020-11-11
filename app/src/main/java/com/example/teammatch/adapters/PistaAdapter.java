package com.example.teammatch.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.teammatch.R;
import com.example.teammatch.activities.PistasActivity;
import com.example.teammatch.objects.Pistum;

import java.util.List;

public class PistaAdapter extends RecyclerView.Adapter<PistaAdapter.MyViewHolder> {
    private List<Pistum> mDataset;

    public interface OnListInteractionListener{
        public void onListInteraction(String url);
    }

    public OnListInteractionListener mListener;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mNombrePista;
        public View mView;

        public Pistum mItem;

        public MyViewHolder(View v) {
            super(v);
            mView=v;
            mNombrePista = v.findViewById(R.id.idNombrePista);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PistaAdapter(List<Pistum> myDataset, PistasActivity listener) {
        mDataset = myDataset;
      //  mListener = listener;
    }

    @Override
    public PistaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        // Create new views (invoked by the layout manager)
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pista, parent, false);

        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.mItem = mDataset.get(position);
        holder.mNombrePista.setText(mDataset.get(position).getFoafName().getValue());

       /* holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListInteraction(holder.mItem.getUri().getValue());

                }
            }
        });*/

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void swap(List<Pistum> dataset){
        mDataset = dataset;
        notifyDataSetChanged();
    }
}