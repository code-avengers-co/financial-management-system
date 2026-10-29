package web.financialmanagement.model;

import java.io.Serializable;
import java.math.BigDecimal;
import org.hibernate.annotations.DynamicUpdate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity 
@Table(name = "budget")
@DynamicUpdate 
public class Budget implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="budget_generator", sequenceName="budget_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "limit_amount", precision = 20, scale = 2)
    private BigDecimal limitAmount;

    @Column(name = "budget_month")
    private int budgetMonth;

    @Column(name = "budget_year")
    private int budgetYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category")
    private Category category;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }

    public BigDecimal getLimitAmount() {
        return this.limitAmount;
    }

    public void setBudgetMonth(int budgetMonth) {
        this.budgetMonth = budgetMonth;
    }

    public int getBudgetMonth() {
        return this.budgetMonth;
    }

    public void setBudgetYear(int budgetYear) {
        this.budgetYear = budgetYear;
    }

    public int getBudgetYear() {
        return this.budgetYear;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return this.category;
    }

    @Override
    public String toString() {
        return "Budget [id=" + id + ", limitAmount=" + limitAmount + ", budgetMonth=" + budgetMonth
                + ", budgetYear=" + budgetYear + ", user=" + user + ", category=" + category + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((limitAmount == null) ? 0 : limitAmount.hashCode());
        result = prime * result + budgetMonth;
        result = prime * result + budgetYear;
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Budget other = (Budget) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (limitAmount == null) {
            if (other.limitAmount != null)
                return false;
        } else if (!limitAmount.equals(other.limitAmount))
            return false;
        if (budgetMonth != other.budgetMonth)
            return false;
        if (budgetYear != other.budgetYear)
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        if (category == null) {
            if (other.category != null)
                return false;
        } else if (!category.equals(other.category))
            return false;
        return true;
    }

    
}
