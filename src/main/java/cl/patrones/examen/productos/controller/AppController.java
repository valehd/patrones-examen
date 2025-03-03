package cl.patrones.examen.productos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import cl.patrones.examen.productos.service.ProductoService;

@Controller
public class AppController {
	
	private ProductoService productoService;
	
	public AppController(ProductoService productoService) {
		super();
		this.productoService = productoService;
	}

	@GetMapping("/")
	String inicio(Model model) {
		var productos = productoService.getProductos();
		model.addAttribute("productos", productos);
		return "inicio";
	}

}
