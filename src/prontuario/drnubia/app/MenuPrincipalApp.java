package prontuario.drnubia.app;

import java.util.Scanner;

public class MenuPrincipalApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n====== MENU PRINCIPAL ======");
            System.out.println("1. Área de Pacientes");
            System.out.println("2. Área de Exames");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1 -> prontuario.drnubia.app.PacienteConsoleApp.executar(scanner);
                case 2 -> prontuario.drnubia.app.ExameConsoleApp.executar(scanner);
                case 0 -> System.out.println("Encerrando o sistema...");
                default -> System.out.println("Opção inválida!");
            }

        } while (opcao != 0);

        scanner.close();
    }
}
