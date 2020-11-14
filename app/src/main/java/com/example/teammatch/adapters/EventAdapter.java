package com.example.teammatch.adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.teammatch.R;
import com.example.teammatch.objects.Evento;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private List<Evento> mItems = new ArrayList<Evento>();
    private final OnItemClickListener listener;

    public EventAdapter(OnItemClickListener listener) { this.listener = listener;}

    public interface OnItemClickListener {
        void onItemClick(Evento item);
    }


    // Create new views (invoked by the layout manager)
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.evento,parent,false);

       return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mItems.get(position),listener);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void add(Evento item) {
        mItems.add(item);
        notifyDataSetChanged();
    }

    public void load(List<Evento> items){
        mItems.clear();
        mItems = items;
        notifyDataSetChanged();
    }

    public void clear(){
        mItems.clear();
        notifyDataSetChanged();
    }

    public Object getItem(int pos) {
        return mItems.get(pos);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nombreView;
        private TextView fechaView;
        private TextView participantesView;
        private TextView descripcionView;
        private TextView deporteView;
        private TextView pistaEventoView;

        public ViewHolder(View itemView) {
            super(itemView);
            nombreView = itemView.findViewById(R.id.nomEvento);
            fechaView = itemView.findViewById(R.id.fechaEvento);
            participantesView = itemView.findViewById(R.id.participantesEvent);
            descripcionView = itemView.findViewById(R.id.descEvento);
            deporteView = itemView.findViewById(R.id.deporteEvento);
            pistaEventoView = itemView.findViewById(R.id.idPistaDeEvento);
        }

        public void bind(final Evento evento, final OnItemClickListener listener) {

            nombreView.setText(evento.getNombre());

            participantesView.setText(evento.getParticipantes().toString());

            descripcionView.setText(evento.getDescripcion());

            deporteView.setText(evento.getDeporte().toString());

            fechaView.setText(evento.FORMAT.format(evento.getFecha()));

            pistaEventoView.setText(evento.getPista());


            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(evento);
                }
            });
        }
    }

}