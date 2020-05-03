package com.example.myhealth.ui.question;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.myhealth.R;
import com.example.myhealth.ui.signup.SignUpViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionActivityLevelFragment extends Fragment {

    private Button mBtnNext;
    private SignUpViewModel signUpViewModel;
    private RadioGroup mRadioGroup;

    public QuestionActivityLevelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_question_activity_level, container, false);
        mBtnNext = root.findViewById(R.id.btn_next);
        mRadioGroup = root.findViewById(R.id.radioGroup);
        signUpViewModel = new ViewModelProvider(requireActivity()).get(SignUpViewModel.class);

        mBtnNext.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.navigate_question_activity_level_to_question_current_weight, null));
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mBtnNext.setEnabled(true);
                RadioButton radioButton;
                switch (checkedId) {
                    case R.id.rdBtn_sedentary:
                        radioButton = root.findViewById(R.id.rdBtn_sedentary);
                        break;
                    case R.id.rdBtn_low_active:
                        radioButton = root.findViewById(R.id.rdBtn_low_active);
                        break;
                    case R.id.rdBtn_active:
                        radioButton = root.findViewById(R.id.rdBtn_active);
                        break;
                    default:
                        radioButton = root.findViewById(R.id.rdBtn_very_active);
                        break;
                }
                signUpViewModel.setActivityLevel(radioButton.getText().toString().trim());
            }
        });


        return root;
    }
}
