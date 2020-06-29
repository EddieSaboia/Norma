package main;

import java.util.List;

public class NormaInstruction {
	
	private Instructions instructions;
	private List<String> argumentos;
	private List<NormaInstruction> instrucoes;
	
	
	public NormaInstruction(Instructions instructions, List<String> argumentos, List<NormaInstruction> instrucoes) {
		super();
		this.instructions = instructions;
		this.argumentos = argumentos;
		this.instrucoes = instrucoes;
	}

	public Instructions getInstructions() {
		return instructions;
	}


	public void setInstructions(Instructions instructions) {
		this.instructions = instructions;
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
