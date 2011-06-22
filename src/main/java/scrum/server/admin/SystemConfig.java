/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package scrum.server.admin;

import ilarkesto.base.Str;

public class SystemConfig extends GSystemConfig {

	@Override
	public boolean isVisibleFor(User user) {
		return true;
	}

	@Override
	public boolean isEditableBy(User user) {
		if (user == null) return false;
		return user.isAdmin();
	}

	@Override
	protected String prepareUrl(String url) {
		if (url != null && !url.endsWith("/")) url += "/";
		return super.prepareUrl(url);
	}

	@Override
	public void ensureIntegrity() {
		super.ensureIntegrity();
		if (!isDefaultUserPasswordSet()) setDefaultUserPassword(scrum.client.admin.User.INITIAL_PASSWORD);
		if (!isMaxFileSizeSet()) setMaxFileSize(20);
	}

	public boolean isOpenIdDomainAllowed(String openId) {
		if (!isOpenIdDomainsSet()) return true;
		String[] domains = Str.tokenize(getOpenIdDomains(), ",; ");
		for (String domain : domains) {
			if (openId.contains(domain)) return true;
		}
		return false;
	}

}