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