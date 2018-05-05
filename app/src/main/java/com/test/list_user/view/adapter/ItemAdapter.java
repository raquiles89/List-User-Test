package com.test.list_user.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.list_user.R;
import com.test.list_user.model.response.UserResponse;
import com.test.list_user.view.listener.ItemListener;
import com.test.list_user.view.viewholder.ItemViewHolder;

import java.util.List;


/**
 * Created by Rafael Quiles
 */

public class ItemAdapter extends SelectableAdapter<ItemViewHolder> {

    private final Context context;
    private List<UserResponse> items;
    ItemListener onItemClicked;

    public ItemAdapter(final Context context, final List<UserResponse> items, final ItemListener onItemClicked) {
        this.context = context;
        this.items = items;
        this.onItemClicked = onItemClicked;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ItemViewHolder(v, onItemClicked);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        final UserResponse item = items.get(position);
        renderSideBar(item, holder);
        renderBody(item, holder);

        // Highlight the item if it's selected
        holder.item_instance_layout.setBackgroundColor(isSelected(position) ? Color.parseColor(context.getString(R.string.selected_color))
                : Color.TRANSPARENT);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<UserResponse> getItems() {
        return items;
    }

    public UserResponse getItem(final int position) {
        return items.get(position);
    }

    private void renderSideBar(final UserResponse item_shape, ItemViewHolder holder) {
        if (item_shape.getImage() != null) {
            holder.image.setImageBitmap(item_shape.getImage());
        } else {
            holder.image.setImageResource(R.mipmap.ic_launcher);

        }
    }

    private void renderBody(final UserResponse item_shape, ItemViewHolder holder) {
        holder.name.setText(item_shape.getFirst_name());
        holder.lastName.setText(item_shape.getLast_name());
    }

    public void animateTo(List<UserResponse> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<UserResponse> newModels) {
        for (int i = items.size() - 1; i >= 0; i--) {
            final UserResponse model = items.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<UserResponse> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final UserResponse model = newModels.get(i);
            if (!items.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<UserResponse> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final UserResponse model = newModels.get(toPosition);
            final int fromPosition = items.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    private UserResponse removeItem(int position) {
        final UserResponse model = items.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    private void addItem(int position, UserResponse model) {
        items.add(position, model);
        notifyItemInserted(position);
    }

    private void moveItem(int fromPosition, int toPosition) {
        final UserResponse model = items.remove(fromPosition);
        items.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

}
