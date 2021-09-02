package net.hogelab.android.recoana.view.common;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class DataBindingViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = DataBindingViewHolder.class.getSimpleName();

    protected ViewDataBinding binding;

    public DataBindingViewHolder(@NonNull ViewDataBinding binding) {
        super(binding.getRoot());

        Log.d(TAG, "constructor:");

        this.binding = binding;
    }

    public ViewDataBinding getBinding() {
        return binding;
    }
}