package net.hogelab.android.recoana.view.audiotimeline;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import net.hogelab.android.recoana.R;
import net.hogelab.android.recoana.databinding.ListItemAudioTimelineBinding;
import net.hogelab.android.recoana.model.entity.AudioTimelineList;
import net.hogelab.android.recoana.view.common.DataBindingViewHolder;

import java.util.List;

public class AudioTimelineListAdapter extends RecyclerView.Adapter<DataBindingViewHolder> {
    private static final String TAG = AudioTimelineListAdapter.class.getSimpleName();

    private final LayoutInflater inflater;
    private AudioTimelineList data;
    private float density;

    private int cellDpSize;

    public AudioTimelineListAdapter(Context context, AudioTimelineList data, float density) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        this.density = density;

        cellDpSize = 8;
    }


    @NonNull
    @Override
    public DataBindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.v(TAG, "onCreateViewHolder");

        ViewDataBinding binding = getBinding(parent, viewType);

        return new DataBindingViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull DataBindingViewHolder holder, int position) {
        if (data != null && position < data.getBlockCount()) {
            byte[] audioData = data.get(position);
            setBinding(holder.getBinding(), position, audioData);
        }
    }


    @Override
    public int getItemCount() {
        if (data != null) {
            return data.getBlockCount();
        }

        return 0;
    }


    public void setCellDpSize(int cellDpSize) {
        if (cellDpSize != this.cellDpSize) {
            this.cellDpSize = cellDpSize;

            notifyDataSetChanged();
        }
    }

    public void updateData(AudioTimelineList data) {
        this.data = data;

        notifyDataSetChanged();
    }


    private ViewDataBinding getBinding(@NonNull ViewGroup viewGroup, int viewType) {
        return DataBindingUtil.inflate(inflater, R.layout.list_item_audio_timeline, viewGroup, false);
    }

    private void setBinding(ViewDataBinding binding, int position, byte[] audioData) {
        int pix = (int) (cellDpSize * density);
        ListItemAudioTimelineBinding targetBinding = (ListItemAudioTimelineBinding) binding;
        targetBinding.pcmGraphView.getLayoutParams().width = pix;
        targetBinding.pcmGraphView.setPcmData(position, audioData);
    }
}