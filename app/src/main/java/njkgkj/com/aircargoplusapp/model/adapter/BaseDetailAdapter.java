package njkgkj.com.aircargoplusapp.model.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.List;

import njkgkj.com.aircargoplusapp.R;
import njkgkj.com.aircargoplusapp.model.BaseDetailModel;
import njkgkj.com.aircargoplusapp.view.AutofitTextView;


/**
 * Created by 46296 on 2020/11/25.
 */

public class BaseDetailAdapter extends BaseAdapter {
    private List<BaseDetailModel> baseDetailModelList;
    private Context context;

    public BaseDetailAdapter(List<BaseDetailModel> bdList, Context context) {
        baseDetailModelList = bdList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return baseDetailModelList.size();
    }

    @Override
    public BaseDetailModel getItem(int position) {
        return baseDetailModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.basedetail_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.inTxt_name = (AutofitTextView) convertView.findViewById(R.id.BaseDetail_inTxt_name);
            viewHolder.inTxt_value = (AutofitTextView) convertView.findViewById(R.id.BaseDetail_inTxt_value);
            viewHolder.showLy = (LinearLayout) convertView.findViewById(R.id.BaseDetail_in_layout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String myname = baseDetailModelList.get(position).getBasename();
        if (!TextUtils.isEmpty(myname)) {
            viewHolder.inTxt_name.setText(myname);
        } else {
            viewHolder.inTxt_name.setText("");
        }

        String myvalue = baseDetailModelList.get(position).getBasevalue();
        if (!TextUtils.isEmpty(myname)) {
            viewHolder.inTxt_value.setText(myvalue);
        } else {
            viewHolder.inTxt_value.setText("");
        }

        switch (myname) {
            case "运单号:":
                viewHolder.inTxt_value.setTextColor(Color.BLUE);
                viewHolder.inTxt_value.setTextIsSelectable(true);
                break;
            default:
                break;
        }

        return convertView;
    }

    class ViewHolder {
        AutofitTextView inTxt_name;
        AutofitTextView inTxt_value;
        LinearLayout showLy;
    }
}
