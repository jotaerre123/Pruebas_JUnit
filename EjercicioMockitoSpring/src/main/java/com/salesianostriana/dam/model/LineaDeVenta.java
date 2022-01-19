package com.salesianostriana.dam.model;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LineaDeVenta {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Producto producto;

    private int cantidad;
    private double pvp; // precio unitario

    @ManyToOne
    private Venta venta;

    public LineaDeVenta(Producto producto, int cantidad, double pvp) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.pvp = pvp;
    }

    @Override
    public String toString() {
        return "LineaDeVenta{" +
                "id=" + id +
                ", producto=" + producto +
                ", cantidad=" + cantidad +
                ", pvp=" + pvp +
                '}';
    }
}
