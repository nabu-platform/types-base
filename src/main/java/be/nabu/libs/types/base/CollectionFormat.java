package be.nabu.libs.types.base;

public enum CollectionFormat {
	CSV(','),
	SSV(' '),
	TSV('\t'),
	PIPES('|'),
	MULTI('&')
	;
	private char character;

	private CollectionFormat(char character) {
		this.character = character;
	}
	public char getCharacter() {
		return character;
	}
}