package br.com.dimdim.service;
import br.com.dimdim.dto.AlbumRequest; import br.com.dimdim.entity.Album; import br.com.dimdim.repository.AlbumRepository; import org.springframework.stereotype.Service; import org.springframework.http.HttpStatus; import org.springframework.web.server.ResponseStatusException; import java.util.*;
@Service public class AlbumService {
 private final AlbumRepository repo; public AlbumService(AlbumRepository repo){this.repo=repo;}
 public List<Album> listar(){return repo.findAll();}
 public Album buscar(UUID id){return repo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Álbum não encontrado"));}
 public Album criar(AlbumRequest r){return repo.save(new Album(r.titulo(),r.artista(),r.anoLancamento()));}
 public Album atualizar(UUID id, AlbumRequest r){Album a=buscar(id);a.setTitulo(r.titulo());a.setArtista(r.artista());a.setAnoLancamento(r.anoLancamento());return repo.save(a);}
 public void excluir(UUID id){if(!repo.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Álbum não encontrado");repo.deleteById(id);}
}
