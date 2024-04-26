package com.temmahadi.packyourbag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.temmahadi.packyourbag.Adapter.Check_list_adapter;
import com.temmahadi.packyourbag.Constants.MyConstants;
import com.temmahadi.packyourbag.DataBase.roomDB;
import com.temmahadi.packyourbag.Models.items;

import java.util.ArrayList;
import java.util.List;

public class CheckList extends AppCompatActivity {
    RecyclerView recyclerView;
    Check_list_adapter checkListAdapter;
    roomDB database;
    List<items> itemsList= new ArrayList<>();
    String header,show;
    EditText txtAdd; Button btnAdd;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        header = intent.getStringExtra(MyConstants.HEADER_SMALL);
        show = intent.getStringExtra(MyConstants.SHOW_SMALL);
        getSupportActionBar().setTitle(header);

        txtAdd = findViewById(R.id.txtAdd);
        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recyclerView);
        linearLayout = findViewById(R.id.linearLayout);

        database = roomDB.getInstance(this);
        if(MyConstants.FALSE_STRING.equals(show)){
            linearLayout.setVisibility(View.GONE);
            itemsList = database.mainDAO().getAllSelected(true);
        }else
            itemsList = database.mainDAO().getAll(header);

        updateRecycler(itemsList);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = txtAdd.getText().toString();
                if(!itemName.isEmpty()){
                    addNewItem(itemName);
                    Toast.makeText(CheckList.this,"Item added",Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(CheckList.this,"Empty Cannot be added",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void addNewItem(String itemName){
        items item= new items();
        item.setChecked(false);
        item.setItemName(itemName);
        item.setCategory(header);
        item.setAddedBy(MyConstants.USER_SMALL);
        database.mainDAO().saveItem(item);
        itemsList = database.mainDAO().getAll(header);
        updateRecycler(itemsList);
        recyclerView.scrollToPosition(checkListAdapter.getItemCount()-1);
        txtAdd.setText("");
    }

    private void updateRecycler(List<items> itemsList){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        checkListAdapter = new Check_list_adapter(CheckList.this,itemsList,database,show);
        recyclerView.setAdapter(checkListAdapter);
    }
}