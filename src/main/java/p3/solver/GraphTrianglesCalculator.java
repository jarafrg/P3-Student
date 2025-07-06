package p3.solver;

import p3.graph.AdjacencyMatrix;
import p3.graph.Edge;
import p3.graph.Graph;

import java.util.HashMap;
import java.util.Map;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * A utility class for performing triangle-related operations on directed graphs
 * represented by an {@link AdjacencyMatrix}.
 * <p>
 * A triangle in a directed graph is a cycle of three nodes, where each node is
 * connected to the next in a directed manner. This class provides methods to
 * perform matrix multiplication, compute the trace of a matrix, and count
 * the number of such triangles in a graph.
 */
public class GraphTrianglesCalculator<N> {

    /**
     * The adjacency matrix representing the graph.
     */
    protected final AdjacencyMatrix matrix;

    /**
     * The graph to calculate paths in.
     */
    protected final Graph<N> graph;

    /**
     * Constructs a new {@link GraphTrianglesCalculator} for the given matrix.
     *
     * @param graph The graph representing the graph.
     */
    public GraphTrianglesCalculator(Graph<N> graph) {
        this.graph = graph;
        this.matrix = toAdjacencyMatrix(graph);
    }

    /**
     * Converts a {@link Graph} with arbitrary node types to an {@link AdjacencyMatrix}.
     *
     * @param graph The input graph to convert.
     * @param <N>   The node type.
     * @return An {@link AdjacencyMatrix} representing the structure of the input graph.
     */
    public static <N> AdjacencyMatrix toAdjacencyMatrix(Graph<N> graph) {
        return crash(); //TODO: H3 a) - remove if implemented
    }

    /**
     * Multiplies two adjacency matrices and returns the result.
     *
     * @param leftMatrix  The left-hand matrix operand.
     * @param rightMatrix The right-hand matrix operand.
     * @return The result of the matrix multiplication, or {@code null} if dimensions do not match.
     */
    public AdjacencyMatrix matrixMultiply(AdjacencyMatrix leftMatrix, AdjacencyMatrix rightMatrix) {
        return crash(); //TODO: H3 b) - remove if implemented
    }

    /**
     * Computes the trace of the given adjacency matrix.
     *
     * @param matrix The adjacency matrix.
     * @return The trace of the matrix.
     */
    public int trace(AdjacencyMatrix matrix) {
        return crash(); //TODO: H3 b) - remove if implemented
    }

    /**
     * Counts the number of directed triangles (3-node cycles) in the graph.
     *
     * @param matrix The adjacency matrix of the graph.
     * @return The number of directed triangles in the graph.
     */
    public int graphTriangles(AdjacencyMatrix matrix) {
        return crash(); //TODO: H3 c) - remove if implemented
    }

}
