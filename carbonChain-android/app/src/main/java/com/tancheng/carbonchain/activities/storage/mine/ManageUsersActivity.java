package com.tancheng.carbonchain.activities.storage.mine;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.customview.SlideListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户管理
 */
public class ManageUsersActivity extends AppCompatActivity {

    private SlideListView slideListView;
    private ManageUserAdapter manageUserAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);
        initView();
    }

    private void initView() {
        slideListView = findViewById(R.id.slideListView);
        manageUserAdapter = new ManageUserAdapter(this);
        slideListView.setAdapter(manageUserAdapter);
        List<String> stringList = new ArrayList<>();
        stringList.add("");
        stringList.add("");
        stringList.add("");
        manageUserAdapter.setList(stringList);
        manageUserAdapter.notifyDataSetChanged();

        slideListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(ManageUsersActivity.this,UserStorageActivity.class));
            }
        });
    }


    public class ManageUserAdapter extends BaseAdapter
    {
        private Context context;
        private List<String> list;

        public ManageUserAdapter(Context context) {
            this.context = context;
        }

        public void setList(List<String> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            if (list != null)
                return list.size();
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.mine_manage_user_list_item_layout,null);
                viewHolder.ivHeard = convertView.findViewById(R.id.ivHeard);
                viewHolder.tvPosition = convertView.findViewById(R.id.tvPosition);
                viewHolder.tvName = convertView.findViewById(R.id.tvName);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            return convertView;
        }
        class ViewHolder{
            ImageView ivHeard;
            TextView tvPosition;
            TextView tvName;
        }
    }

}
