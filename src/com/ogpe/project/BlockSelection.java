package com.ogpe.project;

@Deprecated
public enum BlockSelection {
	CONSTANT_BOOLEAN("Constant Boolean Block"), 
	CONSTANT_NUMBER("Constant Number Block"),
	CONSTANT_STRING("Constant String Block"), 
	ADDITION_BLOCK("Addition Block"), 
	PRINT("Print Block");


	private String displayName;

	private BlockSelection(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
	
	@Override
	public String toString() {
		return displayName;
	}
}
