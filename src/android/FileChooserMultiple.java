package cordova.plugin.file.chooser.multiple;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Modified version of FileChooser plugin: https://github.com/ihadeed/cordova-filechooser
 * Support for multiple files to select
 * Supported MIME types: image/png, image/jpeg, application/pdf
 */
public class FileChooserMultiple extends CordovaPlugin {

    private static final String ACTION_OPEN = "open";
    private static final int PICK_FILE_REQUEST = 1;
    private static final String[] SUPPORTED_MIME_TYPES = new String[]{"image/png", "image/jpeg", "application/pdf"};

    private CallbackContext callback;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
        if (action.equals(ACTION_OPEN)) {
            chooseFile(callbackContext);
            return true;
        }
        return false;
    }

    private void chooseFile(CallbackContext callbackContext) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.putExtra(Intent.EXTRA_MIME_TYPES, SUPPORTED_MIME_TYPES);

        Intent chooser = Intent.createChooser(intent, "Select File");
        cordova.startActivityForResult(this, chooser, PICK_FILE_REQUEST);

        PluginResult pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
        pluginResult.setKeepCallback(true);
        callback = callbackContext;
        callbackContext.sendPluginResult(pluginResult);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_FILE_REQUEST && callback != null) {
            if (resultCode == Activity.RESULT_OK) {
                List<Uri> uris = getResultUris(data);
                List<String> uriStrings = new ArrayList<>(uris.size());
                for (Uri uri : uris) {
                    uriStrings.add(uri.toString());
                }
                callback.success(new JSONArray(uriStrings));
            } else if (resultCode == Activity.RESULT_CANCELED) {
                callback.success(new JSONArray(Collections.emptyList()));
            } else {
                callback.error(resultCode);
            }
        }
    }

    private List<Uri> getResultUris(Intent data) {
        List<Uri> uris = new ArrayList<>();
        if (data.getClipData() != null) {
            ClipData clipData = data.getClipData();
            int itemCount = clipData.getItemCount();
            for(int i = 0; i < itemCount; i++) {
                uris.add(clipData.getItemAt(i).getUri());
            }
        } else if (data.getData() != null) {
            uris.add(data.getData());
        }
        return uris;
    }
}
