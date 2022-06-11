package com.example.pubquizremote.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.pubquizremote.R;
import com.example.pubquizremote.dataobjects.PlayerData;
import com.example.pubquizremote.fragments.HomeFragment;
import java.util.List;


public class ScoringTableAdapter extends BaseAdapter {

    private Context context;
    private TextView playerName, playerPoints,playerRank;
    HomeFragment homeFragment;
    public List<PlayerData> pointsDataList;

    public ScoringTableAdapter(Context context, List<PlayerData> pointsDataList) {
        this.context = context;
        this.pointsDataList = pointsDataList;
    }

    @Override
    public int getCount() {
        return pointsDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View scoringTableRowView, ViewGroup parent) {
        scoringTableRowView = LayoutInflater.from(context).inflate(R.layout.fragment_home_scoring_table_row, parent, false);

        homeFragment = new HomeFragment();
        playerName = scoringTableRowView.findViewById(R.id.playerName);
        playerPoints = scoringTableRowView.findViewById(R.id.playerPoints);
        playerRank = scoringTableRowView.findViewById(R.id.playerRank);

        playerRank.setText(String.valueOf(position+1));
        playerName.setText(pointsDataList.get(position).getName());
        playerPoints.setText(pointsDataList.get(position).getPoints());

        return scoringTableRowView;
    }

}
