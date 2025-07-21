package cl.patrones.examen.productos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import cl.patrones.examen.productos.service.ProductoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import cl.patrones.examen.productos.domain.Producto;
import cl.patrones.examen.service.DescuentoService;
import cl.patrones.examen.descuentos.ProductoConDescuento;


import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AppController {
	
	private ProductoService productoService;
	private final DescuentoService descuentoService;
	
	public AppController(ProductoService productoService, DescuentoService descuentoService) {
		super();
		this.productoService = productoService;
		this.descuentoService = descuentoService;
	}

	@GetMapping("/")
	String inicio(Model model) {
		var productos = productoService.getProductos();
		
		
		// Verificar si el usuario tiene el rol EMPLEADO
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boolean esEmpleado = auth.getAuthorities().stream()
			.anyMatch(rol -> rol.getAuthority().equals("ROLE_EMPLEADO"));

		// Aplicar descuentos según tipo de usuario
		var productosConDescuento = productos.stream()
			.map(p -> descuentoService.aplicarDescuento(p, esEmpleado))
			.toList();
		
		model.addAttribute("productos", productosConDescuento);
		return "inicio";
	}

}
