package edu.polo.qatar.controladores;

import edu.polo.qatar.entidades.*;
import edu.polo.qatar.servicios.*;
import java.io.*;
import java.nio.file.Paths;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RestController
@RequestMapping("estadios")
public class EstadioControlador implements WebMvcConfigurer {

	@Autowired
    EstadioServicio estadioServicio;

	@GetMapping
    private ModelAndView index()
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Listado de estadios");
        maw.addObject("vista", "estadios/index");
        maw.addObject("estadios", estadioServicio.getAll());
        return maw;
    }

	@GetMapping("/{id}")
    private ModelAndView one(@PathVariable("id") Long id)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Detalle del estadio #" + id);
        maw.addObject("vista", "estadios/ver");
        maw.addObject("estadios", estadioServicio.getById(id));
        return maw;
    }

	@GetMapping("/crear")
	public ModelAndView crear(Estadio estadio)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Crear estadio");
        maw.addObject("vista", "estadios/crear");
        return maw;
	}

	@PostMapping("/crear")
	public ModelAndView guardar(@RequestParam("archivo") MultipartFile archivo, @Valid Estadio estadio, BindingResult br, RedirectAttributes ra)
    {
        if ( archivo.isEmpty() )
			br.reject("archivo", "Por favor, cargue una imagen"); 

		if ( br.hasErrors() ) {
			return this.crear(estadio);
		}

		estadioServicio.save(estadio);

        String tipo = archivo.getContentType();
        String extension = "." + tipo.substring(tipo.indexOf('/') + 1, tipo.length());
        String foto = estadio.getId() + extension;
        String path = Paths.get("src/main/resources/static/images/estadios", foto).toAbsolutePath().toString();
        ModelAndView maw = this.index();

        try {
            archivo.transferTo( new File(path) );
        } catch (Exception e) {
            maw.addObject("error", "No se pudo guardar la imagen");
            return maw;
        }

        estadio.setFoto(foto);
        estadioServicio.save(estadio);
        maw.addObject("exito", "Estadio guardado exitosamente");
		return maw;
	}

	@GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id, Estadio estadio)
    {
        return this.editar(id, estadio, true);
    }

    public ModelAndView editar(@PathVariable("id") Long id, Estadio estadio, boolean estaPersistido)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Editar estadio");
        maw.addObject("vista", "estadios/editar");

        if (estaPersistido)
            maw.addObject("estadio", estadioServicio.getById(id));
        else
            estadio.setFoto( estadioServicio.getById(id).getFoto() );

        return maw;
    }

    @PutMapping("/editar/{id}")
    private ModelAndView update(@PathVariable("id") Long id,
    @RequestParam(value = "archivo", required = false) MultipartFile archivo,
    @Valid Estadio estadio, BindingResult br, RedirectAttributes ra)
    {
        if ( br.hasErrors() ) {
			return this.editar(id, estadio, false);
		}

        Estadio registro = estadioServicio.getById(id);
        registro.setNombre( estadio.getNombre() );
        registro.setCapacidad(estadio.getCapacidad());
        registro.setCiudad(estadio.getCiudad());
        ModelAndView maw = this.index();

        if ( ! archivo.isEmpty() ) {
            String tipo = archivo.getContentType();
            String extension = "." + tipo.substring(tipo.indexOf('/') + 1, tipo.length());
            String foto = estadio.getId() + extension;
            String path = Paths.get("src/main/resources/static/images/estadios", foto).toAbsolutePath().toString();

            try {
                archivo.transferTo( new File(path) );
            } catch (Exception e) {
                maw.addObject("error", "No se pudo guardar la imagen");
                return maw;
            }

            registro.setFoto(foto);
        }

        estadioServicio.save(registro);
        maw.addObject("exito", "Estadio editado exitosamente");
		return maw;
    }

    @DeleteMapping("/{id}")
    private ModelAndView delete(@PathVariable("id") Long id)
    {
        estadioServicio.delete(id);
        ModelAndView maw = this.index();
        maw.addObject("exito", "Estadio borrado exitosamente");
		return maw;
    }
    
}