package id.ghodel.crash.data.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

public class DeviceInfo implements Parcelable {

    private String manufacturer;
    private String model;
    private String brand;
    private String resolution;
    private String density;
    private String version;
    private String release;
    private String cpuAbi;

    public DeviceInfo(){}

    public DeviceInfo(String manufacturer, String model, String brand, String resolution, String density, String version, String release, String cpuAbi) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.brand = brand;
        this.resolution = resolution;
        this.density = density;
        this.version = version;
        this.release = release;
        this.cpuAbi = cpuAbi;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getCpuAbi() {
        return cpuAbi;
    }

    public void setCpuAbi(String cpuAbi) {
        this.cpuAbi = cpuAbi;
    }

    protected DeviceInfo(Parcel in) {
        manufacturer = in.readString();
        model = in.readString();
        brand = in.readString();
        resolution = in.readString();
        density = in.readString();
        version = in.readString();
        release = in.readString();
        cpuAbi = in.readString();
    }

    public static final Creator<DeviceInfo> CREATOR = new Creator<DeviceInfo>() {
        @Override
        public DeviceInfo createFromParcel(Parcel in) {
            return new DeviceInfo(in);
        }

        @Override
        public DeviceInfo[] newArray(int size) {
            return new DeviceInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(manufacturer);
        dest.writeString(model);
        dest.writeString(brand);
        dest.writeString(resolution);
        dest.writeString(density);
        dest.writeString(version);
        dest.writeString(release);
        dest.writeString(cpuAbi);
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", resolution='" + resolution + '\'' +
                ", density='" + density + '\'' +
                ", version='" + version + '\'' +
                ", release='" + release + '\'' +
                ", cpuAbi='" + cpuAbi + '\'' +
                '}';
    }
}
