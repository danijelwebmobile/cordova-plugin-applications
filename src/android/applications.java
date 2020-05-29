package pm.danijel;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;

import org.apache.cordova.PluginResult;

import java.io.File;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class applications extends CordovaPlugin {

    public Context context = null;
    private static final boolean IS_AT_LEAST_LOLLIPOP = Build.VERSION.SDK_INT >= 21;

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) {

        context = IS_AT_LEAST_LOLLIPOP ? cordova.getActivity().getWindow().getContext() : cordova.getActivity().getApplicationContext();

        if (action.equals("start")) {

            JSONArray resultArray = new JSONArray();

            final PackageManager pm = cordova.getActivity().getPackageManager();
            Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            List<ResolveInfo> apps = pm.queryIntentActivities(intent, PackageManager.GET_META_DATA);
            for (ResolveInfo packageInfo : apps) {
                JSONObject result = getPackageInfo(context, packageInfo.activityInfo.applicationInfo.packageName);
                resultArray.put(result);
            }

            PluginResult pr = new PluginResult(PluginResult.Status.OK, resultArray);
            callbackContext.sendPluginResult(pr);
            return true;
        }

        callbackContext.error("PackageManager " + action + " is not a supported function.");
        return false;
    }
    
    private static JSONObject getPackageInfo(Context context, String packageName) {
        JSONObject jsonObject = new JSONObject();
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(packageName, 0);
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

             jsonObject.put("id", packageName);
             jsonObject.put("name", packageInfo.applicationInfo.loadLabel(pm).toString());
             jsonObject.put("versionCode", packageInfo.versionCode);
             jsonObject.put("versionName", packageInfo.versionName);
             jsonObject.put("installedDate", sdf.format(new Date(packageInfo.firstInstallTime)));
             jsonObject.put("lastUsed", sdf.format(new Date(packageInfo.lastUpdateTime)));

             File file = new File(packageInfo.applicationInfo.sourceDir);
             jsonObject.put("memoryUsedMb", file.length() / 1024.00 / 1024.00);
        } catch (PackageManager.NameNotFoundException | JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}