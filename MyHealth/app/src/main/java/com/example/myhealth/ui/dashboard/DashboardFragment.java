package com.example.myhealth.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.myhealth.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private CardView mCardViewBreakfast, mCardViewLunch, mCardViewDinner, mCardViewSnack;
    private FloatingActionButton actionButton;
    private String date;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BottomNavigationView view = getActivity().findViewById(R.id.nav_view);
        actionButton = getActivity().findViewById(R.id.action_button);
        actionButton.setVisibility(View.VISIBLE);
        if (view.getVisibility() == View.GONE)
            view.setVisibility(View.VISIBLE);
        dashboardViewModel =
                new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
//        final TextView textView = root.findViewById(R.id.txt_date);
//        dashboardViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        /************date***********/
        date = "2020-04-29";
        /***************************/

        mCardViewDinner = root.findViewById(R.id.cardView_dinner);
        mCardViewSnack = root.findViewById(R.id.cardView_snack);
        mCardViewBreakfast = root.findViewById(R.id.cardView_breakfast);
        mCardViewLunch = root.findViewById(R.id.cardView_lunch);
        mCardViewBreakfast.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("mealName", "Өглөөний цай");
            navigate(bundle);
        });

        mCardViewLunch.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("mealName", "Үдийн хоол");
            navigate(bundle);
        });
        mCardViewDinner.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("mealName", "Оройн хоол");
            navigate(bundle);
        });
        mCardViewSnack.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("mealName", "Хөнгөн зууш");
            navigate(bundle);
        });
        return root;
    }

    private void navigate(Bundle bundle) {
        bundle.putString("mealDate", date);
        Navigation.findNavController(this.requireView()).navigate(R.id.action_navigation_dashboard_to_navigation_search, bundle);
    }
}