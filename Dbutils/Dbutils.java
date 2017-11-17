/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pers.database;

import com.pers.bean.User;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import static jdk.nashorn.internal.objects.NativeJava.type;

/**
 *
 * @author 伯通
 */
public class Dbutils {

    public Object getByKey(Class type, Integer id) throws Exception {
        {
            String classname;
            Class clazz = Class.forName(type.getName());
            classname = clazz.getSimpleName();
            Field[] fields = clazz.getFields();
            Constructor c = clazz.getDeclaredConstructor(null);
            c.setAccessible(true);
            Object p = (Object) c.newInstance(null);
            StringBuffer sb = new StringBuffer();
            for (Field field : fields) {
                sb.append(field.getName() + ",");
            }
            String sql1 = sb.substring(0, sb.length() - 1);
            String sql = "select" + " " + sql1 + " " + "from" + " " + classname + " " + "where id=" + id + "";
            DatabaseMysql db = new DatabaseMysql();
            ResultSet rs = db.query(sql);
            if (rs != null && rs.next()) {
                for (Field field : fields) {
                    field.set(p, rs.getObject(field.getName()));
                }
            } else {
                rs.close();
            }
            return p;
        }
    }

    public boolean insert(Object type) throws Exception {
        String classname;
        Class clazz = Class.forName(type.getClass().getName());
        classname = clazz.getSimpleName();
        StringBuffer sb = new StringBuffer();
        StringBuffer sz = new StringBuffer();
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            Class a = field.getType();
            sz.append(field.getName() + ",");
            if (a.equals(String.class)) {
                sb.append("'" + field.get(type) + "'" + ",");
            } else {
                sb.append(field.get(type) + ",");
            }
        }
        String sql1 = sb.substring(0, sb.length() - 1);
        String sql2 = sz.substring(0, sz.length() - 1);
        String sql = "insert into " + classname + " " + "(" + sql2 + ")" + " values " + "(" + sql1 + ")";
        System.out.println(sql);
        DatabaseMysql db = new DatabaseMysql();
        db.update(sql);
        boolean b = db.isResultSetNull();
        return b;
    }

    public boolean delByKey(Class type, Integer id) throws Exception {
        String classname;
        Class clazz = Class.forName(type.getName());
        classname = clazz.getSimpleName();
        String sql = "delete from " + classname + " where id=" + id;
        DatabaseMysql db = new DatabaseMysql();
        boolean b = db.update(sql);
        return b;
    }

    public boolean update(Object type) throws Exception {
        String classname;
        Class clazz = Class.forName(type.getClass().getName());
        classname = clazz.getSimpleName();
        Field[] fields = clazz.getFields();
        StringBuffer sb = new StringBuffer();
        for (Field field : fields) {
            Class b = field.getType();
            if (b.equals(String.class)) {
                sb.append(field.getName() + "='" + field.get(type) + "'" + ",");
            } else {
                sb.append(field.getName() + "=" + field.get(type) + ",");
            }

        }
        String sql = sb.substring(0, sb.length() - 6);
        Field p = clazz.getField("id");
        int id = (int) p.get(type);
        String sql2 = "update " + classname + " set " + sql + " where id=" + id;
        System.out.println(sql);
        System.out.println(sql2);
        DatabaseMysql db = new DatabaseMysql();
        boolean b = db.update(sql2);
        return b;
    }

    public List<Object> getAll(Class type) throws Exception {
        String classname;
        Class clazz = Class.forName(type.getName());
        classname = clazz.getSimpleName();
        Field[] fields = clazz.getFields();
        DatabaseMysql db = new DatabaseMysql();
        String sql = "select * from " + classname;
        ResultSet rs = db.query(sql);
        LinkedList<Object> userlist = new LinkedList<Object>();
        while (rs != null && rs.next()) {
            Constructor c = clazz.getDeclaredConstructor(null);
            c.setAccessible(true);
            Object p = (Object) c.newInstance(null);
            for (Field field : fields) {
                field.set(p, rs.getObject(field.getName()));
            }
            userlist.add(p);

        }
        db.close();
        return userlist;
    }
}
