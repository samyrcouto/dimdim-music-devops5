package br.com.dimdim.dto;
import jakarta.validation.constraints.*;
import java.util.UUID;
public record MusicaRequest(@NotBlank @Size(max=160) String titulo, @NotNull @Positive Integer duracaoSegundos, @NotNull UUID albumId) {}
