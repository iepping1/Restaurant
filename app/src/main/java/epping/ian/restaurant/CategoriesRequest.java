package epping.ian.restaurant;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.VolleyError;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;

// requests the download of JSON objects
public class CategoriesRequest implements Response.ErrorListener, Response.Listener<JSONObject> {
    private Context context;
    private Callback callback;

    // call methods for error and succesful requests
    public interface Callback {
        void gotCategories(ArrayList<String> categories);
        void gotCategoriesError(String message);
    }

    // constructor for context parameter
    public CategoriesRequest(Context context) {
        this.context = context;
    }

    // retrieves categories from the API
    void getCategories(Callback callback){
        this.callback = callback;

        // link to the API site
        String url = "https://resto.mprog.nl/categories";

        // request the data from the API
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, this, this);
        queue.add(jsonObjectRequest);
    }

    // listener for succesful requests
    @Override
    public void onResponse(JSONObject response) {

        //define the list of categories
        ArrayList<String> categories = new ArrayList<>();

        try {
            // extract and define array
            JSONArray jsonArray;
            jsonArray = response.getJSONArray("categories");

            // fill list with all categories
            for (int i = 0; i < jsonArray.length(); i++) {
                categories.add(jsonArray.getString(i));
            }
            // pass list back to calling activity
            callback.gotCategories(categories);
        }
        // exception for network error
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // handles request errors
    @Override
    public void onErrorResponse(VolleyError error) {
        callback.gotCategoriesError(error.getMessage());
        error.printStackTrace();
    }
}