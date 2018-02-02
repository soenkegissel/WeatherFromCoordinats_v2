
package com.rucksack.weathermaps.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main implements Parcelable {

    @SerializedName("temp")
    @Expose
    private Double temp;
    @SerializedName("humidity")
    @Expose
    private Double humidity;
    @SerializedName("pressure")
    @Expose
    private Double pressure;
    @SerializedName("temp_min")
    @Expose
    private Double tempMin;
    @SerializedName("temp_max")
    @Expose
    private Double tempMax;

    public Double getTemp() {
        return temp;
    }

    public Double getHumidity() {
        return humidity;
    }

    public Double getPressure() {
        return pressure;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.temp);
        dest.writeValue(this.humidity);
        dest.writeValue(this.pressure);
        dest.writeValue(this.tempMin);
        dest.writeValue(this.tempMax);
    }

    public Main() {
    }

    protected Main(Parcel in) {
        this.temp = (Double) in.readValue(Double.class.getClassLoader());
        this.humidity = (Double) in.readValue(Double.class.getClassLoader());
        this.pressure = (Double) in.readValue(Double.class.getClassLoader());
        this.tempMin = (Double) in.readValue(Double.class.getClassLoader());
        this.tempMax = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Main> CREATOR = new Parcelable.Creator<Main>() {
        @Override
        public Main createFromParcel(Parcel source) {
            return new Main(source);
        }

        @Override
        public Main[] newArray(int size) {
            return new Main[size];
        }
    };
}
