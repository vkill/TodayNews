package com.kx.todaynews.base;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {
    protected V mView;
    private CompositeDisposable compositeDisposable;
    @Override
    public void attachView(V view) {
        this.mView = view;
    }

    public V getView() {
        return mView;
    }

    @Override
    public void detachView() {
        if (mView!=null){
            mView =null;
        }
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    @Override
    public void addRxSubscribe(Disposable disposable) {
        if (compositeDisposable==null){
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }
}
