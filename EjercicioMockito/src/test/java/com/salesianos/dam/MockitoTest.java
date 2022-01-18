package com.salesianos.dam;

import com.salesianostriana.dam.ejerciciotesting.model.Cliente;
import com.salesianostriana.dam.ejerciciotesting.model.LineaDeVenta;
import com.salesianostriana.dam.ejerciciotesting.model.Producto;
import com.salesianostriana.dam.ejerciciotesting.model.Venta;
import com.salesianostriana.dam.ejerciciotesting.repos.ProductoRepositorio;
import com.salesianostriana.dam.ejerciciotesting.repos.VentaRepositorio;
import com.salesianostriana.dam.ejerciciotesting.services.VentaServicio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class MockitoTest {

    @Mock
    VentaRepositorio ventaRepositorio;

    @Mock
    ProductoRepositorio productoRepositorio;

    @InjectMocks
    VentaServicio ventaServicio;

    //Caja Negra
    @Test
    public void whenNuevaVenta_thenReturnVenta(){
        Cliente c = new Cliente();
        LineaDeVenta lineaDeVenta = new LineaDeVenta(new Producto("2", "Ordenador de sobremesa", 499.0), 5, 499.0);
        List<LineaDeVenta> lista = new ArrayList<>();
        lista.add(lineaDeVenta);
        Venta venta1 = new Venta(1L, LocalDate.of(2022, 01, 18), lista, c);
        //He intentado poner 2L, pero no se puede crear una venta con id2 cuando no hay con id1 :/
        lenient().when(productoRepositorio.findOne("2")).thenReturn(new Producto("2", "Ordenador de sobremesa", 499.0));
        lenient().when(ventaRepositorio.save(venta1)).thenReturn(venta1);
        Venta venta2 = ventaServicio.nuevaVenta(Map.of("2", 10), c);
        assertEquals(venta2.getId(), venta1.getId());
    }


    //Caja Blanca
    @Test
    public void whenAddProductoToVenta_thenReturnVenta(){
        Cliente c = new Cliente();
        LineaDeVenta lnv1 = new LineaDeVenta(new Producto("3", "Teclado mecánico retroiluminado", 59.99), 5, 59.99);
        LineaDeVenta lnv2 = new LineaDeVenta(new Producto("4", "Ratón 3 botones", 19.99), 5, 19.99);
        List<LineaDeVenta> lista = new ArrayList<>();
        lista.add(lnv1);
        lista.add(lnv2);
        Optional<Venta> optionalVenta = Optional.of(new Venta(1L, LocalDate.of(2022, 01, 18), lista, c));
        Venta venta1 = new Venta(1L, LocalDate.of(2022, 01, 18), lista, c);
        lenient().when(ventaRepositorio.findOneOptional(1L)).thenReturn(optionalVenta);
        lenient().when(productoRepositorio.findOne("4")).thenReturn(new Producto("4", "Ratón 3 botones", 19.99));
        lenient().when(ventaRepositorio.edit(optionalVenta.get())).thenReturn(venta1);
        Venta venta2 = ventaServicio.addProductoToVenta(1L, "4", 10);
        assertEquals(venta1, venta2);
    }

    //Caja Blanca
    @Test
    public void whenRemoveLineaVenta_thenReturnVenta(){
        Cliente c = new Cliente();
        List<LineaDeVenta> lista = new ArrayList<>();
        LineaDeVenta lnv1 = new LineaDeVenta(new Producto("3", "Teclado mecánico retroiluminado", 59.99), 5, 59.99);
        LineaDeVenta lnv2 = new LineaDeVenta(new Producto("4", "Ratón 3 botones", 19.99), 5, 19.99);
        lista.add(lnv1);
        lista.add(lnv2);
        Optional<Venta> optionalVenta = Optional.of(new Venta(1L, LocalDate.of(2022, 01, 18), lista, c));
        lista.remove(lnv1);
        Venta venta1 = new Venta(1L, LocalDate.of(2022, 01, 18), lista, c);
        lenient().when(ventaRepositorio.findOneOptional(1L)).thenReturn(optionalVenta);
        lenient().when(ventaRepositorio.edit(optionalVenta.get())).thenReturn(venta1);
        Venta venta2 = ventaServicio.removeLineaVenta(1L, "4");
        assertEquals(venta1, venta2);
    }





}
