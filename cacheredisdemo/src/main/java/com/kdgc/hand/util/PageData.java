package com.kdgc.hand.util;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <一句话功能简述> 参数封装Map <功能详细描述>
 * 
 * @author shandong
 * @version [版本号, 2016年10月12日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PageData extends HashMap implements Map
{
    
    private static final long serialVersionUID = 1L;
    
    Map map = null;
    
    HttpServletRequest request;
    
    public PageData(HttpServletRequest request)
    {
        this.request = request;
        Map properties = request.getParameterMap();
        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext())
        {
            entry = (Entry)entries.next();
            name = (String)entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj)
            {
                value = "";
            }
            else if (valueObj instanceof String[])
            {
                String[] values = (String[])valueObj;
                for (int i = 0; i < values.length; i++)
                {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            }
            else
            {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        map = returnMap;
    }
    
    public PageData()
    {
        map = new HashMap();
    }
    
    @Override
    public Object get(Object key)
    {
        Object obj = null;
        if (map.get(key) instanceof Object[])
        {
            Object[] arr = (Object[])map.get(key);
            obj = request == null ? arr : (request.getParameter((String)key) == null ? arr : arr[0]);
        }
        else
        {
            obj = map.get(key);
        }
        return obj;
    }
    
    public String getString(Object key)
    {
        return (String)get(key);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Object put(Object key, Object value)
    {
        return map.put(key, value);
    }
    
    @Override
    public Object remove(Object key)
    {
        return map.remove(key);
    }
    
    @Override
    public void clear()
    {
        map.clear();
    }
    
    @Override
    public boolean containsKey(Object key)
    {
        // TODO Auto-generated method stub
        return map.containsKey(key);
    }
    
    @Override
    public boolean containsValue(Object value)
    {
        // TODO Auto-generated method stub
        return map.containsValue(value);
    }
    
    @Override
    public Set entrySet()
    {
        // TODO Auto-generated method stub
        return map.entrySet();
    }
    
    @Override
    public boolean isEmpty()
    {
        // TODO Auto-generated method stub
        return map.isEmpty();
    }
    
    @Override
    public Set keySet()
    {
        // TODO Auto-generated method stub
        return map.keySet();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public void putAll(Map t)
    {
        // TODO Auto-generated method stub
        map.putAll(t);
    }
    
    @Override
    public int size()
    {
        // TODO Auto-generated method stub
        return map.size();
    }
    
    @Override
    public Collection values()
    {
        // TODO Auto-generated method stub
        return map.values();
    }
    
}
