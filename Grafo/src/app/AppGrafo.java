package app;

import lib.Aresta;
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
                    if (grafoCidades.isConexo()) {
                        grafoCidades.calcularAGM(); // Chamar o método a partir da instância grafoCidades
                    } else {
                        System.err.println("O grafo não é conexo. Não é possível calcular a AGM.");
                    }
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

        if (grafoCidades.isConexo()) {
            gravarGrafoEmArquivo(grafoCidades.calcularAGM(), "agm.txt");
        } else {
            System.err.println("O grafo não é conexo. A AGM não será gravada.");
        }

        System.out.println("Saindo...");
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
            int numVertices = grafo.getVertices().size();
            writer.println(numVertices); // Número de vértices

            // Escrever os nomes dos vértices
            for (Vertice<String> vertice : grafo.getVertices()) {
                writer.println(vertice.getValor());
            }

            // Escrever a matriz de adjacência com os pesos
            for (Vertice<String> verticeOrigem : grafo.getVertices()) {
                for (Vertice<String> verticeDestino : grafo.getVertices()) {
                    float peso = 0; // Valor padrão para quando não há aresta
                    for (Aresta aresta : verticeOrigem.getDestinos()) {
                        if (aresta.getDestino() == verticeDestino) {
                            peso = aresta.getPeso();
                            break;
                        }
                    }
                    writer.print(peso);
                    if (verticeDestino != grafo.getVertices().get(numVertices - 1)) {
                        writer.print(",");
                    }
                }
                writer.println();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao gravar arquivo: " + nomeArquivo);
        }
    }


    private void adicionarCidade(Scanner scanner) {
        System.out.print("Nome da cidade: ");
        String nomeCidade = scanner.nextLine();

        // Verificar se a cidade já existe
        if (grafoCidades.obterVertice(nomeCidade) != null) {
            System.out.println("A cidade " + nomeCidade + " já existe no grafo.");
        } else {
            grafoCidades.adicionarVertice(nomeCidade);
            System.out.println("Cidade " + nomeCidade + " adicionada com sucesso!");
        }
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


