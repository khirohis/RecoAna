package net.hogelab.android.recoana;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;

import net.hogelab.android.recoana.databinding.ActivityMainBinding;
import net.hogelab.android.recoana.view.MainFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v(TAG, "onCreate");

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_root_container, MainFragment.newInstance())
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