package epping.ian.restaurant;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuItemRequest implements Response.ErrorListener, Response.Listener<JSONObject> {

        // call methods for error and succesful requests
        public interface Callback {
            void gotMenuItems(ArrayList<MenuItem> categories);
            void gotMenuItemsError(String message);
        }

        private Context context;
        private Callback callback;
        private String category;
        private ArrayList<MenuItem> items = new ArrayList<>();

        // constructor for context parameter
        public MenuItemRequest(Context context, String category) {
            this.context = context;
            this.category = category;
        }

        // retrieves menu items from the API
        void getMenuItems(Callback callback, String category){
            this.callback = callback;
            this.category = category;

            // link to API site
            String url = "https://resto.mprog.nl/menu";

            // request the data from API
            RequestQueue queue = Volley.newRequestQueue(context);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, this, this);
            queue.add(jsonObjectRequest);
        }

        // listener for succesful requests
        @Override
        public void onResponse(JSONObject response) {

            // define menu fields
            String name;
            String description;
            String imageUrl;
            Double price;
            String category;

            //define list of menu items
            JSONArray jsonArray;

            try {
                // extract and define array
                jsonArray = response.getJSONArray("items");

                // fill list with all menu items
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    String itemCategory = item.getString("category");

                    // get all info from site
                    if(this.category.equals(itemCategory)) {
                        name = item.getString("name");
                        description = item.getString("description");
                        imageUrl = item.getString("image_url");
                        price = item.getDouble("price");
                        category = item.getString("category");

                        // add new menu item to arraylist
                        items.add(new MenuItem(name, description, imageUrl, price, category));
                    }
                }
            }
            // exception for network error
            catch (JSONException e) {
                e.printStackTrace();
            }
            // pass list back to calling activity
            callback.gotMenuItems(items);
        }

        // handles request errors
        @Override
        public void onErrorResponse(VolleyError error) {
            callback.gotMenuItemsError(error.getMessage());
            error.printStackTrace();
        }
}
