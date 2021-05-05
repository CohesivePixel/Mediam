package com.example.ben_e.mediam;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ben_e.mediam.fragments.DataListFragment;
import com.example.ben_e.mediam.fragments.InputFragment;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Fragment nextFragment;
    private FragmentManager fManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    nextFragment = new InputFragment();
                    break;
            }
            final FragmentTransaction fTransaction = fManager.beginTransaction();
            fTransaction.replace(R.id.content, nextFragment).commit();

            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fManager = getSupportFragmentManager();

        if (savedInstanceState == null)
        {
            InputFragment inputFragment = new InputFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, inputFragment).commit();

        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
