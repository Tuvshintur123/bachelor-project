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
public class QuestionGoalFragment extends Fragment {

    private Button mBtnNext;
    private SignUpViewModel signUpViewModel;
    private RadioGroup mRadioGroup;

    public QuestionGoalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_question_goal, container, false);
        mBtnNext = root.findViewById(R.id.btn_next);
        mRadioGroup = root.findViewById(R.id.radioGroup);
        signUpViewModel = new ViewModelProvider(requireActivity()).get(SignUpViewModel.class);

        mBtnNext.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.navigate_question_goal_to_question_gender, null));
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mBtnNext.setEnabled(true);
                RadioButton radioButton;
                switch (checkedId) {
                    case R.id.rdBtn_gain:
                        radioButton = root.findViewById(R.id.rdBtn_gain);
                        break;
                    case R.id.rdBtn_maintain:
                        radioButton = root.findViewById(R.id.rdBtn_maintain);
                        break;
                    default:
                        radioButton = root.findViewById(R.id.rdBtn_loss);
                        break;
                }
                signUpViewModel.setGoal(radioButton.getText().toString().trim());
            }
        });


        return root;
    }
}
