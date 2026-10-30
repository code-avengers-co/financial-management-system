package web.financialmanagement.model;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "category")
@DynamicUpdate
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="category_generator", sequenceName="category_id_seq", allocationSize=1)
    @GeneratedValue(generator="category_generator", strategy=GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private boolean active;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
