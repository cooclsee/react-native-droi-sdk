
package com.cooclsee.droi.sdk;

import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;

import com.droi.sdk.core.Core;
import com.droi.sdk.selfupdate.DroiDownloadListener;
import com.droi.sdk.selfupdate.DroiUpdate;
import com.droi.sdk.selfupdate.DroiUpdateListener;
import com.droi.sdk.selfupdate.DroiUpdateResponse;
import com.droi.sdk.selfupdate.UpdateFailCode;
import com.droi.sdk.selfupdate.UpdateStatus;
import com.droi.sdk.selfupdate.UpdateType;
import com.droi.sdk.selfupdate.UpdateUIStyle;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.io.File;

/**
 * 自更新Sdk
 */
public class DroiSelfUpdateModule extends ReactContextBaseJavaModule implements DroiUpdateListener {

    private final ReactApplicationContext reactContext;
    private DroiUpdateResponse mDroiUpdateResponse = null;
    private final MyDroiDownloadListener myDroiDownloadListener;

    public DroiSelfUpdateModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        myDroiDownloadListener = new MyDroiDownloadListener(this);
    }

    @Override
    public String getName() {
        return "DroiSelfUpdate";
    }

    private void sendEvent(String eventName, @Nullable WritableMap params) {
        if (reactContext != null) {
            reactContext
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(eventName, params);
        }
    }

    @Override
    public void onUpdateReturned(int updateStatus, DroiUpdateResponse droiUpdateResponse) {
        this.mDroiUpdateResponse = droiUpdateResponse;
        sendEvent("onUpdateReturned", MapUtil.onUpdateReturnedToMap(updateStatus, droiUpdateResponse));
    }

    /**
     * 初始化
     *
     * @param options 配置
     *                {
     *                onlyWifi: true|false, //是否只在wifi下更新
     *                autoPopup: true|false, //是否自动弹出提示
     *                uiStyle: 'both'|'dialog'|'notification' // 通知样式
     *                }
     */
    @ReactMethod
    public void init(ReadableMap options) {
        this.mDroiUpdateResponse = null;
        DroiUpdate.initialize(reactContext);
        if (options.hasKey("onlyWifi")) {
            DroiUpdate.setUpdateOnlyWifi(options.getBoolean("onlyWifi"));
        }
        if (options.hasKey("autoPopup")) {
            DroiUpdate.setUpdateAutoPopup(options.getBoolean("autoPopup"));
        }
        if (options.hasKey("uiStyle")) {
            String styleValue = options.getString("uiStyle");
            switch (styleValue) {
                case "both":
                    DroiUpdate.setUpdateUIStyle(UpdateUIStyle.STYLE_BOTH);
                    break;
                case "dialog":
                    DroiUpdate.setUpdateUIStyle(UpdateUIStyle.STYLE_DIALOG);
                    break;
                case "notification":
                    DroiUpdate.setUpdateUIStyle(UpdateUIStyle.STYLE_NOTIFICATION);
                    break;
            }
        }
        DroiUpdate.setUpdateListener(this);
    }

    /**
     * 自动检测更新
     */
    @ReactMethod
    public void update(Promise promise) {
        this.mDroiUpdateResponse = null;
        try {
            DroiUpdate.update(reactContext);
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    /**
     * 手动检测更新
     */
    @ReactMethod
    public void manualUpdate(Promise promise) {
        this.mDroiUpdateResponse = null;
        try {
            DroiUpdate.manualUpdate(reactContext);
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    /**
     * 显示对话框
     */
    @ReactMethod
    public void showUpdateDialog(Promise promise) {
        if (this.mDroiUpdateResponse != null) {
            try {
                DroiUpdate.showUpdateDialog(reactContext, mDroiUpdateResponse);
            } catch (Exception e) {
                promise.reject(e);
            }
        }
    }

    /**
     * 显示通知栏
     */
    @ReactMethod
    public void showUpdateNotification(Promise promise) {
        if (this.mDroiUpdateResponse != null) {
            try {
                DroiUpdate.showUpdateNotification(reactContext, mDroiUpdateResponse);
            } catch (Exception e) {
                promise.reject(e);
            }
        }
    }

    /**
     * 检查更新是否被忽略
     */
    @ReactMethod
    public void isUpdateIgnore(Promise promise) {
        if (this.mDroiUpdateResponse != null) {
            try {
                DroiUpdate.isUpdateIgnore(reactContext, mDroiUpdateResponse);
            } catch (Exception e) {
                promise.reject(e);
            }
        }
    }

    /**
     * 设置该更新忽略
     */
    @ReactMethod
    public void setUpdateIgnore(Promise promise) {
        if (this.mDroiUpdateResponse != null) {
            try {
                DroiUpdate.setUpdateIgnore(reactContext, mDroiUpdateResponse);
            } catch (Exception e) {
                promise.reject(e);
            }
        }
    }

    /**
     * 安装应用
     */
    @ReactMethod
    public void installApp(Promise promise) {
        File apkFile = DroiUpdate.getDownloadedFile(reactContext, mDroiUpdateResponse);
        try {
            DroiUpdate.installApp(reactContext, apkFile, 3);
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    /**
     * 下载app
     */
    @ReactMethod
    public void downloadApp(Promise promise) {
        try {
            DroiUpdate.downloadApp(reactContext, mDroiUpdateResponse, myDroiDownloadListener);
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    /**
     * DroiDownloadListener
     */
    class MyDroiDownloadListener implements DroiDownloadListener {
        private final DroiSelfUpdateModule module;

        public MyDroiDownloadListener(DroiSelfUpdateModule module) {
            this.module = module;
        }

        @Override
        public void onStart(long l) {
            WritableMap map = Arguments.createMap();
            map.putDouble("result", (double) l);
            sendEvent("download_onStart", map);
        }

        @Override
        public void onProgress(float v) {
            WritableMap map = Arguments.createMap();
            map.putDouble("result", v);
            sendEvent("download_onProgress", map);
        }

        @Override
        public void onFinished(File file) {
            WritableMap map = Arguments.createMap();
            map.putString("fileName", file.getAbsolutePath());
            sendEvent("download_onFinished", map);
        }

        @Override
        public void onFailed(int i) {
            WritableMap map = Arguments.createMap();
            String failCode = null;
            switch (i) {
                case UpdateFailCode.OTHER_FAILED:
                    failCode = "other_failed";
                    break;
                case UpdateFailCode.PATCH_FAILED:
                    failCode = "patch_failed";
                    break;
                case UpdateFailCode.DOWNLOAD_FAILED:
                    failCode = "download_failed";
                    break;
                case UpdateFailCode.MD5_FAILED:
                    failCode = "md5_failed";
                    break;
            }
            map.putString("code", failCode);
            sendEvent("download_onFailed", map);
        }

        @Override
        public void onPatching() {
            sendEvent("download_onStart", null);
        }
    }
}