/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package scrum.client.sprint;

import scrum.client.common.AScrumWidget;
import scrum.client.common.BlockListWidget;
import scrum.client.workspace.PagePanel;

import com.google.gwt.user.client.ui.Widget;

public class SprintHistoryWidget extends AScrumWidget {

	private BlockListWidget<Sprint> sprintList;

	@Override
	protected Widget onInitialization() {
		sprintList = new BlockListWidget<Sprint>(SprintBlock.FACTORY);
		sprintList.setAutoSorter(Sprint.END_DATE_COMPARATOR);

		PagePanel page = new PagePanel();
		page.addHeader("Sprint history");
		page.addSection(sprintList);
		return page;
	}

	@Override
	protected void onUpdate() {
		sprintList.setObjects(getCurrentProject().getCompletedSprints());
		super.onUpdate();
	}

	public boolean select(Sprint sprint) {
		return sprintList.showObject(sprint);
	}
}
