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

import com.yicao.calculationtest.databinding.FragmentWinBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class WinFragment extends Fragment {


    public WinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final MyViewModel viewModel;
        viewModel = new ViewModelProvider(requireActivity(),
                new SavedStateViewModelFactory(requireActivity().getApplication(), requireActivity()))
                .get(MyViewModel.class);

        FragmentWinBinding binding;
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_win, container, false);
        binding.setData(viewModel);
        binding.setLifecycleOwner(requireActivity());
        binding.button.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_winFragment_to_honeFragment);
        });

        return binding.getRoot();
    }

}
