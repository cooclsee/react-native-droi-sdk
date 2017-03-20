/**
 * Created by cooclsee on 2017/3/17.
 */

package com.cooclsee.droi.sdk;

import com.droi.sdk.selfupdate.DroiUpdateResponse;
import com.droi.sdk.selfupdate.UpdateStatus;
import com.droi.sdk.selfupdate.UpdateType;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

public class MapUtil {
    public static WritableMap onUpdateReturnedToMap(int updateStatus, DroiUpdateResponse response){
        WritableMap map = droiUpdateResponseToMap(response);
        String statusStr = null;
        switch (updateStatus) {
            case UpdateStatus.NO:
                //Log.i(TAG,"没有更新");
                statusStr = "no";
                break;
            case UpdateStatus.YES:
                //Log.i(TAG,"发现更新");
                statusStr = "yes";
                break;
            case UpdateStatus.ERROR:
                //Log.i(TAG,"发生错误");
                statusStr = "error";
                break;
            case UpdateStatus.TIMEOUT:
                //Log.i(TAG,"超时");
                statusStr = "timeout";
                break;
            case UpdateStatus.NON_WIFI:
                //Log.i(TAG,"没有wifi");
                statusStr = "non_wifi";
                break;
            case UpdateStatus.UPDATING:
                //Log.i(TAG,"正在更新中");
                statusStr = "updating";
                break;
        }
        map.putString("status", statusStr);
        return map;
    }

    public static WritableMap droiUpdateResponseToMap(DroiUpdateResponse response) {
        WritableMap map = Arguments.createMap();
        map.putInt("errorCode", response.getErrorCode());
        map.putString("title", response.getTitle());
        map.putString("content", response.getContent());
        String updateType = null;
        switch (response.getUpdateType()){
            case UpdateType.NONEW:
                updateType = "nonew";
                break;
            case UpdateType.NORMAL:
                updateType = "normal";
                break;
            case UpdateType.FORCE:
                updateType = "force";
                break;
            case UpdateType.SILENT:
                updateType = "silent";
                break;
        }
        map.putString("updateType", updateType);
        map.putString("versionName", response.getVersionName());
        map.putString("versionCode", response.getVersionCode());
        map.putString("newMd5", response.getNewMd5());
        map.putString("newSize", response.getNewSize());
        map.putString("fileUrl", response.getFileUrl());
        map.putBoolean("isDeltaUpdate", response.isDeltaUpdate());
        map.putString("patchUrl", response.getPatchUrl());
        map.putString("patchMd5", response.getPatchMd5());
        map.putString("patchSize", response.getPatchSize());
        return map;
    }
}
