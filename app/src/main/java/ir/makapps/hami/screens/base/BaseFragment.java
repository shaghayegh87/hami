package ir.makapps.hami.screens.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    public View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {

            rootView = inflater.inflate(getFragmentLayout(), container, false);
        }
//        injectDagger();
//        bindViews();
//        getData();
        return rootView;
    }



    @Override
    public void onStart() {
        super.onStart();
        injectDagger();
        bindViews();
        getData();
//        getData();
//        injectDagger();
//        bindViews();

    }

    public abstract void getData();
    public abstract int getFragmentLayout();
    public abstract void bindViews();
    public abstract void injectDagger();

}
