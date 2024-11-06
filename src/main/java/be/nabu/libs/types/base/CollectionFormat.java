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

package be.nabu.libs.types.base;

public enum CollectionFormat {
	CSV(','),
	SSV(' '),
	TSV('\t'),
	PIPES('|'),
	MULTI('&'),
	
	LABEL('.'),
	// special handling required...
	MATRIX_IMPLODE(','),
	MATRIX_EXPLODE(',')
	;
	private char character;

	private CollectionFormat(char character) {
		this.character = character;
	}
	public char getCharacter() {
		return character;
	}
}