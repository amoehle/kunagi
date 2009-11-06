package scrum.client.journal;

import ilarkesto.gwt.client.AWidget;
import ilarkesto.gwt.client.TableBuilder;
import ilarkesto.gwt.client.editor.DateAndTimeEditorWidget;
import ilarkesto.gwt.client.editor.TextEditorWidget;

import com.google.gwt.user.client.ui.Widget;

public class ProjectEventWidget extends AWidget {

	private ProjectEvent projectEvent;

	public ProjectEventWidget(ProjectEvent projectEvent) {
		this.projectEvent = projectEvent;
	}

	@Override
	protected Widget onInitialization() {
		TableBuilder tb = new TableBuilder();

		tb.addFieldRow("Timestamp", new DateAndTimeEditorWidget(null));
		tb.addFieldRow("Label", new TextEditorWidget(projectEvent.getLabelModel()));

		return tb.createTable();
	}

}
