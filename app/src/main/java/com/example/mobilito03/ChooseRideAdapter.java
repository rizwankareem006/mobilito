package com.example.mobilito03;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.time.LocalDateTime;

public class ChooseRideAdapter extends RecyclerView.Adapter<ChooseRideAdapter.MyViewHolder> {

    private static final String TAG = "ChooseRide";
    Rides[] rides;
    Context context;
    ClickListener listener;

    public ChooseRideAdapter(Context context,  Rides[] rides, ClickListener listener){
        this.context = context;
        this.rides = rides;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChooseRideAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_choose_ride, parent, false);
        return new ChooseRideAdapter.MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseRideAdapter.MyViewHolder holder, int position) {
        String provider = rides[position].getProvider().getFullName();
        int expectedAmount = rides[position].getExpectedAmount();
        LocalDateTime startTime = rides[position].getStartTime();

        holder.providerText.setText(provider);
        holder.expectedAmountText.setText("Rs. "+expectedAmount);
        holder.startTimeText.setText(startTime.toString());

    }

    @Override
    public int getItemCount() {
        return rides.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView providerText, expectedAmountText, startTimeText;

        private WeakReference<ClickListener> listenerRef;

        public MyViewHolder(@NonNull View itemView, ClickListener listener) {
            super(itemView);
            listenerRef = new WeakReference<>(listener);
            providerText = itemView.findViewById(R.id.chooseProvider);
            expectedAmountText = itemView.findViewById(R.id.chooseExpectedAmount);
            startTimeText = itemView.findViewById(R.id.chooseStartTime);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listenerRef.get().onPositionClicked(getAdapterPosition());
        }
    }
}
