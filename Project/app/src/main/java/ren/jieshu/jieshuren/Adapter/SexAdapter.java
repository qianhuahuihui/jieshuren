package ren.jieshu.jieshuren.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ren.jieshu.jieshuren.R;

/**
 * Created by laomaotao on 2017/8/22.
 */

public class SexAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    public SexAdapter(Context context, List<String> list){
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String s = list.get(position);
        CityViewHolder cityViewHolder = null;
        if (convertView == null){
            cityViewHolder = new CityViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_city, null);
            cityViewHolder.item_city_cityname = (TextView) convertView.findViewById(R.id.item_city_cityname);
            convertView.setTag(cityViewHolder);
        }else {
            cityViewHolder = (CityViewHolder) convertView.getTag();
        }
        cityViewHolder.item_city_cityname.setText(s);
        return convertView;
    }

    class CityViewHolder{
        private TextView item_city_cityname;

    }

}