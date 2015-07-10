package me.whiteworld.zlist;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import me.whiteworld.zlist.model.Site;

/**
 * Created by whiteworld on 2015/3/27.
 */
public final class Util {
    private static ObjectMapper mapper = new ObjectMapper();

    public static List<Site> getSitesFromJson(String jsonString) {
        try {
            return mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, Site.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
