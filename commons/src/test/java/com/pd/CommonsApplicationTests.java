package com.pd;


import com.pd.jsonconversion.DataMap;
import com.pd.jsonconversion.Items;
import com.pd.jsonconversion.Message;
import com.pd.pojo.Person;
import com.pd.util.ReflectValue;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class CommonsApplicationTests {

    @Test
    public void t() {
        String s = "HTTP/1.1 200 OK [Server: Tengine, Date: Mon, 03 Aug 2020 16:23:30 GMT, Content-Type: application/json; charset=UTF-8, Content-Length: 710, Connection: keep-alive, Access-Control-Allow-Origin: *, Access-Control-Allow-Methods: POST, Access-Control-Allow-Headers: x-requested-with, Access-Control-Max-Age: 3600, X-Ca-Request-Id: 75ABAE82-9ECE-40A0-BA60-CD2648687947, Set-Cookie: SERVERID=b0a4aeec523942db7a56458586cbced4|1596471808|1596471808;Path=/] org.apache.http.conn.BasicManagedEntity@8c89c6a\n" +
                "{\"message\":{\"status\":0,\"value\":\"比对完成\"},\"cardsinfo\":[{\"type\":\"9999\",\"items\":[{\"nID\":null,\"index\":null,\"desc\":\"判定值\",\"content\":\"0.97731405\"},{\"nID\":null,\"index\":null,\"desc\":\"判定结果\",\"content\":\"是\"},{\"nID\":null,\"index\":null,\"desc\":\"保留\",\"content\":\"\"},{\"nID\":null,\"index\":null,\"desc\":\"姓名\",\"content\":\"葛天宇\"},{\"nID\":null,\"index\":null,\"desc\":\"性别\",\"content\":\"男\"},{\"nID\":null,\"index\":null,\"desc\":\"民族\",\"content\":\"汉\"},{\"nID\":null,\"index\":null,\"desc\":\"出生\",\"content\":\"1998-10-26\"},{\"nID\":null,\"index\":null,\"desc\":\"住址\",\"content\":\"江西省丰城市拖船镇塘下村塘下组211号\"},{\"nID\":null,\"index\":null,\"desc\":\"公民身份号码\",\"content\":\"360981199810260817\"}]}]}\n";
        int i = s.indexOf("{");
        String substring = s.substring(i, s.length()-1);

        System.out.println(substring);

    }

    @Test
    public void test() throws IOException {
        String json = "{\"message\":{\"status\":0,\"value\":\"比对完成\"},\"cardsinfo\":[{\"type\":\"9999\",\"items\":[{\"nID\":null,\"index\":null,\"desc\":\"判定值\",\"content\":\"0.97731405\"},{\"nID\":null,\"index\":null,\"desc\":\"判定结果\",\"content\":\"是\"},{\"nID\":null,\"index\":null,\"desc\":\"保留\",\"content\":\"\"},{\"nID\":null,\"index\":null,\"desc\":\"姓名\",\"content\":\"葛天宇\"},{\"nID\":null,\"index\":null,\"desc\":\"性别\",\"content\":\"男\"},{\"nID\":null,\"index\":null,\"desc\":\"民族\",\"content\":\"汉\"},{\"nID\":null,\"index\":null,\"desc\":\"出生\",\"content\":\"1998-10-26\"},{\"nID\":null,\"index\":null,\"desc\":\"住址\",\"content\":\"江西省丰城市拖船镇塘下村塘下组211号\"},{\"nID\":null,\"index\":null,\"desc\":\"公民身份号码\",\"content\":\"360981199810260817\"}]}]}\n";
        //转化为Java对象 person对象
//        JsonRootBean person = ob.readValue(json, JsonRootBean.class);
//        System.out.println(person);
//        List<Cardsinfo> cardsinfo = person.getCardsinfo();
//        for (Cardsinfo cardsinfo1 : cardsinfo) {
//            for (Items item : cardsinfo1.getItems()) {
//                if (item.getDesc() != null){
//                    System.out.println(item.getDesc()+":"+item.getContent());
//                }
//            }
//        }
//        Map<String, String> jsonData = DataMap.getJsonData(json);
//        jsonData.forEach((e, v) ->
//                System.out.println(e + ":" + v)
//        );
        Person person = new Person();
        Map<String, String> jsonData = DataMap.getJsonData(json);
        Set<String> strings = jsonData.keySet();
        for (String key : strings) {
            String value = jsonData.get(key);
            if (("姓名").equals(key)) {
                person.setRealName(value);
            } else if (("公民身份号码").equals(key)) {
                person.setIdCardNo(value);
            } else if (("住址").equals(key)) {
                person.setAddress(value);
            }
        }
        System.out.println(person);
    }

    @Test
    public void test2() throws Exception {
        String s = "{\n" +
                "\t\"msg\": \"\",\n" +
                "\t\"success\": true,\n" +
                "\t\"code\": 1000,\n" +
                "\t\"data\": {\n" +
                "            \"order_no\": \"5727511255188800987\",//订单号\n" +
                "            \"result\": 0,//0 一致，1 不一致，2未认证，3 已注销\n" +
                "            \"msg\": \"一致\",//验证结果\t\t\n" +
                "\t    \"desc\": \"认证信息匹配\", //验证结果描述信息\t\t\n" +
                "\t}\n" +
                "}";
        int code = s.indexOf("code");
        String substring = s.substring(code,code+11);
        String numeric = getNumeric(substring);
        System.out.println(numeric);

    }

    /**
     * 过滤非数字
     * @param str
     * @return
     */
    public static String getNumeric(String str) {
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    static Message ms = new Message();

    public static void main(String[] args) throws Exception {
        Items items = new Items();
        Message message = new Message();
        Message message1 = new Message();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入:2：");
        Integer next1 = scanner.nextInt();
        message.setStatus(next1);
        //System.out.println((Message)ReflectValue.getObjectValue(message,Message.class));
        System.out.println("请输入:3：");
        String next2 = scanner.next();
        message1.setValue(next2);
        Message objectValue = (Message) ReflectValue.getObjectValue(message, Message.class);
        Message objectValue1 = (Message) ReflectValue.getObjectValue(message1, Message.class);
        System.out.println(objectValue + ":" + objectValue1);
        System.out.println((Message) ReflectValue.getObjectValue(ms, Message.class));

    }

    public static Object getObjectValue(Object object, Class<?> objectClass) throws Exception {
        //我们项目的所有实体类都继承BaseDomain （所有实体基类：该类只是串行化一下）
        //不需要的自己去掉即可
//        Class<Items> itemsClass = Items.class;
        Object obj = objectClass.newInstance();
        if (object != null) {//if (object!=null )  ----begin
            // 拿到该类
            Class<?> clz = object.getClass();
            // 获取实体类的所有属性，返回Field数组
            Field[] fields = clz.getDeclaredFields();

            //获取所有的键
            String[] fieldNames = new String[fields.length];
            for (int j = 0; j < fields.length; ++j) {
                fieldNames[j] = fields[j].getName();
            }

            for (Field field : fields) {// --for() begin
                // 如果类型是String
                if (field.getGenericType().toString().equals(
                        "class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                    // 拿到该属性的gettet方法

                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(field.getName()));

                    String val = (String) m.invoke(object);// 调用getter方法获取属性值
                    if (val != null) {
                        for (int i1 = 0; i1 < fieldNames.length; i1++) {
                            if (fieldNames[i1].equals(field.getName())) {
                                Field f = objectClass.getDeclaredField(fieldNames[i1]);
                                f.setAccessible(true);
                                f.set(obj, val);
                            }
                        }
                    } else {
                        System.out.println("不添加");
                    }
                }

//                // 如果类型是Integer
                if (field.getGenericType().toString().equals(
                        "class java.lang.Integer")) {
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(field.getName()));
                    Integer val = (Integer) m.invoke(object);
                    if (val != null) {
                        for (int i1 = 0; i1 < fieldNames.length; i1++) {
                            if (fieldNames[i1].equals(field.getName())) {
                                Field f = objectClass.getDeclaredField(fieldNames[i1]);
                                f.setAccessible(true);
                                f.set(obj, val);
                            }
                        }
                    }

                }
//
//                // 如果类型是Double
                if (field.getGenericType().toString().equals(
                        "class java.lang.Double")) {
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(field.getName()));
                    Double val = (Double) m.invoke(object);
                    if (val != null) {
                        for (int i1 = 0; i1 < fieldNames.length; i1++) {
                            if (fieldNames[i1].equals(field.getName())) {
                                Field f = objectClass.getDeclaredField(fieldNames[i1]);
                                f.setAccessible(true);
                                f.set(obj, val);
                            }
                        }
                    }

                }

                // 如果类型是Boolean 是封装类
                if (field.getGenericType().toString().equals(
                        "class java.lang.Boolean")) {
                    Method m = (Method) object.getClass().getMethod(
                            field.getName());
                    Boolean val = (Boolean) m.invoke(object);
                    if (val != null) {
                        for (int i1 = 0; i1 < fieldNames.length; i1++) {
                            if (fieldNames[i1].equals(field.getName())) {
                                Field f = objectClass.getDeclaredField(fieldNames[i1]);
                                f.setAccessible(true);
                                f.set(obj, val);
                            }
                        }
                    }

                }

                // 如果类型是boolean 基本数据类型不一样 这里有点说名如果定义名是 isXXX的 那就全都是isXXX的
                // 反射找不到getter的具体名
                if (field.getGenericType().toString().equals("boolean")) {
                    Method m = (Method) object.getClass().getMethod(
                            field.getName());
                    Boolean val = (Boolean) m.invoke(object);
                    if (val != null) {
                        for (int i1 = 0; i1 < fieldNames.length; i1++) {
                            if (fieldNames[i1].equals(field.getName())) {
                                Field f = objectClass.getDeclaredField(fieldNames[i1]);
                                f.setAccessible(true);
                                f.set(obj, val);
                            }
                        }
                    }

                }
                // 如果类型是Date
                if (field.getGenericType().toString().equals(
                        "class java.util.Date")) {
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(field.getName()));
                    Date val = (Date) m.invoke(object);
                    if (val != null) {
                        for (int i1 = 0; i1 < fieldNames.length; i1++) {
                            if (fieldNames[i1].equals(field.getName())) {
                                Field f = objectClass.getDeclaredField(fieldNames[i1]);
                                f.setAccessible(true);
                                f.set(obj, val);
                            }
                        }
                    }

                }
                // 如果类型是Short
                if (field.getGenericType().toString().equals(
                        "class java.lang.Short")) {
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(field.getName()));
                    Short val = (Short) m.invoke(object);
                    if (val != null) {
                        for (int i1 = 0; i1 < fieldNames.length; i1++) {
                            if (fieldNames[i1].equals(field.getName())) {
                                Field f = objectClass.getDeclaredField(fieldNames[i1]);
                                f.setAccessible(true);
                                f.set(obj, val);
                            }
                        }
                    }

                }
                // 如果还需要其他的类型请自己做扩展

            }//for() --end

        }//if (object!=null )  ----end
        return obj;
    }

    // 把一个字符串的第一个字母大写、效率是最高的、
    private static String getMethodName(String fildeName) throws Exception {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }


}