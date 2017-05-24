package dali.oversight.activity.trackers;

import dali.oversight.activity.CallArchieve.HistoriqueView;
import dali.oversight.data.friend;

/**
 * Created by Mohamed ali on 08/05/2017.
 */

public interface ListTrackerView extends HistoriqueView {

    void disableNext();
    void EnableNext();
    void SetFriend(friend f);
    void Alertsnackbar();
    void NoFriend();

}
