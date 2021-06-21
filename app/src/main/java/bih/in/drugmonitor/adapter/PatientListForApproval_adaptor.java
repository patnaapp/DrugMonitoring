package bih.in.drugmonitor.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bih.in.drugmonitor.R;
import bih.in.drugmonitor.entity.DrugIssuedDetailsList_Entity;
import bih.in.drugmonitor.entity.PatientDetailsList_Entity;
import bih.in.drugmonitor.utility.MarshmallowPermission;


public class PatientListForApproval_adaptor extends RecyclerView.Adapter<PatientListForApproval_adaptor.ViewHolder>
{

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<PatientDetailsList_Entity> ThrList = new ArrayList<>();
    String panchayatCode, panchayatName = "";
    Boolean isShowDetail = false;
    String regNo;
    PatientDetailsList_Entity complianceinfo;

    MarshmallowPermission permission;
    String str_compliance="";


    public PatientListForApproval_adaptor(Activity listViewshowedit, ArrayList<PatientDetailsList_Entity> rlist)
    {
        this.activity = listViewshowedit;
        this.ThrList = rlist;
        mInflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        //this.str_compliance = comply;
        // this.regNo = regNo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.adaptor_patient_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final PatientDetailsList_Entity info = ThrList.get(position);
        complianceinfo = ThrList.get(position);

        ThrList.get(position).setIschecked(false);
        holder.tv_slno.setText(String.valueOf(position + 1));

        holder.tv_patient_name.setText(ThrList.get(position).getPatientname());
        holder.tv_age.setText(ThrList.get(position).getAge());
        holder.tv_gender.setText(ThrList.get(position).getGender());
        holder.tv_req_qty.setText(ThrList.get(position).getRecdqty());
        holder.tv_already_approved.setText(ThrList.get(position).getAllreadyApproved());
        holder.tv_pending_qty.setText(ThrList.get(position).getPenddingQty());
        holder.edt_appr_qty.setText(ThrList.get(position).getPenddingQty());
        //  holder.tv_appr_qty.setText(ThrList.get(position).getap());

        holder.chk_patient.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                ThrList.get(position).setIschecked(isChecked);
                ((MyInterface) activity).onPatientSelected(position,ThrList.get(position),isChecked,holder.edt_appr_qty.getText().toString());

            }
        });


        holder.edt_appr_qty.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
               // if(holder.chk_patient.isChecked())
                if(ThrList.get(position).getIschecked())
                {
                    ((MyInterface) activity).onQtyToBeApproved(position,holder.edt_appr_qty.getText().toString());
                }

                //holder.tv_total_amt.setText(calculateAmount(holder));
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }

        });

    }



    @Override
    public int getItemCount()
    {
        return ThrList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tv_slno,tv_patient_name,tv_age,tv_gender,tv_req_qty,tv_already_approved,tv_pending_qty,tv_appr_qty;
        CheckBox chk_patient;
        EditText edt_appr_qty;

        ViewHolder(View itemView)
        {
            super(itemView);

            tv_slno=(TextView)itemView.findViewById(R.id.tv_slno);
            tv_patient_name=(TextView)itemView.findViewById(R.id.tv_patient_name);
            tv_age=(TextView)itemView.findViewById(R.id.tv_age);
            tv_gender=(TextView)itemView.findViewById(R.id.tv_gender);
            tv_req_qty=(TextView)itemView.findViewById(R.id.tv_req_qty);
            tv_already_approved=(TextView)itemView.findViewById(R.id.tv_already_approved);
            tv_pending_qty=(TextView)itemView.findViewById(R.id.tv_pending_qty);
            edt_appr_qty=(EditText) itemView.findViewById(R.id.edt_appr_qty);
            chk_patient=(CheckBox) itemView.findViewById(R.id.chk_patient);
           // chk_patient.addTextChangedListener(inputTextWatcher2);

        }

        @Override
        public void onClick(View v)
        {

        }

    }


}

