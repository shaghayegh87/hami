package ir.makapps.hami.screens.main.fragments.addAdvertise.mvp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import ir.makapps.hami.R;
import ir.makapps.hami.model.ImageModel;

public class AddPictureAdapter extends RecyclerView.Adapter<AddPictureAdapter.ViewHolder> {

    private Context context;
    private List<ImageModel> imageList;
    private callBackInterface callBack;

    public interface callBackInterface {
        void showDialogEditPicture(int position);
    }

    public AddPictureAdapter(Context context, List<ImageModel> imageList, callBackInterface callBack) {
        this.context = context;
        this.imageList = imageList;
        this.callBack = callBack;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView item_picture, close_item, item_center;
        private ConstraintLayout main;
        private TextView txt_image_path;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            close_item = itemView.findViewById(R.id.close);
            item_picture = itemView.findViewById(R.id.picture_add_item);
            item_center = itemView.findViewById(R.id.picture_center_item);
            main = itemView.findViewById(R.id.item_add_main);
//            close_item.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    imageList.remove(getAdapterPosition());
////                    notifyItemRemoved(getAdapterPosition());
//                }
//            });
            main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.showDialogEditPicture(getAdapterPosition());
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.add_picture_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ImageModel model = imageList.get(position);
        holder.item_center.setVisibility(View.GONE);
      if (model.getBitmap() != null) {
            holder.item_picture.setImageBitmap(model.getBitmap());
        } else {
            holder.item_center.setVisibility(View.GONE);
            holder.item_picture.setImageBitmap(BitmapFactory.decodeFile(model.getPath()));
        }


    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }


}
