# CrashHandler

[![](https://jitpack.io/v/mhmdmydn/CrashHandler.svg)](https://jitpack.io/#mhmdmydn/CrashHandler)
</br>
CrashHandler implement in Android with to see error exception..

## Usage

### Setup

root build.gradle

```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

app build.gradle

```groovy
	dependencies {
	       implementation 'com.github.mhmdmydn:CrashHandler:0.0.3'
	}
```

### Quick Start

- Create a Java Application class and implement the init, to initiliaze library.

```java
public class App extends CrashHandlerApplication {

}
```

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="id.ghodel.crashhandler">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        <!-- add name in your Application class to manifest -->
        android:name=".App"
        android:supportsRtl="true"
        android:theme="@style/Theme.CrashHandler">



        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <!-- add activity for result error exception -->
        <activity android:name="id.ghodel.lib.CrashHandler$CrashActivity"></activity>
    </application>

</manifest>
```
