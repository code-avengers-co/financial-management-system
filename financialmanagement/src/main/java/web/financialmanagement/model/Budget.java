package web.financialmanagement.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Budget implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id;
    private BigDecimal limitAmout;
    private int buggetMonth;
    private int buggetYear;
    private User user;
    private Category category;

    private void setId(Long id){
        this.id = id;
    }

    private Long getId(){
        return id;
    }

    private void setLimitAmout(BigDecimal limitAmout){
        this.limitAmout = limitAmout;
    }

    private BigDecimal getLimitAmout(){
        return limitAmout;
    }

    private void setBuggetMonth(int buggetMonth){
        this.buggetMonth = buggetMonth;
    }

    private int getBuggetMonth(){
        return buggetMonth;
    }

    private void setBuggetYear(int buggetYear){
        this.buggetYear = buggetYear;
    }

    private int getBuggetYear(){
        return buggetYear;
    }

    private void setUser(User user){
        this.user = user;
    }

    private User getUser(){
        return user;
    }

    private void setCategory(Category category){
        this.category = category;
    }

    private Category getCategory(){
        return category;
    }

}
