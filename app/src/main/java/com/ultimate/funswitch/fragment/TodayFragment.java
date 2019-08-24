package com.ultimate.funswitch.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ultimate.funswitch.R;
import com.ultimate.funswitch.adapter.TodoListAdapter;
import com.ultimate.funswitch.model.Model;
import com.ultimate.funswitch.request_connection.ModelService;
import com.ultimate.funswitch.request_connection.RetrofitService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TodayFragment extends Fragment {
    RecyclerView mRecyclerView_List;
    ProgressDialog progressDialog;
    TodoListAdapter mTodoListAdapter;
    ArrayList<Model> mPendlist;
    ArrayList<Model> mCompletList;
    TextView mDyTv;

    public TodayFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_today, container, false);

        mRecyclerView_List = mView.findViewById(R.id.rc_list);
        mDyTv = mView.findViewById(R.id.day_tv);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        int today = Calendar.getInstance().get(Calendar.DATE);
        mDyTv.setText("" + today);
        ModelService service = RetrofitService.getRetrofitInstance().create(ModelService.class);
        Call<List<Model>> call = service.getAllItems();
        //Call<List<Model>> call=service.getAllItems();
        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                progressDialog.dismiss();
                generateDataList(response.body());
                Log.d("MSG", "" + response.body());
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "No Internet...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
        return mView;
    }


    private void generateDataList(List<Model> mItemList) {
        LinearLayoutManager manager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView_List.setLayoutManager(manager1);
        mPendlist = new ArrayList<>();
        mCompletList = new ArrayList<>();

        for (int i = 0; i < mItemList.size(); i++) {

            if (mItemList.get(i).getStatus().equalsIgnoreCase("PENDING")) {
                mPendlist.add(mItemList.get(i));
            } else if (mItemList.get(i).getStatus().equalsIgnoreCase("COMPLETED")) {
                mCompletList.add(mItemList.get(i));
            }
        }
        final ArrayList<Object> headerList = new ArrayList<>();
        headerList.add("Pending");
        headerList.addAll(mPendlist);
        headerList.add("Completed");
        headerList.addAll(mCompletList);

        mTodoListAdapter = new TodoListAdapter(getActivity(), headerList);
        mRecyclerView_List.setAdapter(mTodoListAdapter);
    }

}
