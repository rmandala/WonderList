package com.example.wunderlist;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity {
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	private static String fileName = "todo.txt";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        
        items = new ArrayList<String>();
        itemsAdapter = new ArrayAdapter<String>(this, 
        		android.R.layout.simple_list_item_checked, items);
        readItems();
        lvItems  = (ListView)findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);

        setupListViewListener();
    }
    
    public void onAddNew(View v) {
    	EditText newTodo = (EditText) findViewById(R.id.NewTodoItem);
    	if(!"".equals(newTodo.getText().toString())) {
    		items.add(newTodo.getText().toString());
//    		itemsAdapter.add(newTodo.getText().toString());
        	newTodo.setText("");
        	writeItems();
    	}
    }
    
    public void setupListViewListener() {
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
//				items.remove(position);
//				itemsAdapter.notifyDataSetChanged();
				itemsAdapter.remove(itemsAdapter.getItem(position));
				writeItems();
				return true;
			}
		});
    	lvItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				((CheckedTextView) view).toggle();
			}
		});
    }
    
    private void readItems() {
    	try {
    		itemsAdapter.addAll(FileUtils.readLines(
    				new File(getFilesDir(), fileName)));
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    private void writeItems() {
    	try {
    		FileUtils.writeLines(new File(getFilesDir(), fileName), items);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}
