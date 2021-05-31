package com.datwhite.simedlk.ui.chat;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datwhite.simedlk.R;

public class ViewHolderTo extends RecyclerView.ViewHolder {

    TextView message;

    public ViewHolderTo(@NonNull View itemView) {
        super(itemView);
        message = itemView.findViewById(R.id.message_item_to);
    }
}
