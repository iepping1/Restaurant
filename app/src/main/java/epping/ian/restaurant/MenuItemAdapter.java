package epping.ian.restaurant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

// connect menu item with activity menu
public class MenuItemAdapter extends ArrayAdapter<MenuItem> {

    private ArrayList<MenuItem> items;
    NetworkImageView imaged;
    ImageLoader imageLoader;
    private Context context;

    public MenuItemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MenuItem> objects) {
        super(context, resource, objects);
        items = objects;
    }

    // create fields for adapterview
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inf = LayoutInflater.from(getContext());
            convertView = inf.inflate(R.layout.menu_item, parent, false);
        }

        // get item info
        MenuItem item = items.get(position);

        String name = item.getName();
        String price = item.getPrice().toString();
        String image = item.getImageURL();

        //get references to item fields
        TextView named = convertView.findViewById(R.id.item_name);
        TextView priced = convertView.findViewById(R.id.item_price);
        imaged = convertView.findViewById(R.id.item_image);

        // set text info to views
        named.setText(name);
        priced.setText("$"+ price);

        // set image to views
        imageLoader = MenuImageRequest.getInstance(this.getContext())
                .getImageLoader();
        imaged.setImageUrl(image, imageLoader);
        //MenuImageRequest imageRequest = new MenuImageRequest(context);
        //imageRequest.getImage(item.getImageURL(), new ImageCallback(imaged));

        return convertView;
    }
}
