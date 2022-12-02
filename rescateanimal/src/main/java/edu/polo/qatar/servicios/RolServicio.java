package edu.polo.qatar.servicios;

import edu.polo.qatar.entidades.*;
import edu.polo.qatar.repositorios.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class RolServicio {

    @Autowired
    RolRepositorio rolRepositorio;

    public List<Rol> getAll()
    {
        List<Rol> lista = new ArrayList<Rol>();
        rolRepositorio.findAll().forEach(registro -> lista.add(registro));
        return lista;
    }

    public Rol getById(Long id)
    {
        return rolRepositorio.findById(id).get();
    }

    public void save(Rol rol)
    {
        rolRepositorio.save(rol);
    }

    public void delete(Long id)
    {
        rolRepositorio.deleteById(id);
    }
    
}