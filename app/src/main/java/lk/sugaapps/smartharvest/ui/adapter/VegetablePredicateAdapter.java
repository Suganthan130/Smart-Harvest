package lk.sugaapps.smartharvest.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lk.sugaapps.smartharvest.data.model.PredicatedPriceModel;
import lk.sugaapps.smartharvest.databinding.ItemVegetableSummaryBinding;

public class VegetablePredicateAdapter extends RecyclerView.Adapter<VegetablePredicateAdapter.ViewMyHolder> {
    private Context context;
    private final List<PredicatedPriceModel> vegetablePriceDetailsList;

    public VegetablePredicateAdapter(List<PredicatedPriceModel> vegetablePriceDetailsList) {
        this.vegetablePriceDetailsList = vegetablePriceDetailsList;
    }

    @NonNull
    @Override
    public VegetablePredicateAdapter.ViewMyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemVegetableSummaryBinding binding = ItemVegetableSummaryBinding.inflate(inflater, parent, false);
        return new VegetablePredicateAdapter.ViewMyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VegetablePredicateAdapter.ViewMyHolder holder, int position) {
        holder.binding.tvSummaryTitle.setText(vegetablePriceDetailsList.get(position).getDate());
        Date today = parseDate(getCurrentDate());
        Date itemDate = parseDate(vegetablePriceDetailsList.get(position).getDate());

        if (itemDate != null && today != null) {
            if (itemDate.before(today) || itemDate.equals(today)) {
            } else {
                holder.binding.tvSummaryDes.setText("Pettah : "+vegetablePriceDetailsList.get(position).getPettah()+"\n"+"Dambulla : "+vegetablePriceDetailsList.get(position).getDambulla());
            }
        }

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
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }
    private Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
