package br.com.dimdim.repository;
import br.com.dimdim.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
public interface AlbumRepository extends JpaRepository<Album, UUID> {}
