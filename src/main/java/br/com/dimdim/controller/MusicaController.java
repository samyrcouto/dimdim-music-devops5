package br.com.dimdim.controller;
import br.com.dimdim.dto.MusicaRequest; import br.com.dimdim.entity.Musica; import br.com.dimdim.service.MusicaService; import jakarta.validation.Valid; import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/musicas") public class MusicaController {
 private final MusicaService service; public MusicaController(MusicaService service){this.service=service;}
 @GetMapping public List<Musica> listar(@RequestParam(required=false) UUID albumId){return albumId==null?service.listar():service.listarPorAlbum(albumId);}
 @GetMapping("/{id}") public Musica buscar(@PathVariable UUID id){return service.buscar(id);} @PostMapping @ResponseStatus(HttpStatus.CREATED) public Musica criar(@Valid @RequestBody MusicaRequest r){return service.criar(r);}
 @PutMapping("/{id}") public Musica atualizar(@PathVariable UUID id,@Valid @RequestBody MusicaRequest r){return service.atualizar(id,r);} @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void excluir(@PathVariable UUID id){service.excluir(id);}
}
