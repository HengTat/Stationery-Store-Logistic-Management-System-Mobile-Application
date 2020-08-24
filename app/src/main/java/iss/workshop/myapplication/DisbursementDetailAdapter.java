package iss.workshop.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import iss.workshop.myapplication.Model.DisbursementDetailModel;

public class DisbursementDetailAdapter extends ArrayAdapter<DisbursementDetailModel> {

    private Context context;
    private ArrayList<DisbursementDetailModel> details;

    public DisbursementDetailAdapter(Context context, ArrayList<DisbursementDetailModel> details) {
        super(context,0,details);
        this.context = context;
        this.details = details;
    }

    public View getView(int pos, View view, ViewGroup parent)
    {
        View disbursementItemDetail = view;
        if (disbursementItemDetail == null)
        {
            disbursementItemDetail = LayoutInflater.from(context).inflate(R.layout.single_detail, parent, false);
        }

        DisbursementDetailModel detail = details.get(pos);

        TextView detailId = (TextView) disbursementItemDetail.findViewById(R.id.detailsId);
        if (detailId != null)
        {
            detailId.setText(String.valueOf(detail.getDisbursementId()));
        }

        TextView description = (TextView) disbursementItemDetail.findViewById(R.id.itemDes);
        if (description != null)
        {
            description.setText(detail.getItemDescription());
        }

        TextView qtyRequested = (TextView) disbursementItemDetail.findViewById(R.id.itemQty);
        if (qtyRequested != null)
        {
            qtyRequested.setText(String.valueOf(detail.getQtyNeeded()));
        }

        TextView ItemCode = (TextView) disbursementItemDetail.findViewById(R.id.itemCode);
        if (ItemCode != null)
        {
            ItemCode.setText(detail.getInventoryItemId());
        }

        return disbursementItemDetail;
    }
}