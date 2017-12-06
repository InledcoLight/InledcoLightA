package com.inledco.light.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inledco.light.R;
import com.inledco.light.activity.WebActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends BaseFragment
{

    public NewsFragment ()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView ( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        View view = inflater.inflate( R.layout.fragment_news, container, false );

        initView( view );
        initEvent();
        initData();
        return view;
    }

    @Override
    protected void initView(View view)
    {
        view.findViewById( R.id.news_web1 ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View view )
            {
                Intent intent = new Intent( getContext(), WebActivity.class );
                startActivity( intent );
            }
        } );

        view.findViewById( R.id.news_web2 ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View view )
            {
                Intent intent = new Intent( getContext(), WebActivity.class );
                startActivity( intent );
            }
        } );
    }

    @Override
    protected void initEvent ()
    {

    }

    @Override
    protected void initData ()
    {

    }
}
