package ren.jieshu.jieshuren.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * Created by laomaotao on 2017/8/6.
 */

public class Sign {
    public static String sign(Map<String,String> map, String token){
        StringBuffer stringA=new StringBuffer();
        Collection<String> keyset= map.keySet();
        List<String> list = new ArrayList<String>(keyset);
        Collections.sort(list);
        for (int i = 0; i < list.size(); i++) {
            stringA.append(list.get(i));
            stringA.append("=");
            stringA.append(map.get(list.get(i)));
            stringA.append("&");
        }
        stringA.append("token=");
        stringA.append(token);
        String sign= md5.getmd5(stringA.toString()).toLowerCase();
        return sign;
    }
    public static String delsign(SortedMap<String,Object> map, String token){
        StringBuffer stringA=new StringBuffer();
        Set<Map.Entry<String,Object>> es= map.entrySet();
        Iterator<Map.Entry<String,Object>> it = es.iterator();
        while (it.hasNext()){
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>)it.next();
            String k = entry.getKey();
            stringA.append(k);
            stringA.append("=");
            stringA.append(entry.getValue());
            stringA.append("&");

        }
        stringA.append("token=");
        stringA.append(token);

        String sign= md5.getmd5(stringA.toString()).toLowerCase();
        return sign;
    }
}
