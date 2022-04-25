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

public class ProvidedRidesAdapter extends RecyclerView.Adapter<ProvidedRidesAdapter.MyViewHolder> {

    private static final String TAG = "ProvidedRides";
    Rides[] rides;
    Context context;
    ClickListener listener;

    public ProvidedRidesAdapter(Context context,  Rides[] rides, ClickListener listener){
        this.context = context;
        this.rides = rides;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_provided_rides, parent, false);
        return new MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        boolean booked = rides[position].isBooked();
        int expectedAmount = rides[position].getExpectedAmount();
        LocalDateTime startTime = rides[position].getStartTime();

        holder.bookedText.setText(String.valueOf(booked));
        holder.expectedAmountText.setText("Rs. "+expectedAmount);
        holder.startTimeText.setText(startTime.toString());

    }

    @Override
    public int getItemCount() {
        return rides.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView bookedText, expectedAmountText, startTimeText;

        private WeakReference<ClickListener> listenerRef;

        public MyViewHolder(@NonNull View itemView, ClickListener listener) {
            super(itemView);
            listenerRef = new WeakReference<>(listener);
            bookedText = itemView.findViewById(R.id.rowBooked);
            expectedAmountText = itemView.findViewById(R.id.rowExpectedAmount);
            startTimeText = itemView.findViewById(R.id.rowStartTime);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listenerRef.get().onPositionClicked(getAdapterPosition());
        }
    }
}
