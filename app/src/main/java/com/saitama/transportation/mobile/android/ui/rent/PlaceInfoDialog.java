package com.saitama.transportation.mobile.android.ui.rent;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.saitama.transportation.mobile.android.R;
import com.saitama.transportation.mobile.android.server.entity.RentalPlace;

/**
 * Created by sharezzorama on 10/26/16.
 * The dialog for displaying the rental place information
 */

public class PlaceInfoDialog extends DialogFragment implements View.OnClickListener {
    private final static String KEY_ARG_PLACE = "PlaceInfoDialog.KEY_ARG_PLACE";
    private RentalPlace mRentalPlace;
    private TextView mTitleView;
    private Button mCancelButton;
    private Button mRentButton;

    public static PlaceInfoDialog newInstance(RentalPlace place) {
        PlaceInfoDialog dialog = new PlaceInfoDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_ARG_PLACE, place);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRentalPlace = (RentalPlace) getArguments().getSerializable(KEY_ARG_PLACE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog_place_info, container, false);
        mTitleView = (TextView) v.findViewById(R.id.title);
        mCancelButton = (Button) v.findViewById(R.id.cancelButton);
        mRentButton = (Button) v.findViewById(R.id.rentButton);
        mTitleView.setText(mRentalPlace.getName());
        mCancelButton.setOnClickListener(this);
        mRentButton.setOnClickListener(this);
        return v;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancelButton:
                dismiss();
                break;
            case R.id.rentButton:
                if (getTargetFragment() != null) {
                    ((RentalPlacesFragment) getTargetFragment()).rent(mRentalPlace);
                }
                dismiss();
                break;
        }
    }
}
