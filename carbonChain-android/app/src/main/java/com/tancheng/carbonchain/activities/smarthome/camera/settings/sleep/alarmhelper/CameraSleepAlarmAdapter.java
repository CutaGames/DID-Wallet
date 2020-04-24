package com.tancheng.carbonchain.activities.smarthome.camera.settings.sleep.alarmhelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tancheng.carbonchain.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by WANG on 18/4/24.
 */

public class CameraSleepAlarmAdapter extends RecyclerView.Adapter<CameraSleepAlarmAdapter.RecViewHolder> {


    private Context context;
    private List<String> data = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private CameraSleepAlarmAdapter.DeletedItemListener deletedItemListener;

    public void setDeletedItemListener(CameraSleepAlarmAdapter.DeletedItemListener deletedItemListener) {
        this.deletedItemListener = deletedItemListener;
    }

    public CameraSleepAlarmAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setList(List<String> list, boolean refresh) {
        if (refresh) {
            data.clear();
        }
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void removeDataByPosition(int position) {
        if (position >= 0 && position < data.size()) {
            data.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, data.size() - 1);
        }
    }

    @Override
    public RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.camera_sleep_alarm_item_layout, parent, false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecViewHolder holder, final int position) {
        String s = data.get(holder.getAdapterPosition());
        //TODO 显示内容 点击事件
//        holder.textView.setText(s);
//        holder.textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "s  " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
//            }
//        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != deletedItemListener) {
                    deletedItemListener.deleted(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    /**
     * view.getWidth()获取的是屏幕中可以看到的大小.
     */
    public class RecViewHolder extends RecyclerView.ViewHolder implements WeSwipeHelper.SwipeLayoutTypeCallBack {
        public RelativeLayout contentlayout;
        public LinearLayout delete;

        public RecViewHolder(View itemView) {
            super(itemView);
            contentlayout = itemView.findViewById(R.id.item_scroll);
            delete = itemView.findViewById(R.id.item_delete);

        }

        @Override
        public float getSwipeWidth() {
            return delete.getWidth();
        }

        @Override
        public View needSwipeLayout() {
            return contentlayout;
        }

        @Override
        public View onScreenView() {
            return contentlayout;
        }
    }

    /**
     * 根据手机分辨率从DP转成PX
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public interface DeletedItemListener {
        void deleted(int position);
    }
}
