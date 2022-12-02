package edu.polo.qatar.servicios;

import edu.polo.qatar.entidades.*;
import edu.polo.qatar.repositorios.*;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class PaisServicio {

    @Autowired
    PaisRepositorio paisRepositorio;

    public List<Pais> getAll()
    {
        List<Pais> lista = new ArrayList<>();
        try {
            paisRepositorio.findAll().forEach(lista::add);
            
            return lista;
            
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return lista;
    }

    public Pais getById(Long id)
    {
        try {        
            Pais getpais = paisRepositorio.findById(id).get();
            
            if (getpais == null){
                return null;
            }
            return getpais;

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public void save(Pais pais)
    {
        try {
            paisRepositorio.save(pais);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void delete(Long id)
    {
        try {
            paisRepositorio.deleteById(id);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
}