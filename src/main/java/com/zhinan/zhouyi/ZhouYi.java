package com.zhinan.zhouyi;

import com.alibaba.fastjson.JSONObject;
import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.date.SolarDateTime;
import com.zhinan.zhouyi.fate.bazi.八字;
import com.zhinan.zhouyi.util.FileUtil;
import okhttp3.*;

import java.time.LocalDateTime;
import java.util.Objects;

public class ZhouYi {
    public static void main(String[] args) {
        LocalDateTime birthday = LocalDateTime.of(1900, 1, 6, 0, 15);
        int sex = 1;
        String url = "https://bzapi2.iwzbz.com/getbasebz.php";
        OkHttpClient client = new OkHttpClient();
        while (birthday.getYear() < 2023) {
            System.out.print("\r" + SolarDateTime.of(birthday));

            八字 bazi = 八字.of(birthday, sex);

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
                干支 year = 干支.getByName(bz.getString("0") + bz.getString("1"));
                干支 month = 干支.getByName(bz.getString("2") + bz.getString("3"));
                干支 day = 干支.getByName(bz.getString("4") + bz.getString("5"));
                干支 hour = 干支.getByName(bz.getString("6") + bz.getString("7"));

//            List<干支> lucks = new ArrayList<>();
//            JSONArray dayun = result.getJSONArray("dayun");
//            for (Object yun : dayun) {
//                lucks.add(干支.getByName((String) yun));
//            }
//            System.out.println(Arrays.deepToString(lucks.toArray()));

//            Assert.assertEquals(bazi.getYear(), year);
//            Assert.assertEquals(bazi.getMonth(), month);
//            Assert.assertEquals(bazi.getDay(), day);
//            Assert.assertEquals(bazi.getTime(), hour);
                if (    !bazi.getYear().equals(year) ||
                        !bazi.getMonth().equals(month) ||
                        !bazi.getDay().equals(day) ||
                        !bazi.getTime().equals(hour)) {
                    System.out.println();
                    System.out.println("我的： " + bazi);
                    System.out.println("问真： " + year + " " + month + " " + day + " " + hour);
                    System.out.println("=============================");
                }

                birthday = birthday.plusHours(1);
                FileUtil.saveToFile("/opt/projects/zhouyi_test/wenzhen/" + SolarDateTime.of(birthday), body);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
