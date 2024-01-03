package com.aiqingzhi;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

// 这是一个ATM类，用于模拟ATM操作
public class ATM {
    private ArrayList<Account> account = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);
    private Account loginS1; // 记录登录后的用户

    // 启动AMT系统，展示欢迎界面。
    public void start(){
        label:
        while (true) {
            System.out.println("---欢迎您进入到了ATM系统---");
            System.out.println("1、用户登录");
            System.out.println("2、用户开户");
            System.out.println("3、退出系统");
            System.out.println("请选择操作，输入对应整数数字即可：");
            int tmp = sc.nextInt();
            switch (tmp){
                case 1:
                    login();
                    break;
                case 2:
                    createAccount();
                    break;
                case 3:
                    break label;
                default:
                    System.out.println("没有该操作~~~");
            }
        }
    }
    // 用户登录操作
    private void login(){
        System.out.println("===系统登录===");

        if(account.size() < 1){
            System.out.println("系统没有任何账户，请开户。");
            return;
        }

        while (true) {
            System.out.println("请您输入卡号：");
            String cardId = sc.next();
            Account s1 = getAccountByCardId(cardId);
            if (s1 == null){
                System.out.println("您输入的卡号不存在，请重新输入~");
            }else{
                while (true) {
                    System.out.println("请输入您的密码：");
                    String passWord = sc.next();
                    if (s1.getPassWord().equals(passWord)){
                        loginS1 = s1;
                        System.out.println("欢迎您" + s1.getUserName() + "登录系统。");
                        showUserCommand();
                        return;
                    }else {
                        System.out.println("您输入的密码不对请重新输入~~~");
                    }
                }
            }
        }
    }

    // 展示登录的操作界面
    private void showUserCommand(){
        while (true) {
            System.out.println(loginS1.getUserName() + "您可以选择如下业务办理和账户查询操作。");
            System.out.println("1、查询账户\n2、存款\n3、取款\n4、转账\n5、密码修改\n6、退出\n7、注销当前账户\n请选择：");
            int tmp = sc.nextInt();
            switch (tmp){
                case 1:
                    showLoginAccount();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdrawMoney();
                    break;
                case 4:
                    transferMoney();
                    break;
                case 5:
                    updatePassWord();
                    System.out.println("请重新登录账户！");
                    return;
                case 6:
                    System.out.println(loginS1.getUserName() + "您退出系统成功！");
                    return;
                case 7:
                    if (deleteAccount()){
                        return;
                    }
                    break;
                default:
                    System.out.println("更多功能正在开发，请重新输入您要办理的业务操作~~~");
            }
        }
    }

    // 业务一 查询账户
    private void showLoginAccount(){
        System.out.println(loginS1.getUserName() + "这是您的账户信息如下：");
        System.out.println("卡号：" + loginS1.getCardId());
        System.out.println("户主：" + loginS1.getUserName());
        System.out.println("性别：" + loginS1.getSex());
        System.out.println("余额：" + loginS1.getMoney());
        System.out.println("限额：" + loginS1.getLimit());
    }
    // 业务二 存款
    private void deposit(){
        System.out.println("==存钱业务==");
        System.out.println("请您输入存款金额：");
        double tmp = sc.nextInt();
        loginS1.setMoney(loginS1.getMoney() + tmp);
        System.out.println("存款完成,存款后您的余额为：" + loginS1.getMoney());
    }
    // 业务三 取款
    private void withdrawMoney() {
        while (true) {
            System.out.println("==取款业务==");
            if (loginS1.getMoney() < 50){
                System.out.println("抱歉,您的账户少于50元，请到柜台办理取款。");
                return;
            }
            System.out.println("请输入您要取款的金额：");
            double money = sc.nextDouble();
            if(loginS1.getMoney() > money){

                if (money > loginS1.getLimit()){
                    System.out.println("您当前取款金额超过了每次限额，您每次最多可以取：" + loginS1.getLimit());
                }else {
                    loginS1.setMoney(loginS1.getMoney() - money);
                    System.out.println("本次取款:" + money + "元，取款后您的余额为：" + loginS1.getMoney());
                    break;
                }
            }else {
                System.out.println("您的余额不足" + money + "元，您现在的余额为：" + loginS1.getMoney());
            }
        }
    }
    // 业务四 转账
    private void transferMoney() {
        System.out.println("==用户转账==");
        if (account.size() < 2){
            System.out.println("当前系统只有您一个用户，无法为其它用户转账~~");
            return;
        }
        if (loginS1.getMoney() <= 0){
            System.out.println("您的账户中没有Money，不能转账~~");
            return;
        }

        while (true) {
            System.out.println("请输入对方卡号：");
            String cardId = sc.next();
            Account s1 =  getAccountByCardId(cardId);
            if (s1 == null){
                System.out.println("您输入的账户不存在，请检查是否输错！");
            }else {
                String name = "*" + s1.getUserName().substring(1);
                System.out.println("请您输入" + name + "的姓氏，以完成确认转账业务,请输入：");
                String okName = sc.next();
                if (s1.getUserName().startsWith(okName)){
                    while (true) {
                        System.out.println("请输入您需要转账的金额");
                        double money = sc.nextDouble();
                        if (loginS1.getMoney() >= money){
                            loginS1.setMoney(loginS1.getMoney() - money);
                            s1.setMoney(s1.getMoney() + money);
                            System.out.println("转账成功，本次转账" + money + "元，您的余额剩余：" + loginS1.getMoney());
                            return;
                        }else {
                            System.out.println("对不起，您的余额不足" + money);
                        }
                    }
                }else {
                    System.out.println("对不起，您输入的姓氏有误，可能对方不是您要转账的人。");
                }
            }
        }
    }
    //业务五 销户功能
    private boolean deleteAccount() {
        System.out.println("==销户操作==");
        System.out.println("请问您确认销户吗？yes/no");
        String tmp = sc.next();
        switch (tmp){
            case "yes":
                if (loginS1.getMoney() <= 0){
                    account.remove(loginS1);
                    System.out.println("您好，您的账户已经成功销户~~");
                    return true;
                }else {
                    System.out.println("对不起，您的账户中存有金额，不能销户~~~~");
                    return false;
                }
            default:
                System.out.println("销户取消！");
                return false;
        }
    }
    // 业务六 密码修改
    private void updatePassWord() {
        System.out.println("==账户密码修改业务==");
        while (true) {
            System.out.println("请您输入您当前账户密码：");
            String passWord = sc.next();

            if (loginS1.getPassWord().equals(passWord)){
                System.out.println("请您输入新密码：");
                String newpassWord = sc.next();
                System.out.println("请您确认密码：");
                String okPassWord = sc.next();
                if (newpassWord.equals(okPassWord)){
                    loginS1.setPassWord(newpassWord);
                    System.out.println("密码修改成功！");
                    return;
                }else {
                    System.out.println("两次密码不一致，请重新修改~~~");
                }
            }else {
                System.out.println("您输入的密码不对~~~");
            }
        }
    }

    // 账户开户功能
    private void createAccount(){
        System.out.println("已进入系统开户操作");
        // 创建账户对象
        Account s1 = new Account();

        // 开始录入账户信息
        System.out.println("请输入您的姓名：");
        sc.nextLine();
        String name = sc.nextLine(); // nextLine这东西，上面有一个回车符下面就要消耗掉一个sc.nextLine()
        s1.setUserName(name);

        while (true) {
            System.out.println("请您输入性别：");
            char sex = sc.next().charAt(0);
            if (sex == '男' || sex == '女'){
                s1.setSex(sex);
                break;
            }else {
                System.out.println("别老想着沃尔玛购物袋！请重新输入您的性别");
            }
        }

        while (true) {
            System.out.println("请您输入您的账户密码：");
            String passWord = sc.next();
            System.out.println("请您再次输入您的密码：");
            String okPassWord = sc.next();
            if (okPassWord.equals(passWord)){
                s1.setPassWord(passWord);
                break;
            }else {
                System.out.println("两次密码不一致，请重新输入！");
            }
        }

        System.out.println("请您输入你每次的取限额度：");
        double limit = sc.nextDouble();
        s1.setLimit(limit);

        String newCardId = createCardId();
        s1.setCardId(newCardId);

        account.add(s1);
        System.out.println("恭喜您：" + s1.getUserName() + "开户成功，您的卡号是：" + s1.getCardId());
    }

    // 返回8位数字卡号，且不能重复。
    private String createCardId(){
        while (true) {
            StringBuilder chardId = new StringBuilder();
            Random r = new Random();

            for (int i = 0; i < 8; i++) {
                int tmp = r.nextInt(10);
                chardId.append(tmp);
            }
            Account s1 = getAccountByCardId(chardId.toString());
            if (s1 == null){
                return chardId.toString();
            }
        }
    }

    // 根据卡号找账户对象
    private Account getAccountByCardId(String cardId){
        for (int i = 0; i < account.size(); i++) {
            Account s1 = account.get(i);

            if (s1.getCardId().equals(cardId)){
                return s1;
            }
        }
        return null;
    }
}
