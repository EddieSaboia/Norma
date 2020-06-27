package main;
import java.util.List;

public class Instrucao {
	
	private Interpretador interpretador;
	private List<String> argumentos;
	private List<Instrucao> instrucoes;
	
	
	public Instrucao(Interpretador interpretador, List<String> argumentos, List<Instrucao> instrucoes) {
		super();
		this.interpretador = interpretador;
		this.argumentos = argumentos;
		this.instrucoes = instrucoes;
	}

	public Interpretador getComando() {
		return interpretador;
	}


	public void setComando(Interpretador interpretador) {
		this.interpretador = interpretador;
	}


	public List<String> getArgumentos() {
		return argumentos;
	}


	public void setArgumentos(List<String> argumentos) {
		this.argumentos = argumentos;
	}


	public List<Instrucao> getInstrucoes() {
		return instrucoes;
	}


	public void setInstrucoes(List<Instrucao> instrucoes) {
		this.instrucoes = instrucoes;
	}
	
	
	

	

}
