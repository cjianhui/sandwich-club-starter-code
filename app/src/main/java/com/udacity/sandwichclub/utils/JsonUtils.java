package com.udacity.sandwichclub.utils;

import android.util.Log;
import android.widget.TextView;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class JsonUtils {

    /*
        Converts a JSONArray into an array of Strings
     */
    private static String[] convertToStringArray(JSONArray jsonArray) throws JSONException {
        String array[] = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            array[i] = jsonArray.get(i).toString();
        }

        return array;
    }

    public static Sandwich parseSandwichJson(String json) {

        /*
            Constants for obtaining values from SandwichJson
         */
        final String SW_NAME = "name";
        final String SW_MAINNAME = "mainName";
        final String SW_AKA = "alsoKnownAs";
        final String SW_ORGIN = "placeOfOrigin";
        final String SW_DESC = "description";
        final String SW_IMAGE = "image";
        final String SW_INGREDIENTS = "ingredients";

        try {
            JSONObject sandwichObject = new JSONObject(json);
            JSONObject nameObject = sandwichObject.getJSONObject(SW_NAME);
            String mainName = nameObject.getString(SW_MAINNAME);
            JSONArray alternativeNamesObject = nameObject.getJSONArray(SW_AKA);
            String alternativeNames[] = convertToStringArray(alternativeNamesObject);

            String origin = sandwichObject.getString(SW_ORGIN);
            String description = sandwichObject.getString(SW_DESC);
            String imageUrl = sandwichObject.getString(SW_IMAGE);
            JSONArray ingredientsObject = sandwichObject.getJSONArray(SW_INGREDIENTS);
            String ingredients[] = convertToStringArray(ingredientsObject);

            return new Sandwich(mainName, Arrays.asList(alternativeNames), origin, description,
                    imageUrl, Arrays.asList(ingredients));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }



}
