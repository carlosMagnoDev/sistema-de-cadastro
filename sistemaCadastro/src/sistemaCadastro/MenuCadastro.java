package sistemaCadastro;
import java.util.*;
import java.nio.file.spi.FileSystemProvider;
import java.text.*;
import java.time.LocalDate;
import java.time.format.*;
import org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.*;

public class MenuCadastro {	
	public static void main(String[] args) {
		AnsiConsole.systemInstall();
		
		Scanner input = new Scanner(System.in);
		String [] error = {"campo vazio", "apenas letras", "apenas números", "caracteres especiais não são permitidos"};
		
		CadastroUsuario cadastroUsuario = new CadastroUsuario();
		cadastroUsuario.conectar();
		if(cadastroUsuario.estaConectado()) {
			while (true) {
				try {
					System.out.println("Bem-vindo ao Sistema de Cadastro de Usuários! "
							+ "\nDigite o número correspondente à ação que deseja executar:\n");
					System.out.println(" 1. Adicionar Usuário \n 2. Editar Usuário \n 3. Listar Usuário \n 4. Procurar Usuário \n 5. Deletar Usuário \n 6. encerrar sua sessão");
					
					System.out.print("Digite o número da opção desejada: ");
					String opc = input.nextLine();
					int opc2 = Integer.parseInt(opc);
					                                         
					if (opc2 == 1) {
                        System.out.println("\nPara cadastrar um novo usuário no sistema,");
                        System.out.println("Basta fornecer informações como nome, idade e endereço\n");
						
						String nomeCompleto = null;
						while (true) {
							System.out.print("Digite seu nome completo: ");
							nomeCompleto = input.nextLine().trim();
							
							if (nomeCompleto.isEmpty() == false) {
								try {
									Integer.parseInt(nomeCompleto);
									System.out.printf("Error: %s, tente novamente!\n", error[1]);
								} catch (Exception e) {
									if (nomeCompleto.matches("[A-Za-z ]+")) {
										break;										
									} else {
										System.out.printf("Error: %s, tente novamente!\n", error[3]);
									}
								}
							} else {
								System.out.printf("Error: %s, tente novamente!\n", error[0]);
							}
						}
						
					       String dataNascimento = null;
					        while (true) {
					            System.out.print("Digite sua data de nascimento: ");
					            dataNascimento = input.nextLine().trim();

					            if (!dataNascimento.isEmpty()) {
					                String validarData = "^(0[1-9]|1\\d|2\\d|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
					                String validarSomenteNumeros = "\\d+";
					                String validarSomenteLetras = "[a-zA-Z]+";

					                if (dataNascimento.matches(validarData)) {
					                    SimpleDateFormat formatoDataOriginal = new SimpleDateFormat("dd/MM/yyyy");
					                    SimpleDateFormat formatoDataSql = new SimpleDateFormat("yyyy-MM-dd");
					                    Date date = formatoDataOriginal.parse(dataNascimento);
					                    dataNascimento = formatoDataSql.format(date);
					                    break;
					                } else if (dataNascimento.matches(validarSomenteNumeros)) {
					                    System.out.println("Erro: a data de nascimento deve conter apenas números, tente novamente!");
					                } else if (dataNascimento.matches(validarSomenteLetras)) {
					                    System.out.println("Erro: a data de nascimento deve conter apenas números, tente novamente!");
					                } else {
					                    System.out.println("Erro: formato de data inválida, tente novamente! (dd/mm/aaaa)");
					                }
					            } else {
					                System.out.printf("Erro: %s, tente novamente!\n", error[0]);
					            }
					        }

						
						String cpf = null;
						while (true) {
							System.out.print("Digite seu cpf: ");
							cpf = input.nextLine().trim();
							if(ValidaCpf.isCpf(cpf)==true) {
								break;
							}
							else {
								System.out.println("Error: cpf inválido, tente novamente!");
							}
						}
						
						String rg = null;
						while (true) {
							System.out.print("Digite seu RG: ");
							rg = input.nextLine().trim();
							
							if (rg.isEmpty() == false) {
								try {
									Long.parseLong(rg);
									if (rg.length() >= 3 && rg.length() <= 13) {
										break;									
									} else if (rg.length() < 3) {
										System.out.println("Error: rg insuficiente, tente novamente!");
									} else if (rg.length() > 13) {
										System.out.println("Error: rg muito grande, tente novamente!");
									}
								} catch (Exception e) {
									System.out.printf("Error: %s, tente novamente!\n", error[2]);
								}
							} else {
								System.out.printf("Error: %s, tente novamente!\n", error[0]);
							}
						}
						
						String genero = null;
						while (true) {
							System.out.print("Digite seu genero: ");
							genero = input.nextLine().trim().toLowerCase();	
							
							if (genero.equals("masculino") || genero.equals("feminino") ||
									genero.equals("transgênero") || genero.equals("transgenero") ||
									genero.equals("gênero neutro") || genero.equals("genero neutro") ||
									genero.equals("não binário") || genero.equals("nao binario") ||
									genero.equals("não binario") || genero.equals("nao binário"))
							{
								
								List<String> generosValidos = Arrays.asList("masculino", "feminino", "transgênero",
										"gênero neutro", "não binário");
								if (generosValidos.contains(genero)) {
									break;
								} else {
									if (genero.equals("transgenero")) {
										genero = "transgênero";
										break;
										
									} else if (genero.equals("genero neutro")) {
										genero = "gênero neutro";
										break;
										
									} else if (genero.equals("nao binario") ||
											genero.equals("não binario") || genero.equals("nao binário")) 
									{
										genero = "não binário";
										break;
									} else {
										System.out.println("Error: genero inválido, tente novamente!");
									}
								}
								
							} else {
								System.out.println("Error: genero inválido, tente novamente ");
							}
						}
						
						String email = null;
						while (true) {
							System.out.print("Digite seu email: ");
							email = input.nextLine().trim();
							
							if (email.isEmpty() == false) {
								try {
									Integer.parseInt(email);
									System.out.printf("Error: %s, tente novamente!\n", error[1]);
								} catch (Exception e) {
									if (email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")) {
					                    break;
					                } else {
					                    System.out.println("Error: email inválido, tente novamente!");
					                }
								}
							} else {
								System.out.printf("Error: %s, tente novamente!\n", error[0]);
							}
						}
						
						String endereco = null;
						while (true) {
							System.out.print("Digite seu endereço: ");
							endereco = input.nextLine().trim();
							
							if (endereco.isEmpty() == false) {
								if (endereco.matches("[A-Za-z-0-9 ]+")) {
									break;
								} else {
									System.out.printf("Error: %s, tente novamente!\n", error[3]);
								}
							} else {
								System.out.printf("Error: %s, tente novamente!\n", error[0]);
							}
						}
						
						String numero = null;
						while (true) {
							System.out.print("Digite o número da sua casa: ");
							numero = input.nextLine().trim();
							
							if (numero.isEmpty() == false) {
								try {
									Integer.parseInt(numero);
									break;
								} catch (Exception e) {
									System.out.printf("Error: %s, tente novamente!\n", error[2]);
								}
							} else {
								System.out.printf("Error: %s, tente novamente!\n", error[0]);
							}
						}
						
						String cep = null;
						while (true) {
							System.out.print("Digite o seu cep: ");
							cep = input.nextLine().trim();
							
							if (cep.isEmpty() == false) {
								try {
									Long.parseLong(cep);
									if (cep.length() == 8) {
										break;									
									} else {
										System.out.println("Error: insuficiente ou grande de mais, tente novamente!");
									}
								} catch (Exception e) {
									System.out.printf("Error: %s, tente novamente!\n", error[2]);
								}
							} else {
								System.out.printf("Error: %s, tente novamente!\n", error[0]);
							}
						}
						
						String bairro = null;
						while (true) {
							System.out.print("Digite seu bairro: ");
							bairro = input.nextLine().trim();
							
							if (bairro.isEmpty() == false) {
								try {
									Integer.parseInt(bairro);
									System.out.printf("Error: %s, tente novamente!\n", error[1]);
								} catch (Exception e) {
									if (bairro.matches("[A-Za-z-0-9 ]+")) {
										break;
									} else {
										System.out.printf("Error: %s, tente novamente!\n", error[3]);
									}
								}
							} else {
								System.out.printf("Error: %s, tente novamente!\n", error[0]);
							}
						}
						
						String cidade = null;
						while (true) {
							System.out.print("Digite a sua cidade: ");
							cidade = input.nextLine().trim();
							
							if (cidade.isEmpty() == false) {
								try {
									Integer.parseInt(cidade);
									System.out.printf("Error: %s, tente novamente!\n", error[1]);
								} catch (Exception e) {
									if (cidade.matches("[A-Za-z ]+")) {
										break;
									} else {
										System.out.printf("Error: %s, tente novamente!\n", error[3]);
									}
								}
							} else {
								System.out.printf("Error: %s, tente novamente!\n", error[0]);
							}
						}
						
						 List<String> estadosValidos = Arrays.asList(
						 "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", 
						 "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", 
						 "SP", "SE", "TO");
						
						String estado = null;
						while (true) {
							System.out.print("Digite a sigla do seu estado: ");
							estado = input.nextLine().toUpperCase().trim();
							
							if (estado.isEmpty() == false) {
								try {
									Integer.parseInt(estado);
									System.out.printf("Error: %s, tente novamente!\n", error[1]);
								} catch (Exception e) {
									if (estadosValidos.contains(estado)) {
										break;
									} else {
										System.out.println("Error: estado inválido, tente novamente!");
									}
								}
							} else {
								System.out.printf("Error: %s, tente novamente!\n", error[0]);
							}
						}
						
						String complemento = null;
						while (true) {
							System.out.print("Digite um complemento: ");
							complemento = input.nextLine().trim();
							break;
						}
						
						String nomePai = null;
						while (true) {
							System.out.print("Digite o nome do seu pai: ");
							nomePai = input.nextLine().trim();
							
								try {
									Integer.parseInt(nomePai);
									System.out.printf("Error: %s, tente novamente!\n", error[1]);
								} catch (Exception e) {
									if (nomePai.isEmpty() || nomePai.matches("[A-Za-z ]+")) {
										break;
									} else {
										System.out.printf("Error: %s, tente novamente!\n", error[3]);
									}
								}
							}
							
						
						String nomeMae = null;
						while (true) {
							System.out.print("Digite o nome da sua mãe: ");
							nomeMae = input.nextLine().trim();
							
							if (nomeMae.isEmpty() == false) {
								try {
									Integer.parseInt(nomeMae);
									System.out.printf("Error: %s, tente novamente!\n", error[1]);
								} catch (Exception e) {
									if (nomeMae.matches("[A-Za-z ]+")) {
										break;
									} else {
										System.out.printf("Error: %s, tente novamente!\n", error[3]);
									}
								}	
							} else {
								System.out.printf("Error: %s, tente novamente!\n", error[0]);
							}
						}
						
						 List<String> dddsValidos = Arrays.asList(
						 "11", "12", "13", "14", "15", "16", "17", "18", "19", "21", "22", "24", "27", "28",
						 "31", "32", "33", "34", "35", "37", "38", "41", "42", "43", "44", "45", "46", "47",
						 "48", "49", "51", "53", "54", "55", "61", "62", "63", "64", "65", "66", "67", "68",
						 "69", "71", "73", "74", "75", "77", "79", "81", "82", "83", "84", "85", "86", "87",
						 "88", "89", "91", "92", "93", "94", "95", "96", "97", "98", "99");
						
						String telefone = null;
						while (true) {
							System.out.print("Digite seu contato: ");
							telefone = input.nextLine().trim();
							
							if (telefone.isEmpty() == false) {
								String dds = telefone.substring(0, 2);
								
								if (dddsValidos.contains(dds)) {
									try {
										Long.parseLong(telefone);
										if (telefone.matches("[0-9]{11}")) {
											break;
										} else if (telefone.length() < 11){
											System.out.print("Error: quantidade de números insuficientes, tente novamente!\n");
										} else if (telefone.length() > 11) {
											System.out.println("Error: quantidade de números excede, tente novamente");
										}
									} catch (Exception e) {
										System.out.printf("Error: %s, tente novamente!\n", error[2]);
									}
									
								} else {
									System.out.println("Error: DDD inválido, tente novamente!");
								}
								
							} else {
								System.out.printf("Error: %s, tente novamente!\n", error[0]);
							}
						}
						
						cadastroUsuario.inserirDados(nomeCompleto, dataNascimento, cpf, rg, genero, email, endereco, numero, cep,
						bairro, cidade, estado, complemento, nomePai, nomeMae, telefone);
						
					} else if (opc2 == 2) {

						System.out.println("\nPara realizar uma alteração, digite o cpf do usuário,");
						System.out.println("o campo que deseja editar e a nova informação.\n");

						String cpfOpc = null;
						while (true) {
							System.out.print("Digite o cpf do usuário que deseja alterar: ");
							cpfOpc = input.nextLine().trim();
							
							if (cpfOpc.isEmpty() == false) {
								if (ValidaCpf.isCpf(cpfOpc)) {
									try {
										Long.parseLong(cpfOpc);
										break;
									} catch (Exception e) {
										System.out.printf("Error: %s, tente novamente!\n", error[2]);
									}
									
								} else {
									System.out.println("Error: cpf inválido, tente novamente!");
								}
								
							} else {
								System.out.printf("Error: %s, tente novamente!\n", error[0]);
							}
						}
						
						List<String> camposValidos = Arrays.asList("nome", "nome Completo", "data de nascimento",
								"rg", "gênero", "genero", "email", "e-mail", "endereço", "numero", "casa", "cep", 
								"bairro", "cidade", "estado", "complemento", "nome do pai", "nome da mae",
								"nome da mãe", "telefone", "contato" );
						String coluna = null;
						while (true) {
							System.out.print("Digite qual campo você deseja alterar: ");
							coluna = input.nextLine().trim().toLowerCase();;

							if (coluna.isEmpty() == false) {
								try {
									Long.parseLong(coluna);
									System.out.printf("Error: %s, tente novamente!\n", error[1]);
								} catch (Exception e) {
									if (camposValidos.contains(coluna)) {
										break;
									} else {
										System.out.println("Error: campo inválido, tente novamente!");
									}
								}
							} else {
								System.out.printf("Error: %s, tente novamente!\n", error[0]);
							}
						}
						
						cadastroUsuario.existeUsuario(cpfOpc, coluna);

					} else if (opc2 == 3) {
						
						System.out.println("\nIsso exibirá as informações de cada usuário");
						
						cadastroUsuario.listarUsuario();
							
					} else if (opc2 == 4) {
						
						System.out.println("\npara pesquisar um usuário específico,");
						System.out.println("basta digite o cpf do usuário que você está procurando\n");
						
						String cpfOpcProc = null;
						while (true) {
							System.out.print("Digite o cpf do usuário que deseja achar: ");
							cpfOpcProc = input.nextLine();
							
							if (cpfOpcProc.isEmpty() == false) {
								if (ValidaCpf.isCpf(cpfOpcProc)) {
									try {
										Long.parseLong(cpfOpcProc);
										cadastroUsuario.procurarUsuario(cpfOpcProc);
										break;
									} catch (Exception e) {
										System.out.printf("Error: %s, tente novamente!\n", error[2]);
									}
									
								} else {
									System.out.println("Error: cpf inválido, tente novamente!");
								}
								
							} else {
								System.out.printf("Error: %s, tente novamente!\n", error[0]);
							}
							
						}
						
					} else if (opc2 == 5) {	
						
						System.out.println("\npara remover um usuário do sistema,");
						System.out.println("basta digitar o cpf do usuário que deseja excluír\n");
						
						String cpfOpcApa = null;
						while (true) {
							System.out.print("Digite o cpf do usuário que deseja excluir: ");
							cpfOpcApa = input.nextLine();
							
							if (cpfOpcApa.isEmpty() == false) {
								if (ValidaCpf.isCpf(cpfOpcApa)) {
									try {
										Long.parseLong(cpfOpcApa);
										cadastroUsuario.apagarUsuario(cpfOpcApa);
										break;
									} catch (Exception e) {
										System.out.printf("Error: %s, tente novamente!\n", error[2]);
									}
									
								} else {
									System.out.println("Error: cpf inválido, tente novamente!");
								}
								
							} else {
								System.out.printf("Error: %s, tente novamente!\n", error[0]);
							}
							
						}
						

					} else if (opc2 == 6) {
						cadastroUsuario.desconectar();
						break;						
					} else {
						System.out.println("Error: valor digitado não corresponde a uma das opções, tente novamente!\n");
					}
					
					
				} catch (Exception e) {
					System.out.println("Error: valor digitado não existe, tente novamente!\n");
				}	
			}
		}		
		AnsiConsole.systemUninstall();
		input.close();
	}
}