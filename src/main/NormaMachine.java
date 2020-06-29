package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NormaMachine {
	private List<Register> registers;
	private List<NormaInstruction> instructions;
	private int pularBlocoIfWhile;
	private Integer valorGoToIfWhile;

	public NormaMachine() {
		this.registers = new ArrayList<>();
		this.instructions = new ArrayList<>();
	}
	
	public Register getRegister(String name) {
		for (Register register:registers) {
			if (register.getName().equals(name)) {
				return register;
			}
		}
		return null;
	}

	public void action() throws Exception {

		for (int i = 0; i < instructions.size(); i++) {

			NormaInstruction instrucao = instructions.get(i);
			Instructions instructions = instrucao.getInstructions();

			switch (instructions) {
			case ISZERO:

				break;
			case GOTO:
				i = goTo(instrucao);
				break;
			case IF:
				checarCondicaoIfElse(instrucao);
				if(valorGoToIfWhile != null) {
					i = valorGoToIfWhile;
					valorGoToIfWhile = null;
				}
				break;
			case WHILE:
				validateConditionWhile(instrucao);
				if(valorGoToIfWhile != null) {
					i = valorGoToIfWhile;
					valorGoToIfWhile = null;
				}
				break;
			default:
				executarInstrucao(instrucao);
			}
		}
	}
	
	public void printRegister() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for (Register register: registers) {
			System.out.println(register.getName() + ":" + register.getValue() + " ");
		}

//		System.out.println("R0:" + registradores[0] + " " + "R1:" + registradores[1] + " " + "R2:" + registradores[2]
//				+ " " + "R3:" + registradores[3] + " " + "R4:" + registradores[4] + "\n" + "R5:" + registradores[5]
//				+ " " + "R6:" + registradores[6] + " " + "R7:" + registradores[7] + " " + "R8:" + registradores[8] + " "
//				+ "R9:" + registradores[9] + " ");

	}
	
	private void validateArguments(List<String> argumentos) throws Exception {
		if (argumentos.size() == 0) {
			throw new Exception("Erro ao encontrar agumentos");
		}
	}

	private void validateInstriction(String instrucao) throws Exception {
		if (instrucao.split(" ")[0] == null) {
			throw new Exception("Erro ao encontrar instructions na instru��o: " + instrucao);
		}
	}

	
