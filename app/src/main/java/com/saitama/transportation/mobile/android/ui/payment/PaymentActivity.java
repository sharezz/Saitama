package com.saitama.transportation.mobile.android.ui.payment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.saitama.transportation.mobile.android.R;
import com.saitama.transportation.mobile.android.event.PaymentEvent;
import com.saitama.transportation.mobile.android.server.ServerErrorResolver;
import com.saitama.transportation.mobile.android.ui.base.BaseActivity;
import com.saitama.transportation.mobile.android.validator.SimpleInputValidator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sharezzorama on 10/26/16.
 */

public class PaymentActivity extends BaseActivity {
    private static final int CARD_NUMBER_LENGTH = 16;
    private static final int CARD_CODE_LENGTH = 3;
    private EditText mNameView;
    private EditText mNumberView;
    private EditText mValidationNumberView;
    private Spinner mMonthSpinner;
    private Spinner mYearSpinner;
    private Button mOrderButton;
    private TextView mErrorView;
    private List<Integer> mMonths = new ArrayList<>();
    private List<Integer> mYears = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        prepareData();
        mNameView = (EditText) findViewById(R.id.cardName);
        mNumberView = (EditText) findViewById(R.id.cardNumber);
        mValidationNumberView = (EditText) findViewById(R.id.cardValidationNumber);
        mMonthSpinner = (Spinner) findViewById(R.id.monthSpinner);
        mYearSpinner = (Spinner) findViewById(R.id.yearSpinner);
        mOrderButton = (Button) findViewById(R.id.orderButton);
        mErrorView = (TextView) findViewById(R.id.error);
        mOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mErrorView.setText(null);
                mErrorView.setVisibility(View.VISIBLE);
                mNameView.setError(null);
                mNumberView.setError(null);
                mValidationNumberView.setError(null);
                if (!isDataValid()) {
                    return;
                }
                Integer selectedMonth = (Integer) mMonthSpinner.getSelectedItem();
                Integer selectedYear = (Integer) mYearSpinner.getSelectedItem();
                StringBuilder expirationStringBuilder = new StringBuilder();
                expirationStringBuilder.append(selectedMonth);
                expirationStringBuilder.append("/");
                expirationStringBuilder.append(selectedYear - 2000);
                getCoreService().getPaymentManager().rent(mNameView.getText().toString(),
                        mNumberView.getText().toString(),
                        expirationStringBuilder.toString(),
                        mValidationNumberView.getText().toString());
            }
        });

        ArrayAdapter<Integer> monthsAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mMonths);
        mMonthSpinner.setAdapter(monthsAdapter);
        ArrayAdapter<Integer> yearsAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mYears);
        mYearSpinner.setAdapter(yearsAdapter);
    }

    /**
     * Prepares months and years lists for spinners filling
     */
    private void prepareData() {
        for (int month = 1; month <= 12; month++) {
            mMonths.add(month);
        }

        for (int year = 2016; year <= 2019; year++) {
            mYears.add(year);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
    public void onMessageEvent(PaymentEvent event) {
        if (event.isOk()) {
            Toast.makeText(this, event.getData(), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            String errorMessage = ServerErrorResolver.resolveError(event.getException());
            mErrorView.setText(errorMessage);
            mErrorView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Checks if input data valid
     */
    private boolean isDataValid() {
        boolean isValid = true;
        if (SimpleInputValidator.isEmpty(mNameView, R.string.error_field_can_not_be_empty)) {
            isValid = false;
        }
        if (!SimpleInputValidator.isLengthEq(mNumberView, CARD_NUMBER_LENGTH, R.string.error_wrong_card_number)) {
            isValid = false;
        }
        if (!SimpleInputValidator.isLengthEq(mValidationNumberView, CARD_CODE_LENGTH, R.string.error_wrong_card_code)) {
            isValid = false;
        }
        return isValid;
    }

}
