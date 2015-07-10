package me.whiteworld.zlist;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by whiteworld on 2015/3/26.
 */
public class ContentFragment extends Fragment {
    private static String TAG = ContentFragment.class.getSimpleName();
    private SimpleAdapter simpleAdpt;
    private List<Map<String, String>> itemList = new ArrayList<Map<String, String>>();
    private Map<String, String> titleUrl = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_content, container, false);
        ListView lv = (ListView) rootView.findViewById(R.id.listView);
        final String url = Consts.URL_PREFIX + getArguments().getString(Consts.URL_KEY);
        Log.e(TAG, url);
        makeJsonArrayRequest(url);
        simpleAdpt = new SimpleAdapter(getActivity(), itemList, android.R.layout.simple_list_item_1,
                new String[]{"title"}, new int[]{android.R.id.text1}) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(getResources().getColor(R.color.myTextPrimaryColor));
                return view;
            }
        };
        lv.setAdapter(simpleAdpt);
        lv.setDivider(new ColorDrawable(getResources().getColor(R.color.selected_gray)));
        lv.setDividerHeight(1);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
                                    long id) {

                // We know the View is a TextView so we can cast it
                TextView clickedView = (TextView) view;
                TextView tvUrl = (TextView) view.findViewById(android.R.id.text1);
                Intent i = new Intent(getActivity(), WebViewDemo.class);
                i.putExtra("url", titleUrl.get(tvUrl.getText()));
                i.putExtra("title", tvUrl.getText());
                startActivity(i);
            }
        });

        return rootView;
    }

    private void makeJsonArrayRequest(String url) {
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        try {
                            itemList.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = (JSONObject) response.get(i);
                                String title = item.getString("title");
                                String url = item.getString("url");
                                itemList.add(createItem("title", title));
                                titleUrl.put(title, url);
                                simpleAdpt.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getmInstance().addToRequestQueue(req);
    }

    private HashMap<String, String> createItem(String key, String name) {
        HashMap<String, String> item = new HashMap<String, String>();
        item.put(key, name);
        return item;
    }
}