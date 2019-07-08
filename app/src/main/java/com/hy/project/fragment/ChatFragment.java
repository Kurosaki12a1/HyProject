package com.hy.project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hy.project.R;


public class ChatFragment extends Fragment {

    Toolbar toolbar;
    TextView tvRoomName, tvNumberJoin;
    private String roomName, roomId;
    private final String HISTORY_ROOM_CHAT = "HISTORY_ROOM_CHAT";

    public static ChatFragment getInstance(Bundle bundle) {
        ChatFragment frag = new ChatFragment();
        if (bundle != null) frag.setArguments(bundle);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            roomName = getArguments().getString("ROOM_NAME", "");
            roomId = getArguments().getString("ROOM_ID", "");

        }
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView();
        initHistoryRoomChat();
    }

    private void initHistoryRoomChat() {
        DatabaseReference dbHistory = FirebaseDatabase.getInstance().getReference(HISTORY_ROOM_CHAT);
        dbHistory.child(roomId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void bindView() {
        toolbar = getView().findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (getActivity() != null) getActivity().onBackPressed();
            }
        });

        tvRoomName = getView().findViewById(R.id.tvRoomName);
        tvRoomName.setText(roomName);

        tvNumberJoin = getView().findViewById(R.id.tvNumber);
        tvNumberJoin.setText("Số người đã tham gia: 2/10"); //Tạm thời xét cứng
    }
}
