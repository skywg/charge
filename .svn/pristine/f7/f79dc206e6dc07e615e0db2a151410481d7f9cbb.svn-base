package com.iycharge.server.domain.service.impl;

import com.iycharge.server.domain.service.PhotoService;
import main.java.com.UpYun;

import java.util.List;

public class PhotoServiceImpl implements PhotoService {
    private UpYun upYun = new UpYun("youetong", "biliking", "biliking123");

    public UpYun getUpYun() {
        return upYun;
    }

    public void setUpYun(UpYun upYun) {
        this.upYun = upYun;
    }

    public void init() {
        this.upYun.setDebug(true);
        this.upYun.setTimeout(60);
        this.upYun.setApiDomain(UpYun.ED_AUTO);
    }

    public boolean mkDir(String path) {
        return this.upYun.mkDir(path, true);
    }

    public boolean rmDir(String path) {
        return this.upYun.rmDir(path);
    }

    public List<UpYun.FolderItem> readDir(String path) {
        return this.upYun.readDir(path);
    }

}
