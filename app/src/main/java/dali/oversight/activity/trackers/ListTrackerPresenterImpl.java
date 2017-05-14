package dali.oversight.activity.trackers;

import java.util.ArrayList;

import dali.oversight.data.friend;

/**
 * Created by Mohamed ali on 08/05/2017.
 */

public class ListTrackerPresenterImpl implements ListTrackerPresenter,ListTrackerInteractor.OnFinishedListener {

   private  ListTrackerView trackerView;
    private ListTrackerInteractor interactor;


    public ListTrackerPresenterImpl(ListTrackerView trackerView) {
        this.interactor=new ListTrackerInteractorImpl();
        this.trackerView = trackerView;
    }

    @Override
    public void onResume() {
        trackerView.showProgress();
        interactor.findItems(this);
    }


    @Override
    public void BeginTraka(friend f) {
            interactor.BeginFriendTraka(f);
            //trackerView.navigateToHome();
    }

    @Override
    public void SetFriendTraka(friend f) {

    }


    @Override
    public void onFinished(ArrayList<?> items) {
            trackerView.hideProgress();
        if(!items.isEmpty()){
            trackerView.setItems(items);
        }
    }


}
