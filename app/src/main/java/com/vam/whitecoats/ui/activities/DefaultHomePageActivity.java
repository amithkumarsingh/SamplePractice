package com.vam.whitecoats.ui.activities;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.adapters.HomePageAdapter;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.HeaderDecoration;

import org.json.JSONArray;
import org.json.JSONException;

public class DefaultHomePageActivity extends AppCompatActivity implements HomePageAdapter.SingleClickListener   {

    private HomePageAdapter mAdapter;
    private Bundle bundle;
    private String associationList;
    private JSONArray associationArray;
    private Button done_action;
    private TextView skip;
    private RecyclerView recyclerView;
    private int selectedItem=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_home_page_selection);
        done_action=(Button)findViewById(R.id.done_action);
        skip=(TextView)findViewById(R.id.skip);

         recyclerView = (RecyclerView) findViewById(R.id.associationsList);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            associationList=bundle.getString("AssociationList");
        }

        try {
            associationArray=new JSONArray(associationList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(DefaultHomePageActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new HomePageAdapter(DefaultHomePageActivity.this,associationArray);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);


        recyclerView.addItemDecoration(HeaderDecoration.with(recyclerView)
                .inflate(R.layout.empty_layout)
                .parallax(0.9f)
                .dropShadowDp(1)
                .build(0, getApplicationContext()));


        done_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtil.isConnectingToInternet(DefaultHomePageActivity.this)) {
                    if (selectedItem != -1) {
                        Intent intent = new Intent();
                        intent.putExtra("ListPosition", selectedItem);
                        setResult(50505, intent);
                        finish();
                    } else {
                        Toast.makeText(DefaultHomePageActivity.this, "Please select a channel", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("ListPosition",-1);
                setResult(50505,intent);
                finish();
            }
        });

    }
    @Override
    public void onItemClickListener(int position, View view) {
        selectedItem=position;
        mAdapter.selectedItem();

    }


    @Override
    public void onBackPressed() {

    }
}
