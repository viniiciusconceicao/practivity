package com.example.wvd.practivity.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wvd.practivity.Data.Activities;
import com.example.wvd.practivity.Data.Entities;
import com.example.wvd.practivity.Misc.JSONParser;
import com.example.wvd.practivity.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by walterjgsp on 11/01/16.
 */
public class EntitiesAdapter extends RecyclerView.Adapter<EntitiesAdapter.ActivityViewHolder> {

    public static final String TAG = "EntitiesADapter";

    private Context mContext;

    private JSONParser jsonParser;

    public static class ActivityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView entitieName;
        TextView entitieAddress;
        TextView entitieSite;
        LinearLayout entitiesMenu;
        Button entitieCall;
        Button entitieSend;
        Entities entities;
        Context mContext;

        ActivityViewHolder(View itemView, Context context,Entities ent) {
            super(itemView);
            mContext = context;
            cv = (CardView) itemView.findViewById(R.id.cv);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            entitieName = (TextView) itemView.findViewById(R.id.entitie_name);
            entitieAddress = (TextView) itemView.findViewById(R.id.entitie_address);
            entitieSite = (TextView) itemView.findViewById(R.id.entitie_site);
            entitiesMenu = (LinearLayout) itemView.findViewById(R.id.entitie_menu);
            entitieCall = (Button) itemView.findViewById(R.id.entitie_call_button);
            entitieSend = (Button) itemView.findViewById(R.id.entitie_email_button);
            this.entities=ent;

            entitieCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone = entities.getTelefone();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });

            entitieSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = entities.getEmail();
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_EMAIL, email);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });

        }

        @Override
        public void onClick(View v) {

            if(entitiesMenu.getVisibility() == View.GONE)
                entitiesMenu.setVisibility(View.VISIBLE);
            else
                entitiesMenu.setVisibility(View.GONE);
        }
    }

    List<Entities> entities;

    public EntitiesAdapter(List<Entities> entities, Context context) {
        this.entities = entities;
        this.mContext = context;
        this.jsonParser = new JSONParser(context);
    }

    @Override
    public int getItemCount() {
        return entities.size();
    }

    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.entities_cardview, viewGroup, false);
        ActivityViewHolder cvh = new ActivityViewHolder(v, mContext,entities.get(i));
        return cvh;
    }

    @Override
    public void onBindViewHolder(ActivityViewHolder categoryViewHolder, int i) {
        categoryViewHolder.entitieName.setText(entities.get(i).getNome());
        categoryViewHolder.entitieAddress.setText(entities.get(i).getEndereco());
        categoryViewHolder.entitieSite.setText(entities.get(i).getSite());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}