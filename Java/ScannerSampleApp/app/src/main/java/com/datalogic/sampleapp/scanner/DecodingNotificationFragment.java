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
import com.datalogic.decode.configuration.DecodingNotification;
import com.datalogic.decode.configuration.ToneNotificationChannel;
import com.datalogic.decode.configuration.ToneNotificationMode;

import java.util.Arrays;
import java.util.List;

public class DecodingNotificationFragment extends Fragment {
    BarcodeManager mBarcodeManager;
    DecodingNotification mDecodingNotification;
    Spinner mSpinnerGoodReadAudioChannel ;
    Spinner mSpinnerGoodReadAudioMode;

    EditText mEditTextGoodReadAudioFile;

    EditText mEditTextGoodReadAudioVolume;

    EditText mEditTextGoodReadCount;

    EditText mEditTextGoodReadDuration;

    EditText mEditTextGoodReadInterval;

    EditText mEditTextGoodReadTimeout;

    Button mButtonStore;

    static List<ToneNotificationChannel> kToneNotificationChannelOptionsList = Arrays.asList(ToneNotificationChannel.values());

    static List<ToneNotificationMode> kToneNotificationModeOptionsList = Arrays.asList(ToneNotificationMode.values());


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBarcodeManager = new BarcodeManager();
        mDecodingNotification = new DecodingNotification(mBarcodeManager);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_decoding_notification, container, false);
        mButtonStore = view.findViewById(R.id.apply_change_button);

        // Adapter for tone notification channel value
        ArrayAdapter<ToneNotificationChannel> adapterToneNotificationChannel = new ArrayAdapter<>(                                                                                                                                                                                getContext()
                , android.R.layout.simple_spinner_item
                , kToneNotificationChannelOptionsList);
        adapterToneNotificationChannel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Adapter for tone notification mode value
        ArrayAdapter<ToneNotificationMode> adapterToneNotificationMode = new ArrayAdapter<>(                                                                                                                                                                                getContext()
                , android.R.layout.simple_spinner_item
                , kToneNotificationModeOptionsList);
        adapterToneNotificationMode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Spinner for tone notification channel
        mSpinnerGoodReadAudioChannel = view.findViewById(R.id.good_read_audio_channel_value);
        mSpinnerGoodReadAudioChannel.setAdapter(adapterToneNotificationChannel);

        // Spinner for tone notification mode
        mSpinnerGoodReadAudioMode = view.findViewById(R.id.good_read_audio_mode_value);
        mSpinnerGoodReadAudioMode.setAdapter(adapterToneNotificationMode);

        // Edittext for audio file
        mEditTextGoodReadAudioFile = view.findViewById(R.id.good_read_audio_file_value);

        // Edittext for audio volume
        mEditTextGoodReadAudioVolume = view.findViewById(R.id.good_read_audio_volume_value);

        // Edittext for count
        mEditTextGoodReadCount = view.findViewById(R.id.good_read_count_value);

        // Edittext for duration
        mEditTextGoodReadDuration = view.findViewById(R.id.good_read_duration_value);

        // Edittext for interval
        mEditTextGoodReadInterval = view.findViewById(R.id.good_read_interval_value);

        // Edittext for timeout
        mEditTextGoodReadTimeout = view.findViewById(R.id.good_read_timeout_value);

        // Handle logic button
        mButtonStore.setOnClickListener(v->{

            // Change audio channel
            ToneNotificationChannel toneNotificationChannel = (ToneNotificationChannel) mSpinnerGoodReadAudioChannel.getSelectedItem();
            mDecodingNotification.goodReadAudioChannel.set(toneNotificationChannel);

            // Change audio mode
            ToneNotificationMode toneNotificationMode = (ToneNotificationMode) mSpinnerGoodReadAudioMode.getSelectedItem();
            mDecodingNotification.goodReadAudioMode.set(toneNotificationMode);

            // Change audio file
            String audioFile = mEditTextGoodReadAudioFile.getText().toString();
            mDecodingNotification.goodReadAudioFile.set(audioFile);

            // Change audio volume
            int audioVolume = Integer.parseInt(mEditTextGoodReadAudioVolume.getText().toString());
            mDecodingNotification.goodReadAudioVolume.set(audioVolume);

            // Change count
            int count = Integer.parseInt(mEditTextGoodReadCount.getText().toString());
            mDecodingNotification.goodReadCount.set(count);

            // Change duration
            int duration = Integer.parseInt(mEditTextGoodReadDuration.getText().toString());
            mDecodingNotification.goodReadDuration.set(duration);

            // Change interval
            int interval = Integer.parseInt(mEditTextGoodReadInterval.getText().toString());
            mDecodingNotification.goodReadInterval.set(interval);

            // Change timeout
            int timeout = Integer.parseInt(mEditTextGoodReadTimeout.getText().toString());
            mDecodingNotification.goodReadTimeout.set(timeout);

            // Apply Change
            mDecodingNotification.store(mBarcodeManager, true);
        });
        return view;
    }

}
