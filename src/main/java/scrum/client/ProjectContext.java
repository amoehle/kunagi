package scrum.client;

import ilarkesto.gwt.client.AGwtEntity;
import ilarkesto.gwt.client.AWidget;
import ilarkesto.gwt.client.GwtLogger;
import ilarkesto.gwt.client.ObjectMappedFlowPanel;
import ilarkesto.gwt.client.SwitcherWidget;
import ilarkesto.gwt.client.SwitchingNavigatorWidget;

import java.util.List;

import scrum.client.admin.ProjectUserConfigWidget;
import scrum.client.admin.PunishmentsWidget;
import scrum.client.admin.User;
import scrum.client.collaboration.Comment;
import scrum.client.collaboration.WikiWidget;
import scrum.client.common.AScrumComponent;
import scrum.client.context.UserHighlightSupport;
import scrum.client.impediments.Impediment;
import scrum.client.impediments.ImpedimentListWidget;
import scrum.client.issues.Issue;
import scrum.client.issues.IssueListWidget;
import scrum.client.journal.JournalWidget;
import scrum.client.project.ProductBacklogWidget;
import scrum.client.project.Project;
import scrum.client.project.ProjectOverviewWidget;
import scrum.client.project.Quality;
import scrum.client.project.QualityBacklogWidget;
import scrum.client.project.Requirement;
import scrum.client.risks.Risk;
import scrum.client.risks.RiskListWidget;
import scrum.client.sprint.NextSprintWidget;
import scrum.client.sprint.SprintBacklogWidget;
import scrum.client.sprint.SprintHistoryWidget;
import scrum.client.sprint.Task;
import scrum.client.tasks.TaskOverviewWidget;
import scrum.client.tasks.WhiteboardWidget;
import scrum.client.test.WidgetsTesterWidget;
import scrum.client.workspace.ProjectSidebarWidget;

import com.google.gwt.user.client.ui.Widget;

public class ProjectContext extends AScrumComponent {

	private Project project;

	private ProjectSidebarWidget sidebar = new ProjectSidebarWidget();
	private ProjectOverviewWidget projectOverview;
	private TaskOverviewWidget taskOverview;
	private WhiteboardWidget whiteboard;
	private SprintBacklogWidget sprintBacklog;
	private ProductBacklogWidget productBacklog;
	private QualityBacklogWidget qualityBacklog;
	private NextSprintWidget nextSprint;
	private ImpedimentListWidget impedimentList;
	private IssueListWidget issueList;
	private RiskListWidget riskList;
	private WikiWidget wiki;
	private SprintHistoryWidget sprintHistory;
	private ProjectUserConfigWidget projectUserConfig;
	private WidgetsTesterWidget widgetsTester;
	private PunishmentsWidget punishments;
	private JournalWidget projectEventListWidget;

	private User highlightedUser;

	ProjectContext() {
		projectOverview = new ProjectOverviewWidget();
		taskOverview = new TaskOverviewWidget();
		whiteboard = new WhiteboardWidget();
		sprintBacklog = new SprintBacklogWidget();
		productBacklog = new ProductBacklogWidget();
		qualityBacklog = new QualityBacklogWidget();
		nextSprint = new NextSprintWidget();
		impedimentList = new ImpedimentListWidget();
		issueList = new IssueListWidget();
		riskList = new RiskListWidget();
		projectUserConfig = new ProjectUserConfigWidget();
		sprintHistory = new SprintHistoryWidget();
		wiki = new WikiWidget();
		widgetsTester = new WidgetsTesterWidget();
		punishments = new PunishmentsWidget();
		projectEventListWidget = new JournalWidget();

		SwitchingNavigatorWidget navigator = getSidebar().getNavigator();
		navigator.addItem("Project Overview", getProjectOverview());
		navigator.addItem("Task Overview", getTaskOverview());
		navigator.addItem("Whiteboard", getWhiteboard());
		navigator.addItem("Sprint Backlog", getSprintBacklog());
		navigator.addItem("Product Backlog", getProductBacklog());
		navigator.addItem("Quality Backlog", getQualityBacklog());
		navigator.addItem("Impediment List", getImpedimentList());
		navigator.addItem("Issue List", getIssueList());
		navigator.addItem("Risk Management", getRiskList());
		navigator.addItem("Wiki", getWiki());
		navigator.addItem("Project Journal", getProjectEventList());
		navigator.addItem("Next Sprint", getNextSprint());
		navigator.addItem("Sprint History", getSprintHistory());
		navigator.addItem("Courtroom", punishments);
		navigator.addItem("Personal Preferences", getProjectUserConfig());
		navigator.addItem("WidgetsTester", getWidgetsTester());
	}

	@Override
	protected void onDestroy() {
		ObjectMappedFlowPanel.objectHeights.clear();
	}

	public void activate() {
		cm.getUi().show(sidebar, projectOverview);
	}

	public void openProject(Project project) {
		this.project = project;

		cm.getUi().lock("Loading project...");
		cm.getApp().callSelectProject(project.getId(), new Runnable() {

			public void run() {
				activate();
			}
		});

		cm.getEventBus().fireProjectOpened();
	}

	public void closeProject(boolean activateHomeView) {
		assert project != null;
		cm.getUi().lock("Closing project...");
		project = null;
		cm.getApp().callCloseProject();
		cm.getEventBus().fireProjectClosed();
		if (activateHomeView) cm.getHomeContext().activate();
	}

	public Project getProject() {
		return project;
	}

	public boolean isProjectOpen() {
		return project != null;
	}

	public List<Comment> getComments(AGwtEntity entity) {
		return cm.getDao().getCommentsByParent(entity);
	}

