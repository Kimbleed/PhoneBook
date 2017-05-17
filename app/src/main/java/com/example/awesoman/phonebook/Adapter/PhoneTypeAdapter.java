package com.example.awesoman.phonebook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.awesoman.phonebook.Entity.PhoneTypeEntity;
import com.example.awesoman.phonebook.R;

import org.w3c.dom.Text;

import java.util.List;
import java.util.zip.Inflater;


/**
 * Created by Awesome on 2016/9/29.
 */
public class PhoneTypeAdapter extends BaseAdapter {

    LayoutInflater mInflater =null;
    List<PhoneTypeEntity> mData =null;
    public PhoneTypeAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }
    public PhoneTypeAdapter(Context context,List<PhoneTypeEntity> data){
        mInflater = LayoutInflater.from(context);
        mData = data;
    }

    @Override
    public int getCount() {
        return mData!=null?mData.size():0;
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(convertView ==null){
            convertView = mInflater.inflate(R.layout.listview_item_phone_type,null);
            viewHolder = new ViewHolder();
            viewHolder.tv_phonetype_name=(TextView)convertView.findViewById(R.id.tv_phonetype_name);
            viewHolder.tv_phonetype_num=(TextView)convertView.findViewById(R.id.tv_phonetype_num);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.tv_phonetype_name.setText(mData.get(i).getPhoneName());
        viewHolder.tv_phonetype_num.setText(mData.get(i).getPhoneNum());
        return convertView;
    }

    class ViewHolder{
        TextView tv_phonetype_name;
        TextView tv_phonetype_num;
    }
    public void fresh( List<PhoneTypeEntity> data){
        mData = data;
    }
}
