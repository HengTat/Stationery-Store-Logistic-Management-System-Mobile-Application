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


public class DisbursementDetailEmployeeAdapter extends ArrayAdapter<DisbursementDetailAPImodel> {
    Context context;
    private List<DisbursementDetailAPImodel> Listofdisbursementdetail = new ArrayList<DisbursementDetailAPImodel>();

    public DisbursementDetailEmployeeAdapter(@NonNull Context context, List<DisbursementDetailAPImodel> Listofdisbursementdetail) {
        super(context, 0, Listofdisbursementdetail);
        this.context = context;
        this.Listofdisbursementdetail =  Listofdisbursementdetail;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View retrievalRow = convertView;
        if (retrievalRow == null) {
            retrievalRow = LayoutInflater.from(context).inflate(R.layout.disbursementdetailemployee_row, parent, false);
        }
        final DisbursementDetailAPImodel currdisdetail = Listofdisbursementdetail.get(position);

        TextView itemcode= (TextView) retrievalRow.findViewById(R.id.itemcodebox2);
        if(itemcode!=null){
            itemcode.setText(currdisdetail.getInventoryItemId());
        }

        TextView Description= (TextView) retrievalRow.findViewById(R.id.descriptionbox2);
        if(Description!=null){
            Description.setText(currdisdetail.getItemDescription());
        }
        TextView qty= (TextView) retrievalRow.findViewById(R.id.qtybox2);
        if(qty!=null){
            qty.setText(String.valueOf(currdisdetail.getQtyNeeded()));

        }

        TextView receivedqty= (TextView) retrievalRow.findViewById(R.id.receivedqtybox2);
        if(receivedqty!=null){
            String test=receivedqty.getText().toString();

            if(receivedqty.getText().toString().equals("receivedqty") ) {
                receivedqty.setText(String.valueOf(currdisdetail.getQtyNeeded()));
            }
            else if(test!="receivedqty"){
                    receivedqty.setText(String.valueOf(receivedqty.getText().toString()));
            }



        }


        return retrievalRow;
    }
}
