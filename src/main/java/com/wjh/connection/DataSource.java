package com.wjh.connection;

import com.wjh.util.DataBaseTypeEnum;

public class DataSource {
    private DataBaseTypeEnum databaseType;
    private Integer minConnectionCount;
    private Integer maxConnectionCount;
    private String url;
    private String user;
    private String pwd;
    private String note;//非必填 备注

    public DataBaseTypeEnum getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(DataBaseTypeEnum databaseType) {
        this.databaseType = databaseType;
    }

    public Integer getMinConnectionCount() {
        return minConnectionCount;
    }

    public void setMinConnectionCount(Integer minConnectionCount) {
        this.minConnectionCount = minConnectionCount;
    }

    public Integer getMaxConnectionCount() {
        return maxConnectionCount;
    }

    public void setMaxConnectionCount(Integer maxConnectionCount) {
        this.maxConnectionCount = maxConnectionCount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void check() throws Exception {
        if (databaseType == null) throw new Exception("数据源的databaseType不可为空");
        if (minConnectionCount == null) throw new Exception("数据源的minConnectionCount不可为空");
        if (maxConnectionCount == null) throw new Exception("数据源的maxConnectionCount不可为空");
        if (url == null) throw new Exception("数据源的url不可为空");
        if (user == null) throw new Exception("数据源的user不可为空");
        if (pwd == null) throw new Exception("数据源的pwd不可为空");
    }

    @Override
    public String toString() {
        return "DataSource{" +
                "databaseType=" + databaseType +
                ", minConnectionCount=" + minConnectionCount +
                ", maxConnectionCount=" + maxConnectionCount +
                ", url='" + url + '\'' +
                ", user='" + user + '\'' +
                ", pwd='" + pwd + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
