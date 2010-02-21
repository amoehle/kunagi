// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.EntityGenerator










package scrum.server.risks;

import java.util.*;
import ilarkesto.persistence.*;
import ilarkesto.core.logging.Log;
import ilarkesto.base.*;
import ilarkesto.base.time.*;
import ilarkesto.auth.*;

public abstract class GRisk
            extends AEntity
            implements ilarkesto.search.Searchable, java.lang.Comparable<Risk> {

    // --- AEntity ---

    public final RiskDao getDao() {
        return riskDao;
    }

    protected void repairDeadDatob(ADatob datob) {
    }

    @Override
    public void storeProperties(Map properties) {
        super.storeProperties(properties);
        properties.put("projectId", this.projectId);
        properties.put("number", this.number);
        properties.put("label", this.label);
        properties.put("description", this.description);
        properties.put("probabilityMitigation", this.probabilityMitigation);
        properties.put("impactMitigation", this.impactMitigation);
        properties.put("probability", this.probability);
        properties.put("impact", this.impact);
    }

    public int compareTo(Risk other) {
        return toString().toLowerCase().compareTo(other.toString().toLowerCase());
    }

    private static final ilarkesto.core.logging.Log LOG = ilarkesto.core.logging.Log.get(GRisk.class);

    public static final String TYPE = "risk";


    // -----------------------------------------------------------
    // - Searchable
    // -----------------------------------------------------------

    public boolean matchesKey(String key) {
        if (super.matchesKey(key)) return true;
        if (matchesKey(getLabel(), key)) return true;
        if (matchesKey(getDescription(), key)) return true;
        if (matchesKey(getProbabilityMitigation(), key)) return true;
        if (matchesKey(getImpactMitigation(), key)) return true;
        return false;
    }

    // -----------------------------------------------------------
    // - project
    // -----------------------------------------------------------

    private String projectId;
    private transient scrum.server.project.Project projectCache;

    private void updateProjectCache() {
        projectCache = this.projectId == null ? null : (scrum.server.project.Project)projectDao.getById(this.projectId);
    }

    public final scrum.server.project.Project getProject() {
        if (projectCache == null) updateProjectCache();
        return projectCache;
    }

    public final void setProject(scrum.server.project.Project project) {
        project = prepareProject(project);
        if (isProject(project)) return;
        this.projectId = project == null ? null : project.getId();
        projectCache = project;
        fireModified();
    }

    protected scrum.server.project.Project prepareProject(scrum.server.project.Project project) {
        return project;
    }

    protected void repairDeadProjectReference(String entityId) {
        if (entityId.equals(this.projectId)) {
            repairMissingMaster();
        }
    }

    public final boolean isProjectSet() {
        return this.projectId != null;
    }

    public final boolean isProject(scrum.server.project.Project project) {
        if (this.projectId == null && project == null) return true;
        return project != null && project.getId().equals(this.projectId);
    }

    protected final void updateProject(Object value) {
        setProject(value == null ? null : (scrum.server.project.Project)projectDao.getById((String)value));
    }

    // -----------------------------------------------------------
    // - number
    // -----------------------------------------------------------

    private int number;

    public final int getNumber() {
        return number;
    }

    public final void setNumber(int number) {
        number = prepareNumber(number);
        if (isNumber(number)) return;
        this.number = number;
        fireModified();
    }

    protected int prepareNumber(int number) {
        return number;
    }

    public final boolean isNumber(int number) {
        return this.number == number;
    }

    protected final void updateNumber(Object value) {
        setNumber((Integer)value);
    }

    // -----------------------------------------------------------
    // - label
    // -----------------------------------------------------------

    private java.lang.String label;

    public final java.lang.String getLabel() {
        return label;
    }

    public final void setLabel(java.lang.String label) {
        label = prepareLabel(label);
        if (isLabel(label)) return;
        this.label = label;
        fireModified();
    }

    protected java.lang.String prepareLabel(java.lang.String label) {
        label = Str.removeUnreadableChars(label);
        return label;
    }

    public final boolean isLabelSet() {
        return this.label != null;
    }

    public final boolean isLabel(java.lang.String label) {
        if (this.label == null && label == null) return true;
        return this.label != null && this.label.equals(label);
    }

    protected final void updateLabel(Object value) {
        setLabel((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - description
    // -----------------------------------------------------------

    private java.lang.String description;

    public final java.lang.String getDescription() {
        return description;
    }

    public final void setDescription(java.lang.String description) {
        description = prepareDescription(description);
        if (isDescription(description)) return;
        this.description = description;
        fireModified();
    }

    protected java.lang.String prepareDescription(java.lang.String description) {
        description = Str.removeUnreadableChars(description);
        return description;
    }

    public final boolean isDescriptionSet() {
        return this.description != null;
    }

    public final boolean isDescription(java.lang.String description) {
        if (this.description == null && description == null) return true;
        return this.description != null && this.description.equals(description);
    }

    protected final void updateDescription(Object value) {
        setDescription((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - probabilityMitigation
    // -----------------------------------------------------------

    private java.lang.String probabilityMitigation;

    public final java.lang.String getProbabilityMitigation() {
        return probabilityMitigation;
    }

    public final void setProbabilityMitigation(java.lang.String probabilityMitigation) {
        probabilityMitigation = prepareProbabilityMitigation(probabilityMitigation);
        if (isProbabilityMitigation(probabilityMitigation)) return;
        this.probabilityMitigation = probabilityMitigation;
        fireModified();
    }

    protected java.lang.String prepareProbabilityMitigation(java.lang.String probabilityMitigation) {
        probabilityMitigation = Str.removeUnreadableChars(probabilityMitigation);
        return probabilityMitigation;
    }

    public final boolean isProbabilityMitigationSet() {
        return this.probabilityMitigation != null;
    }

    public final boolean isProbabilityMitigation(java.lang.String probabilityMitigation) {
        if (this.probabilityMitigation == null && probabilityMitigation == null) return true;
        return this.probabilityMitigation != null && this.probabilityMitigation.equals(probabilityMitigation);
    }

    protected final void updateProbabilityMitigation(Object value) {
        setProbabilityMitigation((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - impactMitigation
    // -----------------------------------------------------------

    private java.lang.String impactMitigation;

    public final java.lang.String getImpactMitigation() {
        return impactMitigation;
    }

    public final void setImpactMitigation(java.lang.String impactMitigation) {
        impactMitigation = prepareImpactMitigation(impactMitigation);
        if (isImpactMitigation(impactMitigation)) return;
        this.impactMitigation = impactMitigation;
        fireModified();
    }

    protected java.lang.String prepareImpactMitigation(java.lang.String impactMitigation) {
        impactMitigation = Str.removeUnreadableChars(impactMitigation);
        return impactMitigation;
    }

    public final boolean isImpactMitigationSet() {
        return this.impactMitigation != null;
    }

    public final boolean isImpactMitigation(java.lang.String impactMitigation) {
        if (this.impactMitigation == null && impactMitigation == null) return true;
        return this.impactMitigation != null && this.impactMitigation.equals(impactMitigation);
    }

    protected final void updateImpactMitigation(Object value) {
        setImpactMitigation((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - probability
    // -----------------------------------------------------------

    private int probability;

    public final int getProbability() {
        return probability;
    }

    public final void setProbability(int probability) {
        probability = prepareProbability(probability);
        if (isProbability(probability)) return;
        this.probability = probability;
        fireModified();
    }

    protected int prepareProbability(int probability) {
        return probability;
    }

    public final boolean isProbability(int probability) {
        return this.probability == probability;
    }

    protected final void updateProbability(Object value) {
        setProbability((Integer)value);
    }

    // -----------------------------------------------------------
    // - impact
    // -----------------------------------------------------------

    private int impact;

    public final int getImpact() {
        return impact;
    }

    public final void setImpact(int impact) {
        impact = prepareImpact(impact);
        if (isImpact(impact)) return;
        this.impact = impact;
        fireModified();
    }

    protected int prepareImpact(int impact) {
        return impact;
    }

    public final boolean isImpact(int impact) {
        return this.impact == impact;
    }

    protected final void updateImpact(Object value) {
        setImpact((Integer)value);
    }

    public void updateProperties(Map<?, ?> properties) {
        for (Map.Entry entry : properties.entrySet()) {
            String property = (String) entry.getKey();
            if (property.equals("id")) continue;
            Object value = entry.getValue();
            if (property.equals("projectId")) updateProject(value);
            if (property.equals("number")) updateNumber(value);
            if (property.equals("label")) updateLabel(value);
            if (property.equals("description")) updateDescription(value);
            if (property.equals("probabilityMitigation")) updateProbabilityMitigation(value);
            if (property.equals("impactMitigation")) updateImpactMitigation(value);
            if (property.equals("probability")) updateProbability(value);
            if (property.equals("impact")) updateImpact(value);
        }
    }

    protected void repairDeadReferences(String entityId) {
        super.repairDeadReferences(entityId);
        repairDeadProjectReference(entityId);
    }

    // --- ensure integrity ---

    public void ensureIntegrity() {
        super.ensureIntegrity();
        if (!isProjectSet()) {
            repairMissingMaster();
            return;
        }
        try {
            getProject();
        } catch (EntityDoesNotExistException ex) {
            LOG.info("Repairing dead project reference");
            repairDeadProjectReference(this.projectId);
        }
    }


    // -----------------------------------------------------------
    // - dependencies
    // -----------------------------------------------------------

    static scrum.server.project.ProjectDao projectDao;

    public static final void setProjectDao(scrum.server.project.ProjectDao projectDao) {
        GRisk.projectDao = projectDao;
    }

    static RiskDao riskDao;

    public static final void setRiskDao(RiskDao riskDao) {
        GRisk.riskDao = riskDao;
    }

}