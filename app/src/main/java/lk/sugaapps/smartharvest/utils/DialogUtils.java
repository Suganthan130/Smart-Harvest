package lk.sugaapps.smartharvest.utils;

import android.app.AlertDialog;
import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DialogUtils {

    @Inject
    public DialogUtils() {
    }

    public void showErrorDialog(Context context,String title, String message, String buttonText, Runnable onClick) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(buttonText, (dialog, which) -> {
                    dialog.dismiss();
                    if (onClick != null) onClick.run();
                })
                .show();
    }
    public void showOkCancelDialog(Context context, String title, String message, String buttonPositiveText, Runnable onClickPositive, String buttonNegativeText, Runnable onClickNegative) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(buttonPositiveText, (dialog, which) -> {
                    dialog.dismiss();
                    if (onClickPositive != null) onClickPositive.run();
                })
                .setNegativeButton(buttonNegativeText, (dialog, which) -> {
                    dialog.dismiss();
                    if (onClickNegative != null) onClickNegative.run();
                })

                .show();
    }
}
