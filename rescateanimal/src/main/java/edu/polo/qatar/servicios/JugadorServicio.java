package edu.polo.qatar.servicios;

import edu.polo.qatar.entidades.*;
import edu.polo.qatar.repositorios.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class JugadorServicio {

    @Autowired
    JugadorRepositorio jugadorRepositorio;

    public List<Jugador> getAll()
    {
        List<Jugador> lista = new ArrayList<Jugador>();
        lista = (ArrayList<Jugador>) jugadorRepositorio.findAll();
        return lista;
    }

    public Jugador getById(Long id)
    {
        return jugadorRepositorio.findById(id).get();
    }

    public void save(Jugador jugador)
    {
        jugadorRepositorio.save(jugador);
    }

    public void delete(Long id)
    {
        jugadorRepositorio.deleteById(id);
    }
    
}