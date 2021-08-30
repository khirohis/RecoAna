package net.hogelab.android.recoana.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import net.hogelab.android.recoana.AnalyzeActivity;
import net.hogelab.android.recoana.PreferencesActivity;
import net.hogelab.android.recoana.R;
import net.hogelab.android.recoana.RecordingActivity;
import net.hogelab.android.recoana.databinding.FragmentMainBinding;
import net.hogelab.android.recoana.viewmodel.MainViewModel;

public class MainFragment extends Fragment {
    private static final String TAG = MainFragment.class.getSimpleName();

    private FragmentMainBinding binding;
    private MainViewModel viewModel;

    public static Fragment newInstance() {
        Log.v(TAG, "newInstance");

        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v(TAG, "onCreate");

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView");

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        binding.setLifecycleOwner(this);

        binding.recordingButton.setOnClickListener(this::onRecording);
        binding.analyzeButton.setOnClickListener(this::onAnalyze);
        binding.preferencesButton.setOnClickListener(this::onPreferences);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.v(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.v(TAG, "onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Log.v(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.v(TAG, "onDestroy");
    }


    // UI action handlers

    private void onRecording(View view) {
        Intent intent = RecordingActivity.newIntent(requireContext());
        startActivity(intent);
    }

    private void onAnalyze(View view) {
        Intent intent = AnalyzeActivity.newIntent(requireContext());
        startActivity(intent);
    }

    private void onPreferences(View view) {
        Intent intent = PreferencesActivity.newIntent(requireContext());
        startActivity(intent);
    }
}