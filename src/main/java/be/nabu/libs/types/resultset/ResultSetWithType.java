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
