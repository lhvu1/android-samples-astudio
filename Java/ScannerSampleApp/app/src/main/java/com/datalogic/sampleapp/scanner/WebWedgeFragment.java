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
import com.datalogic.decode.configuration.WebWedge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WebWedgeFragment extends Fragment {

    WebWedge mWebWedge;

    BarcodeManager mBarcodeManager;

    Button mStoreButton;

    Spinner mSpinnerWebWedge;

    static Map<String, Boolean> kBooleanOptionsMap = new HashMap<>();

    static {
        kBooleanOptionsMap.put("Enable", true);
        kBooleanOptionsMap.put("Disable", false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBarcodeManager = new BarcodeManager();
        mWebWedge = new WebWedge(mBarcodeManager);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_wedge, container, false);

        mStoreButton = view.findViewById(R.id.apply_change_button);

        // Adapter for boolean value
        ArrayAdapter<String> adapter = new ArrayAdapter<>(                                                                                                                                                                                getContext()
                , android.R.layout.simple_spinner_item
                , new ArrayList<>(kBooleanOptionsMap.keySet()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Spinner for enable
        mSpinnerWebWedge = view.findViewById(R.id.web_wedge_enable_value);
        mSpinnerWebWedge.setAdapter(adapter);

        mStoreButton.setOnClickListener(v->{
            // Change web wedge
            String webWedge = mSpinnerWebWedge.getSelectedItem().toString();
            mWebWedge.enable.set(kBooleanOptionsMap.get(webWedge));

            mWebWedge.store(mBarcodeManager, true);
        });



        return view;
    }

}
