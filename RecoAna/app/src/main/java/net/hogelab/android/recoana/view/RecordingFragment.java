package net.hogelab.android.recoana.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import net.hogelab.android.recoana.R;
import net.hogelab.android.recoana.databinding.FragmentRecordingBinding;
import net.hogelab.android.recoana.viewmodel.RecordingViewModel;

public class RecordingFragment extends Fragment {
    private static final String TAG = RecordingFragment.class.getSimpleName();

    private FragmentRecordingBinding binding;
    private RecordingViewModel viewModel;
    private LiveData<RecordingViewModel.RecordingStatus> recordingStatusData;

    ActivityResultLauncher<String> requestPermissionLauncher;

    public static Fragment newInstance() {
        Log.v(TAG, "newInstance");

        return new RecordingFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v(TAG, "onCreate");

        viewModel = new ViewModelProvider(this).get(RecordingViewModel.class);
        recordingStatusData = viewModel.getRecordingStatusData();
        recordingStatusData.observe(this, this::onRecordingStatusChanged);

        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                this::onRequestRecordingPermissionResult);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView");

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recording, container, false);
        binding.setLifecycleOwner(this);

        binding.recordingControlButton.setOnClickListener(this::onRecordingControl);
        binding.closeButton.setOnClickListener(this::onClose);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.v(TAG, "onResume");

        adjustRecodingControlButtonText();
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

    private void onRecordingControl(View view) {
        boolean granted = isRecordingPermissionGranted();
        if (granted) {
            RecordingViewModel.RecordingStatus status = recordingStatusData.getValue();
            if (status != null && status == RecordingViewModel.RecordingStatus.RECORDING) {
                viewModel.stopRecording();
            } else {
                viewModel.startRecording();
            }
        } else {
            requestRecordingPermission();
        }
    }

    private void onClose(View view) {
        requireActivity().finish();
    }


    private void onRecordingStatusChanged(RecordingViewModel.RecordingStatus status) {
        adjustRecodingControlButtonText();

        if (status != null) {
            switch (status) {

                case SUCCESS_STOP:
                case INTERRUPTION_STOP:
                    // TODO: 録音完了
                    break;

                default:
                    break;
            }
        }
    }


    // private functions

    private boolean isRecordingPermissionGranted() {
        int permissionResult = ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.RECORD_AUDIO);

        return permissionResult == PackageManager.PERMISSION_GRANTED;
    }

    private void requestRecordingPermission() {
        if (!isRecordingPermissionGranted()) {
            requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO);
        }
    }

    private void onRequestRecordingPermissionResult(boolean isGranted) {
        adjustRecodingControlButtonText();
    }


    private void adjustRecodingControlButtonText() {
        if (isRecordingPermissionGranted()) {
            RecordingViewModel.RecordingStatus status = recordingStatusData.getValue();
            if (status != null) {
                switch (status) {

                    case RECORDING:
                        binding.recordingControlButton.setText(R.string.stop_recording);
                        break;

                    case WAITING:
                    case SUCCESS_STOP:
                    case INTERRUPTION_STOP:
                    case ERROR_STOP:
                        binding.recordingControlButton.setText(R.string.start_recording);
                        break;

                    default:
                        binding.recordingControlButton.setText(R.string.start_recording);
                        break;
                }
            } else {
                binding.recordingControlButton.setText(R.string.start_recording);
            }
        } else {
            binding.recordingControlButton.setText(R.string.recording_permission);
        }
    }
}