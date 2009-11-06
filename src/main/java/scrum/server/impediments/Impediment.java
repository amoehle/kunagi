package scrum.server.impediments;

import ilarkesto.base.time.Date;
import scrum.server.common.Numbered;

public class Impediment extends GImpediment implements Numbered {

	public void updateNumber() {
		if (getNumber() == 0) setNumber(getProject().generateImpedimentNumber());
	}

	public String getReference() {
		return scrum.client.impediments.Impediment.REFERENCE_PREFIX + getNumber();
	}

	@Override
	public void ensureIntegrity() {
		super.ensureIntegrity();
		updateNumber();
		if (!isDateSet()) setDate(Date.today());

		// delete when closed and older than 4 weeks
		if (isClosed() && getDate().getPeriodToNow().toWeeks() > 4) getDao().deleteEntity(this);

	}

	@Override
	public String toString() {
		return getLabel();
	}
}
