package main;
public enum Instructions {
	INC("inc"), DEC("dec"), SETZERO("set0"), ISZERO("is0"), GOTO("goto"), IF("if"), ELSE("else"), WHILE("while"),
	SET("set"), ADD("add"), DOISPONTOS(":");
	
	private String instructions;
	
	Instructions(String instructions) {
		this.instructions = instructions;
	}
	
	public static Instructions deString(String texto) {
		for(Instructions c : Instructions.values()) {
			if(c.instructions.equalsIgnoreCase(texto)) {
				return c;
			}
		}
		return null;
	}
}