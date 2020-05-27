package pt.up.fe.iart.core.structures.graph;

public class Edge<E> {
    private String label;
    private Vertex<E> destination;
    private double weight;

    public Edge(String label, Vertex<E> destination, double weight) {
        this.label = label;
        this.destination = destination;
        this.weight = weight;
    }

    /**
     *
     * @return
     */
    public String getLabel() {
        return label;
    }

    /**
     *
     * @param label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     *
     * @return
     */
    public Vertex<E> getDestination() {
        return this.destination;
    }

    /**
     *
     * @return
     */
    public double getWeight() {
        return this.weight;
    }

}
