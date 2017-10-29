package cz.fi.muni.legomanager.entity;

import cz.fi.muni.legomanager.enums.Color;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class for representing bricks.
 *
 * @author Lukáš Dvořák
 */
@Entity
public class Brick {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Color color;

    @NotNull
    @ManyToOne
    private Shape shape;

    @ManyToMany(mappedBy = "bricks")
    private Set<Kit> kits = new HashSet<>();

    public Long getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Set<Kit> getKits() {
        return Collections.unmodifiableSet(kits);
    }

    public void addKit(Kit kit) {
        this.kits.add(kit);
    }

    public void removeKit(Kit kit) {
        this.kits.remove(kit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Brick)) {
            return false;
        }

        Brick other = (Brick) obj;
        return this.getId() != null && Objects.equals(id, other.id) &&
                color == other.color && Objects.equals(shape, other.shape);

    }

    @Override
    public int hashCode() {
        final int prime = 59;
        int result = 3;
        result = prime * result +
                (color == null ? 0 : color.hashCode()) +
                (shape == null ? 0 : shape.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Brick{" +
                "id=" + id +
                ", color=" + color +
                ", shape=" + shape +
                '}';
    }

}
