package dali.oversight.activity.gpstrack;

import dali.oversight.BaseView;
import dali.oversight.data.Gps;

/**
 * Created by Mohamed ali on 11/05/2017.
 */

public interface GPSView extends BaseView {
    void initMap();
    void alertSnack();
    void SetPos(Gps gps);

}
