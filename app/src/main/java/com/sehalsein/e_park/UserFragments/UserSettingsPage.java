package com.sehalsein.e_park.UserFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sehalsein.e_park.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserSettingsPage extends Fragment {


    public UserSettingsPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_settings_page, container, false);
    }

}