//	public void executarInstrucaoAdd(NormaInstruction instrucao) {
//		System.out.println("ADD " + instrucao.getArgumentos().get(0) + " " + instrucao.getArgumentos().get(1));
//		
//		Register a = getRegister(instrucao.getArgumentos().get(0));
//		Register b = getRegister(instrucao.getArgumentos().get(1));
//		while (b.getValue()>0) {
//			a.setValue(a.getValue()+1);;
//			b.setValue(b.getValue()-1);;
//			printRegister();
//		}
////		printRegister();
//	}
	

	public void checarCondicaoIfElse(NormaInstruction instrucao) throws Exception {

		Register register = getRegister(instrucao.getArgumentos().get(1));
		int valorRegistrador = register.getValue();
		
//		int registrador = registradores[Integer.parseInt(instrucao.getArgumentos().get(1))];

		if (valorRegistrador == 0) {
			
			System.out.println("IF " + instrucao.getArgumentos().get(0) + " " + instrucao.getArgumentos().get(1));
			
			ifElseWhileExecConditional(instrucao);

		} else {

			
			NormaInstruction instructionElse = searchElse(instrucao);

			if (instructionElse != null) {
				
				System.out.println("ELSE");
				
				ifElseWhileExecConditional(instructionElse);

			}

		}

	}

	
	public void validateConditionWhile(NormaInstruction instrucao) throws Exception {
		
		Register register = getRegister(instrucao.getArgumentos().get(1));
		int valorRegistrador = register.getValue();
		
		System.out.println(" WHILE ");

		while(valorRegistrador == 0) {
			
			if(valorGoToIfWhile == null) {
				ifElseWhileExecConditional(instrucao);
				valorRegistrador = register.getValue();//registradores[Integer.parseInt(instrucao.getArgumentos().get(1))];		
			}else {
				valorRegistrador = 1;
			}
		}
		
	}

	public int goTo(NormaInstruction instucao) {
		
		for (int i = 0; i < instructions.size(); i++) {

			NormaInstruction interna = instructions.get(i);

			if (interna.getArgumentos().get(0).equals(":" + instucao.getArgumentos().get(0))) {
				return i;
			}

		}

		return 0;

	}
	
	public void ifElseWhileExecConditional(NormaInstruction instrucao) throws Exception {
		
		List<NormaInstruction> instrucoesInterna = instrucao.getInstrucoes();

		for (int i = 0; i < instrucoesInterna.size(); i++) {

			NormaInstruction instrucaoInterna = instrucoesInterna.get(i);

			if (instrucaoInterna.getInstructions() == Instructions.IF) {
				checarCondicaoIfElse(instrucaoInterna);
			} else if (instrucaoInterna.getInstructions() == Instructions.WHILE) {
				validateConditionWhile(instrucaoInterna);
			} else {

				if (instrucaoInterna.getInstructions() == Instructions.ELSE)
					continue;
				if (instrucaoInterna.getInstructions() == Instructions.GOTO) {
					valorGoToIfWhile  = goTo(instrucaoInterna);
					break;
				}
					

				executarInstrucao(instrucaoInterna);
			}

		}
		
	}
	
	public NormaInstruction searchElse(NormaInstruction instrucao) {

		List<NormaInstruction> instrucoesInternaIf = instrucao.getInstrucoes();

		for (int i = 0; i < instrucoesInternaIf.size(); i++) {

			NormaInstruction instrucaoInternaIf = instrucoesInternaIf.get(i);

			if (instrucaoInternaIf.getInstructions() == Instructions.ELSE) {
				return instrucaoInternaIf;
			}

		}

		return null;

	}

	public void executarInstrucao(NormaInstruction instrucao) throws Exception {
		Instructions instructions = instrucao.getInstructions();
		
		Register register;

		switch (instructions) {
		case INC:
			System.out.println("INC " + instrucao.getArgumentos().get(0));
			register = getRegister(instrucao.getArgumentos().get(0));
			register.setValue(register.getValue()+1);
//			registradores[Integer.parseInt(instrucao.getArgumentos().get(0))]++;
			printRegister();
			break;
		case DEC:
			System.out.println("DEC " + instrucao.getArgumentos().get(0));
			
			register = getRegister(instrucao.getArgumentos().get(0));
			register.setValue(register.getValue()-1);
//			registradores[Integer.parseInt(instrucao.getArgumentos().get(0))]--;
			printRegister();
			break;
		case SETZERO:
			
			
			System.out.println("SET0 " + instrucao.getArgumentos().get(0));
			
			register = getRegister(instrucao.getArgumentos().get(0));
			register.setValue(0);
			
//			registradores[Integer.parseInt(instrucao.getArgumentos().get(0))] = 0;
			printRegister();
			break;
		case SET:
			System.out.println("SET " + instrucao.getArgumentos().get(0) + " " + instrucao.getArgumentos().get(1));
			
			String name = instrucao.getArgumentos().get(0);
			Register firstRegister = getRegister(instrucao.getArgumentos().get(0));
			if (instrucao.getArgumentos().get(1).matches(".*[0123456789-].*")) { //if second value is a number
				int value = Integer.parseInt(instrucao.getArgumentos().get(1));
				if (firstRegister == null) {
					firstRegister = new Register(name, value);
					registers.add(firstRegister);
				} else {
					firstRegister.setValue(value);
				}
			} else { //if second value is not a number, it's a register
				Register otherRegister = getRegister(instrucao.getArgumentos().get(1));
				if (firstRegister == null) {
					firstRegister = new Register(name, otherRegister.getValue());
					registers.add(firstRegister);
				} else {
					firstRegister.setValue(otherRegister.getValue());
				}
			}
			printRegister();
			break;
		case ADD:
			System.out.println("ADD " + instrucao.getArgumentos().get(0) + " " + instrucao.getArgumentos().get(1));
			
			Register a = getRegister(instrucao.getArgumentos().get(0));
			Register b = getRegister(instrucao.getArgumentos().get(1));
			a.setValue(a.getValue() + b.getValue());
			printRegister();
//			registradores[Integer.parseInt(instrucao.getArgumentos().get(0))] = registradores[Integer
//					.parseInt(instrucao.getArgumentos().get(0))]
//					+ registradores[Integer.parseInt(instrucao.getArgumentos().get(1))];
//			printRegister();
			break;
		default:
			break;
		}

	}


	public void reciveInstructions(String codigo) throws Exception {
		List<String> instucoesString = new ArrayList<String>(Arrays.asList(codigo.split(";")));

		for (int i = 0; i < instucoesString.size(); i++) {
			String instrucao = instucoesString.get(i);
			pularBlocoIfWhile = 0;

			List<String> linha = new ArrayList<String>(Arrays.asList(instrucao.split(" ")));

			validateInstriction(instrucao);
			validateArguments(linha);

			NormaInstruction inst = generateInstruction(linha, instucoesString, i);

			if (inst != null) {
				if (inst.getInstructions() == Instructions.IF || inst.getInstructions() == Instructions.WHILE || inst.getInstructions() == Instructions.ELSE) {
					pularBlocoIfWhile = 0;
					interpretorBlockIfWhile(instucoesString, i);
					i = i + pularBlocoIfWhile;
				}
				instructions.add(inst);
			}

		}

	}

	public NormaInstruction generateInstruction(List<String> linha, List<String> instucoesString, int i) {
		String comando = linha.get(0);

		if (comando.contains(":")) {
			comando = ":";
		}

		switch (comando) {
		case "inc":
			linha.remove(0);
			return new NormaInstruction(Instructions.INC, linha, null);

		case "dec":
			linha.remove(0);
			return new NormaInstruction(Instructions.DEC, linha, null);

		case "set0":
			linha.remove(0);
			return new NormaInstruction(Instructions.SETZERO, linha, null);

		case "is0":
			linha.remove(0);
			return new NormaInstruction(Instructions.ISZERO, linha, null);

		case "goto":
			linha.remove(0);
			return new NormaInstruction(Instructions.GOTO, linha, null);
		case "if":
			return generateInstructionIFElseWhile(instucoesString, i, linha);
		case "while":
			return generateInstructionIFElseWhile(instucoesString, i, linha);
		case "set":
			linha.remove(0);
			return new NormaInstruction(Instructions.SET, linha, null);
		case "add":
			linha.remove(0);
			return new NormaInstruction(Instructions.ADD, linha, null);
		case ":":
			return new NormaInstruction(Instructions.DOISPONTOS, linha, null);

		default:

		}

		return null;
	}
	
