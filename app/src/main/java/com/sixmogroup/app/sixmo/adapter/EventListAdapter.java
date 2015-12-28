package com.sixmogroup.app.sixmo.adapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sixmogroup.app.sixmo.EventDetailActivity;
import com.sixmogroup.app.sixmo.R;
import com.sixmogroup.app.sixmo.model.EventModel;
import com.sixmogroup.app.sixmo.utils.CommonUtils;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Jobin on Oct 14.
 */
public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.MyViewHolder> {
    List<EventModel> data;
    private LayoutInflater inflater;
    private Context context;

    public EventListAdapter(Activity context, List<EventModel> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.event_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.eventid=data.get(position).getEventid();
        holder.title.setText(data.get(position).getName());
        holder.venue.setText(data.get(position).getVenue());
        holder.date.setText(data.get(position).getEventDate());
        holder.time.setText(data.get(position).getTime());
        holder.dayOfWeek.setText(CommonUtils.getDayFromSqlDate(data.get(position).getSqlDate()));
        holder.description.setText(data.get(position).getDescription());
        holder.rate.setText(data.get(position).getPrice());
        ImageLoader imageLoader = ImageLoader.getInstance();
        holder.banner.setImageBitmap(null);
        imageLoader.displayImage(CommonUtils.imageUploadUrl + data.get(position).getImagePath(), holder.banner);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView venue;
        TextView date;
        TextView time;
        TextView rate;
        TextView dayOfWeek;
        TextView description;
        CircularImageView banner;
        String eventid;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.eventName);
            date = (TextView) itemView.findViewById(R.id.eventDateTime);
            time = (TextView) itemView.findViewById(R.id.time);
            venue= (TextView) itemView.findViewById(R.id.eventVenue);
            rate= (TextView) itemView.findViewById(R.id.price);
            description= (TextView) itemView.findViewById(R.id.eventDescription);
            dayOfWeek= (TextView) itemView.findViewById(R.id.eventDay);
            banner=(CircularImageView)itemView.findViewById(R.id.imageViewBanner);
        }

        @Override
        public void onClick(View view) {
            AsyncHttpClient client=new AsyncHttpClient();
            RequestParams params=new RequestParams();
            params.add("eventid", eventid);
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please wait..");
            progressDialog.show();
            client.get(CommonUtils.baseUrl + "eventDetails", params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    progressDialog.dismiss();
                    try {
                        Intent detailIntent=new Intent(context, EventDetailActivity.class);
                        detailIntent.putExtra("name", response.getString("name"));
                        detailIntent.putExtra("datetime", response.getString("eventdate") + " on " + response.getString("time"));
                        detailIntent.putExtra("place", response.getString("place"));
                        detailIntent.putExtra("description", response.getString("description"));
                        detailIntent.putExtra("imagepath", response.getString("imagepath"));
                        detailIntent.putExtra("price", response.getString("price"));
                        detailIntent.putExtra("offers", response.getString("offers"));
                        detailIntent.putExtra("mobile", response.getString("organizermobile"));
                        context.startActivity(detailIntent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    progressDialog.dismiss();
                    Toast.makeText(context,"Failed to load details",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
