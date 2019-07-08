package com.hy.project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hy.project.R;
import com.hy.project.adapter.RoomChatAdapter;
import com.hy.project.model.RoomChat;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements RoomChatAdapter.OnClickItem {

    private static final String ROOMCHAT_PATH = "ALL_ROOM_CHATS_DATABASE";

    private ArrayList<RoomChat> listRoom;

    RecyclerView rvRoomChat;
    RoomChatAdapter roomChatAdapter;

    public static HomeFragment getInstance(Bundle bundle) {
        HomeFragment frag = new HomeFragment();
        if (bundle != null) {
            frag.setArguments(bundle);
        }
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView();
        initListRoom();
    }

    private void initListRoom() {
        DatabaseReference dbRoom = FirebaseDatabase.getInstance().getReference(ROOMCHAT_PATH);
        dbRoom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listRoom = new ArrayList<>();
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    RoomChat room = singleSnapshot.getValue(RoomChat.class);
                    listRoom.add(room);
                }
                roomChatAdapter.setData(listRoom,HomeFragment.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void bindView() {
        rvRoomChat = getView().findViewById(R.id.rvRoomChat);
        rvRoomChat.setHasFixedSize(true);
        rvRoomChat.setLayoutManager(new LinearLayoutManager(getActivity()));
        roomChatAdapter = new RoomChatAdapter();
        rvRoomChat.setAdapter(roomChatAdapter);
   /*     Button btnClick = getView().findViewById(R.id.btnAddRoom);
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dbRoom = FirebaseDatabase.getInstance().getReference(ROOMCHAT_PATH);
                String roomId = dbRoom.push().getKey();
                RoomChat room = new RoomChat("Phòng số 3",roomId,"0/10");
                dbRoom.child(roomId).setValue(room);
            }
        });*/
    }

    @Override
    public void onClick(RoomChat dataRoom) {

    }
}
