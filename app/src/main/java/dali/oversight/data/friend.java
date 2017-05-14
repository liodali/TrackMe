package dali.oversight.data;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mohamed ali on 08/05/2017.
 */

public class friend  implements Parcelable {

    private String id;
    private String name;
    private String Url;

    public friend() {

    }

    public friend(String id, String name, String url) {
        this.id = id;
        this.name = name;
        Url = url;
    }

    protected friend(Parcel in) {
        id = in.readString();
        name = in.readString();
        Url = in.readString();
    }

    public static final Creator<friend> CREATOR = new Creator<friend>() {
        @Override
        public friend createFromParcel(Parcel in) {
            return new friend(in);
        }

        @Override
        public friend[] newArray(int size) {
            return new friend[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(Url);
    }
}
