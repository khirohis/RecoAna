package net.hogelab.android.recoana;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import net.hogelab.android.recoana.databinding.ActivityRecordingBinding;
import net.hogelab.android.recoana.view.RecordingFragment;

public class RecordingActivity extends AppCompatActivity {
    private static final String TAG = RecordingActivity.class.getSimpleName();


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, RecordingActivity.class);

        return intent;
    }


    private ActivityRecordingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v(TAG, "onCreate");

        binding = DataBindingUtil.setContentView(this, R.layout.activity_recording);
        binding.setLifecycleOwner(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_root_container, RecordingFragment.newInstance())
                    .commit();
        }
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
    public void onDestroy() {
        super.onDestroy();

        Log.v(TAG, "onDestroy");
    }
}