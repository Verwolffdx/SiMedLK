package com.datwhite.simedlk.ui.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datwhite.simedlk.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<String> messages;

    LayoutInflater inflater;

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position % 2 * 2;
    }

    public ChatDataAdapter(Context context, ArrayList<String> messages) {
        this.messages = messages;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case 0:
                view = inflater.inflate(R.layout.item_message_from, parent, false);
                viewHolder = new ViewHolderFrom(view);
                break;
//                return new ViewHolderFrom(view);
            case 2:
                view = inflater.inflate(R.layout.item_message_to, parent, false);
                viewHolder = new ViewHolderTo(view);
                break;
//                return new ViewHolderTo(view);
            default:
                view = inflater.inflate(R.layout.item_message_from, parent, false);
                viewHolder = new ViewHolderTo(view);
        }
//        view = inflater.inflate(R.layout.item_message_from, parent, false);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        String msg = messages.get(position);
        switch (holder.getItemViewType()) {
            case 0:
                ViewHolderFrom viewHolderFrom = (ViewHolderFrom) holder;
                System.out.println("MESSAGE " + msg);
                viewHolderFrom.message.setText(msg);
                break;

            case 2:
                ViewHolderTo viewHolderTo = (ViewHolderTo) holder;
                System.out.println("MESSAGE " + msg);
                viewHolderTo.message.setText(msg);
                break;
        }
    }

//    @Override
//    public void onBindViewHolder(@NonNull ViewHolderFrom holder, int position) {
//        String msg = messages.get(position);
//        holder.message.setText(msg);
//    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
