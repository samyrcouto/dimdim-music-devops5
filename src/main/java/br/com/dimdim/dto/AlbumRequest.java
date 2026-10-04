package br.com.dimdim.dto;
import jakarta.validation.constraints.*;
public record AlbumRequest(@NotBlank @Size(max=120) String titulo, @NotBlank @Size(max=120) String artista, @NotNull @Min(1) @Max(2100) Integer anoLancamento) {}
