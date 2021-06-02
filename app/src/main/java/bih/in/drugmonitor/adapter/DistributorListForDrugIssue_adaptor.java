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
import android.widget.CompoundButton;
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
import bih.in.drugmonitor.entity.RequisitionListForAdcCopy_Entity;
import bih.in.drugmonitor.entity.RequisitionListForAdc_Entity;
import bih.in.drugmonitor.ui.ApproveDrugByAdc_Activity;
import bih.in.drugmonitor.utility.MarshmallowPermission;


public class DistributorListForDrugIssue_adaptor extends RecyclerView.Adapter<DistributorListForDrugIssue_adaptor.ViewHolder> {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<DistributorsListForAdc_Entity> ThrList = new ArrayList<>();
    String panchayatCode, panchayatName = "";
    Boolean isShowDetail = false;
    String regNo;
    DistributorsListForAdc_Entity complianceinfo;

    MarshmallowPermission permission;
    String str_compliance="";

    public DistributorListForDrugIssue_adaptor(Activity listViewshowedit, ArrayList<DistributorsListForAdc_Entity> rlist) {
        this.activity = listViewshowedit;
        this.ThrList = rlist;
        mInflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        //this.str_compliance = comply;
        // this.regNo = regNo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.adaptor_surface_scheme, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final DistributorsListForAdc_Entity info = ThrList.get(position);
        complianceinfo = ThrList.get(position);

        holder.tv_slno.setText(String.valueOf(position + 1));

        holder.tv_distributor_name.setText(ThrList.get(position).get_DistrubutorName());
        holder.tv_cntct_perrson.setText(ThrList.get(position).get_ContactPerson());
        holder.tv_cntct_no.setText(ThrList.get(position).get_ContactNo());
        holder.tv_pending_issue.setText(ThrList.get(position).get_PendingforIssue());
        holder.tv_avlbl_qty.setText(ThrList.get(position).get_AvailableQtyinStock());
        holder.tv_avlvl_qty_to_approve.setText(ThrList.get(position).get_AvailableQtyToApprove());

        holder.chk1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                int getPosition = (Integer) buttonView.getTag();
               // if (!isFromView) {
                   // check_gouan_tola_model.setSelected(isChecked);
                    ((MyInterface) activity).onCheckedDistributor(position,ThrList.get(position).get_DistrubutorName(),isChecked);

             //   }
            }
        });


    }

    @Override
    public int getItemCount() {
        return ThrList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_slno,tv_distributor_name,tv_cntct_perrson,tv_cntct_no,tv_pending_issue,tv_avlbl_qty,tv_avlvl_qty_to_approve;
        CheckBox chk1;

        ViewHolder(View itemView) {
            super(itemView);


            tv_slno=(TextView)itemView.findViewById(R.id.tv_slno);
            tv_distributor_name=(TextView)itemView.findViewById(R.id.tv_distributor_name);
            tv_cntct_perrson=(TextView)itemView.findViewById(R.id.tv_cntct_perrson);
            tv_cntct_no=(TextView)itemView.findViewById(R.id.tv_cntct_no);
            tv_pending_issue=(TextView)itemView.findViewById(R.id.tv_pending_issue);
            tv_avlbl_qty=(TextView)itemView.findViewById(R.id.tv_avlbl_qty);
            tv_avlvl_qty_to_approve=(TextView)itemView.findViewById(R.id.tv_avlvl_qty_to_approve);
            chk1=(CheckBox) itemView.findViewById(R.id.chk1);

        }

        @Override
        public void onClick(View v) {

        }

    }


}

