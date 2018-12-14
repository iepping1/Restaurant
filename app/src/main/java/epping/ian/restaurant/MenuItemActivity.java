package epping.ian.restaurant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.ImageLoader;

// the detail activity
public class MenuItemActivity extends AppCompatActivity {

    NetworkImageView imaged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);
        ImageLoader imageLoader = MenuImageRequest.getInstance(this.getApplicationContext()).getImageLoader();

        // show proper menu item
        Intent intent = getIntent();
        MenuItem menuItem = (MenuItem) intent.getSerializableExtra("menu_item");

        // add proper data
        TextView named = findViewById(R.id.item_name);
        TextView described = findViewById(R.id.item_description);
        TextView priced = findViewById(R.id.item_price);
        imaged = findViewById(R.id.item_image);

        // set text
        named.setText(menuItem.getName());
        described.setText(menuItem.getDescription());
        priced.setText("$"+ menuItem.getPrice().toString());

        // set image
        String image = menuItem.getImageURL();
        imaged.setImageUrl(image, imageLoader);
    }
}