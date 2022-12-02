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
@RequestMapping("paises")
public class PaisControlador implements WebMvcConfigurer {

	@Autowired
    PaisServicio paisServicio;

    @Autowired
    EmailServicio emailServicio;

	@GetMapping
    public ModelAndView index()
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Listado de países");
        maw.addObject("vista", "paises/index");
        maw.addObject("paises", paisServicio.getAll());
        return maw;
    }

	@GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Long id)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Detalle del país #" + id);
        maw.addObject("vista", "paises/ver");
        maw.addObject("pais", paisServicio.getById(id));
        return maw;
    }

	@GetMapping("/crear")
	public ModelAndView crear(Pais pais)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Crear país");
        maw.addObject("vista", "paises/crear");
        maw.addObject("pais", pais);
        return maw;
	}

	@PostMapping("/crear")
	public ModelAndView guardar(@Valid Pais pais, BindingResult br, RedirectAttributes ra)
    {
		if ( br.hasErrors() ) {
			return this.crear(pais);
		}

		paisServicio.save(pais);

        ModelAndView maw = this.index();
        maw.addObject("exito", "pais guardado exitosamente");
		return maw;
	}

	@GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id, Pais pais)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Editar pais");
        maw.addObject("vista", "paises/editar");

        maw.addObject("pais", paisServicio.getById(id));

        return maw;
    }

    @PutMapping("/editar/{id}")
    public ModelAndView update(@PathVariable("id") Long id, @Valid Pais pais, BindingResult br, RedirectAttributes ra)
    {
        if ( br.hasErrors() ) {
            ModelAndView maw = new ModelAndView();
            maw.setViewName("fragments/base");
            maw.addObject("titulo", "Editar pais");
            maw.addObject("vista", "paises/editar");
            maw.addObject("pais", pais);
			return maw;
		}

        Pais registro = paisServicio.getById(id);
        registro.setPaisNombre(pais.getPaisNombre());
        registro.setPcc(pais.getPcc());
        ModelAndView maw = this.index();

        paisServicio.save(registro);
        maw.addObject("exito", "pais editado exitosamente");
		return maw;
    }

    @DeleteMapping("/{id}")
    public ModelAndView delete(@PathVariable("id") Long id)
    {
        paisServicio.delete(id);
        ModelAndView maw = this.index();
        maw.addObject("exito", "pais borrado exitosamente");
		return maw;
    }
    
}