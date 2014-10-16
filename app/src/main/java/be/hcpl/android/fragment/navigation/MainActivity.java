package be.hcpl.android.fragment.navigation;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import be.hcpl.android.fragment.navigation.fragments.AboutFragment;
import be.hcpl.android.fragment.navigation.fragments.NavigationDrawerFragment;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * used to keep track of last fragment showing
     */
    public static final String KEY_LAST_FRAGMENT = "last_fragment";

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * current fragment
     */
    private Fragment mContentFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        // Restore state members from saved instance
        mContentFragment = getFragmentManager().getFragment(savedInstanceState, KEY_LAST_FRAGMENT);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (mContentFragment != null && mContentFragment.isAdded() && getFragmentManager() != null) {
            getFragmentManager().putFragment(savedInstanceState,
                    KEY_LAST_FRAGMENT, mContentFragment);
        }
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // make sure we always have some default
        if (mContentFragment == null) {
            mContentFragment = AboutFragment.createInstance();
        }
        // no backstack here
        getFragmentManager().beginTransaction()
                .replace(R.id.container, mContentFragment)
                .commit();
    }

    @Override
    public void onNavigationDrawerItemSelected(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        // use default here
        if (fragment != null) {
            // update the main content by replacing fragments
            switchContent(fragment);
        }
    }

    /**
     * helper to switch content with backstack
     *
     * @param fragment
     */
    public void switchContent(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                        // add to backstack
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
        mContentFragment = fragment;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_about) {
            AboutFragment sf = AboutFragment.createInstance();
            switchContent(sf);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}