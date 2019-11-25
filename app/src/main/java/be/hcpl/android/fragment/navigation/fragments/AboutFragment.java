package be.hcpl.android.fragment.navigation.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.aboutlibraries.ui.LibsActivity;

import be.hcpl.android.fragment.navigation.BuildConfig;
import be.hcpl.android.fragment.navigation.R;

/**
 * Created by hanscappelle on 16/10/14.
 */
public class AboutFragment extends TemplateFragment {

    public static AboutFragment createInstance(){
        return new AboutFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // example of getting values from buildconfig, html and linkification
        TextView aboutText = (TextView)view.findViewById(R.id.text_about);
        aboutText.setText(Html.fromHtml("<center><b>Fragment Navigation</b><p/>version "
                + BuildConfig.VERSION_NAME + "<p/>Available from http://androidco.de.</center>"));
        Linkify.addLinks(aboutText, Linkify.ALL);

        // example of easy listing of libraries with a ... library :-)
        ((Button)view.findViewById(R.id.button_libraries)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*

                // fragment appraoch not possible since support

                Bundle bundle = new Bundle();
                //Pass the fields of your application to the lib so it can find all external lib information
                bundle.putStringArray(Libs.BUNDLE_FIELDS, Libs.toStringArray(R.string.class.getFields()));
                //Define the libs you want (only those which don't include the information, and are not autoDetected)
                //(OPTIONAL if all used libraries offer the information, or are autoDetected)
                bundle.putStringArray(Libs.BUNDLE_LIBS, new String[]{"AndroidIconify", "ActiveAndroid", "FButton", "Crouton", "HoloGraphLibrary", "ShowcaseView", "NineOldAndroids", "AndroidViewpagerIndicator"});

                //Display the library version (OPTIONAL)
                bundle.putBoolean(Libs.BUNDLE_VERSION, true);
                //Display the library license (OPTIONAL
                bundle.putBoolean(Libs.BUNDLE_LICENSE, true);

                //Create a new Fragment (you can do this where ever you want
                Fragment fragment = new LibsFragment();
                //Set the arguments
                fragment.setArguments(bundle);

                // switch
                ((MainActivity) getActivity()).switchContent(fragment);
                */

                /*
                //Create an intent with context and the Activity class

                Intent i = new Intent(getActivity(), LibsActivity.class);
                //Pass the fields of your application to the lib so it can find all external lib information
                i.putExtra(Libs.BUNDLE_FIELDS, Libs.toStringArray(R.string.class.getFields()));
                //Define the libs you want (only those which don't include the information, and are not autoDetected)
                //(OPTIONAL if all used libraries offer the information, or are autoDetected)
                //i.putExtra(Libs.BUNDLE_LIBS, new String[]{"dagger", "gson", "picasso", "butterknife", "timber", "retrofit", "otto"});

                //Display the library version (OPTIONAL)
                i.putExtra(Libs.BUNDLE_VERSION, true);
                //Display the library license (OPTIONAL
                i.putExtra(Libs.BUNDLE_LICENSE, true);

                //Set a title (OPTIONAL)
                i.putExtra(Libs.BUNDLE_TITLE, "Open Source");

                //Pass your theme (OPTIONAL)
                i.putExtra(Libs.BUNDLE_THEME, android.R.style.Theme_Holo);
                //Pass a custom accent color (OPTIONAL)
                //i.putExtra(Libs.BUNDLE_ACCENT_COLOR, "#3396E5");
                //Pass the information if it should use the Translucent decor (OPTIONAL) -> requires ACCENT_COLOR
                //i.putExtra(Libs.BUNDLE_TRANSLUCENT_DECOR, true);

                //start the activity
                startActivity(i);

                 */

                new LibsBuilder().withAutoDetect(true).withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR).start(getContext());



            }
        });


    }
}
