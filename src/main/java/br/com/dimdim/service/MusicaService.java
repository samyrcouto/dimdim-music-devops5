package br.com.dimdim.service;
import br.com.dimdim.dto.MusicaRequest; import br.com.dimdim.entity.*; import br.com.dimdim.repository.*; import org.springframework.stereotype.Service; import org.springframework.http.HttpStatus; import org.springframework.web.server.ResponseStatusException; import java.util.*;
@Service public class MusicaService {
 private final MusicaRepository repo; private final AlbumRepository albums; public MusicaService(MusicaRepository repo,AlbumRepository albums){this.repo=repo;this.albums=albums;}
 public List<Musica> listar(){return repo.findAll();} public List<Musica> listarPorAlbum(UUID id){if(!albums.existsById(id))throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Álbum não encontrado");return repo.findByAlbumId(id);}
 public Musica buscar(UUID id){return repo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Música não encontrada"));}
 public Musica criar(MusicaRequest r){Musica m=new Musica();m.setTitulo(r.titulo());m.setDuracaoSegundos(r.duracaoSegundos());m.setAlbum(album(r.albumId()));return repo.save(m);}
 public Musica atualizar(UUID id,MusicaRequest r){Musica m=buscar(id);m.setTitulo(r.titulo());m.setDuracaoSegundos(r.duracaoSegundos());m.setAlbum(album(r.albumId()));return repo.save(m);}
 public void excluir(UUID id){if(!repo.existsById(id))throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Música não encontrada");repo.deleteById(id);}
 private Album album(UUID id){return albums.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Álbum informado não existe"));}
}
