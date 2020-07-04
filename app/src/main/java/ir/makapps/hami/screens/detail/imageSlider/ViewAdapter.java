package ir.makapps.hami.screens.detail.imageSlider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.makapps.hami.R;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private ViewPager2 viewPager2;
    private Context context;


//    private int[] colorArray = new int[]{android.R.color.black, android.R.color.holo_blue_dark, android.R.color.holo_green_dark, android.R.color.holo_red_dark};

    public ViewAdapter(Context context, List<String> data, ViewPager2 viewPager2) {
//        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = data;
        this.viewPager2 = viewPager2;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String path = mData.get(position);
//        holder.relativeLayout.setBackgroundResource(colorArray[position]);
        Picasso.get().load(path).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ConstraintLayout constraintLayout;

        ViewHolder(View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.container);
            imageView = itemView.findViewById(R.id.img_pager);

//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                    if(viewPager2.getOrientation() == ViewPager2.ORIENTATION_VERTICAL)
//                        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
//                    else{
//                        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
//                    }
//                }
//            });
        }
    }

}
