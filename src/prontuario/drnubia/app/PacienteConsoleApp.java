package prontuario.drnubia.app;

import java.util.List;
import java.util.Scanner;

import prontuario.drnubia.dao.PacienteDAO;
import prontuario.drnubia.database.DatabaseConnectionMySQL;
import prontuario.drnubia.exception.PacienteJaCadastradoException;
import prontuario.drnubia.model.Paciente;

public class PacienteConsoleApp {

    private static PacienteDAO pacienteDAO = new PacienteDAO(new DatabaseConnectionMySQL());

    public static void executar(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Menu Pacientes ---");
            System.out.println("1. Listar Pacientes");
            System.out.println("2. Cadastrar Paciente");
            System.out.println("3. Editar Paciente");
            System.out.println("4. Deletar Paciente");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha: ");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    listarPacientes();
                    break;
                case "2":
                    cadastrarPaciente(scanner);
                    break;
                case "3":
                    editarPaciente(scanner);
                    break;
                case "4":
                    deletarPaciente(scanner);
                    break;
                case "0":
                    running = false;
                    System.out.println("Voltando ao Menu Principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void listarPacientes() {
        List<Paciente> lista = pacienteDAO.findAll();
        System.out.println("\nLista de Pacientes:");
        if (lista.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
        } else {
            for (Paciente p : lista) {
               // System.out.printf("ID: %d | Nome: %s | CPF: %s%n", p.getId(), p.getNome(), p.getCpf());
                System.out.printf("Nome: %s | CPF: %s%n", p.getNome(), p.getCpf());
            }
        }
    }

    private static void cadastrarPaciente(Scanner scanner) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        Paciente paciente = new Paciente();
        paciente.setNome(nome);
        paciente.setCpf(cpf);

        try {
            pacienteDAO.create(paciente);
            System.out.println("Paciente cadastrado com sucesso! ID: " + paciente.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro no cadastro: " + e.getMessage());
        } catch(PacienteJaCadastradoException e){
        	 System.out.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado ao cadastrar paciente.");
            e.printStackTrace();
        }
    }

    private static void editarPaciente(Scanner scanner) {
        System.out.print("ID do paciente para editar: ");
        Long id = Long.parseLong(scanner.nextLine());

        Paciente paciente = pacienteDAO.findById(id);
        if (paciente == null) {
            System.out.println("Paciente não encontrado.");
            return;
        }

        System.out.printf("Nome atual (%s). Novo nome (enter para manter): ", paciente.getNome());
        String nome = scanner.nextLine();
        if (!nome.trim().isEmpty()) {
            paciente.setNome(nome);
        }

        System.out.printf("CPF atual (%s). Novo CPF (enter para manter): ", paciente.getCpf());
        String cpf = scanner.nextLine();
        if (!cpf.trim().isEmpty()) {
            paciente.setCpf(cpf);
        }

        pacienteDAO.update(paciente);
        System.out.println("Paciente atualizado com sucesso.");
    }

    private static void deletarPaciente(Scanner scanner) {
        System.out.print("ID do paciente para deletar: ");
        Long id = Long.parseLong(scanner.nextLine());

        Paciente paciente = pacienteDAO.findById(id);
        if (paciente == null) {
            System.out.println("Paciente não encontrado.");
            return;
        }

        System.out.print("Confirma exclusão? (S/N): ");
        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("S")) {
            pacienteDAO.delete(paciente);
            System.out.println("Paciente deletado com sucesso.");
        } else {
            System.out.println("Exclusão cancelada.");
        }
    }
}
