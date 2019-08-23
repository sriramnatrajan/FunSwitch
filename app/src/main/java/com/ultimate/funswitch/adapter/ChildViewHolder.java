package com.ultimate.funswitch.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ultimate.funswitch.R;

public class ChildViewHolder  extends RecyclerView.ViewHolder{

    public CheckBox itemContent;
    public TextView mDate;
    public ChildViewHolder(View itemView) {
        super(itemView);
        itemContent = (CheckBox) itemView.findViewById(R.id.child_id);
         mDate=itemView.findViewById(R.id.date_tv);
    }
}
