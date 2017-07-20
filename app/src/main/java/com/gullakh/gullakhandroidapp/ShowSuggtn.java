package com.gullakh.gullakhandroidapp;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;



import java.util.ArrayList;

/**
 * Created by njfernandis on 17/02/16.
 */
public class ShowSuggtn extends ArrayAdapter<String> implements Filterable {

    ArrayList<String> _items = new ArrayList<String>();
    ArrayList<String> orig = new ArrayList<String>();

    public ShowSuggtn(Context context, int resource, ArrayList<String> items) {
        super(context, resource, items);

        for (int i = 0; i < items.size(); i++) {
            orig.add(items.get(i));
        }
    }

    @Override
    public int getCount() {
        if (_items != null)
            return _items.size();
        else
            return 0;
    }

    @Override
    public String getItem(int arg0) {
        return _items.get(arg0);
    }


    @Override

    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                if(constraint != null)
                    Log.d("Constraints", constraint.toString());
                FilterResults oReturn = new FilterResults();

                 if (orig == null){
                    for (int i = 0; i < _items.size(); i++) {
                        orig.add(_items.get(i));
                    }
                  }
                String temp;
                int counters = 0;
                if (constraint != null){

                    _items.clear();
                    if (orig != null && orig.size() > 0) {
                        for(int i=0; i<orig.size(); i++)
                        {
                            temp = orig.get(i).toUpperCase();

                            if(temp.startsWith(constraint.toString().toUpperCase()))
                            {

                                _items.add(orig.get(i));
                                counters++;

                            }
                        }
                    }
                    Log.d("REsult size:", String.valueOf(_items.size()));
                  // if(!counters)
                    /*if(counters==0)
                    {
                        _items.clear();
                        _items = orig;
                    }*/
                    oReturn.values = _items;
                    oReturn.count = _items.size();
                }
                return oReturn;
            }


            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                    notifyDataSetChanged();//i added
                }

            }

        };

        return filter;

    }



}