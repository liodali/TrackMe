package dali.oversight.activity.track;

/**
 * Created by Mohamed ali on 06/05/2017.
 */

public interface HomeView {

    void makeCallDialog();

    void MakeCall(String phone);


    void showSnackBar(String msg);


    void navigateToGpsTrack();
    void navigateToSmsGpsTrack();

}
