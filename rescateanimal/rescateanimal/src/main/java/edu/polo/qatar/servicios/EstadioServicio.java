package edu.polo.qatar.servicios;

import edu.polo.qatar.entidades.*;
import edu.polo.qatar.repositorios.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class EstadioServicio {

    @Autowired
    EstadioRepositorio estadioRepositorio;

    public List<Estadio> getAll()
    {
        List<Estadio> lista = new ArrayList<Estadio>();
        estadioRepositorio.findAll().forEach(registro -> lista.add(registro));
        return lista;
    }

    public Estadio getById(Long id)
    {
        return estadioRepositorio.findById(id).get();
    }

    public void save(Estadio estadio)
    {
        estadioRepositorio.save(estadio);
    }

    public void delete(Long id)
    {
        estadioRepositorio.deleteById(id);
    }
    
}