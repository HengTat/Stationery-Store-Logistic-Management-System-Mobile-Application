package iss.workshop.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.myapplication.Model.DisbursementDetailAPImodel;

public class DisbursementDetailAdapter extends ArrayAdapter<DisbursementDetailAPImodel> {
    Context context;
    private List<DisbursementDetailAPImodel> listofDisbursementDetail = new ArrayList<DisbursementDetailAPImodel>();

    public DisbursementDetailAdapter(@NonNull Context context, List<DisbursementDetailAPImodel> listofDisbursementDetail) {
        super(context, 0, listofDisbursementDetail);
        this.context = context;
        this.listofDisbursementDetail =  listofDisbursementDetail;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View disbursementDetailRow = convertView;
        if (disbursementDetailRow == null) {
            disbursementDetailRow  = LayoutInflater.from(context).inflate(R.layout.disbursementdetail_row, parent, false);
        }
        final DisbursementDetailAPImodel currdisdetail = listofDisbursementDetail.get(position);

        TextView itemcode= (TextView) disbursementDetailRow .findViewById(R.id.itemcodebox);
        if(itemcode!=null){
            itemcode.setText(currdisdetail.getInventoryItemId());
        }

        TextView Description= (TextView) disbursementDetailRow .findViewById(R.id.descriptionbox);
        if(Description!=null){
            Description.setText(currdisdetail.getItemDescription());
        }
        TextView qty= (TextView) disbursementDetailRow .findViewById(R.id.qtybox);
        if(qty!=null){
            qty.setText(String.valueOf(currdisdetail.getQtyNeeded()));
        }

        TextView receivedqty= (TextView) disbursementDetailRow .findViewById(R.id.receivedqtybox);
        if(receivedqty!=null){
                receivedqty.setText(String.valueOf(currdisdetail.getQtyReceived()));
        }
        return disbursementDetailRow ;
    }
}
