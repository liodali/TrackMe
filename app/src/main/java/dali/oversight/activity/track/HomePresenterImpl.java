package dali.oversight.activity.track;

/**
 * Created by Mohamed ali on 06/05/2017.
 */

public class HomePresenterImpl implements HomePresenter,HomeInteractor.OnMakeCallFinishedListener {

    private HomeView homeView;
    private HomeInteractor interactor;


    public HomePresenterImpl(HomeView view){
        this.homeView=view;
        interactor=new HomeInteractorImpl();
    }


    @Override
    public void Calls(String number) {
            interactor.ValidateCall(number,this);
    }

    @Override
    public void SmsTrack() {

    }

    @Override
    public void onError() {
            String msg="Number Phone invalid";
            homeView.showSnackBar(msg);
    }

    @Override
    public void onSuccess(String phone) {
            homeView.MakeCall(phone);
    }
}
