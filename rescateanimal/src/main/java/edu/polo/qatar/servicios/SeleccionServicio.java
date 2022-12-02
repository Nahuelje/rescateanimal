package edu.polo.qatar.servicios;

import edu.polo.qatar.entidades.*;
import edu.polo.qatar.repositorios.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class SeleccionServicio {

    @Autowired
    SeleccionRepositorio seleccionRepositorio;

    public List<Seleccion> getAll()
    {
        List<Seleccion> lista = new ArrayList<Seleccion>();
        seleccionRepositorio.findAll().forEach(registro -> lista.add(registro));
        return lista;
    }

    public Seleccion getById(Long id)
    {
        return seleccionRepositorio.findById(id).get();
    }

    public void save(Seleccion seleccion)
    {
        seleccionRepositorio.save(seleccion);
    }

    public void delete(Long id)
    {
        seleccionRepositorio.deleteById(id);
    }
    
}