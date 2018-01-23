package com.hulian.huliantecdemo.database;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.hulian.huliantecdemo.R;
import com.hulian.huliantecdemo.entity.UserEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wiky_zhang on 2017/12/14.
 */

public class AddressBookAdapter extends RecyclerView.Adapter<AddressBookAdapter.MyViewHolder> implements View.OnClickListener {

    private List<UserEntity> userEntityList;
    private Context context;

    public AddressBookAdapter(Context context, List<UserEntity> userEntityList){
        this.context=context;
        this.userEntityList=userEntityList;
    }

    public void refresh(List<UserEntity> userEntityList){
        this.userEntityList=userEntityList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.address_book_list_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        holder.name.setText(userEntityList.get(position).getUser_name());
        holder.msg.setText(userEntityList.get(position).getUser_bdid());
        holder.event.setTag(position);
        holder.event.setOnClickListener(this);
    }

    @Override
    public int getItemCount()
    {
        return userEntityList.size();
    }

    @Override
    public void onClick(View view) {
        int pos= (int) view.getTag();
        Intent intent=new Intent(context, AddressBookMessageActivity.class);
        intent.putExtra("user",userEntityList.get(pos));
        context.startActivity(intent);
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.msg)
        TextView msg;
        @Bind(R.id.event)
        RelativeLayout event;

        public MyViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
