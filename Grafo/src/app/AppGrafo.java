package app;

import lib.Grafo;
import lib.Vertice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class AppGrafo {
    private Grafo<String> grafoCidades;

    public AppGrafo() {
        grafoCidades = new Grafo<>();
        carregarGrafoDeArquivo("entrada.txt");
    }
    public static void main(String[] args) {
        AppGrafo app = new AppGrafo();
        app.executarMenu();
    }

    public void executarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Acrescentar cidade");
            System.out.println("2. Acrescentar rota");
            System.out.println("3. Calcular árvore geradora mínima (AGM)");
            System.out.println("4. Calcular caminho mínimo entre duas cidades");
            System.out.println("5. Calcular caminho mínimo (considerando AGM)");
            System.out.println("6. Gravar e Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    adicionarCidade(scanner);
                    break;
                case 2:
                    adicionarRota(scanner);
                    break;
                case 3:
                    Grafo<String> agm = grafoCidades.calcularAGM();
                    break;
                case 4:
                    calcularCaminhoMinimo(scanner, grafoCidades); // Grafo original
                    break;
                case 5:
                    calcularCaminhoMinimo(scanner, grafoCidades.calcularAGM()); // Grafo da AGM
                    break;
                case 6:
                    gravarGrafosESair();
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 6);
    }

    private void gravarGrafosESair() {
        gravarGrafoEmArquivo(grafoCidades, "grafoCompleto.txt");
        gravarGrafoEmArquivo(grafoCidades.calcularAGM(), "agm.txt");
        System.out.println("Grafos gravados com sucesso. Saindo...");
    }

    private void carregarGrafoDeArquivo(String nomeArquivo) {
        try (Scanner scanner = new Scanner(new File(nomeArquivo))) {
            int numCidades = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha após o número

            String[] cidades = new String[numCidades];
            for (int i = 0; i < numCidades; i++) {
                cidades[i] = scanner.nextLine();
                grafoCidades.adicionarVertice(cidades[i]);
            }

            for (int i = 0; i < numCidades; i++) {
                String[] distanciasStr = scanner.nextLine().split(",");
                for (int j = 0; j < numCidades; j++) {
                    float distancia = Float.parseFloat(distanciasStr[j]);
                    if (distancia > 0) {
                        grafoCidades.adicionarAresta(cidades[i], cidades[j], distancia);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + nomeArquivo);
        }
    }

    private void gravarGrafoEmArquivo(Grafo<String> grafo, String nomeArquivo) {
        try (PrintWriter writer = new PrintWriter(new File(nomeArquivo))) {
            writer.println(grafo.getVertices().size());
            for (Vertice<String> vertice : grafo.getVertices()) {
                writer.println(vertice.getValor());
            }
            // ... (gravar a matriz de adjacência)
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao gravar arquivo: " + nomeArquivo);
        }
    }

private void adicionarCidade(Scanner scanner) {
    System.out.print("Nome da cidade: ");
    String nomeCidade = scanner.nextLine();
    grafoCidades.adicionarVertice(nomeCidade);
}

private void adicionarRota(Scanner scanner) {
    System.out.print("Cidade de origem: ");
    String origem = scanner.nextLine();
    System.out.print("Cidade de destino: ");
    String destino = scanner.nextLine();
    System.out.print("Distância: ");
    float distancia = scanner.nextFloat();
    scanner.nextLine(); // Consumir a quebra de linha
    grafoCidades.adicionarAresta(origem, destino, distancia);
}
    private void calcularCaminhoMinimo(Scanner scanner, Grafo<String> grafo) {
        System.out.print("Cidade de origem: ");
        String origem = scanner.nextLine();
        System.out.print("Cidade de destino: ");
        String destino = scanner.nextLine();
        grafo.calcularCaminhoMinimo(origem, destino);
    }
}