//	public Instrucao generateInstructionAdd(List<String> linha) {
//		
//	}


	public NormaInstruction generateInstructionIFElseWhile(List<String> instucoesString, int i, List<String> linha) {
		List<NormaInstruction> instrucoesInternas = new ArrayList<NormaInstruction>();

		for (int j = i + 1; j < instucoesString.size(); j++) {
			String instrucao = instucoesString.get(j);

			List<String> linhaInterna = new ArrayList<String>(Arrays.asList(instrucao.split(" ")));

			String comando = linhaInterna.get(0);

			if (comando.equals("if") || comando.equals("while") || comando.equals("else")) {
				instrucoesInternas.add(generateInstructionIFElseWhile(instucoesString, j, linhaInterna));
				interpretorBlockIfWhile(instucoesString, j);
				j = j + pularBlocoIfWhile;
				pularBlocoIfWhile = 0;
			} else {
				if (comando.equals("endif") || comando.equals("endwhile") || comando.equals("endelse")) {
					break;
				} else {
					instrucoesInternas.add(generateInstruction(linhaInterna, instucoesString, 0));
				}

			}

		}

		String comando = linha.get(0);
		linha.remove(0);

		if (comando.equals("if")) {
			return new NormaInstruction(Instructions.IF, linha, instrucoesInternas);
		} else if(comando.equals("while")) {
			return new NormaInstruction(Instructions.WHILE, linha, instrucoesInternas);
		}else {
			return new NormaInstruction(Instructions.ELSE, linha, instrucoesInternas);
		}

	}

	public int interpretorBlockIfWhile(List<String> instucoesString, int i) {
		
		int pularBloco = 0;

		for (int j = i + 1; j < instucoesString.size(); j++) {
			String instrucao = instucoesString.get(j);

			List<String> linhaInterna = new ArrayList<String>(Arrays.asList(instrucao.split(" ")));

			String comando = linhaInterna.get(0);

			if (comando.equals("if") || comando.equals("while") || comando.equals("else")) {
				pularBlocoIfWhile++;
				pularBloco++;
				j = j + interpretorBlockIfWhile(instucoesString, j);
			} else {
				if (comando.equals("endif") || comando.equals("endwhile") || comando.equals("endelse")) {
					pularBlocoIfWhile++;
					pularBloco++;
					break;
				} else {
					pularBloco++;
					pularBlocoIfWhile++;
				}

			}

		}

		return pularBloco;

	}
}