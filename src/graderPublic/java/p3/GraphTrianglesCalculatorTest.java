package p3;

import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;
import p3.P3_TestBase;
import p3.graph.AdjacencyMatrix;
import p3.solver.GraphTrianglesCalculator;

import java.util.Arrays;
import java.util.List;

import static util.AssertionUtil.assertEquals;
import static util.ReflectionUtil.setAdjacencyMatrix;

@TestForSubmission
public class GraphTrianglesCalculatorTest extends P3_TestBase {

    @Override
    public String getTestedClassName() {
        return "GraphTrianglesCalculator";
    }

    @Override
    public List<String> getOptionalParams(){
        return List.of("matrix", "leftMatrix", "rightMatrix", "expected", "expectedMatrix");
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "solver/graphtriangles/multiply.json")
    public void testMatrixMultiply(JsonParameterSet params) throws ReflectiveOperationException {
        Context.Builder<?> context = createContext(params, "matrixMultiply");

        AdjacencyMatrix left = createAdjacencyMatrixFromField(params, "leftMatrix");
        AdjacencyMatrix right = createAdjacencyMatrixFromField(params, "rightMatrix");
        AdjacencyMatrix expected = createAdjacencyMatrixFromField(params, "expectedMatrix");

        // Create calculator with null graph (the methods don't use the graph field)
        GraphTrianglesCalculator<String> calculator = callObject(() -> new GraphTrianglesCalculator<>(null), context, "constructor");

        AdjacencyMatrix result = callObject(() -> calculator.matrixMultiply(left, right), context, "matrixMultiply");

        context.add("expected", Arrays.deepToString(expected.getMatrix()));
        context.add("actual", Arrays.deepToString(result.getMatrix()));

        assertMatrixEquals(expected.getMatrix(), result.getMatrix(), context);
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "solver/graphtriangles/traces.json")
    public void testTraces(JsonParameterSet params) throws ReflectiveOperationException {
        Context.Builder<?> context = createContext(params, "trace");

        AdjacencyMatrix matrix = createAdjacencyMatrixFromField(params, "matrix");

        GraphTrianglesCalculator<?> calculator = callObject(() -> new GraphTrianglesCalculator<>(null), context, "constructor");
        int actual = callObject(() -> calculator.trace(matrix), context, "trace");

        int expected = params.getInt("expected");

        context.add("expected", expected);
        context.add("actual", actual);

        assertEquals(expected, actual, context, "Method trace did not return the expected value");
    }

    @ParameterizedTest
    @JsonParameterSetTest(value = "solver/graphtriangles/graphTriangles.json")
    public void testGraphTriangles(JsonParameterSet params) throws ReflectiveOperationException {
        Context.Builder<?> context = createContext(params, "graphTriangles");

        AdjacencyMatrix matrix = createAdjacencyMatrixFromField(params, "matrix");

        GraphTrianglesCalculator<?> calculator = callObject(() -> new GraphTrianglesCalculator<>(null), context, "constructor");
        int actual = callObject(() -> calculator.graphTriangles(matrix), context, "graphTriangles");

        int expected = params.getInt("expected");

        context.add("expected", expected);
        context.add("actual", actual);

        assertEquals(expected, actual, context, "Method graphTriangles did not return the expected value");
    }

    private int[][] listToMatrix(List<List<Integer>> matrixList) {
        int[][] matrix = new int[matrixList.size()][matrixList.size()];

        for (int i = 0; i < matrixList.size(); i++) {
            for (int j = 0; j < matrixList.size(); j++) {
                matrix[i][j] = matrixList.get(i).get(j);
            }
        }

        return matrix;
    }

    private AdjacencyMatrix createAdjacencyMatrixFromField(JsonParameterSet params, String fieldName) throws ReflectiveOperationException {
        List<List<Integer>> matrixData = params.get(fieldName);
        int[][] matrix = listToMatrix(matrixData);
        AdjacencyMatrix adjacencyMatrix = new AdjacencyMatrix(matrix.length);
        setAdjacencyMatrix(adjacencyMatrix, matrix);
        return adjacencyMatrix;
    }

    private void assertMatrixEquals(int[][] expected, int[][] actual, Context.Builder<?> context) {
        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[i].length; j++) {
                assertEquals(expected[i][j], actual[i][j], context,
                        "Mismatch at matrix[%d][%d]".formatted(i, j));
            }
        }
    }
}