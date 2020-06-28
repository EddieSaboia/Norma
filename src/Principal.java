

public class Principal {

	public static void main(String[] args) throws Exception {
		
		String codigo = 
				
				"set a 7;"
				      + "set b 4;"
				 	  + "add a b;" 
				      + "set c -3;"
				 	  + "add a c;"
				      + "set b c;";
	
		NormaMachine machine = new NormaMachine();
		machine.receberInstrucoes(codigo);
		machine.acao();
	}

}
