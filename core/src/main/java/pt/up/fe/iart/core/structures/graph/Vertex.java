package pt.up.fe.iart.core.structures.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Vertex<V> {
    private V content;
    private List<Edge<V>> adjacent;
    private boolean discovered;

    public Vertex(V content) {
        this.content = content;
        this.adjacent = new LinkedList<>();
        this.discovered = false;
    }

    /**
     *
     * @return
     */
    public V getContent() {
        return this.content;
    }

    /**
    *
    */
    public void setDiscovered() {
        this.discovered = true;
    }

    /**
     *
     * @return
     */
    public boolean wasDiscovered() {
        return this.discovered;
    }

    /**
     *
     * @return
     */
    public List<Edge<V>> getAdjacent() {
        return this.adjacent;
    }

    /**
     *
     * @param vertex
     * @param weight
     */
    public void addEdge(Vertex<V> vertex, double weight) {
        adjacent.add(new Edge<>(vertex, weight));
    }

    /**
     *
     * @param edge
     */
    public void addEdge(Edge<V> edge) {
        adjacent.add(edge);
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vertex<?> vertex = (Vertex<?>) o;
        return content.equals(vertex.content);
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
