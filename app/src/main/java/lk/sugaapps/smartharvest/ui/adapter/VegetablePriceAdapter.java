package lk.sugaapps.smartharvest.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.reactivex.annotations.NonNull;
import lk.sugaapps.smartharvest.data.remote.model.VegetablePriceDetails;
import lk.sugaapps.smartharvest.data.remote.model.VegetableResponse;
import lk.sugaapps.smartharvest.databinding.ItemVegetableBinding;

public class VegetablePriceAdapter extends RecyclerView.Adapter<VegetablePriceAdapter.VegetableViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(VegetableResponse item);
    }

    private List<VegetableResponse> vegetableList;
    private final OnItemClickListener listener;
    private Context context;

    public VegetablePriceAdapter(List<VegetableResponse> vegetableList, OnItemClickListener listener) {
        this.vegetableList = vegetableList;
        this.listener = listener;
    }


    @androidx.annotation.NonNull
    @NonNull
    @Override
    public VegetableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemVegetableBinding binding = ItemVegetableBinding.inflate(inflater, parent, false);
        return new VegetableViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VegetableViewHolder holder, int position) {
        holder.binding.tvVegitableTitle.setText(vegetableList.get(position).getName());
        Glide.with(context).load(vegetableList.get(position).getImage_url()).centerCrop().into(holder.binding.ivVegetableImage);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(vegetableList.get(position)));
    }

    @Override
    public int getItemCount() {
        return vegetableList != null ? vegetableList.size() : 0;
    }

    public static class VegetableViewHolder extends RecyclerView.ViewHolder {
        private final ItemVegetableBinding binding;

        public VegetableViewHolder(ItemVegetableBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}