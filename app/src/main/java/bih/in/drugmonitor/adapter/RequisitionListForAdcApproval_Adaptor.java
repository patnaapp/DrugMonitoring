package bih.in.drugmonitor.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import bih.in.drugmonitor.R;
import bih.in.drugmonitor.entity.RequisitionListForAdc_Entity;
import bih.in.drugmonitor.utility.MarshmallowPermission;


public class RequisitionListForAdcApproval_Adaptor extends RecyclerView.Adapter<RequisitionListForAdcApproval_Adaptor.ViewHolder> {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<RequisitionListForAdc_Entity> ThrList = new ArrayList<>();
    String panchayatCode, panchayatName = "";
    Boolean isShowDetail = false;
    String regNo;
    RequisitionListForAdc_Entity complianceinfo;
    MarshmallowPermission permission;
    String str_compliance="";

    public RequisitionListForAdcApproval_Adaptor(Activity listViewshowedit, ArrayList<RequisitionListForAdc_Entity> rlist) {
        this.activity = listViewshowedit;
        this.ThrList = rlist;
        mInflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        //this.str_compliance = comply;
        // this.regNo = regNo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.adaptor_requisition_list_adc, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final RequisitionListForAdc_Entity info = ThrList.get(position);
        complianceinfo = ThrList.get(position);

        holder.tv_slno.setText(String.valueOf(position + 1));

        holder.tv_req_id.setText(ThrList.get(position).getReq_Id());
        holder.tv_req_date.setText(ThrList.get(position).getReq_date());
        holder.tv_hosp_name.setText(ThrList.get(position).getHosp_Name());
        holder.tv_drug_name.setText(ThrList.get(position).getDrug_Name());
        holder.tv_req_qty.setText(ThrList.get(position).getReq_qty());
        holder.tv_approve_qty.setText(ThrList.get(position).getAlready_approved_qty());
        holder.tv_pending_approval.setText(ThrList.get(position).getPending_approval_qty());



        holder.btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }

    @Override
    public int getItemCount() {
        return ThrList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_slno,tv_req_id,tv_req_date,tv_hosp_name,tv_drug_name,tv_req_qty,tv_approve_qty,tv_pending_approval;
        Button btn_approve;

        ViewHolder(View itemView) {
            super(itemView);

            tv_slno=(TextView)itemView.findViewById(R.id.tv_slno);
         tv_req_id=(TextView)itemView.findViewById(R.id.tv_req_id);
         tv_req_date=(TextView)itemView.findViewById(R.id.tv_req_date);
         tv_hosp_name=(TextView)itemView.findViewById(R.id.tv_hosp_name);
         tv_drug_name=(TextView)itemView.findViewById(R.id.tv_drug_name);
         tv_req_qty=(TextView)itemView.findViewById(R.id.tv_req_qty);
         tv_approve_qty=(TextView)itemView.findViewById(R.id.tv_approve_qty);
         tv_pending_approval=(TextView)itemView.findViewById(R.id.tv_pending_approval);
            btn_approve=(Button) itemView.findViewById(R.id.btn_approve);


        }

        @Override
        public void onClick(View v) {

        }

    }


}

