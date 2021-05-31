package com.datwhite.simedlk.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datwhite.simedlk.App;
import com.datwhite.simedlk.MainActivity;
import com.datwhite.simedlk.R;
import com.datwhite.simedlk.entity.Doctor;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatFragment extends AppCompatActivity {

    private App app;

    private static int MAX_MESSAGE_LENGTH = 100;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    DatabaseReference secondRef;

    EditText editTextMessage;
    Button sendButton;
    RecyclerView mMessageRecycler;
    TextView chatFio;

    private Bundle arguments;

    ArrayList<String> messages = new ArrayList<>();

    public static void start(Context caller, Doctor doctor) {
        Intent intent = new Intent(caller, ChatFragment.class);
        intent.putExtra(Doctor.class.getSimpleName(), (Serializable) doctor);
        caller.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat);

        app = (App) getApplication();
        arguments = getIntent().getExtras();

        editTextMessage = findViewById(R.id.message_input);
        sendButton = findViewById(R.id.send_message_b);
        mMessageRecycler = findViewById(R.id.message_recycler);
        chatFio = findViewById(R.id.chatFio);

        Doctor doctor = (Doctor) arguments.getSerializable(Doctor.class.getSimpleName());
        chatFio.setText(doctor.getName());
        myRef = database.getReference("messages").child("from_" + app.getDoctor().getId() + "_to_" + doctor.getId());
        secondRef = database.getReference("messages").child("from_" + doctor.getId() + "_to_" + app.getDoctor().getId());

        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));

        ChatDataAdapter dataAdapter = new ChatDataAdapter(getApplicationContext(), messages);

        mMessageRecycler.setAdapter(dataAdapter);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = editTextMessage.getText().toString();

                if (msg.equals("")) {
                    Toast.makeText(getApplicationContext(), "Введите сообщение!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (msg.length() > MAX_MESSAGE_LENGTH) {
                    Toast.makeText(getApplicationContext(), "Слишком длинное сообщение!", Toast.LENGTH_SHORT).show();
                    return;
                }

                myRef.push().setValue(msg);
//                secondRef.push().setValue(msg);
                editTextMessage.setText("");
            }
        });

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String msg = snapshot.getValue(String.class);
                messages.add(msg);
                dataAdapter.notifyDataSetChanged();
                mMessageRecycler.smoothScrollToPosition(messages.size());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        secondRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String msg = snapshot.getValue(String.class);
                messages.add(msg);
                dataAdapter.notifyDataSetChanged();
                mMessageRecycler.smoothScrollToPosition(messages.size());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
/*
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_chat, container, false);



        return root;
    }

 */
}
