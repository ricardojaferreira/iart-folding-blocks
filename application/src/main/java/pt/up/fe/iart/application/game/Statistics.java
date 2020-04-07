package pt.up.fe.iart.application.game;

import pt.up.fe.iart.application.AppConstants;

public class Statistics {
    private long elapsedTime;
    private int graphNrOfNodes;
    private int nrOfTips;
    private int nrNodesToVictory;

    public Statistics() {
        elapsedTime = 0;
        graphNrOfNodes = 0;
        nrOfTips = 0;
        nrNodesToVictory = 0;
    }

    /**
     *
     * @return
     */
    public long getElapsedTime() {
        return elapsedTime;
    }

    /**
     *
     * @param elapsedTime
     */
    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    /**
     *
     * @return
     */
    public int getNrNodesToVictory() {
        return nrNodesToVictory;
    }

    /**
     *
     */
    public void incrementNrNodesToVictory() {
        this.nrNodesToVictory++;
    }

    /**
     *
     * @param nrNodesToVictory
     */
    public void setNrNodesToVictory(int nrNodesToVictory) {
        this.nrNodesToVictory = nrNodesToVictory;
    }

    /**
     *
     * @return
     */
    public int getGraphNrOfNodes() {
        return graphNrOfNodes;
    }

    /**
     *
     * @param graphNrOfNodes
     */
    public void setGraphNrOfNodes(int graphNrOfNodes) {
        this.graphNrOfNodes = graphNrOfNodes;
    }

    /**
     *
     */
    public void incrementNumberOfTips() {
        this.nrOfTips++;
    }

    /**
     *
     * @return
     */
    public int getNrOfTips() {
        return nrOfTips;
    }

    /**
     *
     * @param nrOfTips
     */
    public void setNrOfTips(int nrOfTips) {
        this.nrOfTips = nrOfTips;
    }

    /**
     *
     * @param playingStrategy
     */
    public void printStatistics(PlayingStrategy playingStrategy) {
        StringBuilder printBuilder = new StringBuilder().append(AppConstants.SCREEN_SEPARATOR).append(AppConstants.NEW_LINE);
        if (playingStrategy.equals(PlayingStrategy.AI)) {
            printBuilder.append("Statistics for AI resolution").append(AppConstants.NEW_LINE)
                    .append("Elapsed Time (ms): ").append(elapsedTime / AppConstants.NANO_TO_MS_DIVISOR).append(AppConstants.NEW_LINE)
                    .append("Nr. of Graph Nodes: ").append(graphNrOfNodes).append(AppConstants.NEW_LINE)
                    .append("Nr. of Movements: ").append(nrNodesToVictory).append(AppConstants.NEW_LINE);
        } else {
            printBuilder.append("Statistics of your resolution").append(AppConstants.NEW_LINE)
                    .append("Elapsed Time (s): ").append(elapsedTime / AppConstants.NANO_TO_S_DIVISOR).append(AppConstants.NEW_LINE)
                    .append("Nr. of Movements: ").append(nrNodesToVictory).append(AppConstants.NEW_LINE)
                    .append("Nr. of Tips: ").append(nrOfTips).append(AppConstants.NEW_LINE);
        }

        System.out.println(printBuilder.toString());
    }


}
