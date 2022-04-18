package com.example.pubquizremote;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.pubquizremote.databinding.FragmentImageRoundBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class ImageRoundFragment extends Fragment {

    private FragmentImageRoundBinding binding;
    static AnswersPlayer answersPlayer;
    HashMap<String, String> imageUrls = new HashMap<String, String>();
    private String link;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentImageRoundBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseDatabase database = RoundFragment.database;

        load_images_from_db(RoundFragment.questionNos);

        /*
        answersPlayer = new AnswersPlayer(
            binding.spinnerAnswerOptions1.getSelectedItem().toString(),
            binding.spinnerAnswerOptions2.getSelectedItem().toString(),
            binding.spinnerAnswerOptions3.getSelectedItem().toString(),
            binding.spinnerAnswerOptions4.getSelectedItem().toString(),
            binding.spinnerAnswerOptions5.getSelectedItem().toString(),
            binding.spinnerAnswerOptions6.getSelectedItem().toString()
        );

         */


    }

    void load_images_from_db(String[] questions) {

        for (String s : questions) {

            FirebaseDatabase database = RoundFragment.database;
            FirebaseAuth auth = RoundFragment.auth;
            String uid = auth.getCurrentUser().getUid();
            DatabaseReference ref_round_one = database.getReference("question_answer_pairs").child("1").child(s);
            ref_round_one.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    link = dataSnapshot.child("picture").getValue(String.class);
                    //page = dataSnapshot.child("page").getValue().toString();
                    //label = dataSnapshot.child("label").getValue().toString();
                    //correct_answer = dataSnapshot.child("correct_answer").getValue().toString();

                    switch (s) {
                        case "1":
                            Picasso.get().load(link).into(binding.pic1);
                            imageUrls.put("pic1", link);
                            //binding.Label1.setText(label);
                            //answer_options.set(3,correct_answer);
                            break;
                        case "2":
                            Picasso.get().load(link).into(binding.pic2);
                            imageUrls.put("pic2", link);
                            //binding.Label2.setText(label);
                            //answer_options.set(5,correct_answer);
                            break;
                        case "3":
                            Picasso.get().load(link).into(binding.pic3);
                            imageUrls.put("pic3", link);
                            //binding.Label3.setText(label);
                            //answer_options.set(2,correct_answer);
                            break;
                        case "4":
                            Picasso.get().load(link).into(binding.pic4);
                            imageUrls.put("pic4", link);
                            //binding.Label4.setText(label);
                            //answer_options.set(1,correct_answer);
                            break;
                        case "5":
                            Picasso.get().load(link).into(binding.pic5);
                            imageUrls.put("pic5", link);
                            //binding.Label5.setText(label);
                            //answer_options.set(4,correct_answer);
                            break;
                        case "6":
                            Picasso.get().load(link).into(binding.pic6);
                            imageUrls.put("pic6", link);
                            //binding.Label6.setText(label);
                            //answer_options.set(0,correct_answer);
                            break;

                    }

                    //set_player_answer_options(answer_options);

                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // we are showing that error message in toast
                    Log.w("Debug_A", "error: loading image", databaseError.toException());
                }

            });
        }

    }


}