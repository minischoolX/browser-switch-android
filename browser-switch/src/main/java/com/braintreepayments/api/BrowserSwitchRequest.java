package com.braintreepayments.api;

import android.net.Uri;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

class BrowserSwitchRequest {


    private final Uri url;
    private final int requestCode;
    private final JSONObject metadata;
    private final String returnUrlScheme;
    private boolean shouldNotifyCancellation;

    static BrowserSwitchRequest fromJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        int requestCode = jsonObject.getInt("requestCode");
        String url = jsonObject.getString("url");
        String returnUrlScheme = jsonObject.getString("returnUrlScheme");
        JSONObject metadata = jsonObject.optJSONObject("metadata");
        boolean shouldNotify = jsonObject.optBoolean("shouldNotify", true);
        return new BrowserSwitchRequest(requestCode, Uri.parse(url), metadata, returnUrlScheme, shouldNotify);
    }

    BrowserSwitchRequest(int requestCode, Uri url, JSONObject metadata, String returnUrlScheme, boolean shouldNotifyCancellation) {
        this.url = url;
        this.requestCode = requestCode;
        this.metadata = metadata;
        this.returnUrlScheme = returnUrlScheme;
        this.shouldNotifyCancellation = shouldNotifyCancellation;
    }

    Uri getUrl() {
        return url;
    }

    int getRequestCode() {
        return requestCode;
    }

    JSONObject getMetadata() {
        return metadata;
    }

    boolean getShouldNotifyCancellation() {
        return shouldNotifyCancellation;
    }

    void setShouldNotifyCancellation(boolean shouldNotifyCancellation) {
        this.shouldNotifyCancellation = shouldNotifyCancellation;
    }

    String toJson() throws JSONException {
        JSONObject result = new JSONObject();
        result.put("requestCode", requestCode);
        result.put("url", url.toString());
        result.put("returnUrlScheme", returnUrlScheme);
        result.put("shouldNotify", shouldNotifyCancellation);
        if (metadata != null) {
            result.put("metadata", metadata);
        }
        return result.toString();
    }

    boolean matchesDeepLinkUrlScheme(@NonNull Uri url) {
        return url.getScheme().equals(returnUrlScheme);
    }
}
