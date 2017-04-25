package com.ogpe.project;

public enum BlockSelection {
	CONSTANT_BOOLEAN("Constant Boolean Block"), 
	CONSTANT_NUMBER("Constant Number Block"),
	CONSTANT_STRING("Constant String Block"), 
	ADDITION_BLOCK("Addition Block"), 
	PRINT("Print Block");

	public static BlockSelection valueOfDisplayName(String displayName) {
		BlockSelection blockListElement = null;
		for (BlockSelection block : values()) {
			if (block.getDisplayName().equals(displayName)) {
				blockListElement = block;
			}
		}
		return blockListElement;
	}

	private String displayName;

	private BlockSelection(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
