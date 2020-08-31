package iss.workshop.myapplication;

import android.app.Activity;
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

import iss.workshop.myapplication.Model.Retrieval;
//AUTHOR: YADANAR PHYO, THUN SU NYI NYI

public class RetrievalListAdapter extends ArrayAdapter<Retrieval> {

    private Context context;
    private ArrayList<Retrieval> retrievals=new ArrayList<Retrieval>();

    public RetrievalListAdapter(@NonNull Context context, ArrayList<Retrieval> retrievals) {
        super(context,0, retrievals);
        this.context=context;
        this.retrievals=retrievals;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        View retrievalRow=convertView;
        if(retrievalRow==null){
            retrievalRow= LayoutInflater.from(context).inflate(R.layout.retrieval_row,parent,false);
        }
        Retrieval currRet=retrievals.get(position);
        TextView retId=(TextView)retrievalRow.findViewById(R.id.txtRetId);
        if(retId!=null)
            retId.setText(Integer.toString(currRet.getId()));

        Button details=(Button) retrievalRow.findViewById(R.id.btnDetail);
        if(details!=null)
            details.setTag(currRet.getId());

        final Button btnDetails=(Button) retrievalRow.findViewById(R.id.btnDetail);
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(parent.getContext(), RetrievalDetails.class);
                int retId= (int) btnDetails.getTag();
                intent.putExtra("RetID", retId);
                ((Activity)parent.getContext()).finish();
                parent.getContext().startActivity(intent);

            }
        });
        return retrievalRow;
    }
}
