USE TP_Filmotheque;  
GO 

DROP PROCEDURE FindActeurs;
GO  
--PROCEDURE STOCKEE : FindActeurs
CREATE PROCEDURE FindActeurs
	@idFilm int
AS  
    SET NOCOUNT ON;

   select p.* from PARTICIPANT p 
	inner join ACTEURS a on a.id_participant = p.id 
	where a.id_film = @idFilm;
    RETURN;
GO


