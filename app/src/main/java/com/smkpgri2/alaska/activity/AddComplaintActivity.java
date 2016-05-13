package com.smkpgri2.alaska.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.smkpgri2.alaska.adapater.ComplaintAdapter;
import com.smkpgri2.alaska.entity.Category;
import com.smkpgri2.alaska.entity.Complaint;
import com.smkpgri2.alaska.entity.Page;
import com.smkpgri2.alaska.service.CategoryClient;
import com.smkpgri2.alaska.service.ComplaintClient;
import com.smkpgri2.alaska.service.ServiceGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by smkpgri2 on 12/05/16.
 */
public class AddComplaintActivity extends AppCompatActivity{
    private ListView listComplaint;
    private ComplaintAdapter complaintAdapter;
    private ComplaintClient complaintClientclient;
    private CategoryClient categoryClient;
    private String TAG = getClass().getSimpleName();
    private List<Category> categories = new ArrayList<>();
    private EditText title, message;
    private Spinner spinnerCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ac = getSupportActionBar();
        ac.setDisplayHomeAsUpEnabled(true);

        title = (EditText) findViewById(R.id.edit_title);
        message = (EditText) findViewById(R.id.edit_content);
        spinnerCategory = (Spinner) findViewById(R.id.pilih_category);

        complaintClientclient = ServiceGenerator.createService(ComplaintClient.class);
        categoryClient = ServiceGenerator.createService(CategoryClient.class);

        Button btnSubmit = (Button) findViewById(R.id.action_save);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
                Complaint complaint = new Complaint();
                complaint.setId(null);
                complaint.setTitle(title.getText().toString());
                complaint.setMessage(message.getText().toString());

                Category category = categories.get(spinnerCategory.getSelectedItemPosition());

                complaint.setCategory(category);

                complaint.setStatus(Complaint.Status.PRIVATE);

              complaintPostTask(complaint);

            }


        });

        /**
         * get category from server
         */
        getCategory();

    }


    private void getCategory(){
        Call<Page<Category>> call = categoryClient.getCategories();
        call.enqueue(new Callback<Page<Category>>() {
            @Override
            public void onResponse(Response<Page<Category>> response) {
                Page p = response.body();
                categories.clear();
                categories = p.getContent();


                List<String> categoryList = new ArrayList<String>();
                for (Category c : categories){
                    categoryList.add(c.getName());
                }

                ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(AddComplaintActivity.this, android.R.layout.simple_dropdown_item_1line, categoryList);
                spinnerCategory.setAdapter(categoryAdapter);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(AddComplaintActivity.this, "failed to get category "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    //POST
    private void complaintPostTask(Complaint complaint) {
        Call<Complaint> call = complaintClientclient.postComplaints(complaint);
        call.enqueue(new Callback<Complaint>() {
            @Override
            public void onResponse(Response<Complaint> response) {
                title.setText("");
                message.setText("");

                AlertDialog.Builder builder = new AlertDialog.Builder(AddComplaintActivity.this);
                builder.setMessage("Success");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        title.requestFocus();
                    }
                });
                builder.show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(AddComplaintActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.getLocalizedMessage() + " |\n| " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
