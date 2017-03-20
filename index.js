import { NativeModules, DeviceEventEmitter } from 'react-native'

const {DroiCore, DroiSelfUpdate} = NativeModules
const onUpdateReturned = 'onUpdateReturned'
const download_onStart = 'download_onStart'
const download_onProgress = 'download_onProgress'
const download_onFinished = 'download_onFinished'
const download_onFailed = 'download_onFailed'
const download_onPatching = 'download_onPatching'

/**
 * 添加onUpdateReturned事件监听
 */
DroiSelfUpdate.addUpdateReturnedListener = (handler) => {
  DeviceEventEmitter.addListener(onUpdateReturned, handler)
}

/**
 * 添加下载相关事件监听
 */
DroiSelfUpdate.addUpdateDownloadListener = (handlers) => {
  if (handlers) {
    if ('onStart' in handlers) {
      DeviceEventEmitter.addListener(download_onStart, handlers.onStart)
    }
    if ('onProgress' in handlers) {
      DeviceEventEmitter.addListener(download_onProgress, handlers.onProgress)
    }
    if ('onFinished' in handlers) {
      DeviceEventEmitter.addListener(download_onFinished, handlers.onFinished)
    }
    if ('onFailed' in handlers) {
      DeviceEventEmitter.addListener(download_onFailed, handlers.onFailed)
    }
    if ('onPatching' in handlers) {
      DeviceEventEmitter.addListener(download_onPatching, handlers.onPatching)
    }
  }
}

export { DroiCore, DroiSelfUpdate }
