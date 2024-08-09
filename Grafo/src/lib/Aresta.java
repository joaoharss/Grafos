package lib;

/**
 * Representa uma aresta em um grafo.
 */
public class Aresta {
    private Vertice origem;
    private Vertice destino;
    private float peso;

    /**
     * Cria uma nova aresta com a origem, destino e peso especificados.
     *
     * @param origem O vértice de origem da aresta.
     * @param destino O vértice de destino da aresta.
     * @param peso O peso da aresta.
     */
    public Aresta(Vertice origem, Vertice destino, float peso) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
    }

    /**
     * Retorna o vértice de origem da aresta.
     *
     * @return O vértice de origem.
     */
    public Vertice getOrigem() {
        return origem;
    }

    /**
     * Retorna o vértice de destino da aresta.
     *
     * @return O vértice de destino.
     */
    public Vertice getDestino() {
        return destino;
    }

    /**
     * Retorna o peso da aresta.
     *
     * @return O peso da aresta.
     */
    public float getPeso() {
        return peso;
    }

    /**
     * Define o vértice de origem da aresta.
     *
     * @param origem O novo vértice de origem.
     */
    public void setOrigem(Vertice origem) {
        this.origem = origem;
    }

    /**
     * Define o vértice de destino da aresta.
     *
     * @param destino O novo vértice de destino.
     */
    public void setDestino(Vertice destino) {
        this.destino = destino;
    }

    /**
     * Define o peso da aresta.
     *
     * @param peso O novo peso da aresta.
     */
    public void setPeso(float peso) {
        this.peso = peso;
    }

    /**
     * Retorna uma representação em string da aresta.
     *
     * @return A representação em string.
     */
    @Override
    public String toString() {
        return "Aresta {origem=" + origem.getValor() + ", destino=" + destino.getValor() + ", peso=" + peso + "}";
    }
}
