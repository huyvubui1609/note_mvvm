package com.example.note_mvvm.View.Dialog;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.note_mvvm.R;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class AddNoteDialog extends Dialog {
    public AddNoteDialog(@NonNull Context context) {
        super(context);
        final PrettyDialog pDialog = new PrettyDialog(context);
        pDialog
                .setIcon(R.drawable.error)
                .setTitle("Warning")
                .setTitleColor(R.color.pdlg_color_red)
                .setMessage("Missing Title or Description!")
                .addButton(
                        "OK",
                        R.color.pdlg_color_white,
                        R.color.pdlg_color_green,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                pDialog.dismiss();
                            }
                        }
                )
                .show();
    }
}
