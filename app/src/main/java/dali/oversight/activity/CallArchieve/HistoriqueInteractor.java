package dali.oversight.activity.CallArchieve;

import java.util.ArrayList;

/**
 * Created by Mohamed ali on 07/05/2017.
 */

public interface HistoriqueInteractor {



    interface OnFinishedListener {
        void onFinished(ArrayList<?> items);
    }

    void findItems( OnFinishedListener listener);
}
