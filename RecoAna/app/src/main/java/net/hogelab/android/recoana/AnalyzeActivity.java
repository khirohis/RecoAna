package net.hogelab.android.recoana;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import net.hogelab.android.recoana.databinding.ActivityAnalyzeBinding;
import net.hogelab.android.recoana.view.AnalyzeFragment;

public class AnalyzeActivity extends AppCompatActivity {
    private static final String TAG = AnalyzeActivity.class.getSimpleName();


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AnalyzeActivity.class);

        return intent;
    }


    private ActivityAnalyzeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v(TAG, "onCreate");

        binding = DataBindingUtil.setContentView(this, R.layout.activity_analyze);
        binding.setLifecycleOwner(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_root_container, AnalyzeFragment.newInstance())
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