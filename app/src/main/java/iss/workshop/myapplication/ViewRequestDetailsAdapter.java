package iss.workshop.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import iss.workshop.myapplication.Model.RequestDetails;
//AUTHOR: NGUI KAI LIN

public class ViewRequestDetailsAdapter extends ArrayAdapter<RequestDetails> {
    private Context context;
    private ArrayList<RequestDetails> requestDetails;


    public ViewRequestDetailsAdapter(Context context, ArrayList<RequestDetails> requestDetails) {
        super(context, 0, requestDetails);
        this.context = context;
        this.requestDetails = requestDetails;
    }

    public View getView(int pos, View view, ViewGroup parent)
    {
        View requestDetailsItem = view;
        if (requestDetailsItem == null)
        {
            requestDetailsItem = LayoutInflater.from(context).inflate(R.layout.requestdetailsitem, parent, false);
        }

        RequestDetails requestDetail = requestDetails.get(pos);
        TextView description = (TextView) requestDetailsItem.findViewById(R.id.description);
        if (description != null)
        {
            description.setText(requestDetail.getDescription());
        }

        TextView qtyRequested = (TextView) requestDetailsItem.findViewById(R.id.qtyrequested);
        if (qtyRequested != null)
        {
            qtyRequested.setText(Integer.toString(requestDetail.getQtyRequested()));
        }

        return requestDetailsItem;
    }
}



