package fr.utt.if26.pills;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AdaptateurDataRappel extends ArrayAdapter<Rappel> {
    List<Rappel> list;
    Context context;
    int ressource;

    public AdaptateurDataRappel(@NonNull Context context, int resource, @NonNull List<Rappel> objects) {
        super(context, resource, objects);
        this.list = objects;
        this.context = context;
        this.ressource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View v = inflater.inflate(ressource, parent, false);

        Rappel elt = list.get(position);
        TextView tv_data_rappel_id= (TextView) v.findViewById(R.id.data_tv_rappel_id);
        TextView tv_data_rappel_med_id= (TextView) v.findViewById(R.id.data_tv_rappel_med_id);
        TextView tv_data_rappel_heure = (TextView) v.findViewById(R.id.data_tv_rappel_heure);
        TextView tv_data_rappel_repetition= (TextView) v.findViewById(R.id.data_tv_rappel_repetition);
        TextView tv_data_rappel_statut= (TextView) v.findViewById(R.id.data_tv_rappel_statut);
        TextView tv_data_rappel_dernier_rappel= (TextView) v.findViewById(R.id.data_tv_rappel_dernier_rappel);

        tv_data_rappel_id.setText(new String(String.valueOf(elt.getId_rappel())));
        tv_data_rappel_med_id.setText(new String(String.valueOf(elt.getId_med())));
        tv_data_rappel_heure.setText(elt.getHeure());
        tv_data_rappel_repetition.setText(new String(String.valueOf(elt.getRepetition())));
        tv_data_rappel_statut.setText(new String(String.valueOf(elt.getStatut())));
        tv_data_rappel_dernier_rappel.setText(elt.getProchain_rappel());


        return v;
    }
}
