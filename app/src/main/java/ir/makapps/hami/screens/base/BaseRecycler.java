package ir.makapps.hami.screens.base;

public interface BaseRecycler {

    interface BaseHolder<T> {

        void fillData(T model);
    }

}