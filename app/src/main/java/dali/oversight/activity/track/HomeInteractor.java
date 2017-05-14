package dali.oversight.activity.track;

/**
 * Created by Mohamed ali on 06/05/2017.
 */

public interface HomeInteractor {

    interface OnMakeCallFinishedListener {
        void onError();

        void onSuccess(String phone);
    }

    void ValidateCall(String number,OnMakeCallFinishedListener listener);
}
