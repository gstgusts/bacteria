package com.example.bacteria.data;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="bacteria_limitation")
@IdClass(CategoryBacteriaId.class)
public class Limitation {

    @Id
    @ManyToOne(targetEntity = Category.class, fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @Id
    @ManyToOne(targetEntity = Bacteria.class, fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "bacteria_id", referencedColumnName = "id")
    private Bacteria bacteria;

    @Column(name = "limitation")
    private int limitation;
}
