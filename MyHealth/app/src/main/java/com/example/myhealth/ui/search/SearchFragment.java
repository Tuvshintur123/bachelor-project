package com.example.myhealth.ui.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;


import com.example.myhealth.R;
import com.example.myhealth.model.CompactFood;
import com.example.myhealth.model.FoodContainer;
import com.example.myhealth.model.MealFood;
import com.example.myhealth.model.MealTemp;
import com.example.myhealth.model.Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener, FoodAdapter.OnFoodClickListener, SelectedFoodAdapter.OnFoodClickListener {

    private View root;
    private RecyclerView mRecyclerView;
    private FoodAdapter mAdapter;
    private SelectedFoodAdapter mSelectedFoodAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SearchViewModel mSearchViewModel;
    private FoodContainer foodContainer;
    private List<CompactFood> foods;
    private SearchView mSearchView;
    private TextView mTxtDone;
    private ImageView mImageViewBack;
    private ImageView mImageViewClose;
    private FloatingActionButton actionButton;
    private Profile profile;
    private Calendar mealDate;
    private String mealName;
    private SimpleDateFormat sdf;
    private TextView mTxtMealName;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseUser currentUser;
    private DatabaseReference myRef;

    public SearchFragment() {
        // Required empty public constructor
        //get selectedfoods
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_search, container, false);
        mSearchViewModel = new ViewModelProvider(requireActivity()).get(SearchViewModel.class);
        BottomNavigationView nav = getActivity().findViewById(R.id.nav_view);
        actionButton = getActivity().findViewById(R.id.action_button);
        actionButton.setVisibility(View.GONE);
        nav.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        mTxtDone = root.findViewById(R.id.txt_done);
        mSearchView = root.findViewById(R.id.searchView);
        mImageViewBack = root.findViewById(R.id.imageView_back);
        mImageViewClose = root.findViewById(R.id.imageView_close);
        mTxtMealName = root.findViewById(R.id.txt_meal_name);
        profile = Profile.getInstance();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        mealName = getArguments().getString("mealName");
        mTxtMealName.setText(mealName);
        mealDate = Calendar.getInstance();
        myRef = database.getReference("profiles").child(currentUser.getUid()).child("history").child(getArguments().getString("mealDate")).child("mealPlan").child(mealName);
        try {
            mealDate.setTime(sdf.parse(getArguments().getString("mealDate")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mSearchViewModel.getFilteredFoods().observe(getViewLifecycleOwner(), compactFoods -> {
            mAdapter.notifyDataSetChanged();
        });

//        mSearchViewModel.getSelectedFoods().observe(getViewLifecycleOwner(), mealFoods -> {
//
//        });

        mSearchViewModel.getSelectedFoods().setValue(profile.getHistory().getDate(mealDate).getMealPlan().getMealByName(mealName).getMealFoods());

//        .forEach(mealFood -> mSearchViewModel.getSelectedFoods().getValue().add(mealFood));

        createRecyclerView();

        mSearchView.setOnQueryTextListener(this);

        mTxtDone.setOnClickListener(v -> {
            if (mRecyclerView.getAdapter() != mSelectedFoodAdapter) {
                mSearchView.setQuery("", false);
                mSearchView.clearFocus();
                mRecyclerView.setAdapter(mSelectedFoodAdapter);
                if (mImageViewClose.getVisibility() == View.GONE) {
                    mImageViewBack.setVisibility(View.GONE);
                    mImageViewClose.setVisibility(View.VISIBLE);
                }
            } else {
                saveFoods();
//                profile.getHistory().getDate(mealDate).getMealPlan().getMealByName(mealName).getMealFoods().clear();
//                mSearchViewModel.getSelectedFoods().getValue().forEach(food -> profile.getHistory().getDate(mealDate).getMealPlan().getMealByName(mealName).addFood(food));
                Navigation.findNavController(this.requireView()).navigate(R.id.action_navigation_search_to_navigation_dashboard);
            }
        });

        mImageViewBack.setOnClickListener(v -> {
            mSearchView.setQuery("", false);
            mSearchView.clearFocus();
            mRecyclerView.setAdapter(mSelectedFoodAdapter);
            mImageViewBack.setVisibility(View.GONE);
            mImageViewClose.setVisibility(View.VISIBLE);

        });
        mImageViewClose.setOnClickListener(v -> {
            saveFoods();
            Navigation.findNavController(this.requireView()).navigate(R.id.action_navigation_search_to_navigation_dashboard);
        });

        return root;
    }

    private void saveFoods() {
        HashMap<String, Object> foods = new HashMap<>();
        profile.getHistory().getDate(mealDate).getMealPlan().getMealByName(mealName).getMealFoods().forEach(mealFood -> {
            foods.put(mealFood.getName(), new MealTemp(mealFood.getId(), mealFood.getDescription(), mealFood.getServingAmount(), mealFood.getServingId(), mealFood.getType()));

//            foods.put(mealFood.getName() + "/foodId", mealFood.getId());
//            foods.put(mealFood.getName() + "/description", mealFood.getDescription());
//            foods.put(mealFood.getName() + "/type", mealFood.getType());
//            foods.put(mealFood.getName() + "/servingAmount", mealFood.getServingAmount());
//            foods.put(mealFood.getName() + "/servingId", mealFood.getServingId());
        });
        myRef.setValue(foods);
    }

    public void createRecyclerView() {
        foods = FoodContainer.getInstance().getFoods();
        mRecyclerView = root.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new FoodAdapter(foods, mSearchViewModel.getSelectedFoods().getValue());
        mSelectedFoodAdapter = new SelectedFoodAdapter(mSearchViewModel.getSelectedFoods().getValue());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mSelectedFoodAdapter);
        mAdapter.setOnFoodClickListener(this);
        mSelectedFoodAdapter.setOnFoodClickListener(this);
    }

    @Override
    public void onFoodClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putLong("food_id", foods.get(position).getId());
        Navigation.findNavController(this.requireView()).navigate(R.id.action_navigation_search_to_foodDetailFragment, bundle);
    }

    @Override
    public void onClickRemove(int position) {
        mSearchViewModel.getSelectedFoods().getValue().remove(position);
        mSelectedFoodAdapter.notifyItemRemoved(position);
        mTxtDone.setVisibility(View.VISIBLE);
    }


    @Override
    public void onAddClick(CompactFood selectedFood) {
        // getFood(selectedFood.getId()) mealFood uusged nemne
//        mSearchViewModel.getFood(selectedFood.getId());
        mSearchViewModel.getSelectedFoods().getValue().add(new MealFood(selectedFood.getId(), selectedFood.getName(), selectedFood.getType(), selectedFood.getDescription(), selectedFood.getBrandName(), 0, 0));
        mSelectedFoodAdapter.notifyDataSetChanged();
        mTxtDone.setVisibility(View.VISIBLE);
//        Log.i("fatsecret", "Added " + selectedFood.getName());
//        mSearchViewModel.getSelectedFoods().getValue().forEach(x -> Log.i("fatsecret", x.getName()));

    }

    @Override
    public void onCheckClick(CompactFood selectedFood) {

        Iterator itr = mSearchViewModel.getSelectedFoods().getValue().iterator();
        while (itr.hasNext()) {
            MealFood x = (MealFood) itr.next();
            if ((long) x.getId() == (long) selectedFood.getId())
                itr.remove();
        }
        mSelectedFoodAdapter.notifyDataSetChanged();
        mTxtDone.setVisibility(View.VISIBLE);


        Log.i("fatsecret", "Removed " + selectedFood.getName());
        mSearchViewModel.getSelectedFoods().getValue().forEach(x -> Log.i("fatsecret", x.getName()));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (mRecyclerView.getAdapter() != mAdapter)
            mRecyclerView.setAdapter(mAdapter);
        if (newText.length() >= 3) {
            if (mImageViewBack.getVisibility() == View.GONE) {
                mImageViewClose.setVisibility(View.GONE);
                mImageViewBack.setVisibility(View.VISIBLE);
            }
            mSearchViewModel.getFoods(newText, 0);
        }
        return false;
    }

}
