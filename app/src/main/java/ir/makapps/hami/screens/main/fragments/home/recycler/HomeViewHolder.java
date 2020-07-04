package ir.makapps.hami.screens.main.fragments.home.recycler;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import com.squareup.picasso.Picasso;
import ir.makapps.hami.R;
import ir.makapps.hami.model.MainBriefModel;
import ir.makapps.hami.screens.base.BaseViewHolder;


public class HomeViewHolder extends BaseViewHolder implements HomeRecyclerContract.HomeViewHolder {
    private TextView title, description,city;
    private ImageView img;
    private CardView main;
    private HomeRecyclerContract.HomeAdapter presenter;

    public HomeViewHolder(final ViewGroup parent, final HomeRecyclerContract.HomeAdapter presenter) {
        super(parent, R.layout.home_list_item);
        this.presenter = presenter;
        title = itemView.findViewById(R.id.title_home);
        city = itemView.findViewById(R.id.city_home);
        img = itemView.findViewById(R.id.img_banner);
        main = itemView.findViewById(R.id.card_main);
        description = itemView.findViewById(R.id.desc_home);

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    @Override
    public void fillData(final MainBriefModel data) {
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.sendToDetailActivity(data.getId());
            }
        });

        title.setText(data.getTitle());
        city.setText(data.getStateName());
        description.setText(data.getDescription());
        if (data.getImage().equals("")) {
            Picasso.get().load(R.drawable.item).placeholder(R.drawable.item).into(img);
        } else {
            Picasso.get().load(data.getImage()).into(img);
        }


    }
}
