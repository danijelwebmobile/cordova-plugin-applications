---
Title: Information Installed Applications  
description: Returns a information list of applications installed on the system.
---

# cordova-plugin-applications

	A simple plugin that will return a info list of installed applications.  
    Return packageName, name( app name ), versionCode, versionName, installedDate, lastUsed, memoryUsedMb.

## Installation

    cordova plugin add "https://github.com/danijelwebmobile/cordova-plugin-applications.git"

### Example

    declare const applications: any;

    //Success Callback Receive
    function successCallback(e) {
        document.getElementById('divApps').innerHTML += JSON.stringify(e);
    }

    //Error Callback Receive
    function errorCallback(e) {
        alert('Error');
    }

    applications.show(successCallback, errorCallback);

#### Parameters

    1. successCallback (Function)
        Success Callback Receive
        * Return: JSON Object Array
        * Type Return Example:  
        [{packageName: '***', name: '***', versionCode: 1, versionName: '1.0.0', installedDate: '2020-05-29', lastUsed: '2020-05-29', memoryUsedMb: 31.535426}, {...}]

    2. errorCallback (Function)
        Error Callback Receive

##### Supported Platforms

- Android