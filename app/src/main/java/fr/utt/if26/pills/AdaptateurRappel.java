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

public class AdaptateurRappel extends ArrayAdapter<Rappel> {
    List<Rappel> list;
    Context context;
    int ressource;


    public AdaptateurRappel(@NonNull Context context, int resource, @NonNull List<Rappel> objects) {
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
        TextView tv_rappel_medicament = (TextView) v.findViewById(R.id.rappel_medicament);
        TextView tv_rappel_heure = (TextView) v.findViewById(R.id.rappel_heure);
        TextView tv_rappel_repetition= (TextView) v.findViewById(R.id.rappel_repetition);

        Medicament med = new Medicament();

        MedicamentPersistance persistance = new MedicamentPersistance(context, "pills.db", null, 1);
        persistance.initData();

        med = persistance.getMedicament(elt.getId_med());

        tv_rappel_medicament.setText("Rappel " + med.getNom());
        tv_rappel_heure.setText(elt.getHeure());
        tv_rappel_repetition.setText(elt.convertirRepetition());

        return v;
    }
}
