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


public class RoundOneFragment extends Fragment {


    private FragmentRoundOneBinding binding;
    FirebaseAuth auth;
    String uid;
    private String link;


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

        Log.w("Debug_A", "RoundOneFragment: "+String.valueOf(requireActivity()));

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://pub-quiz-remote-default-rtdb.europe-west1.firebasedatabase.app");
        auth = FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid();
        DatabaseReference ref_image = database.getReference("question_answer_pairs").child("1").child("QustionAnswerPair").child("picture");

        ref_image.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                link = dataSnapshot.getValue(String.class);
                Picasso.get().load(link).into(binding.Round1Picture1);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // we are showing that error message in toast
                Log.w("Debug_A", "error: loading image", databaseError.toException());
            }
        });



        binding.Round1Picture1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(getView()).navigate(R.id.action_nav_round_one_to_fullScreenImageFragment);

                Bundle result = new Bundle();
                result.putString("bundleKey", link);
                getParentFragmentManager().setFragmentResult("requestKey", result);

            }
        });



    }

}