package com.example.pubquizremote;

import androidx.fragment.app.FragmentResultListener;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pubquizremote.databinding.FullScreenImageFragmentBinding;
import com.squareup.picasso.Picasso;

public class FullScreenImageFragment extends Fragment {

    private FullScreenImageFragmentBinding binding;

    public static FullScreenImageFragment newInstance() {
        return new FullScreenImageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FullScreenImageFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {

                String link = bundle.getString("bundleKey");
                Picasso.get().load(link).into(binding.FullScreenImage);
            }
        });

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_fullScreenImageFragment_to_nav_round_one);
            }
        });

    }


}