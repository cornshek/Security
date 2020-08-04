package com.pd.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author GTY
 * 反射循环赋值
 */
public class ReflectValue {

    /**
     *
     * @param object ：赋值的实体类对象
     * @param objectClass ： Class字节码对象，用于生成新的对象，并且赋值
     * @return
     * @throws Exception
     */
    public static Object getObjectValue(Object object,Class<?> objectClass) throws Exception {
        //我们项目的所有实体类都继承BaseDomain （所有实体基类：该类只是串行化一下）
        //不需要的自己去掉即可
        Object obj = objectClass.newInstance();
        //if (object!=null )  ----begin
        if (object != null) {
            // 拿到该类
            Class<?> clz = object.getClass();
            // 获取实体类的所有属性，返回Field数组
            Field[] fields = clz.getDeclaredFields();

            //获取所有的键
            String[] fieldNames = new String[fields.length];
            for (int j = 0; j < fields.length; ++j) {
                fieldNames[j] = fields[j].getName();
            }

            // --for() begin
            for (Field field : fields) {
                // 如果类型是String 如果type是类类型，则前面包含"class "，后面跟类名
                if (field.getGenericType().toString().equals(
                        "class java.lang.String")) {
                    // 拿到该属性的gettet方法

                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(field.getName()));
                    // 调用getter方法获取属性值
                    String val = (String) m.invoke(object);
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

    /**
     * 把一个字符串的第一个字母大写、效率是最高的、
     */
    private static String getMethodName(String fildeName) throws Exception {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }


}
