package com.cnst.wisdom.model.bean;

/**
 * Created by Jonas on 2016/2/4.
 */
public class Login
{
    /**
     * data : {"stationCode":"15445","username":"zhangdawei","school":"河西幼儿园1","name":"张大伟","stationName":"4515354"}
     * code : 200
     * sessionId : 3AF812CAE63E103C847D85F4C226C54C
     * msg : success
     */

    private DataEntity data;
    private int code;
    private String sessionId;
    private String msg;

    public void setData(DataEntity data)
    {
        this.data = data;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public void setSessionId(String sessionId)
    {
        this.sessionId = sessionId;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public DataEntity getData()
    {
        return data;
    }

    public int getCode()
    {
        return code;
    }

    public String getSessionId()
    {
        return sessionId;
    }

    public String getMsg()
    {
        return msg;
    }

    public static class DataEntity
    {
        /**
         * stationCode : 15445
         * username : zhangdawei
         * school : 河西幼儿园1
         * name : 张大伟
         * stationName : 4515354
         */

        private String stationCode;
        private String username;
        private String school;
        private String name;
        private String stationName;

        public void setStationCode(String stationCode)
        {
            this.stationCode = stationCode;
        }

        public void setUsername(String username)
        {
            this.username = username;
        }

        public void setSchool(String school)
        {
            this.school = school;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public void setStationName(String stationName)
        {
            this.stationName = stationName;
        }

        public String getStationCode()
        {
            return stationCode;
        }

        public String getUsername()
        {
            return username;
        }

        public String getSchool()
        {
            return school;
        }

        public String getName()
        {
            return name;
        }

        public String getStationName()
        {
            return stationName;
        }
    }
}
