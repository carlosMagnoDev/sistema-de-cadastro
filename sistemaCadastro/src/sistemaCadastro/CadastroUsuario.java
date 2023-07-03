package sistemaCadastro;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.util.*;

public class CadastroUsuario {
	
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultset = null;
	
	public void conectar() {
		String servidor = "jdbc:mysql://localhost:3306/sistema_cadastro?useSSL=false";
		String usuario = "seu_usuario";
		String senha = "sua_senha";
		String driver = "com.mysql.cj.jdbc.Driver";
		
		try {
			Class.forName(driver);
			this.connection = DriverManager.getConnection(servidor, usuario, senha);
			this.statement = this.connection.createStatement();
			
		} catch (Exception e) {
			System.out.println("Error " +e.getMessage());
		}
	}
	
	public boolean estaConectado() {
		if(this.connection != null) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public void inserirDados(String nomeCompleto, String dataNascimento, String cpf,
	String rg, String genero, String email, String endereco, String numero, String cep, String bairro, 
	String cidade, String estado, String complemento, String nomePai, String nomeMae, String telefone) {
		try {
			String query = "INSERT INTO usuario(nomeCompleto, dataNascimento, cpf, rg, genero, email, endereco,"
					+ " numero, cep, bairro, cidade, estado, complemento, nomePai, nomeMae, telefone)"
					+ " values('"+nomeCompleto+"', '"+dataNascimento+"', '"+cpf+"', '"+rg+"', '"+genero+"',"
					+ "'"+email+"', '"+endereco+"', '"+numero+"', '"+cep+"', '"+bairro+"', '"+cidade+"',"
					+ " '"+estado+"', '"+complemento+"', '"+nomePai+"', '"+nomePai+"', '"+telefone+"');";
			this.statement.executeUpdate(query);
			System.out.println("Dados do usuário inseridos com sucesso!!\n");
			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		
	}
	
	public void apagarUsuario(String cpf) {
		Scanner input = new Scanner(System.in);
	    try {
	        String query = "SELECT cpf FROM usuario WHERE cpf = '"+cpf+"';";
	        ResultSet resultSet = this.statement.executeQuery(query);

	        if (resultSet.next()) {
	            String deleteQuery = "DELETE FROM usuario WHERE cpf = '"+cpf+"';";
	            
            	System.out.print("Quer mesmo excluir este usuário? [S/N]:");
            	String confirm = input.nextLine().trim().toUpperCase();
            	
            	if (confirm.equals("S")) {
            		this.statement.executeUpdate(deleteQuery);
            		System.out.println("Usuário excluido com sucesso!\n");
            		
            	} else {
            		System.out.println("Usuário não foi excluido!\n");
            	}
	            
	        } else {
	            System.out.println("cpf do usuário não encontrado!\n");
	        }

	    } catch (Exception e) {
	        System.out.println("Erro ao excluir usuário: " + e.getMessage() + "\n");
	    }
	}
	
	public void listarUsuario() {
		try {
			System.out.println("\nLISTA DE USUÁRIOS:");
			String query = "SELECT * FROM usuario;";
			this.resultset = this.statement.executeQuery(query);
			while(this.resultset.next()) {
			System.out.println("NOME: " + this.resultset.getString("nomeCompleto") + "\nDATA NASCIMENTO: "
			+ this.resultset.getString("dataNascimento") + "\nCPF: " + this.resultset.getString("cpf")
			+ "\nRG: " + this.resultset.getString("rg") + "\nGENERO: " + this.resultset.getString("genero") + "\nEMAIL: " 
			+ this.resultset.getString("email") + "\nENDEREÇO: " + this.resultset.getString("endereco") + "\nNUMERO: "
			+ this.resultset.getString("numero") + "\nCEP: " + this.resultset.getString("cep") + "\nBAIRRO: "
			+ this.resultset.getString("bairro") + "\nCIDADE: " + this.resultset.getString("cidade") + "\nESTADO: "
			+ this.resultset.getString("estado") + "\nCOMPLEMENTO: " + this.resultset.getString("complemento") + "\nNOME DO PAI: "
			+ this.resultset.getString("nomePai") + "\nNOME DA MÃE: " + this.resultset.getString("nomeMae") + "\nTELEFONE: "
			+ this.resultset.getString("telefone"));
				System.out.println();
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public void procurarUsuario(String cpf) {
	    try {
	        String query = "SELECT * FROM usuario WHERE cpf = '"+cpf+"'";
	        this.resultset = this.statement.executeQuery(query);
	        
	        if (this.resultset.next()) {
	            do {
	            	System.out.println("\nUSUARIO:");
	                System.out.println("NOME: " + this.resultset.getString("nomeCompleto") + "\nDATA NASCIMENTO: " 
	            	+ this.resultset.getString("dataNascimento") + "\nCPF: " + this.resultset.getString("cpf")
	                + "\nRG: " + this.resultset.getString("rg") + "\nGENERO: " + this.resultset.getString("genero") + "\nEMAIL: "
	                + this.resultset.getString("email") + "\nENDEREÇO: " + this.resultset.getString("endereco") + "\nNUMERO: "
	                + this.resultset.getString("numero") + "\nCEP: " + this.resultset.getString("cep") + "\nBAIRRO: "
	                + this.resultset.getString("bairro") + "\nCIDADE: " + this.resultset.getString("cidade") + "\nESTADO: "
	                + this.resultset.getString("estado") + "\nCOMPLEMENTO: " + this.resultset.getString("complemento") + "\nNOME DO PAI: "
	                + this.resultset.getString("nomePai") + "\nNOME DA MÃE: " + this.resultset.getString("nomeMae") + "\nTELEFONE: "
	                + this.resultset.getString("telefone"));
	                System.out.println();
	            } while (this.resultset.next());
	        } else {
	        	System.out.println("usuário não foi encontando!\n");
	        }
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	}
	
	public void existeUsuario (String cpf, String coluna) {
		Scanner input = new Scanner(System.in);
		String [] error = {"campo vazio", "apenas letras", "apenas números", "caracteres especiais não são permitidos"};
		try {
			String query = "SELECT cpf FROM usuario WHERE cpf = '"+cpf+"';";
	        ResultSet resultSet = this.statement.executeQuery(query);
	        
	        if (resultSet.next()) {
	        	if (coluna.equalsIgnoreCase("nome") || coluna.equalsIgnoreCase("nome completo")) {
					
					String nomeCompleto2 = null;
					while (true) {
						System.out.print("Digite seu nome completo: ");
						nomeCompleto2 = input.nextLine().trim();
						
						if (nomeCompleto2.isEmpty() == false) {
							try {
								Integer.parseInt(nomeCompleto2);
								System.out.printf("Error: %s, tente novamente!\n", error[1]);
							} catch (Exception e) {
								if (nomeCompleto2.matches("[A-Za-z ]+")) {
									break;										
								} else {
									System.out.printf("Error: %s, tente novamente!\n", error[3]);
								}
							}
						} else {
							System.out.printf("Error: %s, tente novamente!\n", error[0]);
						}
					}
		        	
		        	coluna.replace(coluna, "nomeCompleto");
		        	String mudarCadastro = "UPDATE usuario SET nomeCompleto = '"+nomeCompleto2+"' WHERE cpf = '"+cpf+"';";
		        	this.statement.executeUpdate(mudarCadastro);
		        	System.out.println("Cadastro alterado com sucesso!\n");
		        	
	        	} else if (coluna.equalsIgnoreCase("dataNascimento") || coluna.equalsIgnoreCase("data de nascimento")) {
	        		
				       String dataNascimento2 = null;
				        while (true) {
				            System.out.print("Digite sua data de nascimento: ");
				            dataNascimento2 = input.nextLine().trim();

				            if (!dataNascimento2.isEmpty()) {
				                String validarData = "^(0[1-9]|1\\d|2\\d|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
				                String validarSomenteNumeros = "\\d+";
				                String validarSomenteLetras = "[a-zA-Z]+";

				                if (dataNascimento2.matches(validarData)) {
				                    SimpleDateFormat formatoDataOriginal = new SimpleDateFormat("dd/MM/yyyy");
				                    SimpleDateFormat formatoDataSql = new SimpleDateFormat("yyyy-MM-dd");
				                    Date date = formatoDataOriginal.parse(dataNascimento2);
				                    dataNascimento2 = formatoDataSql.format(date);
				                    break;
				                } else if (dataNascimento2.matches(validarSomenteNumeros)) {
				                    System.out.println("Erro: a data de nascimento deve conter apenas números, tente novamente");
				                } else if (dataNascimento2.matches(validarSomenteLetras)) {
				                    System.out.println("Erro: a data de nascimento deve conter apenas números, tente novamente");
				                } else {
				                    System.out.println("Erro: formato de data inválida, tente novamente! (dd/mm/aaaa)");				                }
				            } else {
				                System.out.printf("Erro: %s, tente novamente!\n", error[0]);
				            }
				        }
					
					coluna.replace(coluna, "dataNascimento");
		        	String mudarCadastro = "UPDATE usuario SET dataNascimento = '"+dataNascimento2+"' WHERE cpf = '"+cpf+"';";
		        	this.statement.executeUpdate(mudarCadastro);
		        	System.out.println("Cadastro alterado com sucesso!\n");
		        	
	        	} else if (coluna.equalsIgnoreCase("cpf")) {
	        		System.out.println("Error: cpf não pode ser alterado!");
	        		
	        	}  else if (coluna.equalsIgnoreCase("rg")) {
		        	
					String rg2 = null;
					while (true) {
						System.out.print("Digite seu RG: ");
						rg2 = input.nextLine().trim();
						
						if (rg2.isEmpty() == false) {
							try {
								Long.parseLong(rg2);
								if (rg2.length() >= 3 && rg2.length() <= 13) {
									break;									
								} else if (rg2.length() < 3) {
									System.out.println("Error: rg insuficiente, tente novamente!");
								} else if (rg2.length() > 13) {
									System.out.println("Error: rg muito grande, tente novamente!");
								}
							} catch (Exception e) {
								System.out.printf("Error: %s, tente novamente!\n", error[2]);
							}
						} else {
							System.out.printf("Error: %s, tente novamente!\n", error[0]);
						}
					}
	        		
	        		String mudarCadastro = "UPDATE usuario SET rg = '"+rg2+"' WHERE cpf = '"+cpf+"';";
		        	this.statement.executeUpdate(mudarCadastro);
		        	System.out.println("Cadastro alterado com sucesso!\n");
		        	
	        	}  else if (coluna.equalsIgnoreCase("gênero") || coluna.equalsIgnoreCase("genero")) {
		        	
					String genero2 = null;
					while (true) {
						System.out.print("Digite seu genero: ");
						genero2 = input.nextLine().trim().toLowerCase();	
						
						if (genero2.equals("masculino") || genero2.equals("feminino") ||
								genero2.equals("transgênero") || genero2.equals("transgenero") ||
								genero2.equals("gênero neutro") || genero2.equals("genero neutro") ||
								genero2.equals("não binário") || genero2.equals("nao binario") ||
								genero2.equals("não binario") || genero2.equals("nao binário"))
						{
							
							List<String> generosValidos = Arrays.asList("masculino", "feminino", "transgênero",
									"gênero neutro", "não binário");
							if (generosValidos.contains(genero2)) {
								break;
							} else {
								if (genero2.equals("transgenero")) {
									genero2 = "transgênero";
									break;
									
								} else if (genero2.equals("genero neutro")) {
									genero2 = "gênero neutro";
									break;
									
								} else if (genero2.equals("nao binario") ||
										genero2.equals("não binario") || genero2.equals("nao binário")) 
								{
									genero2 = "não binário";
									break;
								} else {
									System.out.println("Error: genero inválido, tente novamente!");
								}
							}
							
						} else {
							System.out.println("Error: genero inválido, tente novamente ");
						}
					}
	        		
					coluna.replace(coluna, "genero");
	        		String mudarCadastro = "UPDATE usuario SET genero = '"+genero2+"' WHERE cpf = '"+cpf+"';";
		        	this.statement.executeUpdate(mudarCadastro);
		        	System.out.println("Cadastro alterado com sucesso!\n");
		        	
	        	}  else if (coluna.equalsIgnoreCase("email") || coluna.equalsIgnoreCase("e-mail")) {
		        	
					String email2 = null;
					while (true) {
						System.out.print("Digite seu email: ");
						email2 = input.nextLine().trim();
						
						if (email2.isEmpty() == false) {
							try {
								Integer.parseInt(email2);
								System.out.printf("Error: %s, tente novamente!\n", error[1]);
							} catch (Exception e) {
								if (email2.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")) {
				                    break;
				                } else {
				                    System.out.println("Error: email inválido, tente novamente!");
				                }
							}
						} else {
							System.out.printf("Error: %s, tente novamente!\n", error[0]);
						}
					}
	        		
					coluna.replace(coluna, "email");
	        		String mudarCadastro = "UPDATE usuario SET email = '"+email2+"' WHERE cpf = '"+cpf+"';";
		        	this.statement.executeUpdate(mudarCadastro);
		        	System.out.println("Cadastro alterado com sucesso!\n");
		        	
	        	} else if (coluna.equalsIgnoreCase("endereco") || coluna.equalsIgnoreCase("endereço")) {
	        		
					String endereco2 = null;
					while (true) {
						System.out.print("Digite seu endereço: ");
						endereco2 = input.nextLine().trim();
						
						if (endereco2.isEmpty() == false) {
							if (endereco2.matches("[A-Za-z-0-9 ]+")) {
								break;
							} else {
								System.out.printf("Error: %s, tente novamente!\n", error[3]);
							}
						} else {
							System.out.printf("Error: %s, tente novamente!\n", error[0]);
						}
					}
		        	
					coluna.replace(coluna, "endereco");
	        		String mudarCadastro = "UPDATE usuario SET endereco = '"+endereco2+"' WHERE cpf = '"+cpf+"';";
		        	this.statement.executeUpdate(mudarCadastro);
		        	System.out.println("Cadastro alterado com sucesso!\n");

	        	} else if (coluna.equalsIgnoreCase("numero") || coluna.equalsIgnoreCase("casa")) {
		        	
					String numero2 = null;
					while (true) {
						System.out.print("Digite o número da sua casa: ");
						numero2 = input.nextLine().trim();
						
						if (numero2.isEmpty() == false) {
							try {
								Integer.parseInt(numero2);
								break;
							} catch (Exception e) {
								System.out.printf("Error: %s, tente novamente!\n", error[2]);
							}
						} else {
							System.out.printf("Error: %s, tente novamente!\n", error[0]);
						}
					}
	        		
					coluna.replace(coluna, "numero");
	        		String mudarCadastro = "UPDATE usuario SET numero = '"+numero2+"' WHERE cpf = '"+cpf+"';";
		        	this.statement.executeUpdate(mudarCadastro);
		        	System.out.println("Cadastro alterado com sucesso!\n");

	        	} else if (coluna.equalsIgnoreCase("cep")) {
		        	
					String cep2 = null;
					while (true) {
						System.out.print("Digite o seu cep: ");
						cep2 = input.nextLine().trim();
						
						if (cep2.isEmpty() == false) {
							try {
								Long.parseLong(cep2);
								if (cep2.length() == 8) {
									break;									
								} else {
									System.out.println("Error: insuficiente, tente novamente!");
								}
							} catch (Exception e) {
								System.out.printf("Error: %s, tente novamente!\n", error[2]);
							}
						} else {
							System.out.printf("Error: %s, tente novamente!\n", error[0]);
						}
					}
	        		
	        		String mudarCadastro = "UPDATE usuario SET cep = '"+cep2+"' WHERE cpf = '"+cpf+"';";
		        	this.statement.executeUpdate(mudarCadastro);
		        	System.out.println("Cadastro alterado com sucesso!\n");

	        	} else if (coluna.equalsIgnoreCase("bairro")) {
		        	
					String bairro2 = null;
					while (true) {
						System.out.print("Digite seu bairro: ");
						bairro2 = input.nextLine().trim();
						
						if (bairro2.isEmpty() == false) {
							try {
								Integer.parseInt(bairro2);
								System.out.printf("Error: %s, tente novamente!\n", error[1]);
							} catch (Exception e) {
								if (bairro2.matches("[A-Za-z-0-9 ]+")) {
									break;
								} else {
									System.out.printf("Error: %s, tente novamente!\n", error[3]);
								}
							}
						} else {
							System.out.printf("Error: %s, tente novamente!\n", error[0]);
						}
					}
	        		
	        		String mudarCadastro = "UPDATE usuario SET bairro = '"+bairro2+"' WHERE cpf = '"+cpf+"';";
		        	this.statement.executeUpdate(mudarCadastro);
		        	System.out.println("Cadastro alterado com sucesso!\n");

	        	} else if (coluna.equalsIgnoreCase("cidade")) {
	        		
					String cidade2 = null;
					while (true) {
						System.out.print("Digite a sua cidade: ");
						cidade2 = input.nextLine().trim();
						
						if (cidade2.isEmpty() == false) {
							try {
								Integer.parseInt(cidade2);
								System.out.printf("Error: %s, tente novamente!\n", error[1]);
							} catch (Exception e) {
								if (cidade2.matches("[A-Za-z ]+")) {
									break;
								} else {
									System.out.printf("Error: %s, tente novamente!\n", error[3]);
								}
							}
						} else {
							System.out.printf("Error: %s, tente novamente!\n", error[0]);
						}
					}
		        	
	        		String mudarCadastro = "UPDATE usuario SET cidade = '"+cidade2+"' WHERE cpf = '"+cpf+"';";
		        	this.statement.executeUpdate(mudarCadastro);
		        	System.out.println("Cadastro alterado com sucesso!\n");

	        	} else if (coluna.equalsIgnoreCase("estado")) {
	        		
					 List<String> estadosValidos = Arrays.asList(
					 "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", 
					 "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", 
					 "SP", "SE", "TO");
					
					String estado2 = null;
					while (true) {
						System.out.print("Digite a sigla do seu estado: ");
						estado2 = input.nextLine().toUpperCase().trim();
						
						if (estado2.isEmpty() == false) {
							try {
								Integer.parseInt(estado2);
								System.out.printf("Error: %s, tente novamente!\n", error[1]);
							} catch (Exception e) {
								if (estadosValidos.contains(estado2)) {
									break;
								} else {
									System.out.println("Error: estado inválido, tente novamente!");
								}
							}
						} else {
							System.out.printf("Error: %s, tente novamente!\n", error[0]);
						}
					}
		        	
	        		String mudarCadastro = "UPDATE usuario SET estado = '"+estado2+"' WHERE cpf = '"+cpf+"';";
		        	this.statement.executeUpdate(mudarCadastro);
		        	System.out.println("Cadastro alterado com sucesso!\n");

	        	} else if (coluna.equalsIgnoreCase("complemento")) {
	        		
					String complemento2 = null;
					while (true) {
						System.out.print("Digite um complemento: ");
						complemento2 = input.nextLine().trim();
						
						if (complemento2.isEmpty() == false) {
							break;
							
						} else {
							System.out.printf("Error: %s, tente novamente!\n", error[0]);
						}
					}
		        	
	        		String mudarCadastro = "UPDATE usuario SET complemento = '"+complemento2+"' WHERE cpf = '"+cpf+"';";
		        	this.statement.executeUpdate(mudarCadastro);
		        	System.out.println("Cadastro alterado com sucesso!\n");

	        	} else if (coluna.equalsIgnoreCase("nome do pai")) {
	        		
					String nomePai2 = null;
					while (true) {
						System.out.print("Digite o nome do seu pai: ");
						nomePai2 = input.nextLine().trim();
						
						if (nomePai2.isEmpty() == false) {
							
							try {
								Integer.parseInt(nomePai2);
								System.out.printf("Error: %s, tente novamente!\n", error[1]);
							} catch (Exception e) {
								if (nomePai2.matches("[A-Za-z ]+")) {
									break;
								} else {
									System.out.printf("Error: %s, tente novamente!\n", error[3]);
								}
							}
							
						} else {
							System.out.printf("Error: %s, tente novamente!\n", error[0]);
						}
						
					}
		        	
					coluna.replace(coluna, "nomePai");
	        		String mudarCadastro = "UPDATE usuario SET nomePai = '"+nomePai2+"' WHERE cpf = '"+cpf+"';";
		        	this.statement.executeUpdate(mudarCadastro);
		        	System.out.println("Cadastro alterado com sucesso!\n");

	        	} else if (coluna.equalsIgnoreCase("nome da mae") || coluna.equalsIgnoreCase("nome da mãe")) {
	        		
					String nomeMae2 = null;
					while (true) {
						System.out.print("Digite o nome da sua mãe: ");
						nomeMae2 = input.nextLine().trim();
						
						if (nomeMae2.isEmpty() == false) {
							try {
								Integer.parseInt(nomeMae2);
								System.out.printf("Error: %s, tente novamente!\n", error[1]);
							} catch (Exception e) {
								if (nomeMae2.matches("[A-Za-z ]+")) {
									break;
								} else {
									System.out.printf("Error: %s, tente novamente!\n", error[3]);
								}
							}	
						} else {
							System.out.printf("Error: %s, tente novamente!\n", error[0]);
						}
					}
		        	
					coluna.replace(coluna, "nomeMae");
	        		String mudarCadastro = "UPDATE usuario SET nomeMae = '"+nomeMae2+"' WHERE cpf = '"+cpf+"';";
		        	this.statement.executeUpdate(mudarCadastro);
		        	System.out.println("Cadastro alterado com sucesso!\n");

	        	} else if (coluna.equalsIgnoreCase("telefone") || coluna.equalsIgnoreCase("contato")) {
	        		
					 List<String> dddsValidos = Arrays.asList(
					 "11", "12", "13", "14", "15", "16", "17", "18", "19", "21", "22", "24", "27", "28",
					 "31", "32", "33", "34", "35", "37", "38", "41", "42", "43", "44", "45", "46", "47",
					 "48", "49", "51", "53", "54", "55", "61", "62", "63", "64", "65", "66", "67", "68",
					 "69", "71", "73", "74", "75", "77", "79", "81", "82", "83", "84", "85", "86", "87",
					 "88", "89", "91", "92", "93", "94", "95", "96", "97", "98", "99");
					
					String telefone2 = null;
					while (true) {
						System.out.print("Digite seu contato: ");
						telefone2 = input.nextLine().trim();
						
						if (telefone2.isEmpty() == false) {
							String dds = telefone2.substring(0, 2);
							
							if (dddsValidos.contains(dds)) {
								try {
									Long.parseLong(telefone2);
									if (telefone2.matches("[0-9]{11}")) {
										break;
									} else if (telefone2.length() < 11){
										System.out.print("Error: quantidade de números insuficientes, tente novamente!\n");
									} else if (telefone2.length() > 11) {
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
		        	
					coluna.replace(coluna, "telefone");
	        		String mudarCadastro = "UPDATE usuario SET telefone = '"+telefone2+"' WHERE cpf = '"+cpf+"';";
		        	this.statement.executeUpdate(mudarCadastro);
		        	System.out.println("Cadastro alterado com sucesso!\n");
	        	

	        } else {
	        	System.out.println("Error: coluna não existe, tente novamente!");
	        }
	      }	
	        
	        
	        
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public void desconectar() {
		try {
			System.out.println("Sistema encerrado!!");
			this.connection.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
  	
}
