package com.inledco.light.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.inledco.light.R;

public class ManualAutoSwitchFragment extends BaseFragment {

    private OnFragmentInteractionListener mListener;
    private int mPadding = 20;
    private Button mManualButton;
    private Button mAutoButton;

    public ManualAutoSwitchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manual_auto_switch, null );

        initView(view);
        initEvent();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    protected void initView(View view) {
        mManualButton = (Button) view.findViewById(R.id.manualButton);
        mAutoButton = (Button) view.findViewById(R.id.autoButton);
    }

    @Override
    protected void initEvent() {
        mManualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button) v;

                btn.setBackgroundResource(R.drawable.manual_auto_switch_manual_background);
                mAutoButton.setBackgroundResource(R.drawable.manual_auto_switch_manual_blue_background);
                mAutoButton.setPadding(mPadding,mPadding,mPadding,mPadding);
                mAutoButton.setWidth(mAutoButton.getWidth() - mPadding);
                mAutoButton.setHeight(mAutoButton.getHeight() - mPadding);
            }
        });

        mAutoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button) v;

                btn.setBackgroundResource(R.drawable.manual_auto_switch_manual_background);
                mManualButton.setBackgroundResource(R.drawable.manual_auto_switch_manual_blue_background);
                mManualButton.setPadding(mPadding,mPadding,mPadding,mPadding);
                mManualButton.setWidth(mAutoButton.getWidth() - mPadding);
                mManualButton.setHeight(mAutoButton.getHeight() - mPadding);
            }
        });
    }

    @Override
    protected void initData() {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
