package com.example.pubquizremote;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.example.pubquizremote.R;
import com.example.pubquizremote.databinding.FragmentRoundOneBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class RoundOneFragment extends Fragment implements View.OnClickListener {


    private FragmentRoundOneBinding binding;
    FirebaseAuth auth;
    String uid;
    private String link;
    HashMap<String, String> imageUrls = new HashMap<String, String>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRoundOneBinding.inflate(inflater, container, false);
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


        String[] questions = {"11","12","13","14","15","16"};
        for (String s: questions) {

            FirebaseDatabase database = FirebaseDatabase.getInstance("https://pub-quiz-remote-default-rtdb.europe-west1.firebasedatabase.app");
            auth = FirebaseAuth.getInstance();
            String uid = auth.getCurrentUser().getUid();
            DatabaseReference ref_round_one = database.getReference("question_answer_pairs").child("1").child(s).child("picture");
            ref_round_one.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    link = dataSnapshot.getValue(String.class);
                    //Log.w("Debug_A", "Link: "+link );

                    switch (s) {
                        case "11":
                            Picasso.get().load(link).into(binding.pic11);
                            imageUrls.put("pic11",link);
                        case "12":
                            Picasso.get().load(link).into(binding.pic12);
                            imageUrls.put("pic12",link);
                        case "13":
                            Picasso.get().load(link).into(binding.pic13);
                            imageUrls.put("pic13",link);
                        case "14":
                            Picasso.get().load(link).into(binding.pic14);
                            imageUrls.put("pic14",link);
                        case "15":
                            Picasso.get().load(link).into(binding.pic15);
                            imageUrls.put("pic15",link);
                        case "16":
                            Picasso.get().load(link).into(binding.pic16);
                            imageUrls.put("pic16",link);
                    }
                }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // we are showing that error message in toast
                        Log.w("Debug_A", "error: loading image", databaseError.toException());
                    }
                });
            };


        binding.pic11.setOnClickListener(this);
        binding.pic12.setOnClickListener(this);
        binding.pic13.setOnClickListener(this);
        binding.pic14.setOnClickListener(this);
        binding.pic15.setOnClickListener(this);
        binding.pic16.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        Navigation.findNavController(getView()).navigate(R.id.action_nav_round_one_to_fullScreenImageFragment);
        Bundle result = new Bundle();
        switch (view.getId()) {
            case R.id.pic11:
                result.putString("bundleKey",imageUrls.get("pic11"));
                break;
            case R.id.pic12:
                result.putString("bundleKey",imageUrls.get("pic12"));
                break;
            case R.id.pic13:
                result.putString("bundleKey",imageUrls.get("pic13"));
                break;
            case R.id.pic14:
                result.putString("bundleKey",imageUrls.get("pic14"));
                break;
            case R.id.pic15:
                result.putString("bundleKey",imageUrls.get("pic15"));
                break;
            case R.id.pic16:
                result.putString("bundleKey",imageUrls.get("pic16"));
                break;
        }
        getParentFragmentManager().setFragmentResult("requestKey", result);
    }
}