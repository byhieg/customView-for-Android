package cn.byhieg.daydemo02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.InvocationHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by byhieg on 17/5/3.
 * Contact with byhieg@gmail.com
 */

public class ContentAdapter extends BaseAdapter {

    List<SortModel> lists = new ArrayList<>();
    private Context context;

    public ContentAdapter(Context context){
        this.context = context;
    }

    public void setData(List<SortModel> list) {
        this.lists = list;
    }



    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.content_item,null);
            viewHolder.title = (TextView) convertView.findViewById(R.id.catalog);
            viewHolder.content = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        char selection = getSelectionForPos(position);
        if (position == getPosForSelection(selection)) {
            viewHolder.title.setVisibility(View.VISIBLE);
            viewHolder.title.setText(((SortModel)getItem(position)).getSortLetters());
        }else{
            viewHolder.title.setVisibility(View.GONE);
        }

        viewHolder.content.setText(((SortModel)getItem(position)).getName());
        return convertView;
    }


    public char getSelectionForPos(int pos) {
        return lists.get(pos).getSortLetters().charAt(0);
    }

    public int getPosForSelection(char selection) {
        for(int i = 0 ; i < getCount();i++) {
            String letter = lists.get(i).getSortLetters();
            char firstChar = letter.toUpperCase().charAt(0);
            if (firstChar == selection) {
                return i;
            }
        }
        return -1;
    }

    static class ViewHolder{
        TextView  title;
        TextView  content;

    }
}
