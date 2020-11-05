package com.example.teammatch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private final List<Evento> mItems = new ArrayList<Evento>();


    public interface OnItemClickListener {
        void onItemClick(Evento item);     //Type of the element to be returned
    }

    private final OnItemClickListener listener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public EventAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.evento,parent,false); //TODO Es posible que haya que cambiar el layout del fragment por el layout de un evento en particular

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

        public ViewHolder(View itemView) {
            super(itemView);
            nombreView = itemView.findViewById(R.id.nomEvento);
            fechaView = itemView.findViewById(R.id.fechaEvento);
            participantesView = itemView.findViewById(R.id.participantesEvent);
            descripcionView = itemView.findViewById(R.id.descEvento);
            deporteView = itemView.findViewById(R.id.deporteEvento);
        }

        public void bind(final Evento evento, final OnItemClickListener listener) {

            nombreView.setText(evento.getmNombre());

            participantesView.setText(evento.getmParticipantes());

            descripcionView.setText(evento.getmDescripcion());

            deporteView.setText(evento.getmDeporte().toString());

            // Hint - use ToDoItem.FORMAT.format(toDoItem.getDate()) to get date and time String
            fechaView.setText(evento.FORMAT.format(evento.getmFecha()));


            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(evento);
                }
            });
        }
    }

}