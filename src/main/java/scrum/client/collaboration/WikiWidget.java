package scrum.client.collaboration;

import ilarkesto.gwt.client.AAction;
import ilarkesto.gwt.client.ARichtextViewEditWidget;
import ilarkesto.gwt.client.ButtonWidget;
import ilarkesto.gwt.client.Gwt;
import scrum.client.common.AScrumWidget;
import scrum.client.workspace.PagePanel;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class WikiWidget extends AScrumWidget {

	private String pageName;
	private Wikipage wikipage;

	private FlowPanel panel;
	private TextBox pageNameBox;
	private WikipageEditor editor;

	@Override
	protected Widget onInitialization() {
		pageNameBox = new TextBox();
		pageNameBox.addKeyPressHandler(new PageNameHandler());
		editor = new WikipageEditor();

		panel = new FlowPanel();
		panel.setStyleName("WikiWidget");
		return panel;
	}

	@Override
	protected void onUpdate() {
		if (pageName == null || pageName.trim().length() == 0) pageName = "Start";

		wikipage = getCurrentProject().getWikipage(pageName);

		pageNameBox.setText(pageName);

		PagePanel page = new PagePanel();
		AAction action = wikipage == null ? new CreateWikipageAction(pageName) : new DeleteWikipageAction(wikipage);
		page.addHeader("Wiki", new ButtonWidget(action), pageNameBox);

		if (wikipage == null) {
			page.addSection("Page \"" + pageName + "\" does not exist.");
		} else {
			page.addHeader(wikipage.getName());
			editor.update();
			page.addSection(editor);
		}
		if (wikipage != null) page.addSection(new CommentsWidget(wikipage).update());

		panel.clear();
		panel.add(page);
		Gwt.update(panel);
	}

	public void showPage(String name) {
		this.pageName = name;
		update();
	}

	class WikipageEditor extends ARichtextViewEditWidget {

		@Override
		protected void onViewerUpdate() {
			setViewerText(wikipage.getText());
		}

		@Override
		protected void onEditorUpdate() {
			setEditorText(wikipage.getText());
		}

		@Override
		protected void onEditorSubmit() {
			wikipage.setText(getEditorText());
		}

	}

	class PageNameHandler implements KeyPressHandler {

		public void onKeyPress(KeyPressEvent event) {
			if (event.getCharCode() == KeyCodes.KEY_ENTER) {
				event.stopPropagation();
				showPage(pageNameBox.getText());
			}
		}

	}
}