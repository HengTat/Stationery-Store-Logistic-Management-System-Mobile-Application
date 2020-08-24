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

import iss.workshop.myapplication.Model.Request;

public class ViewPendingRequestsAdapter extends ArrayAdapter<Request> {
    private Context context;
    private ArrayList<Request> requests;

    public ViewPendingRequestsAdapter(Context context, ArrayList<Request> requests) {
        super(context, 0, requests);
        this.context = context;
        this.requests = requests;

    }

    public View getView(int pos, View view, ViewGroup parent)
    {
        View pendingRequestItem = view;
        if (pendingRequestItem == null)
        {
            pendingRequestItem = LayoutInflater.from(context).inflate(R.layout.pendingrequestitem, parent, false);
        }
        Request request = requests.get(pos);
        TextView requestId = (TextView) pendingRequestItem.findViewById(R.id.requestId);
        if (requestId != null)
        {
            requestId.setText(Integer.toString(request.getId()));
        }

        Button reqDetails = (Button) pendingRequestItem.findViewById(R.id.requestdetailsbtn);
        if (reqDetails != null)
        {
            reqDetails.setTag(request.getId());
        }

        reqDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int requestID = Integer.parseInt(reqDetails.getTag().toString());
                Intent intent = new Intent(parent.getContext(), ViewRequestDetails.class);
                intent.putExtra("RequestID", requestID);
                parent.getContext().startActivity(intent);
            }
        });

        return pendingRequestItem;
    }
}


