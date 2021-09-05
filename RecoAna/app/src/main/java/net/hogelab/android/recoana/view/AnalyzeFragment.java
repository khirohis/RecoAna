package net.hogelab.android.recoana.view;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import net.hogelab.android.recoana.R;
import net.hogelab.android.recoana.databinding.FragmentAnalyzeBinding;
import net.hogelab.android.recoana.model.datastore.DataStore;
import net.hogelab.android.recoana.model.entity.AudioTimelineList;
import net.hogelab.android.recoana.view.audiotimeline.AudioTimelineListAdapter;
import net.hogelab.android.recoana.viewmodel.AnalyzeViewModel;

public class AnalyzeFragment extends Fragment {
    private static final String TAG = AnalyzeFragment.class.getSimpleName();

    private FragmentAnalyzeBinding binding;
    private AnalyzeViewModel viewModel;

    private DataStore dataStore;
    private AudioTimelineList list;

    DisplayMetrics displaymetrics;
    private AudioTimelineListAdapter adapter;

    public static Fragment newInstance() {
        Log.v(TAG, "newInstance");

        return new AnalyzeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v(TAG, "onCreate");

        viewModel = new ViewModelProvider(this).get(AnalyzeViewModel.class);
        dataStore = viewModel.getRecordedDataStore();
        list = new AudioTimelineList(dataStore);
        list.initialize();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView");

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_analyze, container, false);
        binding.setLifecycleOwner(this);

        displaymetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        adapter = new AudioTimelineListAdapter(requireContext(), list, displaymetrics.density);
        binding.audioTimelineList.setAdapter(adapter);

        adapter.setCellDpSize((binding.timelineScaleSeekbar.getProgress() + 1) * 10);
        binding.timelineScaleSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                onTimelineScaleChanged(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

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

        list.terminate();
    }


    // UI action handlers

    private void onTimelineScaleChanged(int value) {
        // 0-9 to 10-100
        adapter.setCellDpSize((value + 1) * 10);
    }
}