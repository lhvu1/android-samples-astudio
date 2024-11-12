package com.datalogic.sampleapp.scanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.datalogic.decode.BarcodeManager;
import com.datalogic.decode.configuration.GoodRead;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GoodReadFragment extends Fragment {
    BarcodeManager mBarcodeManager;

    GoodRead mGoodRead;

    Spinner mSpinnerGoodReadEnable;
    Spinner mSpinnerGoodReadLedEnable;

    Spinner mSpinnerGoodReadVibrateEnable;
    Spinner mSpinnerGreenSpot;

    Button mStoreButton;

    static Map<String, Boolean> kBooleanOptionsMap = new HashMap<>();

    static {
        kBooleanOptionsMap.put("Enable", true);
        kBooleanOptionsMap.put("Disable", false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBarcodeManager = new BarcodeManager();
        mGoodRead = new GoodRead(mBarcodeManager);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_good_read, container, false);
        // Adapter for boolean value
        ArrayAdapter<String> adapter = new ArrayAdapter<>(                                                                                                                                                                                getContext()
                , android.R.layout.simple_spinner_item
                , new ArrayList<>(kBooleanOptionsMap.keySet()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Spinner for good read enable
        mSpinnerGoodReadEnable = view.findViewById(R.id.good_read_enable_value);
        mSpinnerGoodReadEnable.setAdapter(adapter);

        // Spinner for good read led enable
        mSpinnerGoodReadLedEnable = view.findViewById(R.id.good_read_led_enable_value);
        mSpinnerGoodReadLedEnable.setAdapter(adapter);

        // Spinner for good read vibrate enable
        mSpinnerGoodReadVibrateEnable = view.findViewById(R.id.good_read_vibrate_enable_value);
        mSpinnerGoodReadVibrateEnable.setAdapter(adapter);

        // Spinner for green spot enable
        mSpinnerGreenSpot = view.findViewById(R.id.green_spot_enable_value);
        mSpinnerGreenSpot.setAdapter(adapter);

        // Button for store change
        mStoreButton = view.findViewById(R.id.apply_change_button);

        // Handle logic for button
        mStoreButton.setOnClickListener( v-> {
            // Change good read enable
            String goodReadEnable = mSpinnerGoodReadEnable.getSelectedItem().toString();
            mGoodRead.goodReadEnable.set(kBooleanOptionsMap.get(goodReadEnable));
            // Change good read led enable
            String goodReadLedEnable = mSpinnerGoodReadLedEnable.getSelectedItem().toString();
            mGoodRead.goodReadLedEnable.set(kBooleanOptionsMap.get(goodReadLedEnable));
            // Change good read vibrate enable
            String goodReadVibrateEnable = mSpinnerGoodReadVibrateEnable.getSelectedItem().toString();
            mGoodRead.goodReadVibrateEnable.set(kBooleanOptionsMap.get(goodReadVibrateEnable));
            // Change green spot enable
            String greenSpot = mSpinnerGreenSpot.getSelectedItem().toString();
            mGoodRead.greenSpotEnable.set(kBooleanOptionsMap.get(greenSpot));

            // Apply Change
            mGoodRead.store(mBarcodeManager, true);
        });
        return view;
    }
}
