package com.datalogic.sampleapp.scanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.datalogic.decode.BarcodeManager;
import com.datalogic.decode.configuration.IntentDeliveryMode;
import com.datalogic.decode.configuration.IntentWedge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntentWedgeFragment extends Fragment {
    BarcodeManager mBarcodeManager;
    IntentWedge mIntentWedge;

    Spinner mSpinnerIntentEnable;
    Spinner mSpinnerDeliveryMode;
    EditText mEditTextAction;
    EditText mEditTextCategory;
    EditText mEditTextBarcodeData;
    EditText mEditTextBarcodeString;
    EditText mEditTextBarcodeType;

    Button mButtonStore;

    static List<IntentDeliveryMode> kIntentDeliveryModeOptionsList = Arrays.asList(IntentDeliveryMode.values());

    static Map<String, Boolean> kBooleanOptionsMap = new HashMap<>();

    static {
        kBooleanOptionsMap.put("Enable", true);
        kBooleanOptionsMap.put("Disable", false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBarcodeManager = new BarcodeManager();
        mIntentWedge = new IntentWedge(mBarcodeManager);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intent_wedge, container, false);
        mButtonStore = view.findViewById(R.id.apply_change_button);

        // Adapter for boolean value
        ArrayAdapter<String> adapter = new ArrayAdapter<>(                                                                                                                                                                                getContext()
                , android.R.layout.simple_spinner_item
                , new ArrayList<>(kBooleanOptionsMap.keySet()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Adapter for intent delivery mode value
        ArrayAdapter<IntentDeliveryMode> adapterIntentDeliveryMode = new ArrayAdapter<>(                                                                                                                                                                                getContext()
                , android.R.layout.simple_spinner_item
                , kIntentDeliveryModeOptionsList);
        adapterIntentDeliveryMode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Spinner for enable intent mode
        mSpinnerIntentEnable = view.findViewById(R.id.intent_enable_value);
        mSpinnerIntentEnable.setAdapter(adapter);

        // Spinner for delivery mode
        mSpinnerDeliveryMode = view.findViewById(R.id.delivery_mode_value);
        mSpinnerDeliveryMode.setAdapter(adapterIntentDeliveryMode);
        mSpinnerDeliveryMode.setSelection(2); // Default value for spinner

        // Edittext for action
        mEditTextAction = view.findViewById(R.id.action_value);

        // Edittext for category
        mEditTextCategory = view.findViewById(R.id.category_value);

        // Edittext for barcode data
        mEditTextBarcodeData = view.findViewById(R.id.barcode_data_value);

        // Edittext for barcode string
        mEditTextBarcodeString = view.findViewById(R.id.barcode_string_value);

        // Edittext for barcode type
        mEditTextBarcodeType = view.findViewById(R.id.barcode_type_value);

        // Handle business logic
        mButtonStore.setOnClickListener( v-> {
            // Change intent mode
            String intentValue = mSpinnerIntentEnable.getSelectedItem().toString();
            mIntentWedge.enable.set(kBooleanOptionsMap.get(intentValue));

            // Change delivery mode
            IntentDeliveryMode intentDeliveryMode = (IntentDeliveryMode) mSpinnerDeliveryMode.getSelectedItem();
            mIntentWedge.deliveryMode.set(intentDeliveryMode);

            // Change action
            String action = mEditTextAction.getText().toString();
            mIntentWedge.action.set(action);

            // Change category
            String category = mEditTextCategory.getText().toString();
            mIntentWedge.category.set(category);

            // Change extra barcode data
            String barcodeData = mEditTextBarcodeData.getText().toString();
            mIntentWedge.extraBarcodeData.set(barcodeData);

            // Change extra barcode string
            String barcodeString = mEditTextBarcodeString.getText().toString();
            mIntentWedge.extraBarcodeString.set(barcodeString);

            // Change extra barcode type
            String barcodeType = mEditTextBarcodeType.getText().toString();
            mIntentWedge.extraBarcodeType.set(barcodeType);

            // Apply Change
            mIntentWedge.store(mBarcodeManager, true);
        });
        return view;
    }
}
