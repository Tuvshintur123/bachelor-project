package com.example.myhealth.ui.question;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.myhealth.R;
import com.example.myhealth.ui.signup.SignUpViewModel;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionBirthdayFragment extends Fragment {

    private Button mBtnNext;
    private DatePicker mDatePicker;
    private SignUpViewModel signUpViewModel;

    public QuestionBirthdayFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_question_birthday, container, false);
        mBtnNext = root.findViewById(R.id.btn_next);
        mDatePicker = root.findViewById(R.id.datePicker_birthday);
        signUpViewModel = new ViewModelProvider(requireActivity()).get(SignUpViewModel.class);
        mBtnNext.setOnClickListener(v -> {
            if (signUpViewModel.getGoal().getValue().equals("Жингээ барих")) {
                signUpViewModel.setTargetWeight(signUpViewModel.getCurrentWeight().getValue());
                Navigation.findNavController(v).navigate(R.id.navigate_question_birthday_to_signUp);
            }
            else
                Navigation.findNavController(v).navigate(R.id.navigate_question_birthday_to_question_target_weight);
        });
        mDatePicker.setOnDateChangedListener((view, year, monthOfYear, dayOfMonth) -> {
            Calendar date = Calendar.getInstance();
            date.set(year, monthOfYear, dayOfMonth);
            signUpViewModel.setBirthday(date);
            mBtnNext.setEnabled(true);
        });
        return root;
    }
}
