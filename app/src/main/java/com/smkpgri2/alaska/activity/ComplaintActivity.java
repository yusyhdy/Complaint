package com.smkpgri2.alaska.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.smkpgri2.alaska.adapater.ComplaintAdapter;
import com.smkpgri2.alaska.entity.Complaint;
import com.smkpgri2.alaska.entity.Page;
import com.smkpgri2.alaska.service.ComplaintClient;
import com.smkpgri2.alaska.service.ServiceGenerator;
import com.smkpgri2.alaska.utils.AuthenticationUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComplaintActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ListView listComplaint;
    private ComplaintAdapter complaintAdapter;
    private ComplaintClient client;
    private String TAG = getClass().getSimpleName();
    private Complaint complaint;
    private EditText title, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

//        Log.d(TAG, "Authorization " + "Bearer " + AuthenticationUtils.getCurrentAuthentication().getAccessToken());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listComplaint = (ListView)findViewById(R.id.listComplaint);
        complaintAdapter = new ComplaintAdapter(this, new ArrayList<Complaint>());
        client = ServiceGenerator.createService(ComplaintClient.class);

        listComplaint.setAdapter(complaintAdapter);

        //tampil onclick
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_complaint_bottom);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ComplaintActivity.this, AddComplaintActivity.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        complaintGetTask();
    }

    public void AddComplaint (View view) {
        Intent i = new
                Intent(ComplaintActivity.this, AddComplaintActivity.class);
        startActivity(i);
    }
    //GET
    private void complaintGetTask() {
        Call<Page<Complaint>> call = client.getComplaints();
        call.enqueue(new Callback<Page<Complaint>>() {
            @Override
            public void onResponse(Response<Page<Complaint>> response) {
                Log.d(TAG, "code === "+Integer.toString(response.code()));
                if (response.code() == 200) {
                    Page<Complaint> page = response.body();
                    List<Complaint> complaintList = page.getContent();

                    complaintAdapter.clear();
                    complaintAdapter.addNews(complaintList);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(ComplaintActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.getLocalizedMessage() + " |\n| " + t.getMessage());
            }
        });
    }

    private void complaintGetTask(String q) {
        Call<Page<Complaint>> call = client.getComplaints(q);
        call.enqueue(new Callback<Page<Complaint>>() {
            @Override
            public void onResponse(Response<Page<Complaint>> response) {
                Log.d(TAG, "code === "+Integer.toString(response.code()));
                if (response.code() == 200) {
                    Page<Complaint> page = response.body();
                    List<Complaint> complaintList = page.getContent();

                    complaintAdapter.clear();
                    complaintAdapter.addNews(complaintList);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(ComplaintActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.getLocalizedMessage() + " |\n| " + t.getMessage());
            }
        });
    }

    private void ShowLogout() {

        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle("Peringatan");
        ad.setMessage("Apakah Anda Ingin Logout ?");
        ad.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                AuthenticationUtils.logout();
                ComplaintActivity.this.finish();
                Intent intent = new Intent(ComplaintActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        ad.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        ad.show();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void complaintPostTask(Complaint body) {
        Call<Complaint> call = client.postComplaints(complaint);
        call.enqueue(new Callback<Complaint>() {
            @Override
            public void onResponse(Response<Complaint> response) {
                title.setText("");
                message.setText("");
                complaint = new Complaint();
//                complaintGetTask("");
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(ComplaintActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.getLocalizedMessage() + " |\n| " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.complaint, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
        @Override
            public boolean onQueryTextSubmit(String s) {
            complaintGetTask(s);
            return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {return false;}
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                complaintGetTask();
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }


        if (id == R.id.action_refresh) {
            complaintGetTask();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.add_complaint) {
            // Handle the camera action
            Intent i = new Intent(ComplaintActivity.this, AddComplaintActivity.class);
            startActivity(i);
           // return true;

        } else if (id == R.id.view_complaint) {
            Intent i = new Intent(ComplaintActivity.this, ComplaintActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("geo:" + -7.866688 + "," + 111.466610 ));
            startActivity(intent);

        } else if (id == R.id.call) {
            String url = "https://id-id.facebook.com/SMK-PGRI-2-Ponorogo-231334446887982/";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        }
        else if (id == R.id.Logout) {
            ShowLogout();
            return true;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
