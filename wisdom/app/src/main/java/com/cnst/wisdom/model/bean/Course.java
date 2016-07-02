package com.cnst.wisdom.model.bean;

import java.io.Serializable;

/**
 * 课程对象
 * <功能详细描述>
 * @author taoyuan.
 * @since 1.0
 */
public class Course implements Serializable
    {
        /**
         * id : 778we8uyfwhj7823b
         * createTime : {"nanos":0,"time":1451888755000,"minutes":25,"seconds":55,"hours":14,"month":0,"timezoneOffset":-480,"year":116,"day":1,"date":4}
         * school : {"id":"4028880d2f81b335012f81bf1e30000k","createTime":{"nanos":0,"time":1452721950000,"minutes":52,"seconds":30,"hours":5,"month":0,"timezoneOffset":-480,"year":116,"day":4,"date":14},"address":"深圳市宝安区河西四坊157号","regTime":{"nanos":0,"time":1265040000000,"minutes":0,"seconds":0,"hours":0,"month":1,"timezoneOffset":-480,"year":110,"day":2,"date":2},"remark":"aaa","status":2,"name":"河西幼儿园1","code":"SZHESF15744522","agent":{"id":"40288bd7525e163d01525e2870d10001","createTime":{"nanos":0,"time":1453278733000,"minutes":32,"seconds":13,"hours":16,"month":0,"timezoneOffset":-480,"year":116,"day":3,"date":20},"businessLicense":"SZ100004","remark":"ddddd123","status":2,"name":"测试01","type":1,"legalPerson":"测试人01","mobile":"13632949343"},"legalPerson":"张三","loginAccount":"40288ae845df5fe30145df6522c30004","loginName":"mcms"}
         * order : 0
         * remark : 十点多
         * term : {"id":"4we565wetrhgf8fd","createTime":{"nanos":0,"time":1452925480000,"minutes":24,"seconds":40,"hours":14,"month":0,"timezoneOffset":-480,"year":116,"day":6,"date":16},"school":{"id":"4028880d2f81b335012f81bf1e30000k","createTime":{"nanos":0,"time":1452721950000,"minutes":52,"seconds":30,"hours":5,"month":0,"timezoneOffset":-480,"year":116,"day":4,"date":14},"address":"深圳市宝安区河西四坊157号","regTime":{"nanos":0,"time":1265040000000,"minutes":0,"seconds":0,"hours":0,"month":1,"timezoneOffset":-480,"year":110,"day":2,"date":2},"remark":"aaa","status":2,"name":"河西幼儿园1","code":"SZHESF15744522","agent":{"id":"40288bd7525e163d01525e2870d10001","createTime":{"nanos":0,"time":1453278733000,"minutes":32,"seconds":13,"hours":16,"month":0,"timezoneOffset":-480,"year":116,"day":3,"date":20},"businessLicense":"SZ100004","remark":"ddddd123","status":2,"name":"测试01","type":1,"legalPerson":"测试人01","mobile":"13632949343"},"legalPerson":"张三","loginAccount":"40288ae845df5fe30145df6522c30004","loginName":"mcms"},"order":3,"remark":"到底是范德萨","subject":{"id":"6sd67fdgr3ybfs7t34uhg3","createTime":{"nanos":0,"time":1453443511000,"minutes":18,"seconds":31,"hours":14,"month":0,"timezoneOffset":-480,"year":116,"day":5,"date":22},"order":1,"remark":"打得过","name":"语文","schoolId":"4028880d2f81b335012f81bf1e30000k","totalTermCount":2},"name":"第二学期2"}
         * subject : {"id":"6sd67fdgr3ybfs7t34uhg3","createTime":{"nanos":0,"time":1453443511000,"minutes":18,"seconds":31,"hours":14,"month":0,"timezoneOffset":-480,"year":116,"day":5,"date":22},"order":1,"remark":"打得过","name":"语文","schoolId":"4028880d2f81b335012f81bf1e30000k","totalTermCount":2}
         * name : 上数学课
         */

        private String pk;
        private CreateTimeEntity createTime;
        private SchoolEntity school;
        private int order;
        private String remark;
        private Term term;
        private Subject subject;
        private String name;

        public void setPk(String pk)
        {
            this.pk = pk;
        }

        public void setCreateTime(CreateTimeEntity createTime)
        {
            this.createTime = createTime;
        }

        public void setSchool(SchoolEntity school)
        {
            this.school = school;
        }

        public void setOrder(int order)
        {
            this.order = order;
        }

        public void setRemark(String remark)
        {
            this.remark = remark;
        }

        public void setTerm(Term term)
        {
            this.term = term;
        }

        public void setSubject(Subject subject)
        {
            this.subject = subject;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getPk()
        {
            return pk;
        }

        public CreateTimeEntity getCreateTime()
        {
            return createTime;
        }

        public SchoolEntity getSchool()
        {
            return school;
        }

        public int getOrder()
        {
            return order;
        }

        public String getRemark()
        {
            return remark;
        }

        public Term getTerm()
        {
            return term;
        }

        public Subject getSubject()
        {
            return subject;
        }

        public String getName()
        {
            return name;
        }

        @Override
        public String toString() {
            return "Course{" +
                    "pk='" + pk + '\'' +
                    ", name='" + name + '\'' +
                    ", subject=" + subject +
                    '}';
        }
    }
