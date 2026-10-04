SELECT * FROM albuns ORDER BY ano_lancamento DESC;
SELECT * FROM musicas ORDER BY titulo;
SELECT a.id, a.titulo, a.artista, COUNT(m.id) AS quantidade_musicas
FROM albuns a LEFT JOIN musicas m ON m.album_id = a.id
GROUP BY a.id, a.titulo, a.artista;
