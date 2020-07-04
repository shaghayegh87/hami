package ir.makapps.hami.screens.createNote.mvp;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.makapps.hami.model.HamiResponse;
import ir.makapps.hami.model.MainModel;
import ir.makapps.hami.model.NoteModel;
import ir.makapps.hami.screens.main.fragments.note.NoteDao;
import ir.makapps.hami.utils.Utils;

public class CreateNotePresenter implements CreateNoteContract.Presenter {
    CreateNoteContract.View view;
    CreateNoteContract.Model model;
    CompositeDisposable compositeDisposable;

    private String token = Utils.getToken();

    public CreateNotePresenter(CreateNoteContract.View view, CreateNoteContract.Model model, CompositeDisposable compositeDisposable) {
        this.view = view;
        this.model = model;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void attachView(CreateNoteContract.View view) {
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
    public void updateDetail(int id) {

        model.getDetail(token, id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<HamiResponse<MainModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(HamiResponse<MainModel> modelDetail) {
                        if (modelDetail.getResultCode() == 1) {
                            MainModel model = modelDetail.getResults();
                            view.showDetail(model);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(Utils.errorInApp);
                    }
                });
    }

    @Override
    public void saveBookmark(final int id) {
        view.ShowProgressbar();
        model.saveBookmarkToDB(token, id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<HamiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(HamiResponse hamiResponse) {
                        if (hamiResponse.getResultCode() == 1) {
                            view.changeNoteInModel(true);
                            view.HideProgressbar();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.changeNoteInModel(false);
                        view.HideProgressbar();
                    }
                });

    }

    @Override
    public void deleteBookmark(final int id) {
        model.deleteBookmarkToDB(token, id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<HamiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(HamiResponse hamiResponse) {
                        if (hamiResponse.getResultCode() == 1) {
                            view.changeNoteInModel(false);
                            view.HideProgressbar();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.changeNoteInModel(false);
                        view.HideProgressbar();
                    }
                });
    }

    @Override
    public void updateNote(int id, NoteDao noteDao) {
        NoteModel model = noteDao.edit(id) != null ?  noteDao.edit(id) : new NoteModel();
        String note = model.getText();
        view.showNote(note);
    }
}
