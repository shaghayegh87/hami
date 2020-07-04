package ir.makapps.hami.screens.main.fragments.addAdvertise.mvp;

import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import de.mrapp.android.dialog.MaterialDialog;
import ir.makapps.hami.R;
import ir.makapps.hami.model.CityModel;


public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private List<CityModel> citiesList;
    private Context context;
    private MaterialDialog materialDialog;
    private callBackInterface callBack;

    public interface callBackInterface {
        void setCityName(String name,int id);
    }

    public CityAdapter(List<CityModel> citiesList, Context context, MaterialDialog materialDialog, callBackInterface callBack) {
        this.citiesList = citiesList;
        this.context = context;
        this.materialDialog = materialDialog;
        this.callBack = callBack;
    }

    class CityViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ConstraintLayout item_city;

        public CityViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.city_name);
            item_city = view.findViewById(R.id.item_city);

        }
    }


    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_list_item, parent, false);
        return new CityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        final CityModel model = citiesList.get(position);
        holder.name.setText(model.getName());
        holder.item_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.setCityName(model.getName(),model.getId());
                materialDialog.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
        return citiesList.size();
    }
}