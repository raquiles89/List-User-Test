package com.test.list_user.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.list_user.R;
import com.test.list_user.view.listener.ItemListener;

import butterknife.OnClick;
import butterknife.OnLongClick;


/**
 * Created by Rafael Quiles
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private ItemListener listener;
    public ImageView image;
    public TextView name;
    public TextView lastName;
    public LinearLayout item_instance_layout;

    public ItemViewHolder(View itemView, ItemListener listener) {
        super(itemView);
        this.listener = listener;
        this.image = itemView.findViewById(R.id.logo);
        this.name = itemView.findViewById(R.id.tvName);
        this.lastName = itemView.findViewById(R.id.tvLastName);
        this.item_instance_layout = itemView.findViewById(R.id.item_container);

    }

    @OnClick(R.id.item_container)
    void onCommentItemClicked(final View v) {
        if (listener != null) {
            listener.onItemClicked(getLayoutPosition());
        }
    }

    @OnLongClick(R.id.item_container)
    boolean onCommentItemLongClicked(final View v) {
        return listener != null && listener.onItemLongClicked(getLayoutPosition());
    }
}
