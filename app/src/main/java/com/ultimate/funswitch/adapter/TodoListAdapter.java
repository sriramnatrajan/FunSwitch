package com.ultimate.funswitch.adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.ultimate.funswitch.R;
import com.ultimate.funswitch.model.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TodoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Object> mPendingList_;
    private Context mContext;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public TodoListAdapter(Context context, ArrayList<Object> pendlist) {
        this.mContext = context;
        this.mPendingList_ = pendlist;
    }

    public Object getItem(int position) {
        return mPendingList_.get(position);
    }

    @Override
    public int getItemCount() {
        return mPendingList_.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof Model) {
            return TYPE_ITEM;
        }
        return TYPE_HEADER;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEADER) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_layout, parent, false);
            return new HeaderViewHolder(layoutView);
        } else if (viewType == TYPE_ITEM) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_layout, parent, false);
            return new ChildViewHolder(layoutView);
        }
        throw new RuntimeException("No match for " + viewType + ".");


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {




        if (holder instanceof HeaderViewHolder) {
            String title = (String) getItem(position);

            // Set title (Header)
            ((HeaderViewHolder) holder).headerTitle.setText(title);
        } else if (holder instanceof ChildViewHolder) {
            final Model model = (Model) getItem(position);

            // Set description for completed status
            Log.d("MSG_str", model.getDescription());
            ((ChildViewHolder) holder).itemContent.setText(model.getDescription());

            try {
                String date = model.getScheduledDate();
                Date orgDate = new SimpleDateFormat("yyyymmddhhmm").parse(date);

                String newDate = new SimpleDateFormat("MMM dd").format(orgDate);
                Log.d("MSG_str", newDate);
                ((ChildViewHolder) holder).mDate.setText(newDate);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (model.getStatus().equalsIgnoreCase("COMPLETED")) {
                ((ChildViewHolder) holder).itemContent.setChecked(true);
                ((ChildViewHolder) holder).itemContent.setEnabled(false);
            }

            ((ChildViewHolder) holder).itemContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "" + model.getDescription(), Toast.LENGTH_SHORT).show();

                    model.setStatus("COMPLETED");
                    mPendingList_.remove(position);
                    mPendingList_.add(model);

                    TodoListAdapter.this.notifyDataSetChanged();
                }
            });
            ((ChildViewHolder) holder).itemContent.setChecked(false);
            if (model.getStatus().equalsIgnoreCase("COMPLETED")) {
                ((ChildViewHolder) holder).itemContent.setChecked(true);
                ((ChildViewHolder) holder).itemContent.setEnabled(false);

            }

        }
    }

}

