package fr.eni.tp.filmotheque.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import fr.eni.tp.filmotheque.bll.FilmService;
import fr.eni.tp.filmotheque.bo.Participant;

@Component
public class StringToParticipantConverter implements Converter<String, Participant> {
	// Injection des services
	private FilmService service;

	public StringToParticipantConverter(FilmService service) {
		this.service = service;
	}

	@Override
	public Participant convert(String id) {
		Long theId = Long.parseLong(id);
		return service.consulterParticipantParId(theId);
	}
}
