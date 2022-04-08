package com.example.pubquizremote;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
    private int pageButtonId;
    private String page;
    //private RadioButton selectedPageButton;


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

        Boolean page1 = binding.radioButtonOne.isChecked();
        Boolean page2 = binding.radioButtonOne.isChecked();
        Log.w("Debug_A","Output: "+String.valueOf(page1));

        String[] questions = {"1","2","3","4","5","6"};
        for (String s: questions) {

            FirebaseDatabase database = FirebaseDatabase.getInstance("https://pub-quiz-remote-default-rtdb.europe-west1.firebasedatabase.app");
            auth = FirebaseAuth.getInstance();
            String uid = auth.getCurrentUser().getUid();
            //DatabaseReference ref_round_one = database.getReference("question_answer_pairs").child("1").child(s).child("picture");
            DatabaseReference ref_round_one = database.getReference("question_answer_pairs").child("1").child(s);
            ref_round_one.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    //link = dataSnapshot.getValue(String.class);
                    link = dataSnapshot.child("picture").getValue(String.class);
                    //page = dataSnapshot.child("page").getValue(String.class);

                    //if (page.equals("1")) {
                        switch (s) {
                            case "1":
                                Picasso.get().load(link).into(binding.pic1);
                                imageUrls.put("pic11", link);
                            case "2":
                                Picasso.get().load(link).into(binding.pic2);
                                imageUrls.put("pic12", link);
                            case "3":
                                Picasso.get().load(link).into(binding.pic3);
                                imageUrls.put("pic13", link);
                            case "4":
                                Picasso.get().load(link).into(binding.pic4);
                                imageUrls.put("pic14", link);
                            case "5":
                                Picasso.get().load(link).into(binding.pic5);
                                imageUrls.put("pic15", link);
                            case "6":
                                Picasso.get().load(link).into(binding.pic6);
                                imageUrls.put("pic16", link);
                        }
                 /*
                }
                    else if (page.equals("2")){
                        switch (s) {
                            case "1":
                                Picasso.get().load(link).into(binding.pic1);
                                imageUrls.put("pic21", link);
                            case "2":
                                Picasso.get().load(link).into(binding.pic2);
                                imageUrls.put("pic22", link);
                            case "3":
                                Picasso.get().load(link).into(binding.pic3);
                                imageUrls.put("pic23", link);
                            case "4":
                                Picasso.get().load(link).into(binding.pic4);
                                imageUrls.put("pic24", link);
                            case "5":
                                Picasso.get().load(link).into(binding.pic5);
                                imageUrls.put("pic25", link);
                            case "6":
                                Picasso.get().load(link).into(binding.pic6);
                                imageUrls.put("pic26", link);
                        }
                    }
                    else {
                        Toast.makeText(getContext(), "Bitte oben eine Seite auswählen", Toast.LENGTH_SHORT).show();
                    }
                    */

                }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // we are showing that error message in toast
                        Log.w("Debug_A", "error: loading image", databaseError.toException());
                    }
                });
            };


        binding.pic1.setOnClickListener(this);
        binding.pic2.setOnClickListener(this);
        binding.pic3.setOnClickListener(this);
        binding.pic4.setOnClickListener(this);
        binding.pic5.setOnClickListener(this);
        binding.pic6.setOnClickListener(this);
        binding.radioButtonOne.setOnClickListener(this);

    }





    @Override
    public void onClick(View view) {

        String tag = String.valueOf(view.getTag());
        Log.w("Debug_A","Tag: "+String.valueOf(tag));

        if (tag.equals("radioButtonOne")) {
            Toast.makeText(getContext(), "Page 1 clicked", Toast.LENGTH_SHORT).show();
        }
        else {
            Navigation.findNavController(getView()).navigate(R.id.action_nav_round_one_to_fullScreenImageFragment);
            Bundle result = new Bundle();
            result.putString("bundleKey",imageUrls.get(tag));
    }



        /*
        Navigation.findNavController(getView()).navigate(R.id.action_nav_round_one_to_fullScreenImageFragment);
        Bundle result = new Bundle();
        switch (view.getId()) {
            case R.id.radioButtonOne:
                Toast.makeText(getContext(), "Page 1 clicked", Toast.LENGTH_SHORT).show();
                break;
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

        */
    }
}