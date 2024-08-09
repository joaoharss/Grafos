package lib;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Representa um vértice em um grafo.
 *
 * @param <T> O tipo de dado armazenado no vértice.
 */
public class Vertice<T> {
    private T valor;
    private ArrayList<Aresta> destinos;
    private boolean visitado; // Flag para marcar se o vértice foi visitado
    private float distancia; // Distância do vértice em relação à origem (para Dijkstra)
    private Vertice<T> anterior; // Vértice anterior no caminho (para Dijkstra)

    /**
     * Cria um novo vértice com o valor especificado.
     *
     * @param valor O valor do vértice.
     */
    public Vertice(T valor) {
        this.valor = valor;
        this.destinos = new ArrayList<>();
        this.visitado = false;
        this.distancia = Float.MAX_VALUE; // Inicializa com infinito para Dijkstra
        this.anterior = null;
    }

    /**
     * Retorna o valor do vértice.
     *
     * @return O valor do vértice.
     */
    public T getValor() {
        return valor;
    }

    /**
     * Retorna a lista de arestas que saem deste vértice.
     *
     * @return A lista de arestas.
     */
    public ArrayList<Aresta> getDestinos() {
        return destinos;
    }

    /**
     * Adiciona uma aresta à lista de destinos do vértice.
     *
     * @param aresta A aresta a ser adicionada.
     */
    public void adicionarDestino(Aresta aresta) {
        this.destinos.add(aresta);
    }

    /**
     * Retorna o grau do vértice (número de arestas incidentes).
     *
     * @return O grau do vértice.
     */
    public int getGrau() {
        return destinos.size();
    }

    /**
     * Verifica se o vértice já foi visitado.
     *
     * @return true se o vértice foi visitado, false caso contrário.
     */
    public boolean isVisitado() {
        return visitado;
    }

    /**
     * Marca o vértice como visitado ou não visitado.
     *
     * @param visitado true para marcar como visitado, false para não visitado.
     */
    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    /**
     * Retorna a distância do vértice em relação à origem (para Dijkstra).
     *
     * @return A distância do vértice.
     */
    public float getDistancia() {
        return distancia;
    }

    /**
     * Define a distância do vértice em relação à origem (para Dijkstra).
     *
     * @param distancia A nova distância do vértice.
     */
    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    /**
     * Retorna o vértice anterior no caminho mínimo (para Dijkstra)..
     *
     * @return O vértice anterior.
     */
    public Vertice<T> getAnterior() {
        return anterior;
    }

    /**
     * Define o vértice anterior no caminho mínimo (para Dijkstra).
     *
     * @param anterior O novo vértice anterior.
     */
    public void setAnterior(Vertice<T> anterior) {
        this.anterior = anterior;
    }

    @Override
    public String toString() {
        return "Vértice {valor=" + valor + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertice<?> vertice = (Vertice<?>) o;
        return Objects.equals(valor, vertice.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
