package com.sehalsein.e_park.UserFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sehalsein.e_park.Adapter.MoreAdapter;
import com.sehalsein.e_park.Model.MoreOption;
import com.sehalsein.e_park.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserSettingsPage extends Fragment {

    private RecyclerView recyclerView;

    public UserSettingsPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.fragment_user_settings_page, container, false);

        recyclerView = layout.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new MoreAdapter(createList(), getActivity()));


        return layout;
    }

    private List<MoreOption> createList() {
        List<MoreOption> result = null;
        try {

            String title[] = {
                    "Add Card",
                    "Previous booking",
                    "Pay",
                    "Logout",
            };

            String description[] = {
                    "Save card detail",
                    "",
                    "Pay for parking",
                    "",
            };

            String code[] = {
                    "userAddCard",
                    "userBookings",
                    "userPay",
                    "Logout",
            };

            int icons[] = {
                    R.drawable.ic_credit_card_black_24dp,
                    R.drawable.ic_bookmark_black_24dp,
                    R.drawable.ic_payment_black_24dp,
                    R.drawable.ic_exit_to_app_black_24dp,
            };

            result = new ArrayList<MoreOption>();
            for (int i = 0; i < title.length ; i++) {
                MoreOption moreOption = new MoreOption(title[i],description[i%description.length],icons[i%icons.length]);
                moreOption.setCode(code[i]);
                result.add(moreOption);
            }
        } catch (Exception e) {
            System.out.print(e);
        }
        return result;
    }

}