	public void highlightUser(User user) {
		if (highlightedUser == user) return;
		Widget currentWidget = getWorkarea().getCurrentWidget();
		if (currentWidget instanceof UserHighlightSupport) {
			((UserHighlightSupport) currentWidget).highlightUser(user);
		}
		highlightedUser = user;
	}

	public ProjectUserConfigWidget getProjectUserConfig() {
		return projectUserConfig;
	}

	public void showEntityByReference(final String reference) {
		assert project != null;
		GwtLogger.DEBUG("Showing entity by reference:", reference);

		if (reference.length() > 4 && reference.startsWith("[[")) {
			String page = reference.substring(2, reference.length() - 2);
			showWiki(page);
			return;
		}

		AGwtEntity entity = cm.getDao().getEntityByReference(reference);
		if (entity != null) {
			showEntity(entity);
			return;
		}
		cm.getUi().lock("Searching for " + reference);
		cm.getApp().callRequestEntityByReference(reference, new Runnable() {

			public void run() {
				AGwtEntity entity = cm.getDao().getEntityByReference(reference);
				if (entity == null) {
					cm.getUi().unlock();
					cm.getChat().postSystemMessage("Object does not exist: " + reference, false);
					return;
				}
				cm.getUi().unlock();
				showEntity(entity);
			}
		});
	}

	public void showEntity(AGwtEntity entity) {
		GwtLogger.DEBUG("Showing entity:", entity);
		if (entity instanceof Task) {
			showTask((Task) entity);
		} else if (entity instanceof Requirement) {
			showRequirement((Requirement) entity);
		} else if (entity instanceof Issue) {
			showIssue((Issue) entity);
		} else if (entity instanceof Risk) {
			showRisk((Risk) entity);
		} else if (entity instanceof Quality) {
			showQuality((Quality) entity);
		} else if (entity instanceof Impediment) {
			showImpediment((Impediment) entity);
		} else {
			throw new RuntimeException("Showing entity not supported: " + entity.getClass().getName());
		}
	}

	public void showIssue(Issue issue) {
		select(issueList);
		issueList.select(issue);
	}

	public void showQuality(Quality quality) {
		select(qualityBacklog);
		qualityBacklog.select(quality);
	}

	public void showImpediment(Impediment impediment) {
		select(impedimentList);
		impedimentList.select(impediment);
	}

	public void showRisk(Risk risk) {
		select(riskList);
		riskList.select(risk);
	}

	public void showTask(Task task) {
		if (getWorkarea().isShowing(sprintBacklog)) {
			showSprintBacklog(task);
		} else if (getWorkarea().isShowing(taskOverview)) {
			showTaskOverview(task);
		} else {
			showWhiteboard(task);
		}
	}

	public void showRequirement(Requirement requirement) {
		boolean inCurrentSprint = getCurrentProject().isCurrentSprint(requirement.getSprint());
		if (inCurrentSprint) {
			if (getWorkarea().isShowing(productBacklog)) {
				showProductBacklog(requirement);
			} else {
				showSprintBacklog(requirement);
			}
		} else {
			showProductBacklog(requirement);
		}
	}

	public void showWiki(String page) {
		select(wiki);
		if (page != null) wiki.showPage(page);
	}

	private SwitcherWidget getWorkarea() {
		return cm.getUi().getWorkspace().getWorkarea();
	}

	public void showWhiteboard(Task task) {
		select(whiteboard);
		whiteboard.selectTask(task);
	}

	public void showSprintBacklog(Task task) {
		select(sprintBacklog);
		sprintBacklog.selectTask(task);
	}

	public void showTaskOverview(Task task) {
		select(taskOverview);
		taskOverview.selectTask(task);
	}

	public void showSprintBacklog(Requirement requirement) {
		select(sprintBacklog);
		if (requirement != null) sprintBacklog.selectRequirement(requirement);
	}

	public void showProductBacklog(Requirement requirement) {
		select(productBacklog);
		productBacklog.selectRequirement(requirement);
	}

	public void showImpedimentList(Impediment impediment) {
		select(impedimentList);
		impedimentList.showImpediment(impediment);
	}

	public void showIssueList(Issue issue) {
		select(issueList);
		issueList.showIssue(issue);
	}

	public void showQualityBacklog(Quality quality) {
		select(qualityBacklog);
		qualityBacklog.showQuality(quality);
	}

	public void showRiskList(Risk risk) {
		select(riskList);
		riskList.showRisk(risk);
	}

	private void select(AWidget widget) {
		getSidebar().getNavigator().select(widget);
	}

	public WikiWidget getWiki() {
		return wiki;
	}

	public SprintHistoryWidget getSprintHistory() {
		return sprintHistory;
	}

	public JournalWidget getProjectEventList() {
		return projectEventListWidget;
	}

	public ImpedimentListWidget getImpedimentList() {
		return impedimentList;
	}

	public IssueListWidget getIssueList() {
		return issueList;
	}

	public NextSprintWidget getNextSprint() {
		return nextSprint;
	}

	public ProductBacklogWidget getProductBacklog() {
		return productBacklog;
	}

	public ProjectOverviewWidget getProjectOverview() {
		return projectOverview;
	}

	public QualityBacklogWidget getQualityBacklog() {
		return qualityBacklog;
	}

	public RiskListWidget getRiskList() {
		return riskList;
	}

	public ProjectSidebarWidget getSidebar() {
		return sidebar;
	}

	public SprintBacklogWidget getSprintBacklog() {
		return sprintBacklog;
	}

	public TaskOverviewWidget getTaskOverview() {
		return taskOverview;
	}

	public WhiteboardWidget getWhiteboard() {
		return whiteboard;
	}

	public WidgetsTesterWidget getWidgetsTester() {
		return widgetsTester;
	}

}
