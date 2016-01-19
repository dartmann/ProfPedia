package de.davidartmann.profpedia.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.TimeZone;

import de.davidartmann.profpedia.R;

/**
 * Convinient method to load the json data from the assets folder.
 * This contains a jsonarray holding a jsonobject for each mensa with name and id of API.
 * Created by david on 17.01.16.
 */
public class MensaAssetHelper {

    private static final String TAG = MensaAssetHelper.class.getSimpleName();

    /**
     * Loads the asset json data and return {@link JSONArray}.
     * @param context needed context for the assets loading.
     * @return wanted JSONArray or null.
     */
    public static JSONArray loadMensaDataToJSONArray(Context context) {
        JSONArray jsonArray = null;
        try {
            InputStream inputStream = context.getAssets().open("mensas.json");
            int size  =inputStream.available();
            byte[] bytes = new byte[size];
            inputStream.read(bytes);
            inputStream.close();
            jsonArray = new JSONArray(new String(bytes, "UTF-8"));
        } catch (IOException | JSONException e) {
            Log.e(TAG, "something went wrong while reading assets mensa.json");
            e.printStackTrace();
        }
        return jsonArray;
    }

    public static JSONObject getPreferedMensaJSONObject(Context context, SharedPreferences sharedPreferences) {
        JSONObject jsonObject = null;
        String[] mensaNames = context.getResources().getStringArray(R.array.mensa_names);
        String preferedMensaName = sharedPreferences.getString(context.getString(R.string.pref_key_mensa_location_id),
                mensaNames[0]);
        JSONArray jsonArray = loadMensaDataToJSONArray(context);
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject != null) {
                    if (jsonObject.getString("name").equals(preferedMensaName)) {
                        return jsonObject;
                    }
                }
            } catch (JSONException e) {
                Log.e(TAG, "something went wrong while parsing json from mensas.json");
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    public static void setImageByPreferedMensa(Context context, SharedPreferences sharedPreferences,
                                               ImageView imageView) {
        JSONObject jsonObject = getPreferedMensaJSONObject(context, sharedPreferences);
        try {
            InputStream is = context.getAssets().open(jsonObject.getString("imgAssetName"));
            imageView.setImageDrawable(Drawable.createFromStream(is, null));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
