package br.com.dimdim.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "musicas")
public class Musica {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, length = 160)
    private String titulo;
    @Column(nullable = false)
    private Integer duracaoSegundos;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "album_id", nullable = false, foreignKey = @ForeignKey(name = "fk_musicas_albuns"))
    @JsonIgnore
    private Album album;

    public Musica() {}
    public UUID getId(){return id;} public void setId(UUID id){this.id=id;}
    public String getTitulo(){return titulo;} public void setTitulo(String titulo){this.titulo=titulo;}
    public Integer getDuracaoSegundos(){return duracaoSegundos;} public void setDuracaoSegundos(Integer d){this.duracaoSegundos=d;}
    public Album getAlbum(){return album;} public void setAlbum(Album album){this.album=album;}
}
