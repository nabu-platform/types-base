/*
* Copyright (C) 2014 Alexander Verbruggen
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License
* along with this program. If not, see <https://www.gnu.org/licenses/>.
*/

package be.nabu.libs.types.resultset;

import java.sql.ResultSet;

import be.nabu.libs.types.api.ComplexType;

public class ResultSetWithType {
	private ResultSet set;
	private ComplexType type;
	// whether or not the result set was already next()-ed to see if it contained any content
	private boolean nexted;

	public ResultSetWithType(ResultSet set, ComplexType type, boolean nexted) {
		this.set = set;
		this.type = type;
		this.nexted = nexted;
	}

	public ResultSet getSet() {
		return set;
	}

	public void setSet(ResultSet set) {
		this.set = set;
	}

	public ComplexType getType() {
		return type;
	}

	public void setType(ComplexType type) {
		this.type = type;
	}

	public boolean isNexted() {
		return nexted;
	}

	public void setNexted(boolean nexted) {
		this.nexted = nexted;
	}
	
}
