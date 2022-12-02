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
@RequestMapping("jugadores")
public class JugadorControlador implements WebMvcConfigurer {

	@Autowired
    JugadorServicio jugadorServicio;

    @Autowired
    SeleccionServicio seleccionServicio;
    
    @Autowired
    PaisServicio paisServicio;

	@GetMapping
    private ModelAndView index()
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Listado de jugadores");
        maw.addObject("vista", "jugadores/index");
        maw.addObject("jugadores", jugadorServicio.getAll());
        return maw;
    }

	@GetMapping("/{id}")
    private ModelAndView one(@PathVariable("id") Long id)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Detalle del jugador #" + id);
        maw.addObject("vista", "jugadores/ver");
        maw.addObject("jugador", jugadorServicio.getById(id));
        return maw;
    }

	@GetMapping("/crear")
	public ModelAndView crear(Jugador jugador)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Crear jugador");
        maw.addObject("vista", "jugadores/crear");
        maw.addObject("paises", paisServicio.getAll());
        maw.addObject("selecciones", seleccionServicio.getAll());
        return maw;
	}

	@PostMapping("/crear")
	public ModelAndView guardar(@RequestParam("archivo") MultipartFile archivo, @Valid Jugador jugador, BindingResult br, RedirectAttributes ra)
    {
        if ( archivo.isEmpty() )
			br.reject("archivo", "Por favor, cargue una imagen"); 

		if ( br.hasErrors() ) {
			return this.crear(jugador);
		}

		jugadorServicio.save(jugador);

        String tipo = archivo.getContentType();
        String extension = "." + tipo.substring(tipo.indexOf('/') + 1, tipo.length());
        String foto = jugador.getId() + extension;
        String path = Paths.get("src/main/resources/static/images/jugadores", foto).toAbsolutePath().toString();
        ModelAndView maw = this.index();

        try {
            archivo.transferTo( new File(path) );
        } catch (Exception e) {
            maw.addObject("error", "No se pudo guardar la imagen");
            return maw;
        }

        jugador.setFoto(foto);
        jugadorServicio.save(jugador);
        maw.addObject("exito", "Jugador guardado exitosamente");
		return maw;
	}

	@GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id, Jugador jugador)
    {
        return this.editar(id, jugador, true);
    }

    public ModelAndView editar(@PathVariable("id") Long id, Jugador jugador, boolean estaPersistido)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Editar jugador");
        maw.addObject("vista", "jugadores/editar");
        maw.addObject("paises", paisServicio.getAll());
        maw.addObject("selecciones", seleccionServicio.getAll());

        if (estaPersistido)
            maw.addObject("jugador", jugadorServicio.getById(id));
        else
            jugador.setFoto( jugadorServicio.getById(id).getFoto() );

        return maw;
    }

    @PutMapping("/editar/{id}")
    private ModelAndView update(@PathVariable("id") Long id,
    @RequestParam(value = "archivo", required = false) MultipartFile archivo,
    @Valid Jugador jugador, BindingResult br, RedirectAttributes ra)
    {
        if ( br.hasErrors() ) {
			return this.editar(id, jugador, false);
		}

        Jugador registro = jugadorServicio.getById(id);
        registro.setNombre( jugador.getNombre() );
        registro.setFechaNacimiento( jugador.getFechaNacimiento() );
        registro.setSeleccion( jugador.getSeleccion() );
        ModelAndView maw = this.index();

        if ( ! archivo.isEmpty() ) {
            String tipo = archivo.getContentType();
            String extension = "." + tipo.substring(tipo.indexOf('/') + 1, tipo.length());
            String foto = jugador.getId() + extension;
            String path = Paths.get("src/main/resources/static/images/jugadores", foto).toAbsolutePath().toString();

            try {
                archivo.transferTo( new File(path) );
            } catch (Exception e) {
                maw.addObject("error", "No se pudo guardar la imagen");
                return maw;
            }

            registro.setFoto(foto);
        }

        jugadorServicio.save(registro);
        maw.addObject("exito", "Jugador editado exitosamente");
		return maw;
    }

    @DeleteMapping("/{id}")
    private ModelAndView delete(@PathVariable("id") Long id)
    {
        jugadorServicio.delete(id);
        ModelAndView maw = this.index();
        maw.addObject("exito", "Jugador borrado exitosamente");
		return maw;
    }
    
}