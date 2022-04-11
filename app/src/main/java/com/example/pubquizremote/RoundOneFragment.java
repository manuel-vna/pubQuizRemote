package com.example.pubquizremote;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.example.pubquizremote.R;
import com.example.pubquizremote.databinding.FragmentRoundOneBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    FirebaseDatabase database;
    FirebaseAuth auth;
    String uid;
    private String link;
    HashMap<String, String> imageUrls = new HashMap<String, String>();
    final String[] questionsPage1 = {"11", "12", "13", "14", "15", "16"};
    final String[] questionsPage2 = {"17", "18", "19", "110", "111", "112"};
    String[] questions;
    private int pageButtonId;
    private String page;
    private String label;


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

        load_images_from_db(questionsPage1);

        binding.pic1.setOnClickListener(this);
        binding.pic2.setOnClickListener(this);
        binding.pic3.setOnClickListener(this);
        binding.pic4.setOnClickListener(this);
        binding.pic5.setOnClickListener(this);
        binding.pic6.setOnClickListener(this);
        binding.radioButtonOne.setOnClickListener(this);
        binding.radioButtonTwo.setOnClickListener(this);
        
        set_player_answer_options();

        //read_labels_from_db();

    }

/*
    private void read_labels_from_db() {

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://pub-quiz-remote-default-rtdb.europe-west1.firebasedatabase.app");
        auth = FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid();

        DatabaseReference current_player = database.getReference("question_answer_pairs").child("1").child("11").child("label");
        current_player.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    binding.Label11.setText(task.getResult().getValue().toString());
                }
            }
        });
    }

 */



    private void set_player_answer_options() {

        String[] answer_options = { "1", "2", "3", "4", "5","6" };
        Spinner spin11 = binding.spinnerAnswerOptions11;
        Spinner spin12 = binding.spinnerAnswerOptions12;
        Spinner spin13 = binding.spinnerAnswerOptions13;
        Spinner spin14 = binding.spinnerAnswerOptions14;
        Spinner spin15 = binding.spinnerAnswerOptions15;
        Spinner spin16 = binding.spinnerAnswerOptions16;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, answer_options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin11.setAdapter(adapter);
        spin12.setAdapter(adapter);
        spin13.setAdapter(adapter);
        spin14.setAdapter(adapter);
        spin15.setAdapter(adapter);
        spin16.setAdapter(adapter);
    }


    void load_images_from_db(String[] questions) {

        for (String s : questions) {

            FirebaseDatabase database = FirebaseDatabase.getInstance("https://pub-quiz-remote-default-rtdb.europe-west1.firebasedatabase.app");
            auth = FirebaseAuth.getInstance();
            String uid = auth.getCurrentUser().getUid();
            DatabaseReference ref_round_one = database.getReference("question_answer_pairs").child("1").child(s);
            ref_round_one.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    link = dataSnapshot.child("picture").getValue(String.class);
                    page = dataSnapshot.child("page").getValue().toString();

                    label = dataSnapshot.child("label").getValue().toString();


                    Log.w("Debug_A", "Link1: " + link);
                    switch (s) {
                        case "11":
                            Picasso.get().load(link).into(binding.pic1);
                            imageUrls.put("pic1", link);
                            binding.Label1.setText(label);
                            break;
                        case "12":
                            Picasso.get().load(link).into(binding.pic2);
                            imageUrls.put("pic2", link);
                            binding.Label2.setText(label);
                            break;
                        case "13":
                            Picasso.get().load(link).into(binding.pic3);
                            imageUrls.put("pic3", link);
                            binding.Label3.setText(label);
                            break;
                        case "14":
                            Picasso.get().load(link).into(binding.pic4);
                            imageUrls.put("pic4", link);
                            binding.Label4.setText(label);
                            break;
                        case "15":
                            Picasso.get().load(link).into(binding.pic5);
                            imageUrls.put("pic5", link);
                            binding.Label5.setText(label);
                            break;
                        case "16":
                            Picasso.get().load(link).into(binding.pic6);
                            imageUrls.put("pic6", link);
                            binding.Label6.setText(label);
                            break;
                        case "17":
                            Picasso.get().load(link).into(binding.pic1);
                            imageUrls.put("pic1", link);
                            binding.Label1.setText(label);
                            break;
                        case "18":
                            Picasso.get().load(link).into(binding.pic2);
                            imageUrls.put("pic2", link);
                            binding.Label2.setText(label);
                            break;
                        case "19":
                            Picasso.get().load(link).into(binding.pic3);
                            imageUrls.put("pic3", link);
                            binding.Label3.setText(label);
                            break;
                        case "110":
                            Picasso.get().load(link).into(binding.pic4);
                            imageUrls.put("pic4", link);
                            binding.Label4.setText(label);
                            break;
                        case "111":
                            Picasso.get().load(link).into(binding.pic5);
                            imageUrls.put("pic5", link);
                            binding.Label5.setText(label);
                            break;
                        case "112":
                            Picasso.get().load(link).into(binding.pic6);
                            imageUrls.put("pic6", link);
                            binding.Label6.setText(label);
                            break;
                    }

                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // we are showing that error message in toast
                    Log.w("Debug_A", "error: loading image", databaseError.toException());
                }

            });
        }

    }


    @Override
    public void onClick(View view) {

        String tag = String.valueOf(view.getTag());
        Log.w("Debug_A","Tag: "+tag);

        if (tag.equals("radioButtonOne")) {
            Toast.makeText(getContext(), "Wechsel zu Seite 1", Toast.LENGTH_SHORT).show();
            load_images_from_db(questionsPage1);
        }
        else if (tag.equals("radioButtonTwo")) {
            Toast.makeText(getContext(), "Wechsel zu Seite 2", Toast.LENGTH_SHORT).show();
            load_images_from_db(questionsPage2);
        }
        else {
            Navigation.findNavController(getView()).navigate(R.id.action_nav_round_one_to_fullScreenImageFragment);
            Bundle result = new Bundle();
            result.putString("bundleKey",imageUrls.get(tag));
            getParentFragmentManager().setFragmentResult("requestKey", result);
        }
    }
}