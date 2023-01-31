package com.zhinan.zhouyi;

import com.alibaba.fastjson.JSONObject;
import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.common.ZhouYiAPI;
import com.zhinan.zhouyi.date.SolarDateTime;
import com.zhinan.zhouyi.desc.ZhouYiDescriptor;
import com.zhinan.zhouyi.desc.fate.十神描述器;
import com.zhinan.zhouyi.fate.bazi.八字;
import com.zhinan.zhouyi.fate.luck.*;
import com.zhinan.zhouyi.out.LuckOutputter;
import com.zhinan.zhouyi.util.FileUtil;
import okhttp3.*;
import org.springframework.boot.SpringApplication;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

public class ZhouYi {
    private final static String dir = "/Users/withwind/Downloads/LuckChart";
    private final static String name = "胡南";
    private final static int sex = 1;
    private final static LocalDateTime ww = LocalDateTime.of(1976, 2, 11, 11, 40);
    private final static LocalDateTime tt = LocalDateTime.of(1987, 2, 20, 3 , 40);
    private final static LocalDateTime yx = LocalDateTime.of(1987, 3, 18, 5 , 40);
    private final static LocalDateTime wh = LocalDateTime.of(1980, 4, 19, 0 , 40);

    public static void main(String[] args) {
        ZhouYiAPI.init(ZhouYiAPI.MODEL_TYPE.SCORE, System.lineSeparator());
        ZhouYiDescriptor.autoInit();

//        日运 yun = 日运.of(LocalDateTime.now(), ww, sex);
//        System.out.println(合婚描述器.describe(八字.of(wh, 1), 八字.of(yx, 0)));
//        System.out.println(周易描述器.描述(八字.of(ww, 1).getFate()));
//        Arrays.asList(十神.values()).forEach(shen -> System.out.println(周易描述器.describe(shen)));
//        System.out.println(周易描述器.describe(yun));
//        System.out.println(命局描述器.describe(八字.of(wh, 1).getFatePattern()));
//        System.out.println(周易描述器.describe(健康.of(ww, 1)));
//        System.out.println(周易描述器.describe(财运.of(wh, 1)));
//        System.out.println(周易描述器.describe(财运.of(LocalDateTime.of(2019, 9, 19, 10, 20), 0)));
//        System.out.println(能量描述器.describe(命盘.of(ww, 1).atYear(LocalDateTime.now().getYear()).getEnergy()));
//        System.out.println(称骨描述器.describe(ww, 1));
//        System.out.println(JSON.toJSONString(命盘.of(LocalDateTime.of(1969,2,18,22,0), 1).simplify().get大运(), true));
//                .atDay(2022, 12, 1).simplify().get大运(), true));
//        System.out.println(干支.getByName("壬寅").getStatus());
//        System.out.println(干支.getByName("壬寅").getStatus());
//        System.out.println(干支.getByName("壬寅").getStatus());
//        System.out.println(干支.getByName("壬寅").getStatus());
//        System.out.println(大运.calculateLuckDate(LocalDateTime.of(1969,2,18,22,4), 1));
//        八字 bazi = 八字.of(ww, 1);
//        DateUtil.findDateTime(1900, 2100, bazi.getYear(), bazi.getMonth(), bazi.getDay(), bazi.getTime()).
//                        forEach(dateTime -> {System.out.println(dateTime + " - " + GanZhiDateTime.of(dateTime));});
//        GanZhiDateTime dateTime = GanZhiDateTime.of(bazi.getYear(), bazi.getMonth(), bazi.getDay(), bazi.getTime());
//        System.out.println(dateTime.toLocalDateTime() + " - " + dateTime);
//        System.out.println(大运.of(LocalDateTime.of(2023, 2, 11, 23, 1), ww, 1).getAge());
        System.out.println(十神描述器.getInstance().getClass().getName());

        SpringApplication.run(ZhouYi.class, args);
    }

    public static void createLuckChart() throws IOException {
        LuckOutputter.output(name, 大运.list(ww, sex), dir);
        LuckOutputter.output(name, 年运.list(ww, sex), dir);
        LuckOutputter.output(name, 年运.list(LocalDateTime.now().getYear(), ww, sex), dir);
        LuckOutputter.output(name, 月运.list(LocalDateTime.now().getYear(), ww, sex), dir);
        LuckOutputter.output(name, 日运.list(LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonthValue(), ww, sex), dir);
        LuckOutputter.output(name, 时运.list(LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth(), ww, sex), dir);
    }

    public static void checkBazi() {
        int sex = 1;
        String url = "https://bzapi2.iwzbz.com/getbasebz.php";
        OkHttpClient client = new OkHttpClient();
        LocalDateTime birthday = ww;
        while (birthday.getYear() < 2023) {
            System.out.print("\r" + SolarDateTime.of(ww));

            八字 bazi = 八字.of(birthday, sex);

            HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder()
                    .addQueryParameter("d", SolarDateTime.of(ww).toString())
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
                FileUtil.saveToFile("/opt/projects/zhouyi_test/wenzhen/" + SolarDateTime.of(ww), body);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
