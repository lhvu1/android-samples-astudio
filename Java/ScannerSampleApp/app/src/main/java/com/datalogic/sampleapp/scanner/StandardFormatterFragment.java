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
import com.datalogic.decode.configuration.ECIPolicy;
import com.datalogic.decode.configuration.Formatting;
import com.datalogic.decode.configuration.Gs1Conversion2d;
import com.datalogic.decode.configuration.Gs1LabelSetTransmitMode;
import com.datalogic.decode.configuration.GtinFormat;
import com.datalogic.decode.configuration.SendCodeID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StandardFormatterFragment extends Fragment {
    private static final String TAG = StandardFormatterFragment.class.getSimpleName();

    BarcodeManager mBarcodeManager;

    Formatting mFormatting;

    static List<ECIPolicy> kEciPolicyOptionsList = Arrays.asList(ECIPolicy.values());

    static List<Gs1Conversion2d> kGs1Conversion2dOptionsList = Arrays.asList(Gs1Conversion2d.values());

    static List<Gs1LabelSetTransmitMode> kGs1LabelSetTransmitModeOptionsList = Arrays.asList(Gs1LabelSetTransmitMode.values());

    static List<GtinFormat> kGtinFormatOptionsList = Arrays.asList(GtinFormat.values());

    static List<SendCodeID> kSendCodeIDOptionsList = Arrays.asList(SendCodeID.values());

    static Map<String, Boolean> kBooleanOptionsMap = new HashMap<>();

    static {
        kBooleanOptionsMap.put("Enable", true);
        kBooleanOptionsMap.put("Disable", false);
    }

    Button mStoreButton;
    Spinner mSpinnerECIPolicy;
    Spinner mSpinnerExternalFormatting;
    Spinner mSpinnerGs1Check;
    Spinner mSpinnerGs1Conversion2d;
    Spinner mSpinnerGs1LabelSetTransmitMode;
    Spinner mSpinnerGs1StringFormat;
    Spinner mSpinnerGtinFormat;
    Spinner mSpinnerHexFormat;
    Spinner mSpinnerRemoveNonPrintableChars;
    Spinner mSpinnerSendCodeId;
    EditText mEditTextGs1LabelSetPrefix;
    EditText mEditTextGsSubstitution;
    EditText mEditTextLabelPrefix;
    EditText mEditTextLabelSuffix;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBarcodeManager = new BarcodeManager();
        mFormatting = new Formatting(mBarcodeManager);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_standard_formatter, container, false);
        mStoreButton = view.findViewById(R.id.apply_change_button);

        mEditTextGs1LabelSetPrefix = view.findViewById(R.id.gs1_label_set_prefix_value);
        mEditTextGsSubstitution = view.findViewById(R.id.gs_substitution_value);
        mEditTextLabelPrefix = view.findViewById(R.id.label_prefix_value);
        mEditTextLabelSuffix = view.findViewById(R.id.label_suffix_value);

        // Adapter for boolean value
        ArrayAdapter<String> adapter = new ArrayAdapter<>(                                                                                                                                                                                getContext()
                , android.R.layout.simple_spinner_item
                , new ArrayList<>(kBooleanOptionsMap.keySet()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Adapter for ECI policy value
        ArrayAdapter<ECIPolicy> adapterECIPolicy = new ArrayAdapter<>(                                                                                                                                                                                getContext()
                , android.R.layout.simple_spinner_item
                , kEciPolicyOptionsList);
        adapterECIPolicy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Adapter for Gs1 Conversion2d value
        ArrayAdapter<Gs1Conversion2d> adapterGs1Conversion2d = new ArrayAdapter<>(                                                                                                                                                                                getContext()
                , android.R.layout.simple_spinner_item
                , kGs1Conversion2dOptionsList);
        adapterGs1Conversion2d.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Adapter for Gs1 Label Set Transmit Mode value
        ArrayAdapter<Gs1LabelSetTransmitMode> adapterGs1LabelSetTransmitMode = new ArrayAdapter<>(                                                                                                                                                                                getContext()
                , android.R.layout.simple_spinner_item
                , kGs1LabelSetTransmitModeOptionsList);
        adapterGs1LabelSetTransmitMode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Adapter for Gtin Format value
        ArrayAdapter<GtinFormat> adapterGtinFormat = new ArrayAdapter<>(                                                                                                                                                                                getContext()
                , android.R.layout.simple_spinner_item
                , kGtinFormatOptionsList);
        adapterGtinFormat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Adapter for Send CodeID value
        ArrayAdapter<SendCodeID> adapterSendCodeID = new ArrayAdapter<>(                                                                                                                                                                                getContext()
                , android.R.layout.simple_spinner_item
                , kSendCodeIDOptionsList);
        adapterSendCodeID.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Spinner for eci policy
        mSpinnerECIPolicy = view.findViewById(R.id.eci_policy_value);
        mSpinnerECIPolicy.setAdapter(adapterECIPolicy);

        // Spinner for external formatting
        mSpinnerExternalFormatting = view.findViewById(R.id.external_formatting_value);
        mSpinnerExternalFormatting.setAdapter(adapter);

        // Spinner for gs1 check
        mSpinnerGs1Check = view.findViewById(R.id.gs1_check_value);
        mSpinnerGs1Check.setAdapter(adapter);

        // Spinner for gs1 Conversion2d
        mSpinnerGs1Conversion2d = view.findViewById(R.id.gs1_conversion2d_value);
        mSpinnerGs1Conversion2d.setAdapter(adapterGs1Conversion2d);

        // Spinner for gs1 Label Set Transmit Mode
        mSpinnerGs1LabelSetTransmitMode = view.findViewById(R.id.gs1_label_set_transmit_mode_value);
        mSpinnerGs1LabelSetTransmitMode.setAdapter(adapterGs1LabelSetTransmitMode);

        // Spinner for gs1 String Format
        mSpinnerGs1StringFormat = view.findViewById(R.id.gs1_string_format_value);
        mSpinnerGs1StringFormat.setAdapter(adapter);

        // Spinner for gtin Format
        mSpinnerGtinFormat = view.findViewById(R.id.gtin_format_value);
        mSpinnerGtinFormat.setAdapter(adapterGtinFormat);

        // Spinner for hex Format
        mSpinnerHexFormat = view.findViewById(R.id.hex_format_value);
        mSpinnerHexFormat.setAdapter(adapter);

        // Spinner for remove non printable chars
        mSpinnerRemoveNonPrintableChars = view.findViewById(R.id.remove_non_printable_chars_value);
        mSpinnerRemoveNonPrintableChars.setAdapter(adapter);

        // Spinner for send code id
        mSpinnerSendCodeId = view.findViewById(R.id.send_codeid_value);
        mSpinnerSendCodeId.setAdapter(adapterSendCodeID);

        // Button for handling business logic
        mStoreButton.setOnClickListener(v->{
            // Change Eci policy
            ECIPolicy eciPolicy = (ECIPolicy) mSpinnerECIPolicy.getSelectedItem();
            mFormatting.eciPolicy.set(eciPolicy);
            // Change External formatting
            String externalFormatting = mSpinnerExternalFormatting.getSelectedItem().toString();
            mFormatting.externalFormatting.set(kBooleanOptionsMap.get(externalFormatting));
            // Change Gs1 check
            String gs1Check =  mSpinnerGs1Check.getSelectedItem().toString();
            mFormatting.gs1Check.set(kBooleanOptionsMap.get(gs1Check));
            // Change Gs1 conversion2d
            Gs1Conversion2d gs1Conversion2d = (Gs1Conversion2d) mSpinnerGs1Conversion2d.getSelectedItem();
            mFormatting.gs1Conversion2d.set(gs1Conversion2d);
            // Change gs1 label set prefix
            String gs1LabelSetPrefix = mEditTextGs1LabelSetPrefix.getText().toString();
            mFormatting.gs1LabelSetPrefix.set(gs1LabelSetPrefix);
            // Change gs1 label set transmit mode
            Gs1LabelSetTransmitMode gs1LabelSetTransmitMode = (Gs1LabelSetTransmitMode) mSpinnerGs1LabelSetTransmitMode.getSelectedItem();
            mFormatting.gs1LabelSetTransmitMode.set(gs1LabelSetTransmitMode);
            // Change gs1 string format
            String gs1StringFormat = mSpinnerGs1StringFormat.getSelectedItem().toString();
            mFormatting.gs1StringFormat.set(kBooleanOptionsMap.get(gs1StringFormat));
            // Change gtin format
            GtinFormat gtinFormat = (GtinFormat) mSpinnerGtinFormat.getSelectedItem();
            mFormatting.gtinFormat.set(gtinFormat);
            // Change hex format
            String hexFormat = mSpinnerHexFormat.getSelectedItem().toString();
            mFormatting.hexFormat.set(kBooleanOptionsMap.get(hexFormat));
            // Change remove non printable chars
            String removeNonPrintableChars = mSpinnerRemoveNonPrintableChars.getSelectedItem().toString();
            mFormatting.removeNonPrintableChars.set(kBooleanOptionsMap.get(removeNonPrintableChars));
            // Change send code id
            SendCodeID sendCodeID = (SendCodeID) mSpinnerSendCodeId.getSelectedItem();
            mFormatting.sendCodeId.set(sendCodeID);
            // Change gs substitution
            String gsSubstitution = mEditTextGsSubstitution.getText().toString();
            mFormatting.gsSubstitution.set(gsSubstitution);
            // Change label prefix
            String labelPrefix = mEditTextLabelPrefix.getText().toString();
            mFormatting.labelPrefix.set(labelPrefix);
            // Change label suffix
            String labelSuffix = mEditTextLabelSuffix.getText().toString();
            mFormatting.labelSuffix.set(labelSuffix);

            // Apply change
            mFormatting.store(mBarcodeManager, true);
        });


        return view;
    }
}
