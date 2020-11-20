package com.example.teammatch.adapters;

import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.teammatch.R;
import com.example.teammatch.objects.Evento;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private List<Evento> listaEventos = new ArrayList<>();
    private List<Evento> listaEventosAux = new ArrayList<>();
    private List<Evento> listaEventosFiltrados = new ArrayList<>();
    private final OnItemClickListener listener;

    public EventAdapter(OnItemClickListener listener) { this.listener = listener;}

    public interface OnItemClickListener {
        void onItemClick(Evento item);
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.evento,parent,false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(listaEventos.get(position),listener);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listaEventos.size();
    }

    public void add(Evento item) {
        listaEventos.add(item);
        notifyDataSetChanged();
    }

    public void load(List<Evento> items){
        listaEventos.clear();
        listaEventos = items;
        listaEventosAux.clear();
        listaEventosAux.addAll(listaEventos);
        notifyDataSetChanged();
    }

    public void clear(){
        listaEventos.clear();
        notifyDataSetChanged();
    }


    //BUSCADOR
    public void cargarListaFiltrado(){
        listaEventosFiltrados.clear();
        listaEventosFiltrados.addAll(listaEventos);
        notifyDataSetChanged();
    }

   public List<Evento> filtrado(String palabra){
        if(palabra.isEmpty()){
            listaEventosFiltrados.clear();
            listaEventosFiltrados.addAll(listaEventosAux);
        }else{
            listaEventosFiltrados.clear();
            for(Evento evento : listaEventosAux){
                if(evento.getNombre().toLowerCase().contains(palabra.toLowerCase())){
                    listaEventosFiltrados.add(evento);
                }
            }
        }
        notifyDataSetChanged();
        return listaEventosFiltrados;
    }

    public void loadBuscardor(List<Evento> items){
        listaEventos.clear();
        listaEventos = items;
        notifyDataSetChanged();
    }

    public Object getItem(int pos) {
        return listaEventos.get(pos);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nombreView;
        private TextView fechaView;
        private TextView deporteView;
        private ImageView imgView;

        public ViewHolder(View itemView) {
            super(itemView);
            nombreView = itemView.findViewById(R.id.nomEvento);
            fechaView = itemView.findViewById(R.id.fechaEvento);
            deporteView = itemView.findViewById(R.id.deporteEvento);
            imgView = itemView.findViewById(R.id.item_imageEvent);

        }

        public void bind(final Evento evento, final OnItemClickListener listener) {

            nombreView.setText(evento.getNombre());

            deporteView.setText(evento.getDeporte().toString());

            fechaView.setText(evento.FORMAT.format(evento.getFecha()));


            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(evento);
                }
            });
        }
    }

}