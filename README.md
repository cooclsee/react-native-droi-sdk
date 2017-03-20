
# react-native-droi-sdk

## Getting started

`$ npm install react-native-droi-sdk --save`

### Mostly automatic installation

`$ react-native link react-native-droi-sdk`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-droi-sdk` and add `RNDroiSdk.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNDroiSdk.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.cooclsee.droi.sdk.RNDroiSdkPackage;` to the imports at the top of the file
  - Add `new RNDroiSdkPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-droi-sdk'
  	project(':react-native-droi-sdk').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-droi-sdk/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-droi-sdk')
  	```


## Usage
```javascript
import RNDroiSdk from 'react-native-droi-sdk';

// TODO: What to do with the module?
RNDroiSdk;
```
  