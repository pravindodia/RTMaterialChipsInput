# RTMaterialChipsInput
RTMaterialChipsInput is modified MaterialChipsInput for easy integration 
removed few bugs, 
added few features, 
don't have much time put notes, 
so please run the sample code and check out for all the features.

Modified Version of https://github.com/pchmn/MaterialChipsInput
All credits goes to https://github.com/pchmn/MaterialChipsInput

```java 
AddChipsDialog.builder(MainActivity.this)
                        .withTitle("Add Countries")
                        .withHint("Add Countries")
                        .withExistingChips(arrSelectedList)
                        .withFilterList(Arrays.asList(getResources().getStringArray(R.array.countries_array)))
                        .OnAddChipsDialogListener(new AddChipsDialog.OnAddChipsDialogListener() {
                            @Override
                            public void OnSubmitListener(List<? extends ChipInterface> mArrSelectedChipsDialog) {
                                //TODO: DO Whatever you like with the list
                            }

                            @Override
                            public void OnCancelListener() {
                                //TODO: DO Whatever you like with the list
                            }
                        }).show();
                        
```
