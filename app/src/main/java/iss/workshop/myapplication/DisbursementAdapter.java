package iss.workshop.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import iss.workshop.myapplication.Model.DisbursementModel;

public class DisbursementAdapter extends ArrayAdapter<DisbursementModel> {


    private Context context;
    private ArrayList<DisbursementModel> disbursementList;

    public DisbursementAdapter(Context context, ArrayList<DisbursementModel> disbursementList) {
        super(context, 0, disbursementList);
        this.context = context;
        this.disbursementList = disbursementList;
    }

    public View getView(int pos, View view, final ViewGroup parent) {
        View Details = view;
        if (Details == null) {
            Details = LayoutInflater.from(context).inflate(R.layout.single_list, parent, false);
        }
        final DisbursementModel disbursement = disbursementList.get(pos);
        TextView disbursementId = (TextView) Details.findViewById(R.id.disbursementId);
        if (disbursementId != null) {
            disbursementId.setText(String.valueOf(disbursement.getId()));
        }

        final Button detailBtn = (Button) Details.findViewById(R.id.detailBtn);
        if (detailBtn != null) {
            detailBtn.setTag(String.valueOf(disbursement.getId()));
        }

        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int disbursementId = Integer.parseInt(detailBtn.getTag().toString());
                Intent intent = new Intent(parent.getContext(), DisbursementDetails.class);
                intent.putExtra("DisbursementID", disbursementId);
                intent.putExtra("DepartmentName", disbursement.DepartmentName);
                intent.putExtra("CollectionPoint",disbursement.CollectionPointId);
                parent.getContext().startActivity(intent);
            }
        });

        return Details;

    }
}