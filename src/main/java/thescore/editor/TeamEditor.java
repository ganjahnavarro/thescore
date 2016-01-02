package thescore.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import thescore.model.Team;
import thescore.service.TeamService;

@Component
public class TeamEditor extends PropertyEditorSupport {

	private @Autowired TeamService service;
	
	@Override
    public void setAsText(String id) {
        Team team = this.service.findById(Integer.valueOf(id));
        this.setValue(team);
    }
	
}
