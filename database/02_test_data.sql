DECLARE @AlbumId UNIQUEIDENTIFIER = NEWID();
INSERT INTO albuns (id, titulo, artista, ano_lancamento)
VALUES (@AlbumId, 'Kind of Blue', 'Miles Davis', 1959);
INSERT INTO musicas (id, album_id, titulo, duracao_segundos) VALUES
(NEWID(), @AlbumId, 'So What', 545),
(NEWID(), @AlbumId, 'Freddie Freeloader', 589);

SELECT a.id, a.titulo, a.artista, a.ano_lancamento,
       m.id AS musica_id, m.titulo AS musica, m.duracao_segundos
FROM albuns a LEFT JOIN musicas m ON m.album_id = a.id ORDER BY a.titulo;
