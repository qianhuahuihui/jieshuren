package ren.jieshu.jieshuren.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.entity.CityBean;

/**
 * Created by laomaotao on 2017/8/11.
 */

public class CityAdapter extends BaseAdapter {
    private Context context;
    private List<CityBean> cityBeanList;
    public CityAdapter(Context context, List<CityBean> cityBeanList){
        this.context = context;
        this.cityBeanList = cityBeanList;
    }


    @Override
    public int getCount() {
        return cityBeanList.size();
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
        CityBean cityBean = cityBeanList.get(position);
        CityViewHolder cityViewHolder = null;
        if (convertView == null){
            cityViewHolder = new CityViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_city, null);
            cityViewHolder.item_city_cityname = (TextView) convertView.findViewById(R.id.item_city_cityname);
            convertView.setTag(cityViewHolder);
        }else {
            cityViewHolder = (CityViewHolder) convertView.getTag();
        }
        cityViewHolder.item_city_cityname.setText(cityBean.getName());
        return convertView;
    }

    class CityViewHolder{
        private TextView item_city_cityname;

    }

}

