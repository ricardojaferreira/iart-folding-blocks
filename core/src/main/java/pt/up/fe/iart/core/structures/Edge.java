package pt.up.fe.iart.core.structures;

public class Edge<E> {
    private Vertex<E> destination;
    private double weight;

    public Edge(Vertex<E> destination, double weight) {
        this.destination = destination;
        this.weight = weight;
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
