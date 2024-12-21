package com.dette.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "payement")
public class Payement extends AbstractEntity {

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "detteId")
    private Dette dette;

    private Double montant;

    @Override
    public String toString() {
        return "Payement [date=" + date + ", montant=" + montant + "]";
    }
}
