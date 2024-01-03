package com.aiqingzhi;
// 这是一个账户类，用于存放账户
public class Account {
    // 账户类的 成员变量
    private String cardId; // 卡号
    private String userName; // 用户姓名
    private char sex; // 性别
    private String passWord; // 密码
    private double money; // 卡内余额
    private double limit; // 取款限额

    public Account(){} // 无参构造器
    //除卡号，参构造器
    public Account( String userName, char sex, String passWord, double money, double limit) {
        this.userName = userName;
        this.sex = sex;
        this.passWord = passWord;
        this.money = money;
        this.limit = limit;
    }

    public String getCardId() {
        return cardId;
    }

    public Account setCardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    public String getUserName() {
        return userName + (sex == '男' ? "先生" : "女士");
    }

    public Account setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public char getSex() {
        return sex;
    }

    public Account setSex(char sex) {
        this.sex = sex;
        return this;
    }

    public String getPassWord() {
        return passWord;
    }

    public Account setPassWord(String passWord) {
        this.passWord = passWord;
        return this;
    }

    public double getMoney() {
        return money;
    }

    public Account setMoney(double money) {
        this.money = money;
        return this;
    }

    public double getLimit() {
        return limit;
    }

    public Account setLimit(double limit) {
        this.limit = limit;
        return this;
    }
}
