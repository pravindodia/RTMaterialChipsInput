package com.materialchips.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.materialchips.ChipsInput;
import com.materialchips.model.Chip;
import com.materialchips.model.ChipInterface;
import com.rultech.Util;
import com.rultech.materialchipsample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RulTech on 11/07/18.
 */
public class AddChipsDialog {

    private final Context mContext;
    private OnAddChipsDialogListener onAddChipsDialogListener;
    private List<String> mArrChipsDialog;
    //    private final ViewGroup mRootView;
//    private View mConvertView;
    private Button btnSave;
    private Button btnCancel;
    private ChipsInput chips_input_ChipsDialog;
    private ImageView imageViewClose;
    private List<String> mArrFilterChips;
    private String mStrTitle = "Add ";
    private String mStrHint;
    private TextView txtStrTitle;
//    private String id;

    public static AddChipsDialog builder(Context mContext) {
        return new AddChipsDialog(mContext);
    }

    private AddChipsDialog(@NonNull Context mContext) {
        this.mContext = mContext;
    }

    public AddChipsDialog OnAddChipsDialogListener(@NonNull OnAddChipsDialogListener onAddChipsDialogListener) {
        this.onAddChipsDialogListener = onAddChipsDialogListener;
        return this;
    }

    public AddChipsDialog withTitle(@NonNull String mStrTitle) {
        this.mStrTitle = mStrTitle;
        return this;
    }

    public AddChipsDialog withHint(@NonNull String mStrHint) {
        this.mStrHint = mStrHint;
        return this;
    }

    public AddChipsDialog withExistingChips(@NonNull List<String> mArrChipsDialog) {
        this.mArrChipsDialog = mArrChipsDialog;
        return this;
    }

    public AddChipsDialog withFilterList(@NonNull List<String> mArrFilterChips) {
        this.mArrFilterChips = mArrFilterChips;
        return this;
    }

    public void show() throws NullPointerException {
        showDialog();
    }

    private void initView(Dialog dialog) {
        txtStrTitle = dialog.findViewById(R.id.txtStrTitle);
        btnSave = dialog.findViewById(R.id.btnSave);
        btnCancel = dialog.findViewById(R.id.btnCancel);
        imageViewClose = dialog.findViewById(R.id.imageViewClose);
        chips_input_ChipsDialog = dialog.findViewById(R.id.chips_input);
        if (mArrChipsDialog != null) {
            for (String item :
                    mArrChipsDialog) {
                chips_input_ChipsDialog.addChip(new Chip(item, item));
            }
        }
    }

    private void initListener(final Dialog dialog) {
        txtStrTitle.setText(mStrTitle);

        if (mStrHint == null || mStrHint.length() < 0) {
            mStrHint = mStrTitle;
        }

        chips_input_ChipsDialog.getEditText().post(new Runnable() {
            @Override
            public void run() {
                chips_input_ChipsDialog.getEditText().setHint(mStrHint);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddChipsDialogListener.OnCancelListener();
                dialog.dismiss();
            }
        });
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddChipsDialogListener.OnCancelListener();
//                Util.hideSoftKeyboard(mContext, imageViewClose);
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String freeText = chips_input_ChipsDialog.getEditText().getText().toString();
                if (freeText.length() > 0) {
                    chips_input_ChipsDialog.addChip(new Chip(freeText, freeText));
                }
                onAddChipsDialogListener.OnSubmitListener(chips_input_ChipsDialog.getSelectedChipList());
//                Util.hideSoftKeyboard(mContext, btnSave);
                dialog.dismiss();
            }
        });

    }

    private void loadFilterList() {
        List<Chip> filterList = new ArrayList<>();
        for (int i = 0; i < mArrFilterChips.size(); i++) {
            filterList.add(new Chip(mArrFilterChips.get(i), mArrFilterChips.get(i)));
        }
        // pass the ContactChip list
        chips_input_ChipsDialog.setFilterableList(filterList);
    }

    private void showDialog() {

        Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.cp_add_chipsview);
        initView(dialog);
        initListener(dialog);
        loadFilterList();
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.TOP);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Util.setupUI(dialog.getWindow().getDecorView().getRootView(), (Activity) mContext);
        dialog.getWindow().setSoftInputMode (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

    }

    public interface OnAddChipsDialogListener {

        void OnSubmitListener(List<? extends ChipInterface> mArrSelectedChipsDialog);

        void OnCancelListener();
    }

}
