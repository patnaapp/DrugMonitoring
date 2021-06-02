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
import android.widget.CheckBox;
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
import bih.in.drugmonitor.entity.DistributorsListForAdc_Entity;
import bih.in.drugmonitor.entity.DrugIssuedDetailsList_Entity;
import bih.in.drugmonitor.entity.RequisitionListForAdcCopy_Entity;
import bih.in.drugmonitor.entity.RequisitionListForAdc_Entity;
import bih.in.drugmonitor.ui.ApproveDrugByAdc_Activity;
import bih.in.drugmonitor.utility.MarshmallowPermission;


public class IssuedDrugsListByDistributor_adaptor extends RecyclerView.Adapter<IssuedDrugsListByDistributor_adaptor.ViewHolder> {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<DrugIssuedDetailsList_Entity> ThrList = new ArrayList<>();
    String panchayatCode, panchayatName = "";
    Boolean isShowDetail = false;
    String regNo;
    DrugIssuedDetailsList_Entity complianceinfo;

    MarshmallowPermission permission;
    String str_compliance="";

    public IssuedDrugsListByDistributor_adaptor(Activity listViewshowedit, ArrayList<DrugIssuedDetailsList_Entity> rlist) {
        this.activity = listViewshowedit;
        this.ThrList = rlist;
        mInflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        //this.str_compliance = comply;
        // this.regNo = regNo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.adaptor_issued_drugs, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final DrugIssuedDetailsList_Entity info = ThrList.get(position);
        complianceinfo = ThrList.get(position);

        holder.tv_slno.setText(String.valueOf(position + 1));

        holder.tv_issued_date.setText(ThrList.get(position).get_IssuedDate());
        holder.tv_distributor_name.setText(ThrList.get(position).get_DistributorName());
        holder.tv_drug_name.setText(ThrList.get(position).get_DrugName());
        holder.tv_batch.setText(ThrList.get(position).get_BatchNo());
        holder.tv_issued_qty.setText(ThrList.get(position).get_IssuedQty());



    }

    @Override
    public int getItemCount() {
        return ThrList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_slno,tv_issued_date,tv_distributor_name,tv_drug_name,tv_batch,tv_issued_qty;

        ViewHolder(View itemView) {
            super(itemView);

           tv_slno=(TextView)itemView.findViewById(R.id.tv_slno);
           tv_issued_date=(TextView)itemView.findViewById(R.id.tv_issued_date);
           tv_distributor_name=(TextView)itemView.findViewById(R.id.tv_distributor_name);
           tv_drug_name=(TextView)itemView.findViewById(R.id.tv_drug_name);
           tv_batch=(TextView)itemView.findViewById(R.id.tv_batch);
           tv_issued_qty=(TextView)itemView.findViewById(R.id.tv_issued_qty);

        }

        @Override
        public void onClick(View v) {

        }

    }


}

