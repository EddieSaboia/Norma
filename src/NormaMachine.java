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

	public void acao() throws Exception {

		for (int i = 0; i < instructions.size(); i++) {

			NormaInstruction instrucao = instructions.get(i);
			Comando comando = instrucao.getComando();

			switch (comando) {
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
				checarCondicaoWhile(instrucao);
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
	
//	public void executarInstrucaoAdd(NormaInstruction instrucao) {
//		System.out.println("ADD " + instrucao.getArgumentos().get(0) + " " + instrucao.getArgumentos().get(1));
//		
//		Register a = getRegister(instrucao.getArgumentos().get(0));
//		Register b = getRegister(instrucao.getArgumentos().get(1));
//		while (b.getValue()>0) {
//			a.setValue(a.getValue()+1);;
//			b.setValue(b.getValue()-1);;
//			imprimirRegistradores();
//		}
////		imprimirRegistradores();
//	}
	
	public int goTo(NormaInstruction instucao) {

		for (int i = 0; i < instructions.size(); i++) {

			NormaInstruction interna = instructions.get(i);

			if (interna.getArgumentos().get(0).equals(":" + instucao.getArgumentos().get(0))) {
				return i;
			}

		}

		return 0;

	}

	
	public void checarCondicaoWhile(NormaInstruction instrucao) throws Exception {
		
		Register register = getRegister(instrucao.getArgumentos().get(1));
		int valorRegistrador = register.getValue();
		
		System.out.println(" WHILE ");

		while(valorRegistrador == 0) {
			
			if(valorGoToIfWhile == null) {
				execultarCondicaoIfElseWhile(instrucao);
				valorRegistrador = register.getValue();//registradores[Integer.parseInt(instrucao.getArgumentos().get(1))];		
			}else {
				valorRegistrador = 1;
			}
		}
		
	}

	public void checarCondicaoIfElse(NormaInstruction instrucao) throws Exception {

		Register register = getRegister(instrucao.getArgumentos().get(1));
		int valorRegistrador = register.getValue();
		
//		int registrador = registradores[Integer.parseInt(instrucao.getArgumentos().get(1))];

		if (valorRegistrador == 0) {
			
			System.out.println("IF " + instrucao.getArgumentos().get(0) + " " + instrucao.getArgumentos().get(1));
			
			execultarCondicaoIfElseWhile(instrucao);

		} else {

			
			NormaInstruction instrucaoElse = procurarInstrucaoElse(instrucao);

			if (instrucaoElse != null) {
				
				System.out.println("ELSE");
				
				execultarCondicaoIfElseWhile(instrucaoElse);

			}

		}

	}

	
	public void execultarCondicaoIfElseWhile(NormaInstruction instrucao) throws Exception {
		
		List<NormaInstruction> instrucoesInterna = instrucao.getInstrucoes();

		for (int i = 0; i < instrucoesInterna.size(); i++) {

			NormaInstruction instrucaoInterna = instrucoesInterna.get(i);

			if (instrucaoInterna.getComando() == Comando.IF) {
				checarCondicaoIfElse(instrucaoInterna);
			} else if (instrucaoInterna.getComando() == Comando.WHILE) {
				checarCondicaoWhile(instrucaoInterna);
			} else {

				if (instrucaoInterna.getComando() == Comando.ELSE)
					continue;
				if (instrucaoInterna.getComando() == Comando.GOTO) {
					valorGoToIfWhile  = goTo(instrucaoInterna);
					break;
				}
					

				executarInstrucao(instrucaoInterna);
			}

		}
		
	}
	
	
	public NormaInstruction procurarInstrucaoElse(NormaInstruction instrucao) {

		List<NormaInstruction> instrucoesInternaIf = instrucao.getInstrucoes();

		for (int i = 0; i < instrucoesInternaIf.size(); i++) {

			NormaInstruction instrucaoInternaIf = instrucoesInternaIf.get(i);

			if (instrucaoInternaIf.getComando() == Comando.ELSE) {
				return instrucaoInternaIf;
			}

		}

		return null;

	}

	public void executarInstrucao(NormaInstruction instrucao) throws Exception {
		Comando comando = instrucao.getComando();
		
		Register register;

		switch (comando) {
		case INC:
			System.out.println("INC " + instrucao.getArgumentos().get(0));
			register = getRegister(instrucao.getArgumentos().get(0));
			register.setValue(register.getValue()+1);
			
//			registradores[Integer.parseInt(instrucao.getArgumentos().get(0))]++;
			imprimirRegistradores();
			break;
		case DEC:
			System.out.println("DEC " + instrucao.getArgumentos().get(0));
			
			register = getRegister(instrucao.getArgumentos().get(0));
			register.setValue(register.getValue()-1);
//			registradores[Integer.parseInt(instrucao.getArgumentos().get(0))]--;
			imprimirRegistradores();
			break;
		case SETZERO:
			
			
			System.out.println("SET0 " + instrucao.getArgumentos().get(0));
			
			register = getRegister(instrucao.getArgumentos().get(0));
			register.setValue(0);
			
//			registradores[Integer.parseInt(instrucao.getArgumentos().get(0))] = 0;
			imprimirRegistradores();
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
			imprimirRegistradores();
			break;
		case ADD:
			System.out.println("ADD " + instrucao.getArgumentos().get(0) + " " + instrucao.getArgumentos().get(1));
			
			Register a = getRegister(instrucao.getArgumentos().get(0));
			Register b = getRegister(instrucao.getArgumentos().get(1));
			a.setValue(a.getValue() + b.getValue());
			imprimirRegistradores();
//			registradores[Integer.parseInt(instrucao.getArgumentos().get(0))] = registradores[Integer
//					.parseInt(instrucao.getArgumentos().get(0))]
//					+ registradores[Integer.parseInt(instrucao.getArgumentos().get(1))];
//			imprimirRegistradores();
			break;
		default:
			break;
		}

	}

	public void imprimirRegistradores() {
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

	public void receberInstrucoes(String codigo) throws Exception {
		List<String> instucoesString = new ArrayList<String>(Arrays.asList(codigo.split(";")));

		for (int i = 0; i < instucoesString.size(); i++) {
			String instrucao = instucoesString.get(i);
			pularBlocoIfWhile = 0;

			List<String> linha = new ArrayList<String>(Arrays.asList(instrucao.split(" ")));

			validarComando(instrucao);
			validarArgumentos(linha);

			NormaInstruction inst = gerarInstrucao(linha, instucoesString, i);

			if (inst != null) {
				if (inst.getComando() == Comando.IF || inst.getComando() == Comando.WHILE || inst.getComando() == Comando.ELSE) {
					pularBlocoIfWhile = 0;
					pularBlocosIfWhile(instucoesString, i);
					i = i + pularBlocoIfWhile;
				}
				instructions.add(inst);
			}

		}

	}

	public NormaInstruction gerarInstrucao(List<String> linha, List<String> instucoesString, int i) {
		String comando = linha.get(0);

		if (comando.contains(":")) {
			comando = ":";
		}

		switch (comando) {
		case "inc":
			linha.remove(0);
			return new NormaInstruction(Comando.INC, linha, null);

		case "dec":
			linha.remove(0);
			return new NormaInstruction(Comando.DEC, linha, null);

		case "set0":
			linha.remove(0);
			return new NormaInstruction(Comando.SETZERO, linha, null);

		case "is0":
			linha.remove(0);
			return new NormaInstruction(Comando.ISZERO, linha, null);

		case "goto":
			linha.remove(0);
			return new NormaInstruction(Comando.GOTO, linha, null);
		case "if":
			return gerarIntrucaoIfWhile(instucoesString, i, linha);
		case "while":
			return gerarIntrucaoIfWhile(instucoesString, i, linha);
		case "set":
			linha.remove(0);
			return new NormaInstruction(Comando.SET, linha, null);
		case "add":
			linha.remove(0);
			return new NormaInstruction(Comando.ADD, linha, null);
		case ":":
			return new NormaInstruction(Comando.DOISPONTOS, linha, null);

		default:

		}

		return null;
	}
	
//	public Instrucao gerarInstrucaoAdd(List<String> linha) {
//		
//	}


	public NormaInstruction gerarIntrucaoIfWhile(List<String> instucoesString, int i, List<String> linha) {
		List<NormaInstruction> instrucoesInternas = new ArrayList<NormaInstruction>();

		for (int j = i + 1; j < instucoesString.size(); j++) {
			String instrucao = instucoesString.get(j);

			List<String> linhaInterna = new ArrayList<String>(Arrays.asList(instrucao.split(" ")));

			String comando = linhaInterna.get(0);

			if (comando.equals("if") || comando.equals("while") || comando.equals("else")) {
				instrucoesInternas.add(gerarIntrucaoIfWhile(instucoesString, j, linhaInterna));
				pularBlocosIfWhile(instucoesString, j);
				j = j + pularBlocoIfWhile;
				pularBlocoIfWhile = 0;
			} else {
				if (comando.equals("endif") || comando.equals("endwhile") || comando.equals("endelse")) {
					break;
				} else {
					instrucoesInternas.add(gerarInstrucao(linhaInterna, instucoesString, 0));
				}

			}

		}

		String comando = linha.get(0);
		linha.remove(0);

		if (comando.equals("if")) {
			return new NormaInstruction(Comando.IF, linha, instrucoesInternas);
		} else if(comando.equals("while")) {
			return new NormaInstruction(Comando.WHILE, linha, instrucoesInternas);
		}else {
			return new NormaInstruction(Comando.ELSE, linha, instrucoesInternas);
		}

	}

	public int pularBlocosIfWhile(List<String> instucoesString, int i) {
		
		int pularBloco = 0;

		for (int j = i + 1; j < instucoesString.size(); j++) {
			String instrucao = instucoesString.get(j);

			List<String> linhaInterna = new ArrayList<String>(Arrays.asList(instrucao.split(" ")));

			String comando = linhaInterna.get(0);

			if (comando.equals("if") || comando.equals("while") || comando.equals("else")) {
				pularBlocoIfWhile++;
				pularBloco++;
				j = j + pularBlocosIfWhile(instucoesString, j);
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


	private void validarComando(String instrucao) throws Exception {
		if (instrucao.split(" ")[0] == null) {
			throw new Exception("Erro ao encontrar comando na instrução: " + instrucao);
		}
	}

	private void validarArgumentos(List<String> argumentos) throws Exception {
		if (argumentos.size() == 0) {
			throw new Exception("Erro ao encontrar agumentos");
		}
	}
}