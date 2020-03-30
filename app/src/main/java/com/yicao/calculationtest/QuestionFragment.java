package com.yicao.calculationtest;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.yicao.calculationtest.databinding.FragmentQuestionBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {


    public QuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final MyViewModel viewModel;
        viewModel = new ViewModelProvider(requireActivity(),
                new SavedStateViewModelFactory(requireActivity().getApplication(), requireActivity()))
                .get(MyViewModel.class);

        FragmentQuestionBinding binding;
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_question, container, false);
        binding.setData(viewModel);
        binding.setLifecycleOwner(requireActivity());

        StringBuilder sb = new StringBuilder();
        View.OnClickListener listener = v -> {
            Button button = (Button) v;
            sb.append(button.getText().toString());
            binding.textView9.setText(sb.toString());
        };

        binding.button0.setOnClickListener(listener);
        binding.button1.setOnClickListener(listener);
        binding.button2.setOnClickListener(listener);
        binding.button3.setOnClickListener(listener);
        binding.button4.setOnClickListener(listener);
        binding.button5.setOnClickListener(listener);
        binding.button6.setOnClickListener(listener);
        binding.button7.setOnClickListener(listener);
        binding.button8.setOnClickListener(listener);
        binding.button9.setOnClickListener(listener);

        binding.buttonClear.setOnClickListener(v->{
            sb.setLength(0);
            binding.textView9.setText("");
        });

        binding.buttonSubmit.setOnClickListener(v->{
            //todo:此处数据可能溢出
            if (sb.length() == 0) {
                sb.append(-1);
            }
            try {
                if (Integer.parseInt(sb.toString()) == viewModel.getAnswer().getValue()) {
                    viewModel.answerCorrect();
                    sb.setLength(0);
                    binding.textView9.setText(R.string.question_msg);
                } else {
                    NavController controller = Navigation.findNavController(v);
                    if (viewModel.win_flag) {
                        controller.navigate(R.id.action_questionFragment_to_winFragment);
                        viewModel.win_flag = false;
                        viewModel.save();
                    } else {
                        controller.navigate(R.id.action_questionFragment_to_loseFragment);
                    }
                }
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "数据格式不正确", Toast.LENGTH_SHORT).show();
            }

        });
        return binding.getRoot();
    }

}
