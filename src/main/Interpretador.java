package main;
public enum Interpretador {
	INC("inc"), DEC("dec"), SETZERO("set0"), ISZERO("is0"), GOTO("goto"), SET("set"), ADD("add"), IF("if"),
	WHILE("while"), DOISPONTOS(":"), ELSE("else");
	
	private String comando;
	
	Interpretador(String comando) {
		this.comando = comando;
	}
	
	public static Interpretador deString(String texto) {
		for(Interpretador c : Interpretador.values()) {
			if(c.comando.equalsIgnoreCase(texto)) {
				return c;
			}
		}
		return null;
	}
}