# CrashHandler
[![](https://jitpack.io/v/mhmdmydn/CrashHandler.svg)](https://jitpack.io/#mhmdmydn/CrashHandler)</br>
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
	       implementation 'com.github.mhmdmydn:CrashHandler:0.0.2'
	}
```



### Quick Start

- Create a Java Application class and  implement the init, to initiliaze library.

```java
public class App extends Application {

    private static App singleton = null;

    public static App getInstance(){
        if(singleton == null){
            singleton = new App();
        }

        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        lib.init(singleton);
    }
}
```
