package com.zhinan.zhouyi.util;

import com.alibaba.fastjson.JSONObject;
import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.date.GanZhiDateTime;
import com.zhinan.zhouyi.date.SolarDateTime;
import com.zhinan.zhouyi.fate.bazi.八字;
import okhttp3.*;
import run.zhinan.time.ganzhi.GanZhi;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BaziUtil {
    public static List<八字> listBazi() {
        List<八字> baziList = new ArrayList<>();
        for (int y = 0; y < 60; y++) {
            干支 year = 干支.getByValue(y);
            baziList.addAll(listBazi(year));
        }
        System.out.println(baziList.size());
        return baziList;
    }

    public static List<八字> listBazi(干支 year) {
        List<八字> baziList = new ArrayList<>();
        for (int m = 0; m < 12; m++) {
            干支 month = 干支.getByValue((year.getGan().getValue() % 5) * 2 + m + 2, (m + 2) % 12);
            baziList.addAll(listBazi(year, month));
        }
        return baziList;
    }

    public static List<八字> listBazi(干支 year, 干支 month) {
        List<八字> baziList = new ArrayList<>();
        for (int d = 0; d < 60; d++) {
            干支 day = 干支.getByValue(d);
            baziList.addAll(listBazi(year, month, day));
        }
        return baziList;
    }

    public static List<八字> listBazi(干支 year, 干支 month, 干支 day) {
        List<八字> baziList = new ArrayList<>();
        for (int h = 0; h < 12; h++) {
            干支 time = DateUtil.toGanZhi(day, h * 2);
            GanZhiDateTime ganZhiDateTime = GanZhiDateTime.of(year, month, day, time);
            baziList.add(八字.of(ganZhiDateTime, 1));
            baziList.add(八字.of(ganZhiDateTime, 0));
        }
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
            干支 year  = 干支.getByName(bz.getString("0") + bz.getString("1"));
            干支 month = 干支.getByName(bz.getString("2") + bz.getString("3"));
            干支 day   = 干支.getByName(bz.getString("4") + bz.getString("5"));
            干支 time  = 干支.getByName(bz.getString("6") + bz.getString("7"));
            bazi = 八字.of(GanZhiDateTime.of(year, month, day, time), sex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bazi;
    }
}
