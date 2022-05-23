package com.example.pubquizremote.utils;

import com.example.pubquizremote.dataobjects.AnswersPlayerData;
import com.example.pubquizremote.models.AdminSettingsViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminSettingsCalculations {

    int round_score = 0;
    String pre_update_score;
    int new_score;
    Map<String, Object> first_db_level;


    AdminSettingsViewModel adminSettingsViewModel = new AdminSettingsViewModel();


    public void set_initial_db_structure(){

        Map<String, Object> third_level_rounds_radio_group_false  = new HashMap<String, Object>() {{
            put("automated_evaluation", "false");

        }};
        Map<String, Object> third_level_rounds_radio_group_true  = new HashMap<String, Object>() {{
            put("automated_evaluation", "true");

        }};
        Map<String, Object> second_level_rounds  = new HashMap<String, Object>() {{
            put("round1", third_level_rounds_radio_group_true);
            put("round2", third_level_rounds_radio_group_true);
            put("round3", third_level_rounds_radio_group_false);
            put("round4", third_level_rounds_radio_group_false);
            put("round5", third_level_rounds_radio_group_false);
            put("round6", third_level_rounds_radio_group_true);
        }};

        first_db_level  = new HashMap<String, Object>() {{
            put("player_data", "");
            put("global_game_data", second_level_rounds);
        }};

        adminSettingsViewModel.write_initial_db_structure(first_db_level);
    }



    public void calculate_points(List<String> answerCorrectList, List<AnswersPlayerData> answers_players_objects) {

        for (AnswersPlayerData user_block : answers_players_objects) {
            if (user_block.playerAnswer0.equals(answerCorrectList.get(0))){
                round_score += 1;
            }
            if (user_block.playerAnswer1.equals(answerCorrectList.get(1))){
                round_score += 1;
            }
            if (user_block.playerAnswer2.equals(answerCorrectList.get(2))){
                round_score += 1;
            }
            if (user_block.playerAnswer3.equals(answerCorrectList.get(3))){
                round_score += 1;
            }
            if (user_block.playerAnswer4.equals(answerCorrectList.get(4))){
                round_score += 1;
            }
            if (user_block.playerAnswer5.equals(answerCorrectList.get(5))){
                round_score += 1;
            }
            pre_update_score = user_block.points;
            new_score = Integer.parseInt(pre_update_score) + round_score;
            adminSettingsViewModel.set_points_in_db(user_block.uid,new_score);
            round_score = 0;
        }

    }
}
