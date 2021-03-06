package com.example.wvd.practivity.Adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wvd.practivity.Data.Entities;
import com.example.wvd.practivity.Misc.JSONParser;
import com.example.wvd.practivity.R;

import java.util.List;

/**
 * Created by walterjgsp on 11/01/16.
 */
public class EntitiesAdapter extends RecyclerView.Adapter<EntitiesAdapter.EntitiesViewHolder> {

    public static final String TAG = "EntitiesAdapter";

    private Context mContext;

    private JSONParser jsonParser;

    private LinearLayout openCardView;
    private int indexOpenCardView;
    private Location userLocation;

    public static class EntitiesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView entitieName;
        TextView entitieAddress;
        TextView entitieSite;
        TextView entitieDistance;
        LinearLayout entitiesMenu;
        Button entitieCall;
        Button entitieSend;
        Context mContext;;

        public EntitiesClickListener listener;
        public EntitiesCallClickListener callListener;
        public EntitiesSendClickListener sendListener;

        //listener passed to viewHolder
        public interface EntitiesClickListener {
            void entitieOnClick(int position,LinearLayout linearLayout);
        }

        //listener passed to viewHolder
        public interface EntitiesCallClickListener {
            void entitieOnClick(int position);
        }

        //listener passed to viewHolder
        public interface EntitiesSendClickListener {
            void entitieOnClick(int position);
        }

        EntitiesViewHolder(View itemView, Context context,EntitiesClickListener listener, final EntitiesCallClickListener callListener,
                           final EntitiesSendClickListener sendListener) {
            super(itemView);
            mContext = context;
            cv = (CardView) itemView.findViewById(R.id.cv);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            entitieName = (TextView) itemView.findViewById(R.id.entitie_name);
            entitieAddress = (TextView) itemView.findViewById(R.id.entitie_address);
            entitieSite = (TextView) itemView.findViewById(R.id.entitie_site);
            entitieDistance = (TextView) itemView.findViewById(R.id.entitie_distance);
            entitiesMenu = (LinearLayout) itemView.findViewById(R.id.entitie_menu);
            entitieCall = (Button) itemView.findViewById(R.id.entitie_call_button);
            entitieSend = (Button) itemView.findViewById(R.id.entitie_email_button);
            this.listener=listener;
            this.callListener = callListener;
            this.sendListener = sendListener;


            entitieCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callListener.entitieOnClick(getPosition());
                }
            });

            entitieSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   sendListener.entitieOnClick(getPosition());
                }
            });

        }

        @Override
        public void onClick(View v) {

            listener.entitieOnClick(getPosition(),entitiesMenu);
        }
    }

    List<Entities> entities;
    public EntitiesAdapterClickListener recListener;

    public EntitiesAdapter(List<Entities> entities, Context context,EntitiesAdapterClickListener recListener,double longitude, double latitude) {
        this.entities = entities;
        this.mContext = context;
        this.jsonParser = new JSONParser(context);
        this.recListener=recListener;
        this.indexOpenCardView = -1;
        this.userLocation = new Location("User Location");
        userLocation.setLatitude(latitude);
        userLocation.setLongitude(longitude);
    }

    public interface EntitiesAdapterClickListener {
        void recyclerViewClick(int position);
    }

    @Override
    public int getItemCount() {
        return entities.size();
    }

    @Override
    public EntitiesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.entities_cardview, viewGroup, false);
        EntitiesViewHolder.EntitiesSendClickListener entitiesSendClickListener = new EntitiesViewHolder.EntitiesSendClickListener() {
            @Override
            public void entitieOnClick(int position) {
                String email = entities.get(position).getEmail();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                intent.putExtra(Intent.EXTRA_SUBJECT, mContext.getResources().getString(R.string.information));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        };
        EntitiesViewHolder.EntitiesCallClickListener entitiesCallClickListener = new EntitiesViewHolder.EntitiesCallClickListener() {
            @Override
            public void entitieOnClick(int position) {
                String phone = entities.get(position).getTelefone();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        };
        EntitiesViewHolder cvh = new EntitiesViewHolder(v, mContext,new EntitiesViewHolder.EntitiesClickListener(){
            @Override
            public void entitieOnClick(int position,LinearLayout clicked) {
                if(indexOpenCardView!=position){

                    try {
                        openCardView.setVisibility(View.GONE);
                    }catch (NullPointerException e){

                    }

                    clicked.setVisibility(View.VISIBLE);
                    openCardView = clicked;
                    indexOpenCardView = position;
                }else{
                    if(clicked.getVisibility() == View.GONE) {
                        clicked.setVisibility(View.VISIBLE);
                        indexOpenCardView=position;
                    }
                    else {
                        clicked.setVisibility(View.GONE);
                        indexOpenCardView=-1;
                    }
                }
            }
        },entitiesCallClickListener,entitiesSendClickListener);
        return cvh;
    }

    @Override
    public void onBindViewHolder(EntitiesViewHolder categoryViewHolder, int i) {
        categoryViewHolder.entitieName.setText(entities.get(i).getNome());
        categoryViewHolder.entitieAddress.setText(entities.get(i).getEndereco());
        categoryViewHolder.entitieSite.setText(entities.get(i).getSite());
        double distance = entities.get(i).getDistancefromUser();
        String distancia;
        if(distance>1000) {
            distancia=String.format("%.1f",distance*0.001)+" km";
        }else {
            distancia = (int) distance + " m";
        }

        categoryViewHolder.entitieDistance.setText(distancia);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}