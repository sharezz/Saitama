package com.saitama.transportation.mobile.android.ui.auth;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.saitama.transportation.mobile.android.MainActivity;
import com.saitama.transportation.mobile.android.R;
import com.saitama.transportation.mobile.android.event.AuthEvent;
import com.saitama.transportation.mobile.android.server.ServerErrorResolver;
import com.saitama.transportation.mobile.android.ui.base.BaseFragment;
import com.saitama.transportation.mobile.android.validator.SimpleInputValidator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by sharezzorama on 10/26/16.
 */

public class AuthFragment extends BaseFragment implements View.OnClickListener {
    private EditText mEmailView;
    private EditText mPasswordView;
    private TextView mErrorView;
    private Button mLoginButton;
    private Button mRegisterButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth, container, false);
        mEmailView = (EditText) view.findViewById(R.id.email);
        mPasswordView = (EditText) view.findViewById(R.id.password);
        mErrorView = (TextView) view.findViewById(R.id.error);
        mLoginButton = (Button) view.findViewById(R.id.loginButton);
        mRegisterButton = (Button) view.findViewById(R.id.registerButton);
        mLoginButton.setOnClickListener(this);
        mRegisterButton.setOnClickListener(this);
        mErrorView.setVisibility(!mErrorView.getText().toString().isEmpty() ? View.VISIBLE : View.INVISIBLE);
        return view;
    }

    /**
     * Handles the login button click
     */
    private void onLoginClick() {
        mErrorView.setText(null);
        mErrorView.setVisibility(View.INVISIBLE);
        mEmailView.setError(null);
        mPasswordView.setError(null);
        getCoreService().getAuthManager().login(mEmailView.getText().toString(), mPasswordView.getText().toString());
    }

    /**
     * Handles the register button click
     */
    private void onRegisterClick() {
        mErrorView.setText(null);
        mErrorView.setVisibility(View.INVISIBLE);
        mEmailView.setError(null);
        mPasswordView.setError(null);
        getCoreService().getAuthManager().register(mEmailView.getText().toString(), mPasswordView.getText().toString());
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AuthEvent event) {
        if (event.isOk()) {
            ((MainActivity) getActivity()).setStartFragment();
            hideKeyboard();
        } else {
            String errorMessage = ServerErrorResolver.resolveError(event.getException());
            mErrorView.setText(errorMessage);
            mErrorView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Hides software keyboard
     */
    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                if (isDataValid()) {
                    onLoginClick();
                }
                break;
            case R.id.registerButton:
                if (isDataValid()) {
                    onRegisterClick();
                }
                break;
        }
    }

    /**
     * Checks if input data valid
     */
    private boolean isDataValid() {
        boolean isValid = true;
        if (!SimpleInputValidator.isEmail(mEmailView)) {
            isValid = false;
        }
        if (SimpleInputValidator.isEmpty(mPasswordView, R.string.error_field_can_not_be_empty)) {
            isValid = false;
        }
        return isValid;
    }
}
