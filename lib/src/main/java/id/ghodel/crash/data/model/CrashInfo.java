package id.ghodel.crash.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CrashInfo implements Parcelable {
    private Throwable exception;
    private String exceptionMsg;
    private String className;
    private String fileName;
    private String methodName;
    private int lineNumber;
    private String exceptionType;
    private String fullException;
    private long timeOfCrash;

    private String versionCode;
    private String versionName;
    private String packageName;
    private boolean buildType;

    private DeviceInfo deviceInfo = new DeviceInfo();

    public CrashInfo(){

    }

    public CrashInfo(Throwable exception, String exceptionMsg, String className, String fileName, String methodName, int lineNumber, String exceptionType, String fullException, long timeOfCrash, String versionCode, String versionName, String packageName, boolean buildType, DeviceInfo deviceInfo) {
        this.exception = exception;
        this.exceptionMsg = exceptionMsg;
        this.className = className;
        this.fileName = fileName;
        this.methodName = methodName;
        this.lineNumber = lineNumber;
        this.exceptionType = exceptionType;
        this.fullException = fullException;
        this.timeOfCrash = timeOfCrash;
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.packageName = packageName;
        this.buildType = buildType;
        this.deviceInfo = deviceInfo;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getFullException() {
        return fullException;
    }

    public void setFullException(String fullException) {
        this.fullException = fullException;
    }

    public long getTimeOfCrash() {
        return timeOfCrash;
    }

    public void setTimeOfCrash(long timeOfCrash) {
        this.timeOfCrash = timeOfCrash;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean isBuildType() {
        return buildType;
    }

    public void setBuildType(boolean buildType) {
        this.buildType = buildType;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    protected CrashInfo(Parcel in) {
        exceptionMsg = in.readString();
        className = in.readString();
        fileName = in.readString();
        methodName = in.readString();
        lineNumber = in.readInt();
        exceptionType = in.readString();
        fullException = in.readString();
        timeOfCrash = in.readLong();
        versionCode = in.readString();
        versionName = in.readString();
        packageName = in.readString();
        buildType = in.readByte() != 0;
        deviceInfo = in.readParcelable(DeviceInfo.class.getClassLoader());
    }

    public static final Creator<CrashInfo> CREATOR = new Creator<CrashInfo>() {
        @Override
        public CrashInfo createFromParcel(Parcel in) {
            return new CrashInfo(in);
        }

        @Override
        public CrashInfo[] newArray(int size) {
            return new CrashInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(exceptionMsg);
        dest.writeString(className);
        dest.writeString(fileName);
        dest.writeString(methodName);
        dest.writeInt(lineNumber);
        dest.writeString(exceptionType);
        dest.writeString(fullException);
        dest.writeLong(timeOfCrash);
        dest.writeString(versionCode);
        dest.writeString(versionName);
        dest.writeString(packageName);
        dest.writeByte((byte) (buildType ? 1 : 0));
        dest.writeParcelable(deviceInfo, flags);
    }

    @Override
    public String toString() {
        return "CrashInfo{" +
                "exception=" + exception +
                ", exceptionMsg='" + exceptionMsg + '\'' +
                ", className='" + className + '\'' +
                ", fileName='" + fileName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", lineNumber=" + lineNumber +
                ", exceptionType='" + exceptionType + '\'' +
                ", fullException='" + fullException + '\'' +
                ", timeOfCrash=" + timeOfCrash +
                ", versionCode='" + versionCode + '\'' +
                ", versionName='" + versionName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", buildType=" + buildType +
                ", deviceInfo=" + deviceInfo +
                '}';
    }
}
