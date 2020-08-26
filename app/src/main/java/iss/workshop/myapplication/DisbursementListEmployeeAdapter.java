package iss.workshop.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
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

public class DisbursementListEmployeeAdapter  extends ArrayAdapter<DisbursementAPImodel> {
    Context context;
    private List<DisbursementAPImodel> listofDisbursement = new ArrayList<DisbursementAPImodel>();

    public DisbursementListEmployeeAdapter(@NonNull Context context, List<DisbursementAPImodel> listofDisbursement) {
        super(context, 0, listofDisbursement);
        this.context = context;
        this.listofDisbursement =  listofDisbursement;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View disbursementRow  = convertView;
        if (disbursementRow  == null) {
            disbursementRow  = LayoutInflater.from(context).inflate(R.layout.disbursement_row, parent, false);
        }
        final DisbursementAPImodel currdis = listofDisbursement.get(position);
        TextView DisbursementId = (TextView) disbursementRow .findViewById(R.id.disbursemetIdtext);
        if (DisbursementId != null)
            DisbursementId.setText(Integer.toString(currdis.getId()));

        Button details = (Button) disbursementRow .findViewById(R.id.viewDetailbtn);
        if (details != null){
            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent(context, EmployeeDisbursementDetail.class);
                    intent.putExtra("DisbursementId", currdis.getId());
                    intent.putExtra("Department",currdis.getDepartmentName());
                    intent.putExtra("CollectionPoint",currdis.getCollectionPointId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });
        }
        return disbursementRow ;
    }
}

