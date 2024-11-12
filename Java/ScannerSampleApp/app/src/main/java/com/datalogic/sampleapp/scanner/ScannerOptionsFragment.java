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

import com.datalogic.decode.configuration.BeamMode;
import com.datalogic.decode.configuration.IlluminationTime;
import com.datalogic.decode.configuration.IlluminationType;
import com.datalogic.decode.configuration.ImageCaptureProfile;
import com.datalogic.decode.configuration.ScanMode;
import com.datalogic.decode.*;
import com.datalogic.decode.configuration.ScannerOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScannerOptionsFragment extends Fragment {
    private static final String TAG = ScannerOptionsFragment.class.getSimpleName();

    BarcodeManager mBarcodeManager;

    com.datalogic.decode.configuration.ScannerOptions mScannerOptions;

    Button mStoreButton;

    static Map<String, Boolean> kBooleanOptionsMap = new HashMap<>();
    static List<BeamMode> kBeamModeOptionsList = Arrays.asList(BeamMode.values());
    static Map<Double, Integer> kTargetTimeoutOptionsMap = new HashMap<>();
    static {
        // Initialize boolean map
        kBooleanOptionsMap.put("Enable", true);
        kBooleanOptionsMap.put("Disable", false);

        // Initialize target timeout map
        kTargetTimeoutOptionsMap.put(0.25, 0);
        kTargetTimeoutOptionsMap.put(0.5, 1);
        kTargetTimeoutOptionsMap.put(1.0, 2);
        kTargetTimeoutOptionsMap.put(1.5, 3);
        kTargetTimeoutOptionsMap.put(2.0, 4);
    }

    static List<ImageCaptureProfile> kImageCaptureProfileOptionsList = Arrays.asList(ImageCaptureProfile.values());

    static List<IlluminationType> kIlluminationTypeOptionsList = Arrays.asList(IlluminationType.values());

    static List<ScanMode> kScanModeOptionsList = Arrays.asList(ScanMode.values());

    static List<IlluminationTime> kIlluminationTimeOptionsList = Arrays.asList(IlluminationTime.values());


    Spinner mSpinnerDisplayMode;

    Spinner mSpinnerIlluminationMode;

    Spinner mSpinnerAimMode;
    Spinner mSpinnerPickListMode;
    Spinner mSpinnerBeamMode;

    Spinner mSpinnerTargetMode;

    Spinner mSpinnerTargetTimeout;

    EditText mEditTextTargetReleaseTimeout;

    EditText mEditTextDecodeTimeout;

    Spinner mSpinnerImageCaptureProfile;

    EditText mEditTextCustomImageCaptureProfile;

    Spinner mSpinnerIlluminationType;

    Spinner mSpinnerScanMode;

    EditText mEditTextDoubleReadTimeout;

    Spinner mSpinnerIlluminationTime;

    Spinner mSpinnerEnhanceDOFEnable;

    EditText mEditTextImageDecodeTimeout;

    Spinner mSpinnerScannerMode;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBarcodeManager = new BarcodeManager();
        mScannerOptions = new ScannerOptions(mBarcodeManager);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scanner_options, container, false);

        mStoreButton = view.findViewById(R.id.apply_change_button);

        // Adapter for boolean value
        ArrayAdapter<String> adapter = new ArrayAdapter<>(                                                                                                                                                                                getContext()
                , android.R.layout.simple_spinner_item
                , new ArrayList<>(kBooleanOptionsMap.keySet()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Adapter for beam value
        ArrayAdapter<BeamMode> adapterBeamMode = new ArrayAdapter<>(getContext()
                , android.R.layout.simple_spinner_item, kBeamModeOptionsList);
        adapterBeamMode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Adapter for target timeout
        List<Double> sortedKeys = new ArrayList<>(kTargetTimeoutOptionsMap.keySet());
        Collections.sort(sortedKeys);
        ArrayAdapter<Double> adapterTargetTimeoutMode = new ArrayAdapter<>(getContext()
                , android.R.layout.simple_spinner_item, sortedKeys);
        adapterTargetTimeoutMode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Adapter for image capture profile value
        ArrayAdapter<ImageCaptureProfile> adapterImageCaptureProfile = new ArrayAdapter<>(getContext()
                , android.R.layout.simple_spinner_item, kImageCaptureProfileOptionsList);
        adapterImageCaptureProfile.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Adapter for illumination type value
        ArrayAdapter<IlluminationType> adapterIlluminationType = new ArrayAdapter<>(getContext()
                , android.R.layout.simple_spinner_item, kIlluminationTypeOptionsList);
        adapterIlluminationType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Adapter for scan mode value
        ArrayAdapter<ScanMode> adapterScanMode = new ArrayAdapter<>(getContext()
                , android.R.layout.simple_spinner_item, kScanModeOptionsList);
        adapterScanMode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Adapter for illumination time
        ArrayAdapter<IlluminationTime> adapterIlluminationTime = new ArrayAdapter<>(getContext()
                , android.R.layout.simple_spinner_item, kIlluminationTimeOptionsList);
        adapterIlluminationTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Spinner for display mode
        mSpinnerDisplayMode = view.findViewById(R.id.display_mode_value);
        mSpinnerDisplayMode.setAdapter(adapter);

        // Spinner for illumination mode
        mSpinnerIlluminationMode = view.findViewById(R.id.illumination_mode_value);
        mSpinnerIlluminationMode.setAdapter(adapter);

        // Spinner for aim mode
        mSpinnerAimMode = view.findViewById(R.id.aim_mode_value);
        mSpinnerAimMode.setAdapter(adapter);

        // Spinner for pick list
        mSpinnerPickListMode = view.findViewById(R.id.pick_list_value);
        mSpinnerPickListMode.setAdapter(adapter);

        // Spinner for target mode
        mSpinnerTargetMode = view.findViewById(R.id.target_mode_value);
        mSpinnerTargetMode.setAdapter(adapter);

        // Spinner for beam mode
        mSpinnerBeamMode = view.findViewById(R.id.beam_mode_value);
        mSpinnerBeamMode.setAdapter(adapterBeamMode);

        // Spinner for target timeout
        mSpinnerTargetTimeout = view.findViewById(R.id.target_timeout_value);
        mSpinnerTargetTimeout.setAdapter(adapterTargetTimeoutMode);

        // Edittext for target release scan
        mEditTextTargetReleaseTimeout = view.findViewById(R.id.target_release_timeout_value);

        // Edittext for decode timeout
        mEditTextDecodeTimeout = view.findViewById(R.id.decode_timeout_value);

        // Spinner for image capture profile
        mSpinnerImageCaptureProfile = view.findViewById(R.id.target_image_capture_profile_value);
        mSpinnerImageCaptureProfile.setAdapter(adapterImageCaptureProfile);

        // Edittext for custom image capture profile
        mEditTextCustomImageCaptureProfile = view.findViewById(R.id.custom_image_capture_profile_value);

        // Spinner for Illumination Type
        mSpinnerIlluminationType = view.findViewById(R.id.illumination_type_value);
        mSpinnerIlluminationType.setAdapter(adapterIlluminationType);

        // Spinner for Scan Mode
        mSpinnerScanMode = view.findViewById(R.id.scan_mode_value);
        mSpinnerScanMode.setAdapter(adapterScanMode);

        // Edittext for double read timeout
        mEditTextDoubleReadTimeout = view.findViewById(R.id.double_read_timeout_value);

        // Spinner for Illumination Time
        mSpinnerIlluminationTime = view.findViewById(R.id.illumination_time_value);
        mSpinnerIlluminationTime.setAdapter(adapterIlluminationTime);

        // Spinner for Illumination Time
        mSpinnerEnhanceDOFEnable = view.findViewById(R.id.enhance_dof_enable_value);
        mSpinnerEnhanceDOFEnable.setAdapter(adapter);

        // Edittext for image decode timeout
        mEditTextImageDecodeTimeout = view.findViewById(R.id.image_decode_value);

        // Spinner for scanner
        mSpinnerScannerMode = view.findViewById(R.id.enable_scanner_value);
        mSpinnerScannerMode.setAdapter(adapter);

        // Logic for Change scanner option
        mStoreButton.setOnClickListener(v->{
            // Change display mode
            String displayMode = mSpinnerDisplayMode.getSelectedItem().toString();
            mScannerOptions.displayModeEnable.set(kBooleanOptionsMap.get(displayMode));

            // Change illumination mode
            String illuminationMode = mSpinnerIlluminationMode.getSelectedItem().toString();
            mScannerOptions.illuminationEnable.set(kBooleanOptionsMap.get(illuminationMode));

            // Change aim mode
            String aimMode = mSpinnerAimMode.getSelectedItem().toString();
            mScannerOptions.aimEnable.set(kBooleanOptionsMap.get(aimMode));

            // Change picklist mode
            String picklistMode = mSpinnerPickListMode.getSelectedItem().toString();
            mScannerOptions.picklistEnable.set(kBooleanOptionsMap.get(picklistMode));

            // Change target mode
            String targetMode = mSpinnerTargetMode.getSelectedItem().toString();
            mScannerOptions.targetModeEnable.set(kBooleanOptionsMap.get(targetMode));

            // Change beam mode
            BeamMode beamMode = (BeamMode) mSpinnerBeamMode.getSelectedItem();
            mScannerOptions.targetMode.set(beamMode);

            if (beamMode == BeamMode.TARGET_TIMEOUT) {
                // Change target timeout
                Double targetTimeout = (Double) mSpinnerTargetTimeout.getSelectedItem();
                mScannerOptions.targetTimeout.set(kTargetTimeoutOptionsMap.get(targetTimeout));
            } else {
                // Change target release scan
                int targetReleaseScan =  Integer.parseInt(mEditTextTargetReleaseTimeout.getText().toString());
                mScannerOptions.targetReleaseTimeout.set(targetReleaseScan);
            }

            int decodeTimeout = Integer.parseInt(mEditTextDecodeTimeout.getText().toString());
            mScannerOptions.decodeTimeout.set(decodeTimeout);

            // Change Image Capture Profile
            ImageCaptureProfile imageCaptureProfile = (ImageCaptureProfile) mSpinnerImageCaptureProfile.getSelectedItem();
            mScannerOptions.imageCaptureProfile.set(imageCaptureProfile);

            // Change Custom Image Capture Profile
            int customImageCaptureProfile = Integer.parseInt(mEditTextCustomImageCaptureProfile.getText().toString());
            mScannerOptions.customImageCaptureProfile.set(customImageCaptureProfile);

            // Change Illumination type
            IlluminationType illuminationType = (IlluminationType) mSpinnerIlluminationType.getSelectedItem();
            mScannerOptions.illuminationType.set(illuminationType);

            // Change Scan mode
            ScanMode scanMode = (ScanMode) mSpinnerScanMode.getSelectedItem();
            mScannerOptions.scanMode.set(scanMode);

            // Change double read timeout
            int doubleReadTimeout = Integer.parseInt(mEditTextDoubleReadTimeout.getText().toString());
            mScannerOptions.doubleReadTimeout.set(doubleReadTimeout);

            // Change illumination time
            IlluminationTime illuminationTime = (IlluminationTime) mSpinnerIlluminationTime.getSelectedItem();
            mScannerOptions.illuminationTime.set(illuminationTime);

            // Change Enhance DOF
            String enhanceDof = mSpinnerEnhanceDOFEnable.getSelectedItem().toString();
            mScannerOptions.enhanceDOFEnable.set(kBooleanOptionsMap.get(enhanceDof));

            // Change image decode timeout
            int imageDecodeTimeout = Integer.parseInt(mEditTextImageDecodeTimeout.getText().toString());
            mScannerOptions.imageDecodeTimeout.set(imageDecodeTimeout);

            // Change scanner mode
            String scannerMode = mSpinnerScannerMode.getSelectedItem().toString();
            mScannerOptions.enableScanner.set(kBooleanOptionsMap.get(scannerMode));

            mScannerOptions.store(mBarcodeManager, true);
        });

        return view;
    }
}
