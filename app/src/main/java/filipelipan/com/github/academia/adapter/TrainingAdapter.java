package filipelipan.com.github.academia.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import filipelipan.com.github.academia.R;
import filipelipan.com.github.academia.database.TrainingDataSource;
import filipelipan.com.github.academia.model.Training;
import filipelipan.com.github.academia.ui.ViewExerciseActivity;

/**
 * Created by lispa on 08/05/2016.
 */
public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.TrainingViewHolder> {

    private static final String TRAINING = "TRAINING";
    private ArrayList<Training> mTrainings;
    private Context mContext;

    public TrainingAdapter(Context context, ArrayList<Training> trainings) {
        mTrainings = trainings;
        mContext = context;
    }

    private TextView mTrainingTitle;

    @Override
    public TrainingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_training_list_item, parent, false);
        TrainingViewHolder viewHolder = new TrainingViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrainingViewHolder holder, int position) {
        holder.bindTraining(mTrainings.get(position));
    }

    @Override
    public int getItemCount() {
        return mTrainings.size();
    }

    public class TrainingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public TrainingViewHolder(View itemView) {
            super(itemView);

            mTrainingTitle = (TextView) itemView.findViewById(R.id.trainingTitleLabel);


            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void bindTraining(Training training) {
            mTrainingTitle.setText(training.getTitle());
        }


        @Override
        public void onClick(View view) {
            Training training = mTrainings.get(getPosition());

            Intent intent = new Intent(view.getContext(), ViewExerciseActivity.class);
            intent.putExtra(TRAINING, training);

            view.getContext().startActivity(intent);


        }


        @Override
        public boolean onLongClick(View view) {
            final Dialog dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.confirm_dialog);
            dialog.setTitle(R.string.confirmation);

            final Training training = mTrainings.get(getPosition());

            TextView text = (TextView) dialog.findViewById(R.id.textDialog);
            Button cancelButton = (Button) dialog.findViewById(R.id.cancelDialogButton);
            Button confirmButton;
            confirmButton = (Button) dialog.findViewById(R.id.confirmDialogButton);

            text.setText(R.string.confirmationDeleteTraining);

            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TrainingDataSource dataSource = new TrainingDataSource(view.getContext());
                    dataSource.deleteTraining(training);
                    mTrainings.remove(getPosition());
                    notifyItemRemoved(getPosition());
                    dialog.dismiss();
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();

            return true;
        }
    }


}
