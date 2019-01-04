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

public class AdaptateurMedicament extends ArrayAdapter<Medicament> {
    List<Medicament> list;
    Context context;
    int ressource;

    public AdaptateurMedicament(@NonNull Context context, int resource, @NonNull List<Medicament> objects) {
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

        Medicament elt = list.get(position);
        //TextView tv_med_id= (TextView) v.findViewById(R.id.medicament_id);
        TextView tv_med_nom = (TextView) v.findViewById(R.id.medicament_nom);
        TextView tv_med_fabricant= (TextView) v.findViewById(R.id.medicament_fabricant);
        //TextView tv_med_type= (TextView) v.findViewById(R.id.medicament_type);
       // TextView tv_med_stock= (TextView) v.findViewById(R.id.medicament_stock);

        ////tv_med_id.setText(new String(String.valueOf(elt.getId())));
        tv_med_nom.setText(elt.getNom());
        tv_med_fabricant.setText(elt.getFabricant());
        //tv_med_type.setText(elt.getType());
        //tv_med_stock.setText(new String(String.valueOf(elt.getStock())));

        return v;
    }
}
