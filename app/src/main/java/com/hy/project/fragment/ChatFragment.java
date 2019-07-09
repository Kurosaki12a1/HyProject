package com.hy.project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hy.project.R;
import com.hy.project.activity.ChatActivity;
import com.hy.project.activity.HomeActivity;
import com.hy.project.adapter.MessageChatAdapter;
import com.hy.project.model.MessageChatDatabase;
import com.hy.project.model.Profile;

import java.util.ArrayList;

import static com.hy.project.fragment.RegisterFragment.ACCOUNT_PATH;


public class ChatFragment extends Fragment {

    androidx.appcompat.widget.Toolbar toolbar;
    TextView tvRoomName, tvNumberJoin;
    EditText txtChat;
    ImageView imgSend;
    RecyclerView rvChat;
    private String roomName, roomId;
    private FirebaseAuth mAuth;
    private final String HISTORY_ROOM_CHAT = "HISTORY_ROOM_CHAT";
    private ArrayList<MessageChatDatabase> listChat;
    private Profile myProfile;
    private MessageChatAdapter messageChatAdapter;

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
        initUser();
        bindView();
        initHistoryRoomChat();
    }

    private void initUser() {
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference dbProfile = FirebaseDatabase.getInstance().getReference(ACCOUNT_PATH);
        dbProfile.child(mAuth.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Profile profile = dataSnapshot.getValue(Profile.class);
                        if (profile != null) {
                            myProfile = new Profile();
                            myProfile = profile;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    private void initHistoryRoomChat() {
        DatabaseReference dbHistory = FirebaseDatabase.getInstance().getReference(HISTORY_ROOM_CHAT);
        dbHistory.child(roomId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listChat = new ArrayList<>();
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    listChat.add(singleSnapshot.getValue(MessageChatDatabase.class));
                }
                setDataChat(listChat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setDataChat(ArrayList<MessageChatDatabase> lstChat) {
        messageChatAdapter.setData(lstChat);
        rvChat.scrollToPosition(lstChat.size() -1 );
    }

    private void bindView() {
        toolbar = getView().findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (getActivity() != null) ((ChatActivity)getActivity()).finish();
            }
        });

        tvRoomName = getView().findViewById(R.id.tvRoomName);
        tvRoomName.setText(roomName);

        tvNumberJoin = getView().findViewById(R.id.tvNumber);
        tvNumberJoin.setText("Số người đã tham gia: 2/10"); //Tạm thời xét cứng

        messageChatAdapter = new MessageChatAdapter();

        rvChat = getView().findViewById(R.id.rvMessageView);
        rvChat.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvChat.setAdapter(messageChatAdapter);

        txtChat = getView().findViewById(R.id.txtChat);
        imgSend = getView().findViewById(R.id.imgSend);

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtChat.length()>0){
                    DatabaseReference dbHistory = FirebaseDatabase.getInstance().getReference(HISTORY_ROOM_CHAT);
                    MessageChatDatabase messageChatDatabase = new MessageChatDatabase(
                            mAuth.getCurrentUser().getUid(),
                            myProfile.getUserName(),
                            myProfile.getAvatarURL(),
                            roomId,
                            txtChat.getText().toString()
                    );
                    String chatId = dbHistory.push().getKey();
                    dbHistory.child(roomId).child(chatId).setValue(messageChatDatabase);
                    txtChat.setText("");
                }

            }
        });
    }
}
