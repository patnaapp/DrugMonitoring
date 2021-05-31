package bih.in.drugmonitor.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import bih.in.drugmonitor.R;
import bih.in.drugmonitor.entity.SurfaceSchemeEntity;
import bih.in.drugmonitor.entity.UserDetails;


public class IssuedDrugsListByDistributor_adaptor extends BaseAdapter {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<SurfaceSchemeEntity> ThrList=new ArrayList<>();
    UserDetails userInfo;
    String panchayatCode,panchayatName="";

    public IssuedDrugsListByDistributor_adaptor(Activity listViewshowedit, ArrayList<SurfaceSchemeEntity> rlist, UserDetails userInfo) {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        this.userInfo = userInfo;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ThrList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        //if (convertView == null) {
        convertView = mInflater.inflate(R.layout.adaptor_issued_drugs, null);

        holder = new ViewHolder();
        holder.tv_slno=(TextView)convertView.findViewById(R.id.tv_slno);
        holder.tv_issued_date=(TextView)convertView.findViewById(R.id.tv_issued_date);
        holder.tv_distributor_name=(TextView)convertView.findViewById(R.id.tv_distributor_name);
        holder.tv_drug_name=(TextView)convertView.findViewById(R.id.tv_drug_name);
        holder.tv_batch=(TextView)convertView.findViewById(R.id.tv_batch);
        holder.tv_issued_qty=(TextView)convertView.findViewById(R.id.tv_issued_qty);



        convertView.setTag(holder);

        holder.tv_distributor_name.setText(ThrList.get(position).getSCHEME_NAME());

        return convertView;
    }

    private class ViewHolder
    {
        TextView tv_slno,tv_issued_date,tv_distributor_name,tv_drug_name,tv_batch,tv_issued_qty;
    }

}
