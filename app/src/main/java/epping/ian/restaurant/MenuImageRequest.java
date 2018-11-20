package epping.ian.restaurant;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.VolleyError;

public class MenuImageRequest {

    private Context myContext;
    private ImageLoader imageLoader;
    private RequestQueue queue;
    private static MenuImageRequest requestInstance;

    // constructor for context parameter
    public MenuImageRequest(Context context) {
        myContext = context;
        queue = getRequestQueue();
        imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap>
                cache = new LruCache<>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static synchronized MenuImageRequest getInstance(Context context){
        if (requestInstance == null) {
            requestInstance = new MenuImageRequest(context);
        }
        return requestInstance;
    }

    public RequestQueue getRequestQueue() {
        if (queue == null) {
            Cache cache = new DiskBasedCache(myContext.getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            queue = new RequestQueue(cache, network);
            queue.start();
        }
        return queue;
    }

    public ImageLoader getImageLoader(){
        return imageLoader;
    }
}