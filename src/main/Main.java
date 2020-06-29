package main;

public class Main {

	public static void main(String[] args) throws Exception {
		
		String codigo = 
				
				"set 0 2;"
					      + "set 1 8;"
					      + "set 5 0;"
					      + "set 4 0;"
					      + "if is0 5;"
					      + "set 5 5;"
					      + "endif;"
					      + "while is0 4;"
					      + "dec 5;"
					      + "if is0 5;"
					      + "inc 4;"
					      + "endif;"
					      + "endwhile;"
					      + "add 0 1;"
					      + "set0 1;";
	
		NormaMachine machine = new NormaMachine();
		machine.reciveInstructions(codigo);
		machine.action();
	}
}
