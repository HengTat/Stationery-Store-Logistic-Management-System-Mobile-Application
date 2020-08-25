package iss.workshop.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.myapplication.Model.DisbursementAPImodel;


public class DisbursementListAdapter extends ArrayAdapter<DisbursementAPImodel> {
    Context context;
    private List<DisbursementAPImodel> Listofdisbursement = new ArrayList<DisbursementAPImodel>();

    public DisbursementListAdapter(@NonNull Context context, List<DisbursementAPImodel> Listofdisbursement) {
        super(context, 0, Listofdisbursement);
        this.context = context;
        this.Listofdisbursement =  Listofdisbursement;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View retrievalRow = convertView;
        if (retrievalRow == null) {
            retrievalRow = LayoutInflater.from(context).inflate(R.layout.disbursement_row, parent, false);
        }
        final DisbursementAPImodel currdis = Listofdisbursement.get(position);
        TextView DisbursementId = (TextView) retrievalRow.findViewById(R.id.disbursemetIdtext);
        if (DisbursementId != null)
            DisbursementId.setText(Integer.toString(currdis.getId()));

        Button details = (Button) retrievalRow.findViewById(R.id.viewDetailbtn);
        if (details != null){
            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent(context, ClerkDisbursementDetail.class);
                    intent.putExtra("DisbursementId", currdis.getId());
                    intent.putExtra("Department",currdis.getDepartmentName());
                    intent.putExtra("CollectionPoint",currdis.getCollectionPointId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }

        return retrievalRow;
    }
}
