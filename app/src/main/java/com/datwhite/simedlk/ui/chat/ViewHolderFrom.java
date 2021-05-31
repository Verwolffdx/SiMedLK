package com.datwhite.simedlk.ui.chat;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datwhite.simedlk.R;

public class ViewHolderFrom extends RecyclerView.ViewHolder {

    TextView message;

    public ViewHolderFrom(@NonNull View itemView) {
        super(itemView);
        message = itemView.findViewById(R.id.message_item_from);
    }
}
