package ir.makapps.hami.screens.main.fragments.home.mvp;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.makapps.hami.model.HamiResponse;
import ir.makapps.hami.model.MainBriefModel;
import ir.makapps.hami.screens.main.fragments.home.recycler.HomeRecyclerContract;
import ir.makapps.hami.utils.Utils;

public class HomeFragmentPresenter implements HomeFragmentContract.Presenter {

    List<MainBriefModel> hamiMainModelList;

    HomeFragmentContract.View view;
    CompositeDisposable compositeDisposable;
    HomeFragmentContract.Model model;


    public HomeFragmentPresenter(HomeFragmentContract.View view, HomeFragmentContract.Model model,
                                 CompositeDisposable compositeDisposable) {
        this.model = model;
        this.view = view;
        this.compositeDisposable = compositeDisposable;
        hamiMainModelList = new ArrayList<>();
    }

    @Override
    public void attachView(HomeFragmentContract.View view) {
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
    public void refreshMainList() {
        view.showProgressBar();;
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
        return Utils.networkInfo();
    }

    @Override
    public void bindHomeViewHolder(int position, HomeRecyclerContract.HomeViewHolder holder) {
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
        model.getMainList(stateId, desc, pageIndex).subscribeOn(Schedulers.newThread())
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
                                       view.hideProgressBar();
                                       view.refreshMainList();
                                   }
                                   else if(!listHamiResponse.getMessage().equals(""))
                                   {
                                       view.showError(listHamiResponse.getMessage());
                                       view.hideProgressBar();
                                       view.refreshMainList();
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
