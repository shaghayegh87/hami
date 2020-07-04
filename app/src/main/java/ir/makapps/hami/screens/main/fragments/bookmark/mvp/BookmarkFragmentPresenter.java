package ir.makapps.hami.screens.main.fragments.bookmark.mvp;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.makapps.hami.model.HamiResponse;
import ir.makapps.hami.model.MainBriefModel;
import ir.makapps.hami.screens.main.fragments.bookmark.recycler.BookmarkRecyclerContract;
import ir.makapps.hami.utils.Utils;

public class BookmarkFragmentPresenter implements BookmarkFragmentContract.Presenter {

    List<MainBriefModel> hamiMainModelList;

    BookmarkFragmentContract.View view;
    CompositeDisposable compositeDisposable;
    BookmarkFragmentContract.Model model;


    public BookmarkFragmentPresenter(BookmarkFragmentContract.View view, BookmarkFragmentContract.Model model,
                                     CompositeDisposable compositeDisposable) {
        this.model = model;
        this.view = view;
        this.compositeDisposable = compositeDisposable;
        hamiMainModelList = new ArrayList<>();
    }

    @Override
    public void attachView(BookmarkFragmentContract.View view) {
        this.view = view;
        view.showProgressBar();
        if (getInternet()) {
//            getMainList(0, "", 1);
        } else {
            view.showNoContent();
            view.showError(Utils.errorInternet);
            view.hideProgressBar();
            view.showNoContent();
        }
    }

    @Override
    public void refreshBookmarkList() {
        if (getInternet()) {
            getMainList(0, "", 1);
            view.swipeRefresh(false);
            view.hideProgressBar();
        } else {
            view.showError(Utils.errorInternet);
            view.swipeRefresh(false);
            view.hideProgressBar();
        }
    }


    @Override
    public int getHomeRowsCount() {
        return hamiMainModelList.size();
    }


    @Override
    public boolean getInternet() {
        return Utils.isConnected();
    }

    @Override
    public void bindHomeViewHolder(int position, BookmarkRecyclerContract.HomeViewHolder holder) {
        MainBriefModel model = hamiMainModelList.get(position);
        holder.fillData(model);
    }

    @Override
    public void sendToDetailActivity(int id) {
        view.sendDataToDetail(id);
    }

    @Override
    public void detachView() {
        this.view = null;
    }


    @Override
    public void getMainList(int stateId, String desc, int pageIndex) {
        model.getBookmarkList().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<HamiResponse<List<MainBriefModel>>>() {
                               @Override
                               public void onSubscribe(Disposable d) {
                                   compositeDisposable.add(d);
                               }

                               @Override
                               public void onSuccess(HamiResponse<List<MainBriefModel>> listHamiResponse) {
                                   if(listHamiResponse.getResultCode() == 1)
                                   {
                                       hamiMainModelList = listHamiResponse.getResults();
                                       view.refreshMainList();
                                       if(hamiMainModelList.size() == 0)
                                       {
                                           view.showNoContent();
                                       }
                                       view.hideProgressBar();
                                   }
                                   else if(!listHamiResponse.getMessage().equals(""))
                                   {
                                       view.showError(listHamiResponse.getMessage());
                                       view.refreshMainList();
                                       view.hideProgressBar();
                                   }

                               }

                               @Override
                               public void onError(Throwable e) {
                                   view.showError(Utils.errorInApp);
                                   view.showProgressBar();
                               }
                           }
                );
    }
}
