package bih.in.drugmonitor.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bih.in.drugmonitor.R;
import bih.in.drugmonitor.entity.SurfaceInspectionDetailEntity;
import bih.in.drugmonitor.entity.SurfaceSchemeEntity;
import bih.in.drugmonitor.entity.UserDetails;
import bih.in.drugmonitor.utility.GlobalVariables;
import bih.in.drugmonitor.utility.Utiilties;
import bih.in.drugmonitor.web_services.WebServiceHelper;


public class DistributorListForDrugIssue_adaptor extends BaseAdapter {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<SurfaceSchemeEntity> ThrList=new ArrayList<>();
    UserDetails userInfo;
    String panchayatCode,panchayatName="";

    public DistributorListForDrugIssue_adaptor(Activity listViewshowedit, ArrayList<SurfaceSchemeEntity> rlist, UserDetails userInfo) {
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
        convertView = mInflater.inflate(R.layout.adaptor_surface_scheme, null);

        holder = new ViewHolder();
        holder.tv_slno=(TextView)convertView.findViewById(R.id.tv_slno);
        holder.tv_distributor_name=(TextView)convertView.findViewById(R.id.tv_distributor_name);
        holder.tv_cntct_perrson=(TextView)convertView.findViewById(R.id.tv_cntct_perrson);
        holder.tv_cntct_no=(TextView)convertView.findViewById(R.id.tv_cntct_no);
        holder.tv_pending_issue=(TextView)convertView.findViewById(R.id.tv_pending_issue);
        holder.tv_avlbl_qty=(TextView)convertView.findViewById(R.id.tv_avlbl_qty);
        holder.tv_avlvl_qty_to_approve=(TextView)convertView.findViewById(R.id.tv_avlvl_qty_to_approve);
        holder.chk1=(CheckBox) convertView.findViewById(R.id.chk1);


        convertView.setTag(holder);

        holder.tv_distributor_name.setText(ThrList.get(position).getSCHEME_NAME());

        return convertView;
    }

    private class ViewHolder
    {
        TextView tv_slno,tv_distributor_name,tv_cntct_perrson,tv_cntct_no,tv_pending_issue,tv_avlbl_qty,tv_avlvl_qty_to_approve;
        CheckBox chk1;
    }

}
