package com.appsfs.sfs.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.appsfs.sfs.Objects.Orders;
import com.appsfs.sfs.R;
import com.appsfs.sfs.api.helper.CustomRespond;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longdv on 5/19/16.
 */
public class DetailOrdersActivity extends AppCompatActivity implements Response.Listener<CustomRespond>, Response.ErrorListener {

    ListView mListViewDetails;
    List<Orders> mOrdersList	= null;
    DetailOrdersAdapter		mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_orders);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mListViewDetails = (ListView) findViewById(R.id.lv_detail_orders);

        mOrdersList = getListOrders();
        if (mOrdersList.size() != 0) {
            mAdapter = new DetailOrdersAdapter(getApplicationContext(), mOrdersList);
            mListViewDetails.setAdapter(mAdapter);
        } else {
            mListViewDetails.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onResponse(CustomRespond response) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }


    private List<Orders> getListOrders() {

        ArrayList<Orders> ordersArrayList = new ArrayList<Orders>();
        return ordersArrayList;

    }
}


    class DetailOrdersAdapter extends BaseAdapter {
        private List<Orders> mOrdersList;
        private LayoutInflater mInflater;

        public DetailOrdersAdapter(Context context, final List<Orders> ordersList) {
            mInflater = LayoutInflater.from(context);
            mOrdersList = ordersList;
            context.getPackageManager();

        }

        public class ViewHolder {
            TextView mCodeOrder;
            TextView mPhoneCustomer;
            TextView mPhoneShipper;
            TextView mCodeCheckOrder;
            TextView mDate;
            ImageView mOrderIcon;
            TextView mStatus;
        }

        public int getCount() {
            return mOrdersList.size();
        }

        public Object getItem(int position) {
            return mOrdersList.get(position);
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            final ViewHolder holder;
            final Orders orderInfo = mOrdersList.get(position);

            if (convertView == null) {
                convertView = mInflater.inflate(
                        R.layout.row_list_order, null);
                holder = new ViewHolder();
                holder.mCodeOrder = (TextView) convertView
                        .findViewById(R.id.tv_row_code_order);
                holder.mPhoneCustomer = (TextView) convertView
                        .findViewById(R.id.tv_row_phone_customer);
                holder.mPhoneShipper = (TextView) convertView
                        .findViewById(R.id.tv_row_phone_shipper);
                holder.mCodeCheckOrder = (TextView) convertView
                        .findViewById(R.id.tv_row_code_check);
                holder.mDate = (TextView) convertView
                        .findViewById(R.id.tv_row_date);
                holder.mStatus = (TextView) convertView
                        .findViewById(R.id.tv_row_status);

                holder.mOrderIcon = (ImageView) convertView
                        .findViewById(R.id.iv_row_icon_order);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.mCodeOrder.setText(orderInfo.getCodeOrder());
            holder.mPhoneCustomer.setText(orderInfo.getPhoneCustomer());
            holder.mPhoneShipper.setText(orderInfo.getPhoneShipper());
            holder.mCodeCheckOrder.setText(orderInfo.getCodeCheckOrder());
            holder.mDate.setText(orderInfo.getDate());
            holder.mStatus.setText(orderInfo.getStatus());

            return convertView;
        }


}
