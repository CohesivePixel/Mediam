package com.example.ben_e.mediam.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ben_e.mediam.R;

/**
 * Created by ben-e on 8-10-17.
 */

public class DataListFragment extends Fragment {
    FragmentActivity listener;

    // When the fragment instance is associated with an activity
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof Activity)
        {
            this.listener = (FragmentActivity) context;
        }
    }

    // When the fragment is being created
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    // When the fragment creates it's view objects
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_data_list, parent, false);
    }

    // When a view is setup, do view lookups here and attach viewListeners here
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {

    }

    // When the fragment is no longer connected to the activity
    @Override
    public void onDetach()
    {
        super.onDetach();
        // prevent memory leaks, set everything created in onViewCreated to null here
    }

    // After onCreate has been completed, search for views by ID here
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }
}
