

public class Principal {

	public static void main(String[] args) throws Exception {
		
		String codigo = 
				
				"set a 2;"
				      + "set b 8;"
				 	  + "add a b;"
				 	  + "set0 a;";
	 
	
		Norma n = new Norma();
		n.receberInstrucoes(codigo);
		n.acao();
	}

}
