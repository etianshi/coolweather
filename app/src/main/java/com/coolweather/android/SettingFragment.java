package com.coolweather.android;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.coolweather.android.service.AutoUpdateService;

/**
 * Created by nano on 2017/3/31.
 */

public class SettingFragment extends Fragment {
    private SharedPreferences pref;
    private LinearLayout layout;
    private Button stateButton;
    private EditText freqText;
    private View configView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.config_setting, container, false);
        layout = (LinearLayout) view.findViewById(R.id.config_layout);
        stateButton = (Button) view.findViewById(R.id.change_state);
        freqText = (EditText) view.findViewById(R.id.change_freq);
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String updateState = pref.getString("update_state", null);
        String updateFreq = pref.getString("update_freq", null);
        if (updateState != null) {
            if (updateState.equals("automatic")) {
                stateButton.setText("手动更新");
                layout.setVisibility(View.GONE);
            } else if (updateState.equals("manual")) {
                stateButton.setText("自动更新");
                layout.setVisibility(View.VISIBLE);
            }
        } else {
            // 默认是“自动更新”
            stateButton.setText("手动更新");
            layout.setVisibility(View.GONE);
        }
        if (updateFreq != null) {
            freqText.setText(updateFreq);
        } else {
            // 默认是8小时更新一次
            freqText.setText("8");
        }
        configView = view;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle saveInstanceState) {
        super.onActivityCreated(saveInstanceState);
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final SharedPreferences.Editor editor = pref.edit();
        stateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonText = (String)stateButton.getText();
                WeatherActivity activity = (WeatherActivity) getActivity();
                if (buttonText.equals("手动更新")) {
                    stateButton.setText("自动更新");
                    layout.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(getActivity(), AutoUpdateService.class);
                    activity.stopService(intent);
                } else if (buttonText.equals("自动更新")) {
                    stateButton.setText("手动更新");
                    editor.putString("update_freq", freqText.getText().toString());
                    editor.apply();
                    layout.setVisibility(View.GONE);
                    hideSoftInput();
                    Intent intent = new Intent(getActivity(), AutoUpdateService.class);
                    activity.startService(intent);
                }
            }
        });
    }

    private void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = (View) getActivity().findViewById(R.id.main_layout);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public void onStart() {
        super.onStart();
        //Log.d("settingfragment", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        //Log.d("settingfragment", "onResume");
    }
    @Override
    public void onPause() {
        super.onPause();
        //Log.d("settingfragment", "onPause");
    }
    @Override
    public void onStop() {
        super.onStop();
        //Log.d("settingfragment", "onStop");
    }
}
