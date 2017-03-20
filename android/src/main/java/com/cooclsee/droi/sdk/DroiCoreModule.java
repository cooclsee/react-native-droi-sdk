package com.cooclsee.droi.sdk;

import com.droi.sdk.core.Core;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class DroiCoreModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public DroiCoreModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "DroiCore";
    }

    /**
     * 初始化
     */
    @ReactMethod
    public void init() {
        Core.initialize(reactContext);
    }
}
