-- DROP DATABASE [TP_Filmotheque];--Supprime la base si elle existe

-- CREATE DATABASE [TP_Filmotheque];--Crée la base si elle existe

--Supprime les tables si elles existent
DROP TABLE [ACTEURS];
DROP TABLE [AVIS];
DROP TABLE [MEMBRE];
DROP TABLE [FILM];
DROP TABLE [PARTICIPANT];
DROP TABLE [GENRE];

--Crée la table GENRE
CREATE TABLE [GENRE](
	[id] int NOT NULL PRIMARY KEY IDENTITY ,
	[titre] [NVARCHAR](255) NOT NULL) ;
	
--Crée la table PARTICIPANT
CREATE TABLE [PARTICIPANT](
	[id] int NOT NULL PRIMARY KEY IDENTITY,
	[nom] [NVARCHAR](255) NOT NULL,
	[prenom] [NVARCHAR](255) NOT NULL);
	

--Crée la table MEMBRE
CREATE TABLE [MEMBRE](
	[id] int NOT NULL PRIMARY KEY IDENTITY,
	[nom] [NVARCHAR](255) NOT NULL,
	[prenom] [NVARCHAR](255) NOT NULL,
	[email][NVARCHAR](255) NOT NULL,
	[password][NVARCHAR](255) NOT NULL,
	admin BIT DEFAULT 0);
	
ALTER TABLE MEMBRE add
	constraint UC_MEMBRE_EMAIL UNIQUE (email);
	

--Crée la table FILM
CREATE TABLE [FILM](
	[id] int NOT NULL PRIMARY KEY IDENTITY,
	[titre] [NVARCHAR](250) NOT NULL,
	[annee] int NOT NULL,
	[duree] int NOT NULL,
	[synopsis] [NVARCHAR](250) NOT NULL,
	[id_realisateur] int NOT NULL,
	[id_genre] int NOT NULL,
	) ;
	
-- CHECK CONSTRAINT
ALTER TABLE FILM add
 CONSTRAINT CHK_ANNEE CHECK (ANNEE>=1900);
ALTER TABLE FILM add
 CONSTRAINT CHK_DUREE CHECK (DUREE>=1); 
 
-- FK CONSTRAINT
ALTER TABLE FILM add
constraint FK_FILM_PARTICIPANT foreign key (id_realisateur)
references PARTICIPANT(id);
ALTER TABLE FILM add
constraint FK_FILM_GENRE foreign key (id_genre)
references GENRE(id);


--Crée la table ACTEURS - Table de jointure entre FILM et PARTICIPANT
CREATE TABLE [ACTEURS](
	[id_film] int NOT NULL,
	[id_participant] int NOT NULL,
	PRIMARY KEY ([id_film],[id_participant])) ;
	
-- FK CONSTRAINT	
ALTER TABLE ACTEURS add
constraint FK_ACTEURS_FILM foreign key (id_film)
references FILM(id),
constraint FK_ACTEURS_PARTICIPANT  foreign key (id_participant)
references PARTICIPANT(id);	


--Crée la table AVIS
CREATE TABLE [AVIS](
	[id] int NOT NULL PRIMARY KEY IDENTITY,
	[note] int DEFAULT 0,
	[commentaire] [NVARCHAR](255) NOT NULL,
	[id_membre] int NOT NULL, 
	[id_film] int NOT NULL) ;

-- FK CONSTRAINT
ALTER TABLE AVIS add
constraint FK_AVIS_MEMBRE foreign key (id_membre)
references MEMBRE(id);
ALTER TABLE AVIS add
constraint FK_AVIS_FILM foreign key (id_film)
references FILM(id);

