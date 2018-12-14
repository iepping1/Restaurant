package epping.ian.restaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements MenuItemRequest.Callback{

    // create menu window
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();

        // catch menu item
        String category = intent.getStringExtra("category");

        // get menu from site
        MenuItemRequest request = new MenuItemRequest(this, category);
        request.getMenuItems(this, category);
    }

    @Override
    public void gotMenuItems(ArrayList<MenuItem> items) {
        MenuItemAdapter adapter = new MenuItemAdapter(this, R.layout.menu_item, items);

        // connect adapter to listview
        ListView menuList = findViewById(R.id.listMenu);
        menuList.setAdapter(adapter);
        menuList.setOnItemClickListener(new ListClickListener());
    }

    @Override
    public void gotMenuItemsError(String message) {

        // send a message if error has occured
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    // move to window with clicked item from list
    public class ListClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Intent intent = new Intent(MenuActivity.this, MenuItemActivity.class);
            intent.putExtra("menu_item", (MenuItem) adapterView.getItemAtPosition(i));
            startActivity(intent);
        }
    }
}
