package pt.up.fe.iart.core.structures.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Vertex<V> {
    private V content;
    private List<Edge<V>> adjacent;
    private List<Vertex<V>> pathFromRoot;
    private int distanceFromRoot;
    private boolean discovered;

    public Vertex(V content) {
        this.content = content;
        this.adjacent = new LinkedList<>();
        this.discovered = false;
        this.distanceFromRoot = Integer.MAX_VALUE;
        this.pathFromRoot = new ArrayList<>();
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
     * @param edge
     */
    public void addEdge(Edge<V> edge) {
        adjacent.add(edge);
    }

    /**
     *
     * @return
     */
    public List<Vertex<V>> getPathFromRoot() {
        return pathFromRoot;
    }

    /**
     *
     * @param pathFromRoot
     */
    public void setPathFromRoot(List<Vertex<V>> pathFromRoot) {
        this.pathFromRoot = pathFromRoot;
    }

    /**
     *
     * @return
     */
    public int getDistanceFromRoot() {
        return distanceFromRoot;
    }

    /**
     *
     * @param distanceFromRoot
     */
    public void setDistanceFromRoot(int distanceFromRoot) {
        this.distanceFromRoot = distanceFromRoot;
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
