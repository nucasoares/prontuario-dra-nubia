package prontuario.drnubia.app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import prontuario.drnubia.dao.ExameDAO;
import prontuario.drnubia.dao.PacienteDAO;
import prontuario.drnubia.database.DatabaseConnectionMySQL;
import prontuario.drnubia.model.Exame;
import prontuario.drnubia.model.Paciente;

public class ExameConsoleApp {

    private static ExameDAO exameDAO;
    private static PacienteDAO pacienteDAO;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void executar(Scanner scanner) {
        exameDAO = new ExameDAO(new DatabaseConnectionMySQL());
        pacienteDAO = new PacienteDAO(new DatabaseConnectionMySQL());

        boolean running = true;
        while (running) {
            System.out.println("\n--- Menu Exames ---");
            System.out.println("1. Listar Exames");
            System.out.println("2. Cadastrar Exame");
            System.out.println("3. Editar Exame");
            System.out.println("4. Deletar Exame");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    listarExames();
                    break;
                case "2":
                    cadastrarExame(scanner);
                    break;
                case "3":
                    editarExame(scanner);
                    break;
                case "4":
                    deletarExame(scanner);
                    break;
                case "0":
                    running = false;
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void listarExames() {
        List<Exame> exames = exameDAO.findAll();
        System.out.println("\nLista de Exames:");
        if (exames.isEmpty()) {
            System.out.println("Nenhum exame cadastrado.");
        } else {
            for (Exame e : exames) {
                System.out.printf("ID: %d | Descrição: %s | Data: %s | Paciente ID: %d%n",
                        e.getId(),
                        e.getDescricao(),
                        e.getDataExame().format(FORMATTER),
                        e.getPaciente().getId());
            }
        }
    }

    private static void cadastrarExame(Scanner scanner) {
        System.out.print("Descrição do exame: ");
        String descricao = scanner.nextLine();

        System.out.print("Data do exame (formato yyyy-MM-dd HH:mm): ");
        String dataStr = scanner.nextLine();
        LocalDateTime data;
        try {
            data = LocalDateTime.parse(dataStr, FORMATTER);
        } catch (Exception e) {
            System.out.println("Formato de data inválido.");
            return;
        }

        System.out.print("ID do paciente: ");
        Long pacienteId;
        try {
            pacienteId = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        Paciente paciente = pacienteDAO.findById(pacienteId);
        if (paciente == null) {
            System.out.println("Paciente não encontrado.");
            return;
        }

        Exame exame = new Exame();
        exame.setDescricao(descricao);
        exame.setDataExame(data);
        exame.setPaciente(paciente);

        exameDAO.create(exame);
        System.out.println("Exame cadastrado com sucesso. ID: " + exame.getId());
    }

    private static void editarExame(Scanner scanner) {
        System.out.print("ID do exame para editar: ");
        Long id;
        try {
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        Exame exame = exameDAO.findById(id);
        if (exame == null) {
            System.out.println("Exame não encontrado.");
            return;
        }

        System.out.printf("Descrição atual (%s). Nova descrição (enter para manter): ", exame.getDescricao());
        String novaDescricao = scanner.nextLine();
        if (!novaDescricao.trim().isEmpty()) {
            exame.setDescricao(novaDescricao);
        }

        System.out.printf("Data atual (%s). Nova data (yyyy-MM-dd HH:mm, enter para manter): ", exame.getDataExame().format(FORMATTER));
        String novaData = scanner.nextLine();
        if (!novaData.trim().isEmpty()) {
            try {
                exame.setDataExame(LocalDateTime.parse(novaData, FORMATTER));
            } catch (Exception e) {
                System.out.println("Formato de data inválido. Mantendo a data atual.");
            }
        }

        System.out.printf("ID atual do paciente (%d). Novo ID (enter para manter): ", exame.getPaciente().getId());
        String novoPacienteId = scanner.nextLine();
        if (!novoPacienteId.trim().isEmpty()) {
            try {
                Paciente novoPaciente = pacienteDAO.findById(Long.parseLong(novoPacienteId));
                if (novoPaciente != null) {
                    exame.setPaciente(novoPaciente);
                } else {
                    System.out.println("Paciente não encontrado. Mantendo o atual.");
                }
            } catch (NumberFormatException e) {
                System.out.println("ID inválido. Mantendo o paciente atual.");
            }
        }

        exameDAO.update(exame);
        System.out.println("Exame atualizado com sucesso.");
    }

    private static void deletarExame(Scanner scanner) {
        System.out.print("ID do exame para deletar: ");
        Long id;
        try {
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        Exame exame = exameDAO.findById(id);
        if (exame == null) {
            System.out.println("Exame não encontrado.");
            return;
        }

        System.out.print("Confirma exclusão? (S/N): ");
        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("S")) {
            exameDAO.delete(exame);
            System.out.println("Exame deletado com sucesso.");
        } else {
            System.out.println("Exclusão cancelada.");
        }
    }
}
