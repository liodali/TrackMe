package dali.oversight.activity.main;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Mohamed ali on 06/05/2017.
 */

public class MainPresenterImpl implements MainPresenter,MainInteractor.OnLoginListener {

    private MainView view;
    private MainInteractor interactor;
    private FirebaseAuth auth;
    public MainPresenterImpl(MainView view, FirebaseAuth auth) {
        this.view = view;
        this.auth=auth;
        this.interactor= new MainInteractorImpl();
    }

    @Override
    public void ValideUser(Context context) {
        FirebaseUser user=auth.getCurrentUser();
        interactor.checkUserLogIn(context,user,this);
    }

    @Override
    public void logOn() {
            view.navigateToHome();
    }

    @Override
    public void logOff() {
        view.navigateToLogin();
    }
}
