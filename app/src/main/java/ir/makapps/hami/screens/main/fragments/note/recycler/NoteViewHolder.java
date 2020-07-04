package ir.makapps.hami.screens.main.fragments.note.recycler;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.squareup.picasso.Picasso;

import ir.makapps.hami.R;
import ir.makapps.hami.model.MainBriefModel;
import ir.makapps.hami.model.NoteModel;
import ir.makapps.hami.screens.base.BaseViewHolder;


public class NoteViewHolder extends BaseViewHolder implements NoteRecyclerContract.HomeViewHolder {
    private TextView title, description;
    private ImageView img;
    private CardView main;
    private NoteRecyclerContract.HomeAdapter presenter;

    public NoteViewHolder(final ViewGroup parent, final NoteRecyclerContract.HomeAdapter presenter) {
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
    public void fillData(final NoteModel data) {
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.sendToDetailActivity(data.getId());
            }
        });

        title.setText(data.getId()+"");
        description.setText(data.getText());
//        if (data.getImage().equals("")) {
//            Picasso.get().load(R.drawable.ic_picture).placeholder(R.drawable.ic_picture).into(img);
//        } else {
//            Picasso.get().load(data.getImage()).into(img);
//        }


    }
}
