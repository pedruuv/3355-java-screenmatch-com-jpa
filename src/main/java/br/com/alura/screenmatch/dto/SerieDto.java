package br.com.alura.screenmatch.dto;

import br.com.alura.screenmatch.model.Categoria;

public record SerieDto(long id, String titulo, Integer totalTemporadas, Double avaliacao,
        Categoria genero,
        String atores,
        String poster,
        String sinopse) {

}
