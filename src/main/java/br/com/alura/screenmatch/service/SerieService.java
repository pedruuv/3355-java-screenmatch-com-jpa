package br.com.alura.screenmatch.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.screenmatch.dto.EpisodioDto;
import br.com.alura.screenmatch.dto.SerieDto;
import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;

@Service
public class SerieService {
    @Autowired
    private SerieRepository repository;

    public List<SerieDto> obterSeries(){
        List<SerieDto> series = convertData(repository.findAll());

        return series;
    }

    public List<SerieDto> obterTop5Series(){
        List<SerieDto> top5Series = convertData(repository.findTop5ByOrderByAvaliacaoDesc());

        return top5Series;
    }

    public List<SerieDto> obterLancamentos(){
        List<SerieDto> lancamento = convertData(repository.getMostRecentEpisodes());

        return lancamento;
    }

    public SerieDto obterSeriePorId(Long id){
        var serie = repository.findById(id);

        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDto(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse());
        } else{
            return null;
        }

    }

    private List<SerieDto> convertData(List<Serie> series){
        return series.stream().map(s -> new SerieDto(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse())).collect(Collectors.toList());
    }

    public List<EpisodioDto> obterTodasTemporadas(Long id) {
        var serie = repository.findById(id);

        if (serie.isPresent()) {
            Serie s = serie.get();
            return s.getEpisodios().stream().map(e -> new EpisodioDto(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio())).collect(Collectors.toList());
        } else{
            return null;
        }
    }

    public List<EpisodioDto> obterTemporadaPorNumero(Long id, Integer numTemporada) {
        List<EpisodioDto> episodios = repository.getEpisodesBySeason(id, numTemporada).stream().map(e -> new EpisodioDto(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio())).collect(Collectors.toList());

        return episodios;
    }

    public List<SerieDto> obterSeriesPorGenero(String genero) {
        Categoria categoria = Categoria.fromPortugues(genero);

        List<SerieDto> series = convertData(repository.findByGenero(categoria));
        return series;
    }
}
