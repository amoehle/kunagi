package scrum.client.project;

import ilarkesto.core.scope.Scope;
import scrum.client.common.TooltipBuilder;
import scrum.client.workspace.ProjectWorkspaceWidgets;

public class SplitRequirementAction extends GSplitRequirementAction {

	public SplitRequirementAction(scrum.client.project.Requirement requirement) {
		super(requirement);
	}

	@Override
	public String getLabel() {
		return "Split Story";
	}

	@Override
	public String getTooltip() {
		TooltipBuilder tt = new TooltipBuilder("Create a new substory from this (epic) story.");
		if (!requirement.getProject().isProductOwner(getCurrentUser())) tt.addRemark(TooltipBuilder.NOT_PRODUCT_OWNER);
		return tt.getTooltip();
	}

	@Override
	public boolean isPermitted() {
		if (!requirement.getProject().isProductOwner(getCurrentUser())) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		Requirement newRequirement = requirement.getProject().createNewRequirement(requirement, true, true);
		Scope.get().getComponent(ProjectWorkspaceWidgets.class).showEntity(newRequirement);
		addUndo(new Undo(newRequirement));
	}

	class Undo extends ALocalUndo {

		private Requirement newRequirement;

		public Undo(Requirement newRequirement) {
			super();
			this.newRequirement = newRequirement;
		}

		@Override
		public String getLabel() {
			return "Undo splitted story " + requirement.getReferenceAndLabel();
		}

		@Override
		protected void onUndo() {
			getDao().deleteRequirement(newRequirement);
		}

	}

}