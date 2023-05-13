package etienne.clement.ecm_phone_shop.controlers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import etienne.clement.ecm_phone_shop.R;

/**
 * created by ETIENNE CLEMENT MBAYE on 25-03-2022.
 */
public class CtlApropos extends ArrayAdapter<String> {
    public CtlApropos(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v;
        LayoutInflater mlayout = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = mlayout.inflate(R.layout.model_a_propos, null);
        ImageView imageView =v.findViewById(R.id.imageViewDev);
        imageView.setImageResource(R.drawable.fantom);
        return v;
    }
}
