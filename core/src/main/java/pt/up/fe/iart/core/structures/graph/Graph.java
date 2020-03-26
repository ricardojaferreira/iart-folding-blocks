package pt.up.fe.iart.core.structures.graph;

import java.util.ArrayList;
import java.util.List;

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
}
