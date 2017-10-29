package cz.fi.muni.legomanager.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Stepan Granat
 */
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable=false,unique=true)
    private String name;


    @NotNull
    @Column(nullable=false)
    private String description;

    @ManyToMany
    private Set<Kit> kits = new HashSet<>();

    @ManyToMany
    private Set<SetOfKits> setsOfKits = new HashSet<>();

    public Category() {
    }

    public Category(String name, String description) {
        this.setName(name);
        this.setDescription(description);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Kit> getKits() {
        return Collections.unmodifiableSet(kits);
    }

    public void addKit(Kit kit) {
        kits.add(kit);
        kit.addCategory(this);
    }

    public void removeKit(Kit kit) {
        kits.remove(kit);
        kit.removeCategory(this);
    }

    public Set<SetOfKits> getSetsOfKits() {
        return Collections.unmodifiableSet(setsOfKits);
    }

    public void addSetOfKits(SetOfKits setOfKits) {
        setsOfKits.add(setOfKits);
        setOfKits.addCategory(this);
    }

    public void removeSetOfKits(SetOfKits setOfKits) {
        setsOfKits.remove(setOfKits);
        setOfKits.removeCategory(this);
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (! (obj instanceof Category)) {
            return false;
        }
        Category other = (Category) obj;
        if (name == null) {
            if (other.getName() != null) {
                return false;
            }
        } else if (!name.equals(other.getName())) {
            return false;
        }
        return true;
    }



}
