package filipelipan.com.github.academia.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import filipelipan.com.github.academia.R;
import filipelipan.com.github.academia.adapter.TrainingAdapter;
import filipelipan.com.github.academia.database.TrainingDataSource;
import filipelipan.com.github.academia.model.Training;

public class MainActivity extends ActionBarActivity {

    private static final String IS_PREVIOUSLY_OPENED = "isPreviouslyOpened";
    private Training mTraining;
    @Bind(R.id.recyclerView)  RecyclerView mRecyclerView;
    private TrainingAdapter mAdapter;
    boolean isPreviouslyOpened;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        
        mTraining = new Training();
        isPreviouslyOpened = false;

        if(savedInstanceState != null){
            isPreviouslyOpened = savedInstanceState.getBoolean("isPreviouslyOpened");
        }

        if(!isPreviouslyOpened) {
            Toast.makeText(this, R.string.mainToast, Toast.LENGTH_LONG).show();
            isPreviouslyOpened = true;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        TrainingDataSource dataSource = new TrainingDataSource(this);
        ArrayList<Training> trainings = dataSource.read();

        setAdapter(this, trainings);
        mAdapter.notifyDataSetChanged();
    };

    @OnClick(R.id.addTrainingButton)
    public void startCreateActivity(View view) {

        Intent intent = new Intent(this, CreateTrainingActivity.class);
        startActivity(intent);
    };

    //set the adapter
    public void setAdapter(Context context, ArrayList<Training> trainings) {
        mAdapter = new TrainingAdapter(context, trainings);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    };


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_PREVIOUSLY_OPENED, isPreviouslyOpened);
    };



}
