package dali.oversight.activity.CallArchieve;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Mohamed ali on 07/05/2017.
 */

public class HistoriquePrensenterImpl implements HistoriquePresenter,HistoriqueInteractor.OnFinishedListener{

    private HistoriqueView view;
    private HistoriqueInteractor interactor;

    public HistoriquePrensenterImpl(HistoriqueView view) {
        this.interactor=new HistoriqueInteractorImpl();
        this.view = view;
    }

    @Override
    public void onResume() {

            view.showProgress();
        Log.i("tag1","test");

        interactor.findItems(this);
    }

    @Override
    public void onFinished(ArrayList<?> items) {
        Log.i("tag2","test");
            view.hideProgress();
            view.setItems(items);


    }
}
