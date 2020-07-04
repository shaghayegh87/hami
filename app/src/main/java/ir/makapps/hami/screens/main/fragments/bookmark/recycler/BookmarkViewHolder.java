package ir.makapps.hami.screens.main.fragments.bookmark.recycler;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.squareup.picasso.Picasso;

import ir.makapps.hami.R;
import ir.makapps.hami.model.MainBriefModel;
import ir.makapps.hami.screens.base.BaseViewHolder;


public class BookmarkViewHolder extends BaseViewHolder implements BookmarkRecyclerContract.HomeViewHolder {
    private TextView title, description;
    private ImageView img;
    private CardView main;
    private BookmarkRecyclerContract.HomeAdapter presenter;

    public BookmarkViewHolder(final ViewGroup parent, final BookmarkRecyclerContract.HomeAdapter presenter) {
        super(parent, R.layout.home_list_item);
        this.presenter = presenter;
        title = itemView.findViewById(R.id.title_home);
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
        description.setText(data.getDescription());
        if (data.getImage().equals("")) {
            Picasso.get().load(R.drawable.ic_picture).placeholder(R.drawable.ic_picture).into(img);
        } else {
            Picasso.get().load(data.getImage()).into(img);
        }


    }
}
