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

public class AdaptateurDataMedicament extends ArrayAdapter<Medicament> {
    List<Medicament> list;
    Context context;
    int ressource;

    public AdaptateurDataMedicament(@NonNull Context context, int resource, @NonNull List<Medicament> objects) {
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
        TextView tv_data_med_id= (TextView) v.findViewById(R.id.data_tv_medicine_id);
        TextView tv_data_med_nom = (TextView) v.findViewById(R.id.data_tv_medicine_nom);
        TextView tv_data_med_fabricant= (TextView) v.findViewById(R.id.data_tv_medicine_fabricant);
        TextView tv_data_med_type= (TextView) v.findViewById(R.id.data_tv_medicine_type);
        TextView tv_data_med_stock= (TextView) v.findViewById(R.id.data_tv_medicine_stock);

        tv_data_med_id.setText(new String(String.valueOf(elt.getId())));
        tv_data_med_nom.setText(elt.getNom());
        tv_data_med_fabricant.setText(elt.getFabricant());
        tv_data_med_type.setText(elt.getType());
        tv_data_med_stock.setText(new String(String.valueOf(elt.getStock())));

        return v;
    }
}
