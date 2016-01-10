package thescore.classes;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseData {

	private String type;
	private ActionData actionData;
	private Map<Integer, TeamPerformance> performances;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ActionData getActionData() {
		return actionData;
	}

	public void setActionData(ActionData actionData) {
		this.actionData = actionData;
	}

	public Map<Integer, TeamPerformance> getPerformances() {
		return performances;
	}

	public void setPerformances(Map<Integer, TeamPerformance> performances) {
		this.performances = performances;
	}

}
