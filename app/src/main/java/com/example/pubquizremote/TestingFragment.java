package com.example.pubquizremote;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.pubquizremote.databinding.FragmentTestingBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TestingFragment extends Fragment implements View.OnClickListener,RoundFragment{


    private FragmentTestingBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    String uid;
    private String link;
    HashMap<String, String> imageUrls = new HashMap<String, String>();
    final String[] questionsPage1 = {"1", "2", "3", "4", "5", "6"};
    final String[] questionsPage2 = {"17", "18", "19", "110", "111", "112"};
    String[] questions;
    private int pageButtonId;
    private String page;
    private String label;
    private String correct_answer;
    List<String> answer_options = new ArrayList<String>();



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentTestingBinding.inflate(inflater, container, false);
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
        binding.UserSaveAnswers.setOnClickListener(this);

        // initialising arraylist with default values. These will be overwritten by database values within load_images_from_db().onDataChange()
        answer_options.add("1");
        answer_options.add("2");
        answer_options.add("3");
        answer_options.add("4");
        answer_options.add("5");
        answer_options.add("6");

    }



    private void set_player_answer_options(List answer_options) {

        Spinner spin11 = binding.spinnerAnswerOptions1;
        Spinner spin12 = binding.spinnerAnswerOptions2;
        Spinner spin13 = binding.spinnerAnswerOptions3;
        Spinner spin14 = binding.spinnerAnswerOptions4;
        Spinner spin15 = binding.spinnerAnswerOptions5;
        Spinner spin16 = binding.spinnerAnswerOptions6;
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
                    correct_answer = dataSnapshot.child("correct_answer").getValue().toString();



                    //Log.w("Debug_A", "Link1: " + link);
                    switch (s) {
                        case "1":
                            Picasso.get().load(link).into(binding.pic1);
                            imageUrls.put("pic1", link);
                            binding.Label1.setText(label);
                            answer_options.set(3,correct_answer);
                            break;
                        case "2":
                            Picasso.get().load(link).into(binding.pic2);
                            imageUrls.put("pic2", link);
                            binding.Label2.setText(label);
                            answer_options.set(5,correct_answer);
                            break;
                        case "3":
                            Picasso.get().load(link).into(binding.pic3);
                            imageUrls.put("pic3", link);
                            binding.Label3.setText(label);
                            answer_options.set(2,correct_answer);
                            break;
                        case "4":
                            Picasso.get().load(link).into(binding.pic4);
                            imageUrls.put("pic4", link);
                            binding.Label4.setText(label);
                            answer_options.set(1,correct_answer);
                            break;
                        case "5":
                            Picasso.get().load(link).into(binding.pic5);
                            imageUrls.put("pic5", link);
                            binding.Label5.setText(label);
                            answer_options.set(4,correct_answer);
                            break;
                        case "6":
                            Picasso.get().load(link).into(binding.pic6);
                            imageUrls.put("pic6", link);
                            binding.Label6.setText(label);
                            answer_options.set(0,correct_answer);
                            break;
                        case "17":
                            Picasso.get().load(link).into(binding.pic1);
                            imageUrls.put("pic1", link);
                            binding.Label1.setText(label);
                            answer_options.set(0,correct_answer);
                            break;
                        case "18":
                            Picasso.get().load(link).into(binding.pic2);
                            imageUrls.put("pic2", link);
                            binding.Label2.setText(label);
                            answer_options.set(1,correct_answer);
                            break;
                        case "19":
                            Picasso.get().load(link).into(binding.pic3);
                            imageUrls.put("pic3", link);
                            binding.Label3.setText(label);
                            answer_options.set(2,correct_answer);
                            break;
                        case "110":
                            Picasso.get().load(link).into(binding.pic4);
                            imageUrls.put("pic4", link);
                            binding.Label4.setText(label);
                            answer_options.set(3,correct_answer);
                            break;
                        case "111":
                            Picasso.get().load(link).into(binding.pic5);
                            imageUrls.put("pic5", link);
                            binding.Label5.setText(label);
                            answer_options.set(4,correct_answer);
                            break;
                        case "112":
                            Picasso.get().load(link).into(binding.pic6);
                            imageUrls.put("pic6", link);
                            binding.Label6.setText(label);
                            answer_options.set(5,correct_answer);
                            break;
                    }

                    set_player_answer_options(answer_options);

                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // we are showing that error message in toast
                    Log.w("Debug_A", "error: loading image", databaseError.toException());
                }

            });
        }

    }

    void player_answers_to_db(){

        String uid = auth.getCurrentUser().getUid();
        DatabaseReference ref_player_answers = database.getReference("players").child(uid).child("1");

        String one = "11"; String two = "12";String three = "13";
        String four = "14";String five = "15";String six = "16";
        if (binding.radioButtonTwo.isChecked()) {
            one = "17";two = "18";three = "19";
            four = "110";five = "111";six = "112";
        }

        ref_player_answers.child(one).setValue(binding.spinnerAnswerOptions1.getSelectedItem().toString());
        ref_player_answers.child(two).setValue(binding.spinnerAnswerOptions2.getSelectedItem().toString());
        ref_player_answers.child(three).setValue(binding.spinnerAnswerOptions3.getSelectedItem().toString());
        ref_player_answers.child(four).setValue(binding.spinnerAnswerOptions4.getSelectedItem().toString());
        ref_player_answers.child(five).setValue(binding.spinnerAnswerOptions5.getSelectedItem().toString());
        ref_player_answers.child(six).setValue(binding.spinnerAnswerOptions6.getSelectedItem().toString());


    }

    @Override
    public void onClick(View view) {

        String tag = String.valueOf(view.getTag());
        //Log.w("Debug_A","Tag: "+tag);

        if (tag.equals("radioButtonOne")) {
            Toast.makeText(getContext(), "Wechsel zu Seite 1", Toast.LENGTH_SHORT).show();
            load_images_from_db(questionsPage1);
        }
        else if (tag.equals("radioButtonTwo")) {
            Toast.makeText(getContext(), "Wechsel zu Seite 2", Toast.LENGTH_SHORT).show();
            load_images_from_db(questionsPage2);
        }
        else if (tag.equals("UserSaveAnswers")) {
            Toast.makeText(getContext(), "User Input button pressed", Toast.LENGTH_SHORT).show();
            player_answers_to_db();
        }
        else {
            Log.w("Debug_A", "No Click event found in onClick()-method" );
        }
        /*
        else {
            Navigation.findNavController(getView()).navigate(R.id.action_nav_round_one_to_fullScreenImageFragment);
            Bundle result = new Bundle();
            result.putString("bundleKey",imageUrls.get(tag));
            getParentFragmentManager().setFragmentResult("requestKey", result);
        }
         */
    }
}