CREATE TABLE albuns (
    id UNIQUEIDENTIFIER NOT NULL CONSTRAINT pk_albuns PRIMARY KEY,
    titulo NVARCHAR(120) NOT NULL,
    artista NVARCHAR(120) NOT NULL,
    ano_lancamento INT NOT NULL CONSTRAINT ck_albuns_ano CHECK (ano_lancamento BETWEEN 1 AND 2100)
);

CREATE TABLE musicas (
    id UNIQUEIDENTIFIER NOT NULL CONSTRAINT pk_musicas PRIMARY KEY,
    album_id UNIQUEIDENTIFIER NOT NULL,
    titulo NVARCHAR(160) NOT NULL,
    duracao_segundos INT NOT NULL CONSTRAINT ck_musicas_duracao CHECK (duracao_segundos > 0),
    CONSTRAINT fk_musicas_albuns FOREIGN KEY (album_id) REFERENCES albuns(id) ON DELETE CASCADE
);

CREATE INDEX ix_musicas_album_id ON musicas(album_id);
