package edu.polo.qatar.controladores;

import edu.polo.qatar.entidades.*;
import edu.polo.qatar.servicios.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("selecciones")
public class SeleccionControlador {

    @Autowired
    SeleccionServicio seleccionServicio;

    @GetMapping
    private List<Seleccion> index()
    {
        return seleccionServicio.getAll();
    }

    @GetMapping("/{id}")
    private Seleccion one(@PathVariable("id") Long id)
    {
        return seleccionServicio.getById(id);
    }

    @PostMapping
    private Long save(@RequestBody Seleccion seleccion)
    {
        seleccionServicio.save(seleccion);
        return seleccion.getId();
    }

    @PutMapping
    private Seleccion update(@RequestBody Seleccion seleccion)
    {
        seleccionServicio.save(seleccion);
        return seleccion;
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable("id") Long id)
    {
        Seleccion seleccion = seleccionServicio.getById(id);

        if (seleccion.getJugadores().size() == 0)
            seleccionServicio.delete(id);
    }
    
}