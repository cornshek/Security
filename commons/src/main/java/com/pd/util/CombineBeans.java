package com.pd.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author GTY
 * 将两个相同类型的类进行合并
 *      规则：将两个对象各自的空值进行合并
 *      P p = new P("zhangsan")
 *      P p1 = new P(123)
 *      将p对象的String + p1对象的int值进行合并
 *      ==> p+p1 = new P("zhangsan",123)
 */
public class CombineBeans {

    /**
     * 使用Apache BeanUtil利用反射复制javabean
     * 如果obj1的某个get方法得出的参数不为空，则调用obj2的set方法，最后返回合并后的javabean
     * @param obj1
     * @param obj2
     * @return obj2
     * @throws Exception
     */
    public static Object CopyBeanToBean(Object obj1, Object obj2)
            throws Exception {

        Method[] method1 = obj1.getClass().getMethods();

        Method[] method2 = obj2.getClass().getMethods();

        String methodName1;

        String methodFix1;

        String methodName2;

        String methodFix2;

        for (int i = 0; i < method1.length; i++) {

            methodName1 = method1[i].getName();

            methodFix1 = methodName1.substring(3, methodName1.length());

            if (methodName1.startsWith("get")) {

                for (int j = 0; j < method2.length; j++) {

                    methodName2 = method2[j].getName();

                    methodFix2 = methodName2.substring(3, methodName2.length());

                    if (methodName2.startsWith("set")) {

                        if (methodFix2.equals(methodFix1)) {

                            Object[] objs1 = new Object[0];

                            Object[] objs2 = new Object[1];
                            objs2[0] = method1[i].invoke(obj1, objs1);// 调用obj1的相应的get的方法，objs1数组存放调用该方法的参数,此例中没有参数，该数组的长度�?
                            if (objs2[0] != null && (!"".equals(objs2[0]))) {
                                method2[j].invoke(obj2, objs2);// 调用obj2的相应的set的方法，objs2数组存放调用该方法的参数
                            }
                            continue;
                        }

                    }

                }

            }

        }

        return obj2;

    }
}
