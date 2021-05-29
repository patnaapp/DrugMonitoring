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


public class RequisitionListForAdcApproval_Adaptor extends BaseAdapter
{
    Activity activity;
    LayoutInflater mInflater;
    ArrayList<SurfaceSchemeEntity> ThrList=new ArrayList<>();
    UserDetails userInfo;
    String panchayatCode,panchayatName="";

    public RequisitionListForAdcApproval_Adaptor(Activity listViewshowedit, ArrayList<SurfaceSchemeEntity> rlist, UserDetails userInfo) {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        this.userInfo = userInfo;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return ThrList.size();
    }

    @Override
    public Object getItem(int position)
    {
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
        convertView = mInflater.inflate(R.layout.adaptor_requisition_list_adc, null);

        holder = new ViewHolder();
        holder.tv_req_id=(TextView)convertView.findViewById(R.id.tv_req_id);
        holder.tv_req_date=(TextView)convertView.findViewById(R.id.tv_req_date);
        holder.tv_hosp_name=(TextView)convertView.findViewById(R.id.tv_hosp_name);
        holder.tv_drug_name=(TextView)convertView.findViewById(R.id.tv_drug_name);
        holder.tv_req_qty=(TextView)convertView.findViewById(R.id.tv_req_qty);
        holder.tv_approve_qty=(TextView)convertView.findViewById(R.id.tv_approve_qty);
        holder.tv_pending_approval=(TextView)convertView.findViewById(R.id.tv_pending_approval);


        convertView.setTag(holder);

        holder.tv_pending_approval.setText(ThrList.get(position).getSCHEME_NAME());


        holder.btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utiilties.isOnline(activity) == false) {

                    AlertDialog.Builder ab = new AlertDialog.Builder(activity);
                    ab.setTitle("Internet Connnection Error!!!");
                    ab.setMessage("Please turn on your mobile data or wifi connection");
                    ab.setPositiveButton("Turn On", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int whichButton) {
                            GlobalVariables.isOffline = false;
                            Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                            activity.startActivity(I);
                        }
                    });
                    ab.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton)
                        {
                            dialog.dismiss();
                        }
                    });

                    ab.show();

                }
                else
                    {
                    new SyncSchemeData(ThrList.get(position), userInfo).execute();
                }
            }
        });
        return convertView;
    }


    private class ViewHolder
    {
        TextView tv_req_id,tv_req_date,tv_hosp_name,tv_drug_name,tv_req_qty,tv_approve_qty,tv_pending_approval;
        Button btn_approve;

    }

    private class SyncSchemeData extends AsyncTask<String, Void, ArrayList<SurfaceInspectionDetailEntity>> {
        private final ProgressDialog dialog = new ProgressDialog(activity);

        SurfaceSchemeEntity scheme;
        UserDetails userInfo;

        public SyncSchemeData(SurfaceSchemeEntity scheme, UserDetails userInfo) {
            this.scheme = scheme;
            this.userInfo = userInfo;
        }

        @Override
        protected void onPreExecute()
        {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading Inspection Detail...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<SurfaceInspectionDetailEntity> doInBackground(String...arg)
        {

            return WebServiceHelper.getSurfaceSchemeInspectionData(userInfo.getUserrole(), userInfo.getUserID(), userInfo.getPassword(),scheme.getSCHEME_ID(),"","","","");

        }

        @Override
        protected void onPostExecute(ArrayList<SurfaceInspectionDetailEntity> result)
        {
            if (this.dialog.isShowing())
            {
                this.dialog.dismiss();
            }

            if(result.size() > 0 && result.get(0).isAuthenticated())
            {
//                Intent i = new Intent(activity, SurfaceViewInspectionActivity.class);
//                i.putExtra("data", scheme);
//                i.putExtra("user", userInfo);
//                i.putExtra("dataList", result);
//                activity.startActivity(i);

            }
            else
                {
                Toast.makeText(activity, "No Inspection Detail Found!!", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
