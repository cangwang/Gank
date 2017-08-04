## Add project specific ProGuard rules here.
## By default, the flags in this file are appended to flags specified
## in D:\android_sdk/tools/proguard/proguard-android.txt
## You can edit the include path and order by changing the proguardFiles
## directive in build.gradle.
##
## For more details, see
##   http://developer.android.com/guide/developing/tools/proguard.html
#
## Add any project specific keep options here:
#
## If your project uses WebView with JS, uncomment the following
## and specify the fully qualified class name to the JavaScript interface
## class:
##-keepclassmembers class fqcn.of.javascript.interface.for.webview {
##   public *;
##}
#
#
##Glide
#-keep public class * implements com.bumptech.glide.module.GlideModule
#-keep public class * extends com.bumptech.glide.AppGlideModule
#-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
#  **[] $VALUES;
#  public *;
#}
#
## for DexGuard only
##-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
#
##Retrofit混淆
#-dontwarn okio.**
#-dontwarn javax.annotation.**
#
##ARouter混淆
#-keep public class com.alibaba.android.arouter.routes.**{*;}
#-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
#
##EventBus
#-keepattributes *Annotation*
#-keepclassmembers class ** {
#    @org.greenrobot.eventbus.Subscribe <methods>;
#}
#-keep enum org.greenrobot.eventbus.ThreadMode { *; }
#
## Only required if you use AsyncExecutor
#-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
#    <init>(java.lang.Throwable);
#}
#
##databinding
#-keep class android.databinding.** { *; }
#-keepnames class * implements java.io.Serializable
#-keepclassmembers class * implements java.io.Serializable {
#    static final long serialVersionUID;
#    private static final java.io.ObjectStreamField[] serialPersistentFields;
#    !static !transient <fields>;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#}
#-keepattributes *Annotation*
#-keepattributes javax.xml.bind.annotation.*
#-keepattributes javax.annotation.processing.*
#-keepclassmembers class * extends java.lang.Enum { *; }
#-keepclasseswithmembernames class android.**
#-keepclasseswithmembernames interface android.**
#-dontobfuscate
#-libraryjars  <java.home>/lib/rt.jar
#-libraryjars  <java.home>/lib/jce.jar
#-dontwarn
#
#-keep class de.greenrobot.dao.** {*;}
#-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
#    public static Java.lang.String TABLENAME;
#}
#-keep class **$Properties
#
##---------------------------------默认保留区---------------------------------
##继承activity,application,service,broadcastReceiver,contentprovider....不进行混淆
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.support.multidex.MultiDexApplication
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class * extends android.app.backup.BackupAgentHelper
#-keep public class * extends android.preference.Preference
#-keep public class * extends android.view.View
#-keep public class com.android.vending.licensing.ILicensingService
#-keep class android.support.** {*;}
#
#-keep public class * extends android.view.View{
#    *** get*();
#    void set*(***);
#    public <init>(android.content.Context);
#    public <init>(android.content.Context, android.util.AttributeSet);
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#}
#-keepclasseswithmembers class * {
#    public <init>(android.content.Context, android.util.AttributeSet);
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#}
##这个主要是在layout 中写的onclick方法android:onclick="onClick"，不进行混淆
#-keepclassmembers class * extends android.app.Activity {
#   public void *(android.view.View);
#}
#
#-keepclassmembers class * implements java.io.Serializable {
#    static final long serialVersionUID;
#    private static final java.io.ObjectStreamField[] serialPersistentFields;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#}
#-keep class **.R$* {
# *;
#}
#
#-keepclassmembers class * {
#    void *(*Event);
#}
#
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
#-keep class * implements android.os.Parcelable {
#  public static final android.os.Parcelable$Creator *;
#}
##// natvie 方法不混淆
#-keepclasseswithmembernames class * {
#    native <methods>;
#}
#
##保持 Parcelable 不被混淆
#-keep class * implements android.os.Parcelable {
#  public static final android.os.Parcelable$Creator *;
#}
#
##
#-keep class material.com.base.utils.ListDataSave