-- ENREGISTREMENTS :
	
--Insère des enregistrements dans la table GENRE
INSERT INTO [GENRE] ([titre]) VALUES ('Animation');
INSERT INTO [GENRE] ([titre]) VALUES ('Science-fiction');
INSERT INTO [GENRE] ([titre]) VALUES ('Documentaire');
INSERT INTO [GENRE] ([titre]) VALUES ('Action');
INSERT INTO [GENRE] ([titre]) VALUES ('Comédie');
INSERT INTO [GENRE] ([titre]) VALUES ('Drame');
INSERT INTO [GENRE] ([titre]) VALUES ('Policier');
INSERT INTO [GENRE] ([titre]) VALUES ('Horreur');
                          
	
--Insère des enregistrements dans la table PARTICIPANT
INSERT INTO [PARTICIPANT] ([nom],[prenom]) VALUES ('Spielberg','Steven');
INSERT INTO [PARTICIPANT] ([nom],[prenom]) VALUES ('Cronenberg','David');
INSERT INTO [PARTICIPANT] ([nom],[prenom]) VALUES ('Boon','Dany');
INSERT INTO [PARTICIPANT] ([nom],[prenom]) VALUES ('Attenborough','Richard');
INSERT INTO [PARTICIPANT] ([nom],[prenom]) VALUES ('Goldblum','Jeff');
INSERT INTO [PARTICIPANT] ([nom],[prenom]) VALUES ('Davis','Geena');
INSERT INTO [PARTICIPANT] ([nom],[prenom]) VALUES ('Rylance','Mark');
INSERT INTO [PARTICIPANT] ([nom],[prenom]) VALUES ('Barnhill','Ruby');
INSERT INTO [PARTICIPANT] ([nom],[prenom]) VALUES ('Merad','Kad');
                          
						  
	
--Insère des enregistrements dans la table MEMBRE
INSERT INTO [MEMBRE] ([email],[nom],[prenom],[password]) VALUES ('abaille@campus-eni.fr','BAILLE','Anne-Lise', '{bcrypt}$2a$10$KaSHgvH9p/cUnsOVPzYvzunWDAIv68whrOxmui1S.0AjzbP5RX7yO');/*Mot de Passe = annelise*/
INSERT INTO [MEMBRE] ([email],[nom],[prenom],[password]) VALUES ('jtrillard@campus-eni.fr','TRILLARD','Julien','{bcrypt}$2a$10$VwQ7gMUPLeQnFC6vCsOoTevzdPe.JPu0L/7cwPGdJ6TjSpipGwY.y');/*Mot de Passe = julien*/
INSERT INTO [MEMBRE] ([email],[nom],[prenom],[password]) VALUES ('fdelaschesnais@campus-eni.fr','DELACHESNAIS','Frédéric', '{bcrypt}$2a$10$GhRo8SeZhL64e7E7/DQ3Le5mgkAjuesQCjQptJFh0oQLjwdeH9ZWG');/*Mot de Passe = frederic*/
INSERT INTO [MEMBRE] ([email],[nom],[prenom],[password],[is_admin]) VALUES ('pmontembault@campus-eni.fr','MONTEMBAULT','Philippe', '{bcrypt}$2a$10$0g3xzuioWFxQLVdmc8LRKOb.sVxyH/uQQjdNg.CPX7tg3cYRK3VTe',1);/*Mot de Passe = philippe*/
INSERT INTO [MEMBRE] ([email],[nom],[prenom],[password]) VALUES ('hbernard@campus-eni.fr','BERNARD','Hervé', '{bcrypt}$2a$10$oYzLsRNezWP.ruY83rkYAuUH14nuNv356V3z3O4pRkuA.GwaXNGke');/*Mot de Passe = herve*/
INSERT INTO [MEMBRE] ([email],[nom],[prenom],[password]) VALUES ('tgroussard@campus-eni.fr','GROUSSARD','Thierry', '{bcrypt}$2a$10$ca.IC8NI2Km3kRRUoZ9EeORDJMPPe0iyyYShQ1IqPswr5YXDLqwey');/*Mot de Passe = thierry*/
INSERT INTO [MEMBRE] ([email],[nom],[prenom],[password],[is_admin]) VALUES ('sgobin@campus-eni.fr','GOBIN','Stéphane','{bcrypt}$2a$10$B5U29ajHsIKd8aY3c/JNn.xTJpOCAeoXvT9XvfzbbHGP4iIFV9Lkm');/*Mot de Passe = stephane*/


--Insère des enregistrements dans la table FILM
INSERT INTO [FILM] ([titre],[annee],[duree],[synopsis],[id_realisateur],[id_genre]) VALUES ('Jurassic Park',1993,128,'Le film raconte l''histoire d''un milliardaire et son équipe de généticiens parvenant à ramener à la vie des dinosaures grâce au clonage.',1,2);
INSERT INTO [FILM] ([titre],[annee],[duree],[synopsis],[id_realisateur],[id_genre]) VALUES ('The Fly',1986, 95,'Il s''agit de l''adaptation cinématographique de la nouvelle éponyme de l''auteur George Langelaan.',2,2);
INSERT INTO [FILM] ([titre],[annee],[duree],[synopsis],[id_realisateur],[id_genre]) VALUES ('The BFG',2016, 117,'Le Bon Gros Géant est un géant bien différent des autres habitants du Pays des Géants.',1,5);
INSERT INTO [FILM] ([titre],[annee],[duree],[synopsis],[id_realisateur],[id_genre]) VALUES ('Bienvenue chez les Ch''tis',2008, 106,'Philippe Abrams marié à Julie (dépressive) essaie d''être mutaté dans le Sud. Il se retrouve à Bergues dans le Nord.',3,5);

--Insère des enregistrements dans la table de jointure avec FILM - ACTEURS
INSERT INTO [ACTEURS] ([id_film],[id_participant]) VALUES (1,4);
INSERT INTO [ACTEURS] ([id_film],[id_participant]) VALUES (1,5);
INSERT INTO [ACTEURS] ([id_film],[id_participant]) VALUES (2,5);
INSERT INTO [ACTEURS] ([id_film],[id_participant]) VALUES (2,6);
INSERT INTO [ACTEURS] ([id_film],[id_participant]) VALUES (3,7);
INSERT INTO [ACTEURS] ([id_film],[id_participant]) VALUES (3,8);
INSERT INTO [ACTEURS] ([id_film],[id_participant]) VALUES (4,3);
INSERT INTO [ACTEURS] ([id_film],[id_participant]) VALUES (4,9);

--Insère des enregistrements dans la table de AVIS
INSERT INTO [AVIS] ([note],[commentaire],[id_membre],[id_film]) VALUES (4,'On rit du début à la fin',1,4);

-- Requetes pour aider à tester
/*
select * from GENRE;
select * from PARTICIPANT;
select * from MEMBRE;

select * from FILM;

select f.*, g.titre as Genre, (r.prenom + ' ' +r.nom)as Realisateur  from Film f 
	inner join genre g on f.id_genre=g.id
	inner join PARTICIPANT r on r.id = f.id_realisateur;
select * from ACTEURS;

select f.*, (p.prenom + ' '+ p.nom) as Acteur from FILM f 
	inner join ACTEURS a on a.id_film = f.id 
	inner join PARTICIPANT p on a.id_participant = p.id;

select * from AVIS;

*/