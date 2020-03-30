package com.yicao.calculationtest;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.yicao.calculationtest.databinding.FragmentHomeBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false);

        final MyViewModel viewModel;
        viewModel = new ViewModelProvider(requireActivity(),
                new SavedStateViewModelFactory(requireActivity().getApplication(), requireActivity()))
                .get(MyViewModel.class);

        FragmentHomeBinding binding;
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.setData(viewModel);
        binding.setLifecycleOwner(requireActivity());

        binding.button2.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_honeFragment_to_questionFragment);
            viewModel.getCurrentScore().setValue(0);
            viewModel.generate();
        });

        return binding.getRoot();

    }

}
