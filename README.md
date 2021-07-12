# CrashHandler

[![](https://jitpack.io/v/mhmdmydn/CrashHandler.svg)](https://jitpack.io/#mhmdmydn/CrashHandler)
</br>
CrashHandler implement in Android with to see error exception..

## Screenshots

| First | Second |
|:-:|:-:|
| ![First](/images/SS-1.png?raw=true) | ![Sec](/images/SS-2.png?raw=true) |

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
	       implementation 'com.github.mhmdmydn:CrashHandler:0.0.4'
	}
```



### Quick Start

- Create a Java Application class and implement the init, to initiliaze library.

```java
public class App extends Application {

    private static App singleton = null;

    public static App getInstance(){
        if(singleton == null ){
            singleton = new App();
        }
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        CrashHandler.getInstance()
                .init(singleton)
                .setEmail("ghodelchibar@gmail.com")
                .saveErrorToPath(true);
    }

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
        android:name=".App"
        android:supportsRtl="true"
        android:theme="@style/Theme.CrashHandler">



        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

    </application>

</manifest>
```

## Demo APK

[<img src="/images/direct-apk-download.png?raw=true"
      alt="Direct apk download"
      height="80">](https://github.com/mhmdmydn/CrashHandler/raw/main/app/release/app-release.apk)


## License

```
MIT License

Copyright (c) 2021 Muhammad Mayudin

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
