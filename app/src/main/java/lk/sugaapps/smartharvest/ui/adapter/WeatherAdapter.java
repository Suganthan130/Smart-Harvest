package lk.sugaapps.smartharvest.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import lk.sugaapps.smartharvest.R;
import lk.sugaapps.smartharvest.data.model.WeatherItemModel;
import lk.sugaapps.smartharvest.databinding.ItemWeatherInfomationBinding;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private final Context context;
    private final List<WeatherItemModel> weatherItems;

    public WeatherAdapter(Context context, List<WeatherItemModel> weatherItems) {
        this.context = context;
        this.weatherItems = weatherItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemWeatherInfomationBinding binding = ItemWeatherInfomationBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherItemModel item = weatherItems.get(position);
        holder.binding.txvTitile.setText(item.getTitle());
        holder.binding.txvValue.setText(item.getValue());
        String iconUrl =  item.getIconUrl();
        if (iconUrl.equalsIgnoreCase("wind.png")){
            Glide.with(context)
                    .load(R.drawable.wind).centerCrop()
                    .into(holder.binding.iconWeater);
        }else if (iconUrl.equalsIgnoreCase("humidity.png")){
            Glide.with(context)
                    .load(R.drawable.humidity).centerCrop()
                    .into(holder.binding.iconWeater);
        }else {
            Glide.with(context)
                    .load("https:" +iconUrl)
                    .into(holder.binding.iconWeater);

        }



    }

    @Override
    public int getItemCount() {
        return weatherItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemWeatherInfomationBinding binding;

        public ViewHolder(ItemWeatherInfomationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
