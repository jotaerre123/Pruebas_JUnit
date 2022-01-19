package com.salesianostriana.dam;

import com.salesianostriana.dam.model.Cliente;
import com.salesianostriana.dam.model.LineaDeVenta;
import com.salesianostriana.dam.model.Producto;
import com.salesianostriana.dam.model.Venta;
import com.salesianostriana.dam.repos.ProductoRepositorio;
import com.salesianostriana.dam.repos.VentaRepositorio;
import com.salesianostriana.dam.service.VentaServicio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;


@ExtendWith(SpringExtension.class)
class DemoApplicationTests {

	@Mock
	ProductoRepositorio productoRepositorio;

	@Mock
	VentaRepositorio ventaRepositorio;

	@InjectMocks
	VentaServicio ventaServicio;

	//Caja Negra
	@Test
	void whenNueva_success(){

		Producto p = Producto.builder()
				.id(1L)
				.codigoProducto("1")
				.nombre("Bola de Dragón 4 Estrellas")
				.precio(12.34)
				.build();
		Cliente c = Cliente.builder()
				.nombre("pepe")
				.email("pepe@gmail.com")
				.dni("12345678R")
				.build();


		lenient().when(productoRepositorio.findById(1L)).thenReturn(java.util.Optional.ofNullable(p));


		Map<Long, Integer> carrito = Map.of(1L, 2);

		Venta venta = Venta.builder()
				.cliente(c)
				.lineasDeVenta(List.of(new LineaDeVenta( p, 2, 12.34)))
				.build();
		Venta venta2 = ventaServicio.nuevaVenta(carrito, c);

		lenient().when(ventaRepositorio.save(venta)).thenReturn(venta);
		assertThat(venta.equals(venta2));

	}

//Caja Blanca
	@Test
	void whenAddProductoToVenta_success(){
		Cliente c = Cliente.builder()
				.nombre("pepe")
				.email("pepe@gmail.com")
				.dni("12345678R")
				.build();
		LineaDeVenta lnv1 = new LineaDeVenta(new Producto(1L,"3", "Teclado mecánico retroiluminado", 59.99), 5, 59.99);
		LineaDeVenta lnv2 = new LineaDeVenta(new Producto(2L,"4", "Ratón 3 botones", 19.99), 5, 19.99);
		List<LineaDeVenta> lista = new ArrayList<>();
		lista.add(lnv1);
		Optional<Venta> optionalVenta = Optional.of(new Venta(3L, LocalDate.of(2022, 01, 19), lista, c));
		lista.add(lnv2);
		Venta venta1 = new Venta(3L, LocalDate.of(2022, 01, 19), lista, c);
		lenient().when(ventaRepositorio.findById(1L)).thenReturn(optionalVenta);
		lenient().when(productoRepositorio.findById(2L)).thenReturn(Optional.of(new Producto(2L, "4", "Ratón 3 botones", 19.99)));
		lenient().when(ventaRepositorio.save(optionalVenta.get())).thenReturn(venta1);
		Venta venta2 = ventaServicio.addProductoToVenta(1L, 2L, 10);
		assertThat(venta1.equals(venta2));
	}

//Caja Blanca
	@Test
	void whenRemoveLineaVenta(){
		Cliente c = Cliente.builder()
				.nombre("pepe")
				.email("pepe@gmail.com")
				.dni("12345678R")
				.build();
		List<LineaDeVenta> lista = new ArrayList<>();
		LineaDeVenta lnv1 = new LineaDeVenta(new Producto(1L, "3", "Teclado mecánico retroiluminado", 59.99), 5, 59.99);
		LineaDeVenta lnv2 = new LineaDeVenta(new Producto(2L, "4", "Ratón 3 botones", 19.99), 5, 19.99);
		lista.add(lnv1);
		lista.add(lnv2);
		Optional<Venta> optionalVenta = Optional.of(new Venta(1L, LocalDate.of(2022, 01, 19), lista, c));
		lista.remove(lnv1);
		Venta venta1 = new Venta(1L, LocalDate.of(2022, 01, 19), lista, c);
		lenient().when(ventaRepositorio.findById(1L)).thenReturn(optionalVenta);
		lenient().when(ventaRepositorio.save(optionalVenta.get())).thenReturn(venta1);
		Venta venta2 = ventaServicio.removeLineaVenta(1L, 2L);
		assertThat(venta1.equals(venta2));
	}



}
