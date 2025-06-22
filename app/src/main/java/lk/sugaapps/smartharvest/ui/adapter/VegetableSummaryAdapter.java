package lk.sugaapps.smartharvest.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import lk.sugaapps.smartharvest.data.remote.model.VegetablePriceDetails;
import lk.sugaapps.smartharvest.databinding.ItemVegetableSummaryBinding;

public class VegetableSummaryAdapter extends RecyclerView.Adapter<VegetableSummaryAdapter.ViewMyHolder> {
    private Context context;
    private final List<VegetablePriceDetails> vegetablePriceDetailsList;

    public VegetableSummaryAdapter(List<VegetablePriceDetails> vegetablePriceDetailsList) {
        this.vegetablePriceDetailsList = vegetablePriceDetailsList;
    }

    @NonNull
    @Override
    public VegetableSummaryAdapter.ViewMyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemVegetableSummaryBinding binding = ItemVegetableSummaryBinding.inflate(inflater, parent, false);
        return new VegetableSummaryAdapter.ViewMyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VegetableSummaryAdapter.ViewMyHolder holder, int position) {
        holder.binding.tvSummaryTitle.setText(vegetablePriceDetailsList.get(position).getTitle());
        List<String> details = vegetablePriceDetailsList.get(position).getDetails();

        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < details.size(); i++) {
            formatted.append(i + 1).append(". ").append(details.get(i)).append("\n");
        }

        holder.binding.tvSummaryDes.setText(formatted.toString());

    }

    @Override
    public int getItemCount() {
        return vegetablePriceDetailsList.size();
    }

    public static class ViewMyHolder extends RecyclerView.ViewHolder{
        private final ItemVegetableSummaryBinding binding;
        public ViewMyHolder(@NonNull ItemVegetableSummaryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
