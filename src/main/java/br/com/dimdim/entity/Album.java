package br.com.dimdim.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "albuns")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, length = 120)
    private String titulo;
    @Column(nullable = false, length = 120)
    private String artista;
    @Column(name = "ano_lancamento", nullable = false)
    private Integer anoLancamento;

    public Album() {}
    public Album(String titulo, String artista, Integer anoLancamento) { this.titulo=titulo; this.artista=artista; this.anoLancamento=anoLancamento; }
    public UUID getId(){return id;} public void setId(UUID id){this.id=id;}
    public String getTitulo(){return titulo;} public void setTitulo(String titulo){this.titulo=titulo;}
    public String getArtista(){return artista;} public void setArtista(String artista){this.artista=artista;}
    public Integer getAnoLancamento(){return anoLancamento;} public void setAnoLancamento(Integer anoLancamento){this.anoLancamento=anoLancamento;}
}
