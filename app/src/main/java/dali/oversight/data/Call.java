package dali.oversight.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mohamed ali on 06/05/2017.
 */

public class Call implements Parcelable {

    private String id;
    private String Caller;
    private String Receiver;
    private String date;
    private String dure;
    public Call() {
    }

    public Call(String id, String caller, String receiver, String date, String dure) {
        this.id = id;
        Caller = caller;
        Receiver = receiver;
        this.date = date;
        this.dure = dure;
    }

    protected Call(Parcel in) {
        id = in.readString();
        Caller = in.readString();
        Receiver = in.readString();
        date = in.readString();
        dure = in.readString();
    }

    public static final Creator<Call> CREATOR = new Creator<Call>() {
        @Override
        public Call createFromParcel(Parcel in) {
            return new Call(in);
        }

        @Override
        public Call[] newArray(int size) {
            return new Call[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaller() {
        return Caller;
    }

    public void setCaller(String caller) {
        Caller = caller;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDure() {
        return dure;
    }

    public void setDure(String dure) {
        this.dure = dure;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(Caller);
        dest.writeString(Receiver);
        dest.writeString(date);
        dest.writeString(dure);
    }
}
