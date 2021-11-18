# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Dagger ProGuard rules.
# https://github.com/square/dagger

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep Request Parameter Classes

-keep public class com.oacikel.baseapp.data*
-dontwarn com.oacikel.baseapp.api*
-dontwarn com.oacikel.baseapp.db*
-keepclassmembers classcom.oacikel.baseapp.api*
-keepclassmembers classcom.oacikel.baseapp.db*

## dagger

# dagger2 https://github.com/google/dagger/issues/645
-dontwarn com.google.errorprone.annotations.*

# moshi
# okhttp3
-dontwarn okio.**
-dontwarn javax.annotation.**

### Kotlin Coroutine
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

## Databinding or library depends on databinding
-dontwarn android.databinding.**
-keep class android.databinding.** { *; }

# ViewModel's empty constructor is considered to be unused by proguard
-keepclassmembers class * extends android.arch.lifecycle.ViewModel {
    <init>(...);
}

# retrofit2 http://square.github.io/retrofit/
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*

# https://github.com/orhanobut/hawk/issues/143
-keepattributes EnclosingMethod

# Android support v7
-keep public class android.support.v7.widget.** { *; }

# okhttp3 https://github.com/square/okhttp/issues/2230
-dontwarn okhttp3.**

# glide https://github.com/krschultz/android-proguard-snippets/blob/master/libraries/proguard-glide.pro
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class com.bumptech.glide.integration.okhttp3.OkHttpGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

# LifecycleObserver's empty constructor is considered to be unused by proguard
-keepclassmembers class * implements android.arch.lifecycle.LifecycleObserver {
    <init>(...);
}
-keep class * implements android.arch.lifecycle.LifecycleObserver {
    <init>(...);
}


# ViewModel's empty constructor is considered to be unused by proguard
-keepclassmembers class * extends android.arch.lifecycle.ViewModel {
    <init>(...);
}

# keep Lifecycle State and Event enums values
-keepclassmembers class android.arch.lifecycle.Lifecycle$State { *; }
-keepclassmembers class android.arch.lifecycle.Lifecycle$Event { *; }

# keep methods annotated with @OnLifecycleEvent even if they seem to be unused
# (Mostly for LiveData.LifecycleBoundObserver.onStateChange(), but who knows)
-keepclassmembers class * {
    @android.arch.lifecycle.OnLifecycleEvent *;
}

-keepclassmembers class android.arch.** { *; }
-keep class android.arch.** { *; }
-dontwarn android.arch.**

-keep class * implements android.arch.lifecycle.GeneratedAdapter {<init>(...);}



-ignorewarnings
-keep class * {
    public private *;
}

