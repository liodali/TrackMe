package dali.oversight.activity.track;

/**
 * Created by Mohamed ali on 06/05/2017.
 */

public class HomeInteractorImpl implements HomeInteractor {

    @Override
    public void ValidateCall(String number, OnMakeCallFinishedListener listener) {

        if(number.length()<8){
            listener.onError();
        }else if(number.length()==8){
            listener.onSuccess(number);
        }
    }
}
