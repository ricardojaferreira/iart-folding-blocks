package pt.up.fe.iart.core.structures;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Graph<V> {

    private List<Vertex<V>> vertexList;

    public Graph() {
        this.vertexList = new ArrayList<>();
    }

    /**
     * Add a new vertice
     * @param vertex
     */
    public void addVertex(Vertex<V> vertex) {

        this.vertexList.add(vertex);
    }

    /**
     *
     * @return
     */
    public List<Vertex<V>> getVertexList() {
        return vertexList;
    }

    /**
     * Get the direct path to the root
     * @param vertex
     * @return
     */
    public Set<Vertex<V>> getPath(Vertex<V> vertex) {
        Set<Vertex<V>> path = new LinkedHashSet<>();
        path.add(vertex);
        if (vertex.getParent() == null) {
            return path;
        } else {
            return getPath(vertex.getParent());
        }
    }
}
