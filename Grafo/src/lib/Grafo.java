package lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Grafo<T> {
    private ArrayList<Vertice<T>> vertices;

    public Grafo() {
        vertices = new ArrayList<>();
    }

    public Vertice<T> adicionarVertice(T valor) {
        Vertice<T> novo = new Vertice<>(valor);
        this.vertices.add(novo);
        return novo;
    }

    private Vertice<T> obterVertice(T valor) {
        for (Vertice<T> v : this.vertices) {
            if (v.getValor().equals(valor)) {
                return v;
            }
        }
        return null;
    }

    public void adicionarAresta(T origem, T destino, float peso) {
        Vertice<T> verticeOrigem = obterVertice(origem);
        Vertice<T> verticeDestino = obterVertice(destino);

        if (verticeOrigem == null) {
            verticeOrigem = adicionarVertice(origem);
        }
        if (verticeDestino == null) {
            verticeDestino = adicionarVertice(destino);
        }

        // Verificar se a aresta já existe (opcional)
        for (Aresta aresta : verticeOrigem.getDestinos()) {
            if (aresta.getDestino() == verticeDestino) {
                return; // Aresta já existe, não adiciona novamente
            }
        }

        verticeOrigem.adicionarDestino(new Aresta(verticeOrigem, verticeDestino, peso));
        verticeDestino.adicionarDestino(new Aresta(verticeDestino, verticeOrigem, peso));
    }

    public ArrayList<Vertice<T>> getVertices() {
        return vertices;
    }

    // Algoritmo de Kruskal para AGM
    public Grafo<T> calcularAGM() {
        Grafo<T> agm = new Grafo<>();
        if (this.getVertices().isEmpty()) {
            return agm; // Grafo vazio, AGM também é vazio
        }

        Vertice<T> verticeInicial = this.getVertices().get(0); // Começa com o primeiro vértice
        PriorityQueue<Aresta> filaPrioridade = new PriorityQueue<>(Comparator.comparingDouble(Aresta::getPeso));
        Set<Vertice<T>> verticesIncluidos = new HashSet<>();
        verticesIncluidos.add(verticeInicial);

        for (Aresta aresta : verticeInicial.getDestinos()) {
            filaPrioridade.offer(aresta);
        }

        while (!filaPrioridade.isEmpty()) {
            Aresta arestaMenor = filaPrioridade.poll();
            Vertice<T> origem = arestaMenor.getOrigem();
            Vertice<T> destino = arestaMenor.getDestino();

            if (!verticesIncluidos.contains(destino)) {
                agm.adicionarVertice(origem.getValor());
                agm.adicionarVertice(destino.getValor());
                agm.adicionarAresta(origem.getValor(), destino.getValor(), arestaMenor.getPeso());
                verticesIncluidos.add(destino);

                for (Aresta aresta : destino.getDestinos()) {
                    if (!verticesIncluidos.contains(aresta.getDestino())) {
                        filaPrioridade.offer(aresta);
                    }
                }
            }
        }

        System.out.println("Árvore Geradora Mínima (AGM):");
        double pesoTotal = 0;
        Set<String> arestasProcessadas = new HashSet<>();

        for (Vertice<T> v : agm.getVertices()) {
            for (Aresta a : v.getDestinos()) {
                // Cria uma representação única da aresta, independente da direção
                String aresta = v.getValor() + "-" + a.getDestino().getValor();
                String arestaInversa = a.getDestino().getValor() + "-" + v.getValor();

                // Se a aresta ou a aresta inversa já foram processadas, não a processe novamente
                if (!arestasProcessadas.contains(aresta) && !arestasProcessadas.contains(arestaInversa)) {
                    System.out.println(a);
                    pesoTotal += a.getPeso();
                    arestasProcessadas.add(aresta);  // Marca a aresta como processada
                }
            }
        }
        System.out.println("Peso total da AGM: " + pesoTotal);

        return agm;
    }

    // Algoritmo de Dijkstra para caminho mínimo
    public void calcularCaminhoMinimo(T origem, T destino) {
        Vertice<T> verticeOrigem = obterVertice(origem);
        Vertice<T> verticeDestino = obterVertice(destino);

        if (verticeOrigem == null || verticeDestino == null) {
            System.out.println("Origem ou destino inválidos.");
            return;
        }

        Map<Vertice<T>, Float> distancias = new HashMap<>();
        Map<Vertice<T>, Vertice<T>> anteriores = new HashMap<>();
        PriorityQueue<Vertice<T>> filaPrioridade = new PriorityQueue<>(Comparator.comparingDouble(distancias::get));

        for (Vertice<T> v : vertices) {
            distancias.put(v, Float.MAX_VALUE);
        }
        distancias.put(verticeOrigem, 0f);
        filaPrioridade.offer(verticeOrigem);

        while (!filaPrioridade.isEmpty()) {
            Vertice<T> u = filaPrioridade.poll();

            if (u == verticeDestino) {
                break;
            }

            for (Aresta aresta : u.getDestinos()) {
                Vertice<T> v = aresta.getDestino();
                float novaDistancia = distancias.get(u) + aresta.getPeso();
                if (novaDistancia < distancias.get(v)) {
                    distancias.put(v, novaDistancia);
                    anteriores.put(v, u);
                    filaPrioridade.offer(v);
                }
            }
        }

        if (distancias.get(verticeDestino) == Float.MAX_VALUE) {
            System.out.println("Não há caminho entre " + origem + " e " + destino);
            return;
        }

        List<Vertice<T>> caminho = new ArrayList<>();
        Vertice<T> atual = verticeDestino;
        while (atual != null) {
            caminho.add(0, atual);
            atual = anteriores.get(atual);
        }

        System.out.println("Caminho mínimo de " + origem + " para " + destino + ":");
        for (Vertice<T> v : caminho) {
            System.out.print(v.getValor() + " -> ");
        }
        System.out.println("FIM");
        System.out.println("Distância total: " + distancias.get(verticeDestino));
    }
    public boolean isConexo() {
        if (vertices.isEmpty()) {
            return true; // Grafo vazio é considerado conexo
        }

        Set<Vertice<T>> visitados = new HashSet<>();
        dfs(vertices.get(0), visitados); // Começa a busca a partir do primeiro vértice

        return visitados.size() == vertices.size(); // Todos os vértices foram visitados?
    }

    private void dfs(Vertice<T> vertice, Set<Vertice<T>> visitados) {
        visitados.add(vertice);
        for (Aresta aresta : vertice.getDestinos()) {
            Vertice<T> destino = aresta.getDestino();
            if (!visitados.contains(destino)) {
                dfs(destino, visitados);
            }
        }
    }

}
