package bih.in.drugmonitor.adapter;

import bih.in.drugmonitor.entity.PatientDetailsList_Entity;

public interface MyInterface {

    void onCheckedDistributor(int position,String distributor_id, Boolean isChecked);
    void onPatientSelected(int position, PatientDetailsList_Entity data, Boolean isChecked,String qty);
    void onQtyToBeApproved(int position,String quantity);
}
