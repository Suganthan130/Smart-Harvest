package lk.sugaapps.smartharvest.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rajat.pdfviewer.PdfViewerActivity;

import java.util.List;

import lk.sugaapps.smartharvest.Constant;
import lk.sugaapps.smartharvest.data.model.CropHandBookModel;
import lk.sugaapps.smartharvest.databinding.ItemCropHandBookBinding;
import lk.sugaapps.smartharvest.ui.activities.PdfViewActivity;

public class CropHandBookAdapter extends RecyclerView.Adapter<CropHandBookAdapter.ViewHolder> {
    private List<CropHandBookModel> cropHandBookModelList;
    private Context context;

    public CropHandBookAdapter(List<CropHandBookModel> cropHandBookModelList) {
        this.cropHandBookModelList = cropHandBookModelList;
    }

    @NonNull
    @Override
    public CropHandBookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCropHandBookBinding binding = ItemCropHandBookBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CropHandBookAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context)
                .load(cropHandBookModelList.get(position).getPdfCoverPage())
                .into(holder.binding.ivCoverPage);
        holder.itemView.setOnClickListener(v -> {
            Intent intent =new Intent(context, PdfViewActivity.class);
            intent.putExtra(Constant.PDF_URL,cropHandBookModelList.get(position).getPdfUrl());
            intent.putExtra(Constant.PDF_NAME,cropHandBookModelList.get(position).getName());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return cropHandBookModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemCropHandBookBinding binding;
        public ViewHolder(@NonNull ItemCropHandBookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
