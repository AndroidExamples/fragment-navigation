package be.hcpl.android.fragment.navigation;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import be.hcpl.android.fragment.navigation.fragments.FirstFragment;


/**
 * This main activity could be our only activity within the app. This one is responsible for the
 * fragment handling. If you need more than one activity create an (abstract) base activity with
 * the fragment handling logic.
 */
public class MainActivity extends ActionBarActivity {

    /**
     * a key to retrieve the last stored fragment from bundle
     */
    private final static String KEY_LAST_FRAGMENT = "last_fragment";

    /**
     * the current fragment loaded
     */
    private Fragment mCurrentFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // hook into on pause here
    }

    @Override
    protected void onResume() {
        super.onResume();
        // we need some default fragment here
        if( mCurrentFragment == null ){
           mCurrentFragment = new FirstFragment();
            // also important is not to add this first fragment to stack otherwise we will be able
            // to use back button to go to state of the activity without any fragments loaded
            int result = getSupportFragmentManager().beginTransaction().replace(R.id.container, mCurrentFragment)
                    .commit();
        }
        // perform the switch
        else {
            switchFragment(mCurrentFragment);
        }
        // hook into on resume here
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        mCurrentFragment = getSupportFragmentManager().getFragment(savedInstanceState, KEY_LAST_FRAGMENT);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (mCurrentFragment != null && mCurrentFragment.isAdded() && getSupportFragmentManager() != null) {
            getSupportFragmentManager().putFragment(savedInstanceState,
                    KEY_LAST_FRAGMENT, mCurrentFragment);
        }
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * helper to load other fragments in container, this is just a wrapper
     *
     * @param fragmentClass
     */
    public void switchFragment(Class<? extends Fragment> fragmentClass) {
        try {
            Fragment fragmentInstance = fragmentClass.newInstance();
            switchFragment(fragmentInstance);
        } catch (Exception e) {
            Log.e(getString(R.string.app_name), "failed to load given fragmentClass", e);
            // let's inform the user also
            Toast.makeText(getApplicationContext(), "failed to load given fragmentClass", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * fragment switching with an instance (with properties set)
     *
     * @param fragmentInstance
     */
    public void switchFragment(Fragment fragmentInstance) {
        int result = getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentInstance)
                // also handle back navigation by adding all fragments to the backstack. This step
                // is important if you want back navigation support. Note that we use the fragment
                // class as an identifier here.
                .addToBackStack(fragmentInstance.getClass().getSimpleName())
                .commit();
        mCurrentFragment = fragmentInstance;
    }

}
