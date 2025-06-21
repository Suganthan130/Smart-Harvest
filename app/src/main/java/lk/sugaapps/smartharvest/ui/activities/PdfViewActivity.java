package lk.sugaapps.smartharvest.ui.activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleCoroutineScope;
import androidx.lifecycle.LifecycleOwnerKt;

import com.rajat.pdfviewer.HeaderData;
import com.rajat.pdfviewer.PdfRendererView;
import com.rajat.pdfviewer.util.CacheStrategy;

import java.util.Objects;

import lk.sugaapps.smartharvest.Constant;
import lk.sugaapps.smartharvest.databinding.ActivityPdfViewBinding;

public class PdfViewActivity extends AppCompatActivity {
    private ActivityPdfViewBinding binding;
    private final String TAG = "PDF Viewer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPdfViewBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        String url = getIntent().getStringExtra(Constant.PDF_URL);
        String name = getIntent().getStringExtra(Constant.PDF_NAME);

        if (url != null && !url.isEmpty()) {
            initPdfViewer(url);
        } else {
            Toast.makeText(this, "PDF URL missing", Toast.LENGTH_SHORT).show();
        }

        binding.tvPdfName.setText(name);
        binding.ivBack.setOnClickListener(v -> finish());
    }

    private void initPdfViewer(String url) {
        HeaderData headerData = new HeaderData();
        LifecycleCoroutineScope coroutineScope = LifecycleOwnerKt.getLifecycleScope(this);

        binding.pdfView.initWithUrl(
                url,
                headerData,
                coroutineScope,
                getLifecycle(),
                CacheStrategy.MAXIMIZE_PERFORMANCE
        );
        binding.pdfView.setStatusListener(new PdfRendererView.StatusCallBack() {
            @Override
            public void onPdfLoadStart() {
                binding.pdfProgress.setVisibility(VISIBLE);
            }

            @Override
            public void onPdfLoadProgress(int i, long l, @Nullable Long aLong) {

            }

            @Override
            public void onPdfLoadSuccess(@NonNull String s) {
                binding.pdfProgress.setVisibility(GONE);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                Log.d(TAG, Objects.requireNonNull(throwable.getMessage()));
            }

            @Override
            public void onPageChanged(int i, int i1) {

            }

            @Override
            public void onPdfRenderStart() {

            }

            @Override
            public void onPdfRenderSuccess() {

            }
        });
    }
}