package com.example.pubquizremote;

public class AnswersPlayer extends Player{

        public String playerAnswer1;
        public String playerAnswer2;
        public String playerAnswer3;
        public String playerAnswer4;
        public String playerAnswer5;
        public String playerAnswer6;

    public AnswersPlayer(){
        this(null, null, null,null,null,null, null, null,null);

    }

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

    public String getPlayerAnswer1() {
        return playerAnswer1;
    }

    public void setPlayerAnswer1(String playerAnswer1) {
        this.playerAnswer1 = playerAnswer1;
    }

    public String getPlayerAnswer2() {
        return playerAnswer2;
    }

    public void setPlayerAnswer2(String playerAnswer2) {
        this.playerAnswer2 = playerAnswer2;
    }

    public String getPlayerAnswer3() {
        return playerAnswer3;
    }

    public void setPlayerAnswer3(String playerAnswer3) {
        this.playerAnswer3 = playerAnswer3;
    }

    public String getPlayerAnswer4() {
        return playerAnswer4;
    }

    public void setPlayerAnswer4(String playerAnswer4) {
        this.playerAnswer4 = playerAnswer4;
    }

    public String getPlayerAnswer5() {
        return playerAnswer5;
    }

    public void setPlayerAnswer5(String playerAnswer5) {
        this.playerAnswer5 = playerAnswer5;
    }

    public String getPlayerAnswer6() {
        return playerAnswer6;
    }

    public void setPlayerAnswer6(String playerAnswer6) {
        this.playerAnswer6 = playerAnswer6;
    }



}

   
