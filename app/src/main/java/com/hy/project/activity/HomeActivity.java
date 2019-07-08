package com.hy.project.activity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hy.project.CustomViewPager;
import com.hy.project.HomeBottomNavigationView;
import com.hy.project.R;
import com.hy.project.adapter.HomePagerAdapter;
import com.hy.project.model.Profile;

import static com.hy.project.adapter.HomePagerAdapter.MESSAGE_PAGE_TITLE;
import static com.hy.project.adapter.HomePagerAdapter.SETTINGS_PAGE_TITLE;
import static com.hy.project.fragment.RegisterFragment.ACCOUNT_PATH;

public class HomeActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    CustomViewPager viewPager;
    HomePagerAdapter homePagerAdapter;
    HomeBottomNavigationView bottomView;
    ImageView imgAvatar;
    TextView txtPageTitle;
    DatabaseReference dbProfile = FirebaseDatabase.getInstance().getReference(ACCOUNT_PATH);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        bindView();
        initTopView();
        initBottomView();
        initSearchView();
    }

    private void bindView() {
        imgAvatar = findViewById(R.id.imgAvatar);
        viewPager = findViewById(R.id.viewPager);
        bottomView = findViewById(R.id.bottomView);
        txtPageTitle = findViewById(R.id.txtPageTitle);
    }

    private void initBottomView() {
        viewPager.swipeAble = false;
        homePagerAdapter = new HomePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(homePagerAdapter);
        bottomView.viewPager = viewPager;
        txtPageTitle.setText("Tin nhắn");
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case MESSAGE_PAGE_TITLE:
                        txtPageTitle.setText("Tin nhắn");
                        break;
                    case SETTINGS_PAGE_TITLE:
                        txtPageTitle.setText("Cài đặt");
                        break;
                    default:
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initSearchView(){

    }

    private void initTopView() {
        dbProfile.child(mAuth.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Profile profile = dataSnapshot.getValue(Profile.class);
                        if (profile.getAvatarURL() != null) {
                            Glide.with(HomeActivity.this).load(profile.getAvatarURL())
                                    .apply(RequestOptions.circleCropTransform())
                                    .into(imgAvatar);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
}
