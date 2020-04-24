package com.tancheng.carbonchain.activities.storage.mine;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;

import com.tancheng.carbonchain.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 移交管理员
 */
public class TransferAdminActivity extends AppCompatActivity {
    private GridView mGridView;
    private TransferAdminAdapter transferAdminAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_admin);
        initView();
    }

    private void initView() {
        mGridView = findViewById(R.id.gridView);
        List<String> stringList = new ArrayList<>();
        stringList.add("");
        stringList.add("");
        stringList.add("");
        transferAdminAdapter = new TransferAdminAdapter(this);
        transferAdminAdapter.setStringList(stringList);
        mGridView.setAdapter(transferAdminAdapter);
        transferAdminAdapter.notifyDataSetChanged();
    }


    public class TransferAdminAdapter extends BaseAdapter
    {

        private Context context;
        private List<String> stringList;

        public TransferAdminAdapter(Context context) {
            this.context = context;
        }

        public void setStringList(List<String> stringList) {
            this.stringList = stringList;
        }

        @Override
        public int getCount() {
            if (stringList != null)
                return stringList.size();
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return stringList.get(position);
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
                convertView = LayoutInflater.from(context).inflate(R.layout.transfer_admin_item_layout,null);
                convertView.setTag(viewHolder);
            }else {
               viewHolder = (ViewHolder) convertView.getTag();
            }

            return convertView;
        }

        class ViewHolder{
            CheckBox checkBox;
        }

    }




}
