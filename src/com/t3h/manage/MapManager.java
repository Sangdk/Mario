package com.t3h.manage;

import com.t3h.model.Map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class MapManager {
    int x, y;

    public ArrayList<Map> readMap(String name) {
        ArrayList<Map> arr = new ArrayList<>();
        try {
            File f = new File("src/map/" + name);
            FileReader reader = new FileReader(f);
            BufferedReader br = new BufferedReader(reader);
            int j = 0;
            String line = br.readLine();
            while (line != null) {
                for (int i = 0; i < line.length(); i++) {
                    int bit = 0;

                    if (line.charAt(i) == ' ') continue;
                    if (line.charAt(i) == 'a') {
                        bit = 10;
                    } else if (line.charAt(i) == 'b') {
                        bit = 11;
                    } else {
                        bit = Integer.parseInt(line.charAt(i) + "");
                    }
                    if (bit > 0) {
                        x = 34 * i;
                        y = 40 * j;
                    }
                    Map map = new Map(x, y, bit);
                    arr.add(map);

                }
                j++;
                line = br.readLine();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return arr;
    }
}
