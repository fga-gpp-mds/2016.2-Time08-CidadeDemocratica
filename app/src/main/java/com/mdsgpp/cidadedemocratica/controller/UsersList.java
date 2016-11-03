package com.mdsgpp.cidadedemocratica.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.model.User;
import com.mdsgpp.cidadedemocratica.persistence.DataContainer;
import com.mdsgpp.cidadedemocratica.requester.RequestResponseHandler;
import com.mdsgpp.cidadedemocratica.requester.RequestUpdateListener;
import com.mdsgpp.cidadedemocratica.requester.Requester;
import com.mdsgpp.cidadedemocratica.requester.UserRequestResponseHandler;

import java.util.ArrayList;

public class UsersList extends AppCompatActivity implements RequestUpdateListener {

    private ListView usersListView;
    private ProgressDialog progressDialog;
    final UserListAdapter userAdapter = new UserListAdapter(this, new ArrayList<User>());

    private int preLast = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        if (DataContainer.getInstance().getUsers().size() == 0) {
            pullUsersData();
        } else {
            loadUsersList();
        }
    }

    private void loadUsersList(){
        usersListView = (ListView) findViewById(R.id.userList);

        ArrayList<User> usersList = getUsersList();

        if (usersListView.getAdapter() == null) {
            usersListView.setAdapter(userAdapter);
        }

        userAdapter.updateData(usersList);

        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
                User userClicked = (User)userAdapter.getItem(position);
                long id = userClicked.getId();
                Intent intent = new Intent(getApplicationContext(), UserProfile.class);
                intent.putExtra("userId", id);
                startActivity(intent);
            }
        });

        usersListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;

                if(lastItem == totalItemCount - 15) {
                    if(preLast != lastItem) {
                        preLast = lastItem;
                        pullUsersData();
                    }
                }

            }
        });
    }

    private ArrayList<User> getUsersList(){
        DataContainer dataContainer = DataContainer.getInstance();
        return dataContainer.getUsers();
    }

    private void pullUsersData() {
        if (progressDialog == null) {
            progressDialog = FeedbackManager.createProgressDialog(this, getString(R.string.message_load_users));
        }

        UserRequestResponseHandler userRequestResponseHandler = new UserRequestResponseHandler();
        userRequestResponseHandler.setRequestUpdateListener(this);

        Requester requester = new Requester(UserRequestResponseHandler.usersEndpointUrl, userRequestResponseHandler);
        requester.setParameter("page", String.valueOf(UserRequestResponseHandler.nextPageToRequest));
        System.out.println("loading users...");
        requester.getAsync();
    }

    private void createToast(String message){
        FeedbackManager.createToast(this, message);
    }

    @Override
    public void afterSuccess(RequestResponseHandler handler, Object response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                loadUsersList();
                createToast(getString(R.string.message_success_load_users));
            }
        });

        UserRequestResponseHandler.nextPageToRequest++;
    }

    @Override
    public void afterError(RequestResponseHandler handler, String message) {
        progressDialog.dismiss();
        createToast(message);
    }

}
