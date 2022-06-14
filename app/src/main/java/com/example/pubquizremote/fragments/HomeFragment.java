package com.example.pubquizremote.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import com.example.pubquizremote.adapters.ScoringTableAdapter;
import com.example.pubquizremote.databinding.FragmentHomeBinding;
import com.example.pubquizremote.dataobjects.PlayerData;
import com.example.pubquizremote.models.SharedRoundsViewModel;
import java.util.List;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    public List<PlayerData> pointsDataList; //= new ArrayList<PlayerData>();
    private SharedRoundsViewModel sharedRoundsViewModel = new SharedRoundsViewModel();
    ScoringTableAdapter scoringTableAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        sharedRoundsViewModel.getDataForPlayerScoreTable();

        final Observer<List<PlayerData>> resultObserver = new Observer<List<PlayerData>>(){
        @Override
        public void onChanged(@Nullable final List<PlayerData> result){
            pointsDataList = result;
            pointsDataList.sort((o1,o2) -> Integer.valueOf(o2.getPoints()).compareTo(Integer.valueOf(o1.getPoints())));
            scoringTableAdapter = new ScoringTableAdapter(getContext(), pointsDataList);
            binding.ScoringTable.setAdapter(scoringTableAdapter);

            }
        };
        sharedRoundsViewModel.getPointsDataListLiveData().observe(getViewLifecycleOwner(),resultObserver);


        binding.buttonUpdateScoringTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(),"Clicked: ScoringTable update",Toast.LENGTH_SHORT).show();
                sharedRoundsViewModel.getDataForPlayerScoreTable();
            }
        });


    }


}