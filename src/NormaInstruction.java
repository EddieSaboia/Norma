import java.util.List;

public class NormaInstruction {
	
	private Comando comando;
	private List<String> argumentos;
	private List<NormaInstruction> instrucoes;
	
	
	public NormaInstruction(Comando comando, List<String> argumentos, List<NormaInstruction> instrucoes) {
		super();
		this.comando = comando;
		this.argumentos = argumentos;
		this.instrucoes = instrucoes;
	}

	public Comando getComando() {
		return comando;
	}


	public void setComando(Comando comando) {
		this.comando = comando;
	}


	public List<String> getArgumentos() {
		return argumentos;
	}


	public void setArgumentos(List<String> argumentos) {
		this.argumentos = argumentos;
	}


	public List<NormaInstruction> getInstrucoes() {
		return instrucoes;
	}


	public void setInstrucoes(List<NormaInstruction> instrucoes) {
		this.instrucoes = instrucoes;
	}
	
	
	

	

}
