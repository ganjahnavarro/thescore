package thescore.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import thescore.model.League;
import thescore.service.LeagueService;

@Component
public class LeagueEditor extends PropertyEditorSupport {

	private @Autowired LeagueService service;
	
	@Override
    public void setAsText(String id) {
        League league = this.service.findById(Integer.valueOf(id));
        this.setValue(league);
    }
	
}
