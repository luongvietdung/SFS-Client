package com.appsfs.sfs.api.helper;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by dunglv on 5/15/16.
 */
public class CustomJSONRequest extends JsonRequest<CustomRespond> {
    public CustomJSONRequest(int method, String url, JSONObject requestBody, Response.Listener<CustomRespond> listener, Response.ErrorListener errorListener, String name) {
        super(method, url, (requestBody == null) ? null : requestBody.toString(), listener, errorListener);
        this.name = name;
    }

    @Override
    protected Response<CustomRespond> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            CustomRespond customRespond = new CustomRespond(new JSONObject(jsonString), response.statusCode, this.name);
            return Response.success(customRespond, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new CustomVolleyError(response, e));
        } catch (JSONException je) {
            return Response.error(new CustomVolleyError(response, je));
        } catch (Exception e) {
            return Response.error(new CustomVolleyError(response, e));
        }
    }

    private String name;
}
