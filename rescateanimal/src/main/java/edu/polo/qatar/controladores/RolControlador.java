package edu.polo.qatar.controladores;

import edu.polo.qatar.entidades.*;
import edu.polo.qatar.servicios.*;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RestController
@RequestMapping("roles")
public class RolControlador implements WebMvcConfigurer {

	@Autowired
    RolServicio rolServicio;

	@GetMapping
    private ModelAndView index()
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Listado de roles");
        maw.addObject("vista", "roles/index");
        maw.addObject("roles", rolServicio.getAll());
        return maw;
    }

	@GetMapping("/{id}")
    private ModelAndView one(@PathVariable("id") Long id)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Detalle del rol #" + id);
        maw.addObject("vista", "roles/ver");
        maw.addObject("rol", rolServicio.getById(id));
        return maw;
    }

	@GetMapping("/crear")
	public ModelAndView crear(Rol rol)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Crear rol");
        maw.addObject("vista", "roles/crear");
        return maw;
	}

	@PostMapping("/crear")
	public ModelAndView guardar(@Valid Rol rol, BindingResult br, RedirectAttributes ra)
    {
		if ( br.hasErrors() ) {
			return this.crear(rol);
		}

		rolServicio.save(rol);
        
        ModelAndView maw = this.index();
        maw.addObject("exito", "Rol guardado exitosamente");
		return maw;
	}

    @DeleteMapping("/{id}")
    private ModelAndView delete(@PathVariable("id") Long id)
    {
        rolServicio.delete(id);
        ModelAndView maw = this.index();
        maw.addObject("exito", "Rol borrado exitosamente");
		return maw;
    }
    
}