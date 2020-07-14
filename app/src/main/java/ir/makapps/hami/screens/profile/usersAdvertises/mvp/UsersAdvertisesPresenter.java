package ir.makapps.hami.screens.profile.usersAdvertises.mvp;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.makapps.hami.db.AppDatabase;
import ir.makapps.hami.model.HamiResponse;
import ir.makapps.hami.model.MainBriefModel;
import ir.makapps.hami.model.MainModel;
import ir.makapps.hami.model.NoteDetailModel;
import ir.makapps.hami.screens.main.fragments.note.NoteDao;
import ir.makapps.hami.screens.profile.usersAdvertises.recycler.UsersAdvertiseViewHolder;
import ir.makapps.hami.utils.Utils;

public class UsersAdvertisesPresenter implements UsersAdvertisesContract.Presenter {
    List<MainBriefModel> lstAdvertisesUser;
    UsersAdvertisesContract.View view;
    UsersAdvertisesContract.Model model;
    CompositeDisposable compositeDisposable;
    private String token = Utils.getToken();
    private AppDatabase appDatabase;
    private NoteDao noteDao;

    public UsersAdvertisesPresenter(UsersAdvertisesContract.View view, UsersAdvertisesContract.Model model, CompositeDisposable compositeDisposable) {
        this.view = view;
        this.model = model;
        this.compositeDisposable = compositeDisposable;
        lstAdvertisesUser = new ArrayList<>();
    }

    @Override
    public void attachView(UsersAdvertisesContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
        if (compositeDisposable.size() > 0 && compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    @Override
    public boolean getInternet() {
        return Utils.isConnected();
    }

    @Override
    public void getUsersAdvertise(String token) {
        view.ShowProgressbar();
        model.GetUserMains(token).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<HamiResponse<List<MainBriefModel>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(HamiResponse<List<MainBriefModel>> listHamiResponse) {
                        if (listHamiResponse.getResultCode() == 1) {
                            lstAdvertisesUser = listHamiResponse.getResults();
                            view.HideProgressbar();
                            view.showListOfAdvertises();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.HideProgressbar();
                        view.showError(Utils.errorInApp);
                    }
                });
    }

    @Override
    public int getAdvertisesUserRowsCount() {
        return lstAdvertisesUser.size();
    }

    @Override
    public void bindAdvertisesUserViewHolder(int position, UsersAdvertiseViewHolder holder) {
        MainBriefModel model = lstAdvertisesUser.get(position);
        holder.fillData(model);
    }

    @Override
    public void sendToDetailActivity(int id) {

    }
}
