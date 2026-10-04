package br.com.dimdim.controller;
import br.com.dimdim.dto.AlbumRequest; import br.com.dimdim.entity.Album; import br.com.dimdim.service.AlbumService; import jakarta.validation.Valid; import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/albuns") public class AlbumController {
 private final AlbumService service; public AlbumController(AlbumService service){this.service=service;}
 @GetMapping public List<Album> listar(){return service.listar();} @GetMapping("/{id}") public Album buscar(@PathVariable UUID id){return service.buscar(id);}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public Album criar(@Valid @RequestBody AlbumRequest r){return service.criar(r);}
 @PutMapping("/{id}") public Album atualizar(@PathVariable UUID id,@Valid @RequestBody AlbumRequest r){return service.atualizar(id,r);}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void excluir(@PathVariable UUID id){service.excluir(id);}
}
