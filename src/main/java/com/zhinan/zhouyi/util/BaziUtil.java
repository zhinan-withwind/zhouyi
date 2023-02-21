package com.zhinan.zhouyi.util;

import com.alibaba.fastjson.JSONObject;
import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.date.SolarDateTime;
import com.zhinan.zhouyi.fate.bazi.八字;
import okhttp3.*;
import run.zhinan.time.ganzhi.GanZhi;
import run.zhinan.time.ganzhi.GanZhiDateTime;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BaziUtil {
    public static List<八字> listAllBazi() {
        List<八字> baziList = new ArrayList<>();
        for (int y = 0; y < 60; y++) {
            GanZhi year = GanZhi.getByValue(y);
            for (int m = 0; m < 12; m++) {
                GanZhi month = GanZhi.of((year.getGan().getValue() % 5) * 2 + m, (m + 2) % 12);
                for (int d = 0; d < 60; d++) {
                    GanZhi day = GanZhi.getByValue(d);
                    for (int h = 0; h < 12; h++) {
                        GanZhi hour = GanZhi.toGanZhi(day, h);
                        GanZhiDateTime ganZhiDateTime = GanZhiDateTime.of(year, month, day, hour);
                        baziList.add(八字.of(ganZhiDateTime.toLocalDateTime(), 1));
                        baziList.add(八字.of(ganZhiDateTime.toLocalDateTime(), 0));
                    }
                }
            }
        }
        System.out.println(baziList.size());
        return baziList;
    }

    public static 八字 getWenZhenBazi(LocalDateTime birthday, int sex) {
        String url = "https://bzapi2.iwzbz.com/getbasebz.php";
        OkHttpClient client = new OkHttpClient();

        八字 bazi = null;

        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder()
                .addQueryParameter("d", SolarDateTime.of(birthday).toString())
                .addQueryParameter("s", String.valueOf(sex)).build();
        Request request = new Request.Builder().url(httpUrl).get().build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();

            String body = Objects.requireNonNull(response.body()).string();
            JSONObject result = JSONObject.parseObject(body);
            JSONObject bz = result.getJSONObject("bz");
            GanZhi year  = GanZhi.getByName(bz.getString("0") + bz.getString("1"));
            GanZhi month = GanZhi.getByName(bz.getString("2") + bz.getString("3"));
            GanZhi day   = GanZhi.getByName(bz.getString("4") + bz.getString("5"));
            GanZhi time  = GanZhi.getByName(bz.getString("6") + bz.getString("7"));
            bazi = 八字.of(GanZhiDateTime.of(year, month, day, time).toLocalDateTime(), sex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bazi;
    }
}
