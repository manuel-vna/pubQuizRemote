package com.example.pubquizremote;

public class AnswersPlayer extends Player{

        public String playerAnswer1;
        public String playerAnswer2;
        public String playerAnswer3;
        public String playerAnswer4;
        public String playerAnswer5;
        public String playerAnswer6;

    public AnswersPlayer(String playerAnswer1, String playerAnswer2, String playerAnswer3,
                         String playerAnswer4, String playerAnswer5, String playerAnswer6){
        this(null, null, null,playerAnswer1,playerAnswer2,playerAnswer3,playerAnswer4,playerAnswer5,playerAnswer6);

    }

        public AnswersPlayer(String uid, String name, String points, String playerAnswer1, String playerAnswer2, String playerAnswer3,
                             String playerAnswer4, String playerAnswer5, String playerAnswer6){
            this.uid = uid;
            this.name = name;
            this.points = points;
            this.playerAnswer1 = playerAnswer1;
            this.playerAnswer2 = playerAnswer2;
            this.playerAnswer3 = playerAnswer3;
            this.playerAnswer4 = playerAnswer4;
            this.playerAnswer5 = playerAnswer5;
            this.playerAnswer6 = playerAnswer6;

        }

}

   
