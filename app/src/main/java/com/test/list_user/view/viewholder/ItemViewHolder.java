package com.test.list_user.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.list_user.R;
import com.test.list_user.view.listener.ItemListener;


/**
 * Created by Rafael Quiles
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public ItemListener listener;
    public ImageView image;
    public TextView name;
    public TextView lastName;
    public LinearLayout item_instance_layout;

    public ItemViewHolder(View itemView, final ItemListener listener) {
        super(itemView);
        this.listener = listener;
        this.image = itemView.findViewById(R.id.logo);
        this.name = itemView.findViewById(R.id.tvName);
        this.lastName = itemView.findViewById(R.id.tvLastName);
        this.item_instance_layout = itemView.findViewById(R.id.item_container);
        this.item_instance_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClicked(getLayoutPosition());
                }
            }
        });

        this.item_instance_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return listener != null && listener.onItemLongClicked(getLayoutPosition());
            }
        });

    }
}
