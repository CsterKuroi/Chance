package com.kuroi.chance.service;

import android.content.Context;

import com.kuroi.chance.dataaccess.DBOperation;
import com.kuroi.chance.model.Chance;

import java.util.List;

public class Service {
    private DBOperation dao = null;
    public Service(Context context) {
        dao = new DBOperation(context);
    }
    public boolean save(Chance chance) {
        boolean flag = dao.save(chance);
        return flag;
    }
    public List getByName(String queryName, int sort) {
        List list = dao.getByName(queryName, sort);
        return list;
    }
    public Chance getById(int id) {
        Chance chance = dao.getById(id);
        return chance;
    }
    public boolean update(Chance chance) {
        boolean flag = dao.update(chance);
        return flag;
    }
    public void delete(int id) {
        dao.delete(id);
    }
    public Long getCount() {
        return dao.getCount();
    }
}
