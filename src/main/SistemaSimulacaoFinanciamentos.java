package main;

import model.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class SistemaSimulacaoFinanciamentos {
    private static ArrayList<Financiamento> financiamentos = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Sistema de Simulação de Financiamentos ===");
            System.out.println("1. Adicionar Financiamento");
            System.out.println("2. Listar Financiamentos");
            System.out.println("3. Salvar Financiamentos em Arquivo");
            System.out.println("4. Carregar Financiamentos de Arquivo");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir quebra de linha

            try {
                switch (opcao) {
                    case 1:
                        adicionarFinanciamento(scanner);
                        break;
                    case 2:
                        listarFinanciamentos();
                        break;
                    case 3:
                        salvarFinanciamentos();
                        break;
                    case 4:
                        carregarFinanciamentos();
                        break;
                    case 5:
                        System.out.println("Saindo...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private static void adicionarFinanciamento(Scanner scanner) {
        System.out.print("Nome do cliente: ");
        String cliente = scanner.nextLine();

        System.out.print("Valor do imóvel: ");
        double valorImovel = scanner.nextDouble();

        System.out.print("Duração (meses): ");
        int duracaoMeses = scanner.nextInt();

        System.out.print("Taxa de juros anual (%): ");
        double taxaJuros = scanner.nextDouble();

        System.out.println("Tipo de financiamento: 1. Casa | 2. Terreno | 3. Apartamento");
        int tipo = scanner.nextInt();
        scanner.nextLine(); // Consumir quebra de linha

        Financiamento financiamento;
        switch (tipo) {
            case 1:
                financiamento = new Casa(cliente, valorImovel, duracaoMeses, taxaJuros);
                break;
            case 2:
                financiamento = new Terreno(cliente, valorImovel, duracaoMeses, taxaJuros);
                break;
            case 3:
                financiamento = new Apartamento(cliente, valorImovel, duracaoMeses, taxaJuros);
                break;
            default:
                throw new IllegalArgumentException("Tipo de financiamento inválido.");
        }

        financiamentos.add(financiamento);
        System.out.println("Financiamento adicionado com sucesso.");
    }

    private static void listarFinanciamentos() {
        if (financiamentos.isEmpty()) {
            System.out.println("Nenhum financiamento cadastrado.");
        } else {
            financiamentos.forEach(System.out::println);
        }
    }

    private static void salvarFinanciamentos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("financiamentos.dat"))) {
            oos.writeObject(financiamentos);
            System.out.println("Financiamentos salvos com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar financiamentos: " + e.getMessage());
        }
    }

    private static void carregarFinanciamentos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("financiamentos.dat"))) {
            financiamentos = (ArrayList<Financiamento>) ois.readObject();
            System.out.println("Financiamentos carregados com sucesso.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar financiamentos: " + e.getMessage());
        }
    }
}
