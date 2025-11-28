-keep class com.kakao.sdk.**.model.* { <fields>; }

# https://github.com/square/okhttp/pull/6792
-dontwarn org.bouncycastle.jsse.**
-dontwarn org.conscrypt.*
-dontwarn org.openjsse.**

# refrofit2 (with r8 full mode)
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
-if interface * { @retrofit2.http.* public *** *(...); }
-keep,allowoptimization,allowshrinking,allowobfuscation class <3>
-keep,allowobfuscation,allowshrinking class retrofit2.Response

-keep class dagger.hilt.android.internal.** { *; }
-keep class hilt_aggregated_deps.** { *; }
-keep class dagger.hilt.internal.aggregatedroot.codegen.** { *; }
-keep @dagger.hilt.android.AndroidEntryPoint class *
-keep @dagger.hilt.InstallIn class *
-keep @dagger.Module class *

-keep class timber.log.Timber$Tree

-keepnames class * {
    @kotlinx.serialization.Serializable *;
}
-keepclassmembers class * {
    @kotlinx.serialization.Serializable <fields>;
    @kotlinx.serialization.Serializable <init>(...);
}
-keepclassmembers class * extends kotlinx.serialization.internal.GeneratedSerializer {
    <init>(...);
    public static final ** Companion;
}
-keep public class com.depromeet.team5.**.model.** { *; }
-keep public class com.depromeet.team5.core.navigation.request.** { *;}

-keepclasseswithmembernames,includedescriptorclasses class * {
    native <methods>;
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-dontwarn okio.**
-dontwarn okhttp3.**
-dontwarn com.google.errorprone.annotations.**