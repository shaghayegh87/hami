package ir.makapps.hami.screens.main;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import ir.makapps.hami.R;

public class BottomSheet extends BottomSheetDialogFragment {
    private bottomSheetListner listner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet, container, false);
        Button btnAdvertise = v.findViewById(R.id.btn_selected_advertise);
        Button btnHami = v.findViewById(R.id.btn_selected_hami);
        btnAdvertise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listner.changeActivity("hiiiiiiiiiii");
                dismiss();
            }
        });
        return v;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        Log.d("ffffffff", "dismiss: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("ffffffff", "onResume: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("ffffffff", "onDestroyView: ");
        listner.changeActivity(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listner = (bottomSheetListner) context;
    }

    public interface bottomSheetListner {
        public void changeActivity(Boolean isAlive);
    }
}
