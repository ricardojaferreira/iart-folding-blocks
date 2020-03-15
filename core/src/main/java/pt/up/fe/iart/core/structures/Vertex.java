package pt.up.fe.iart.core.structures;

import java.util.LinkedList;
import java.util.List;

public abstract class Vertex<V> {
    private V content;
    private Vertex<V> parent;
    private List<Edge<V>> adjacent;
    private boolean discovered;

    public Vertex(V content, Vertex<V> parent) {
        this.content = content;
        this.adjacent = new LinkedList<>();
        this.discovered = false;
        this.parent = parent;
    }

    public Vertex(V content) {
        this(content, null);
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
     * @param parent
     */
    public void addParent(Vertex<V> parent) {
        this.parent = parent;
    }

    /**
     *
     * @return
     */
    public Vertex<V> getParent() {
        return this.parent;
    }

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();
}
