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

import com.datalogic.decode.*;
import com.datalogic.decode.configuration.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PresentationModeFragment extends Fragment {

    BarcodeManager mBarcodeManager;

    PresentationMode mPresentationMode;

    Button mStoreButton;

    static Map<String, Boolean> kBooleanOptionsMap = new HashMap<>();

    static {
        // Initialize boolean map
        kBooleanOptionsMap.put("Enable", true);
        kBooleanOptionsMap.put("Disable", false);
    }

    Spinner mSpinnerPresentationModeAimer;

    Spinner mSpinnerPresentationMode;

    EditText mEditTextPresentationModeSensitivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBarcodeManager =  new BarcodeManager();
        mPresentationMode = new PresentationMode(mBarcodeManager);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_presentation, container, false);
        mStoreButton = view.findViewById(R.id.apply_change_button);

        // Adapter for boolean value
        ArrayAdapter<String> adapter = new ArrayAdapter<>(                                                                                                                                                                                getContext()
                , android.R.layout.simple_spinner_item
                , new ArrayList<>(kBooleanOptionsMap.keySet()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Spinner for  presentation mode aimer
        mSpinnerPresentationModeAimer = view.findViewById(R.id.presentation_mode_aimer_value);
        mSpinnerPresentationModeAimer.setAdapter(adapter);

        // Spinner for  presentation mode enable
        mSpinnerPresentationMode = view.findViewById(R.id.presentation_mode_enable_value);
        mSpinnerPresentationMode.setAdapter(adapter);

        // Edit text for  presentation mode sensitivity
        mEditTextPresentationModeSensitivity = view.findViewById(R.id.presentation_mode_sensitivity_value);


        mStoreButton.setOnClickListener(v->{
            // Change presentation aimer
            String presentationModeAimer =  mSpinnerPresentationModeAimer.getSelectedItem().toString();
            mPresentationMode.presentationModeAimerEnable.set(kBooleanOptionsMap.get(presentationModeAimer));

            // Change presentation mode
            String  presentationMode = mSpinnerPresentationMode.getSelectedItem().toString();
            mPresentationMode.presentationModeEnable.set(kBooleanOptionsMap.get(presentationMode));

            // Change presentation mode sensitivity
            int presentationModeSensitivity =  Integer.parseInt(mEditTextPresentationModeSensitivity.getText().toString());
            mPresentationMode.presentationModeSensitivity.set(presentationModeSensitivity);

            // Apply change
            mPresentationMode.store(mBarcodeManager, true);
        });

        return view;
    }
}
