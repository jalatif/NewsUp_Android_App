package app.newsup.com.newsupapp.tryandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import app.newsup.com.newsupapp.R;


public class MainActivity extends ActionBarActivity {

    // Declaring our tabs and the corresponding fragments.
    ActionBar.Tab bmwTab, fordTab, toyotaTab;
    Fragment bmwFragmentTab = new BmwFragmentTab();
    Fragment toyotaFragmentTab = new ToyotaFragmentTab();
    Fragment fordFragmentTab = new FordFragmentTab();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Asking for the default ActionBar element that our platform supports.
        ActionBar actionBar = this.getSupportActionBar();

        // Screen handling while hiding ActionBar icon.
        //actionBar.setDisplayShowHomeEnabled(false);

        // Screen handling while hiding Actionbar title.
        //actionBar.setDisplayShowTitleEnabled(false);

        // Creating ActionBar tabs.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Setting custom tab icons.
        bmwTab = actionBar.newTab().setIcon(R.drawable.bmw_logo);
        toyotaTab = actionBar.newTab().setIcon(R.drawable.toyota_logo);
        fordTab = actionBar.newTab().setIcon(R.drawable.ford_logo);

        // Setting tab listeners.
        bmwTab.setTabListener(new TabListener(bmwFragmentTab));
        toyotaTab.setTabListener(new TabListener(toyotaFragmentTab));
        fordTab.setTabListener(new TabListener(fordFragmentTab));

        // Adding tabs to the ActionBar.
        actionBar.addTab(bmwTab);
        actionBar.addTab(toyotaTab);
        actionBar.addTab(fordTab);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
