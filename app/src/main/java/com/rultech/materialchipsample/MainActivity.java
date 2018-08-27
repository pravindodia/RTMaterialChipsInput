package com.rultech.materialchipsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.materialchips.ChipsInput;
import com.materialchips.model.Chip;
import com.materialchips.model.ChipInterface;
import com.materialchips.views.AddChipsDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ChipsInput chips_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chips_input = findViewById(R.id.chips_input);
        chips_input.getEditText().setFocusableInTouchMode(false);
        chips_input.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> arrSelectedList = new ArrayList<>();
                for (ChipInterface item : chips_input.getSelectedChipList()) {
                    arrSelectedList.add(item.getLabel());
                }
                AddChipsDialog.builder(MainActivity.this)
                        .withTitle("Add Countries")
                        .withHint("Add Countries")
                        .withExistingChips(arrSelectedList)
                        .withFilterList(Arrays.asList(getResources().getStringArray(R.array.countries_array)))
                        .OnAddChipsDialogListener(new AddChipsDialog.OnAddChipsDialogListener() {
                            @Override
                            public void OnSubmitListener(List<? extends ChipInterface> mArrSelectedChipsDialog) {
                                chips_input.removeAllChips();
                                for (ChipInterface item : mArrSelectedChipsDialog) {
                                    if (!chips_input.getSelectedChipList().contains(item))
                                        chips_input.addChip(item);
                                }
                            }

                            @Override
                            public void OnCancelListener() {

                            }
                        }).show();

            }
        });
    }
}
