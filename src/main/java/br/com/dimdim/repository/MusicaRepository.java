package br.com.dimdim.repository;
import br.com.dimdim.entity.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;
public interface MusicaRepository extends JpaRepository<Musica, UUID> { List<Musica> findByAlbumId(UUID albumId); }
