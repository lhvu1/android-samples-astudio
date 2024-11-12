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
import com.datalogic.decode.configuration.KeyWedgeMode;
import com.datalogic.decode.configuration.KeyboardWedge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyboardWedgeFragment extends Fragment {

    KeyboardWedge mKeyboardWedge;

    BarcodeManager mBarcodeManager;

    Spinner mSpinnerKeyboardWedge;

    Spinner mSpinnerOnlyFocus;

    Spinner mSpinnerWedgeMode;

    Button mButtonStore;

    static List<KeyWedgeMode> kKeyWedgeModeOptionsList = Arrays.asList(KeyWedgeMode.values());

    static Map<String, Boolean> kBooleanOptionsMap = new HashMap<>();

    static {
        kBooleanOptionsMap.put("Enable", true);
        kBooleanOptionsMap.put("Disable", false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBarcodeManager = new BarcodeManager();
        mKeyboardWedge = new KeyboardWedge(mBarcodeManager);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_keyboard_wedge, container, false);

        mButtonStore = view.findViewById(R.id.apply_change_button);
        // Adapter for boolean value
        ArrayAdapter<String> adapter = new ArrayAdapter<>(                                                                                                                                                                                getContext()
                , android.R.layout.simple_spinner_item
                , new ArrayList<>(kBooleanOptionsMap.keySet()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Adapter for wedge mode value
        ArrayAdapter<KeyWedgeMode> adapterKeyWedgeMode = new ArrayAdapter<>(                                                                                                                                                                                getContext()
                , android.R.layout.simple_spinner_item
                , kKeyWedgeModeOptionsList);
        adapterKeyWedgeMode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // Spinner for keyboard wedge
        mSpinnerKeyboardWedge = view.findViewById(R.id.keyboard_wedge_enable_value);
        mSpinnerKeyboardWedge.setAdapter(adapter);

        // Spinner for only focus
        mSpinnerOnlyFocus = view.findViewById(R.id.only_focus_value);
        mSpinnerOnlyFocus.setAdapter(adapter);

        // Spinner for wedge mode
        mSpinnerWedgeMode = view.findViewById(R.id.wedge_mode_value);
        mSpinnerWedgeMode.setAdapter(adapterKeyWedgeMode);

        // Handle logic for button
        mButtonStore.setOnClickListener(v->{
            // Change keyboard wedge
            String keyboardWedgeValue = mSpinnerKeyboardWedge.getSelectedItem().toString();
            mKeyboardWedge.enable.set(kBooleanOptionsMap.get(keyboardWedgeValue));

            // Change only focus
            String onlyFocusValue = mSpinnerOnlyFocus.getSelectedItem().toString();
            mKeyboardWedge.onlyOnFocus.set(kBooleanOptionsMap.get(onlyFocusValue));

            // Change wedge mode
            KeyWedgeMode keyWedgeMode = (KeyWedgeMode) mSpinnerWedgeMode.getSelectedItem();
            mKeyboardWedge.wedgeMode.set(keyWedgeMode);

            // Apply Change
            mKeyboardWedge.store(mBarcodeManager, true);

        });


        return view;
    }
}
