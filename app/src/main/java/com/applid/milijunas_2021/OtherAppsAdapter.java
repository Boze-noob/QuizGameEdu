package com.applid.milijunas_2021;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.applid.milijunas_2021.models.OtherAppsModel;

import java.util.ArrayList;

public class OtherAppsAdapter extends RecyclerView.Adapter<OtherAppsAdapter.ViewHolder> {
   ArrayList<OtherAppsModel> otherAppsModels;
   Context context;
   private final static Uri DRAWING_URL = Uri.parse("https://play.google.com/store/apps/details?id=com.applid.kids");
    private final static Uri MUSIC_BOX_URL = Uri.parse("https://play.google.com/store/apps/details?id=com.applid.musicbox");
    private final static Uri TIC_TAC_TOE_URL = Uri.parse("https://play.google.com/store/apps/details?id=com.applid.tictactoe");
    private final static Uri RUN_PRO_URL = Uri.parse("https://play.google.com/store/apps/details?id=com.applid.prorun");

   public OtherAppsAdapter(Context context, ArrayList<OtherAppsModel> otherAppsModels)
   {
       this.context = context;
       this.otherAppsModels = otherAppsModels;
   }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.other_apps_row, parent, false);

       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       holder.imageView.setImageResource(otherAppsModels.get(position).getAppsLogo());
       holder.textView.setText(otherAppsModels.get(position).getAppsName());
    }

    @Override
    public int getItemCount() {
        return otherAppsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

       ImageView imageView;
       TextView textView;
       LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.text_view);
            linearLayout = itemView.findViewById(R.id.linear_layout);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri;

                    if(getAdapterPosition() == 0)
                    {
                        uri = DRAWING_URL;
                    }
                    else if(getAdapterPosition() == 1){
                        uri = MUSIC_BOX_URL;
                    }
                    else if(getAdapterPosition() == 2){
                        uri = TIC_TAC_TOE_URL;
                    }
                    else{
                        uri = RUN_PRO_URL;
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }
            });

        }
    }
}
