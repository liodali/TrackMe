package dali.oversight.activity.gpstrack;

import android.content.Context;

import dali.oversight.data.Gps;
import dali.oversight.data.friend;

/**
 * Created by Mohamed ali on 11/05/2017.
 */

public class GPSPresenterImpl implements GPSPresenter,GPSInteractor.OnFinishedListener,GPSInteractor.OnTrakaFinishedListener {

    private GPSView view;
    private GPSInteractor interactor;
    private Context context;

    public GPSPresenterImpl(GPSView view,Context context) {
        this.interactor=new GPSInteractorImpl();
        this.view = view;
        this.context=context;
    }

    @Override
    public void ReTrack() {

    }

    @Override
    public void CheckGoogleService(Context context) {
            interactor.googleServiceAvaible(context,this);
    }

    @Override
    public void GetPosTraka(friend f) {
        view.showProgress();
            interactor.TrakaPosition(f,this);
    }


    @Override
    public void onError() {

    }

    @Override
    public void onSuccess() {
        view.initMap();
    }

    @Override
    public void onTrakaError() {

    }

    @Override
    public void onTrakaSuccess(Gps g) {
        view.hideProgress();
            view.SetPos(g);
    }
}
