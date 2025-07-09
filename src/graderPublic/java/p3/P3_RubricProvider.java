package p3;

import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.GradeResult;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import static org.sourcegrade.jagr.api.rubric.Grader.*;

public class P3_RubricProvider implements RubricProvider {

    private static Criterion createUntestedCriterion(String shortDescription, int maxPoints) {
        return Criterion.builder()
                .shortDescription(shortDescription)
                .grader((testCycle, criterion) ->
                        GradeResult.of(criterion.getMinPoints(), criterion.getMaxPoints(), "Not graded by public grader"))
                .maxPoints(maxPoints)
                .build();
    }

    @SafeVarargs
    private static Criterion createCriterion(String shortDescription, int maxPoints, Callable<Method>... methodRefs) {
        TestAwareBuilder graderBuilder = testAwareBuilder();

        for (Callable<Method> methodRef : methodRefs) {
            graderBuilder.requirePass(JUnitTestRef.ofMethod(methodRef));
        }

        return Criterion.builder()
                .shortDescription(shortDescription)
                .grader(graderBuilder.pointsFailedMin().pointsPassedMax().build())
                .maxPoints(maxPoints)
                .build();
    }

    private static Criterion createParentCriterion(String taskId, String shortDescription, Criterion... children) {
        return Criterion.builder()
                .shortDescription("H" + taskId + " | " + shortDescription)
                .addChildCriteria(children)
                .build();
    }


    public static final Criterion H1_1_1 = createCriterion(
            "Die Methode [[[put]]] überschreibt korrekt vorhandene Einträge.",
            1,
            () -> p3.ServerMonitorTest.class.getMethod("testPutOverwriteWithSize", String.class, int.class, int.class)
    );

    public static final Criterion H1_1_2 = createCriterion(
            "Die Methode [[[get]]] liefert korrekte Werte für vorhandene Schlüssel.",
            1,
            () -> p3.ServerMonitorTest.class.getMethod("testGet", String.class, Integer.class, Boolean.class)
    );

    public static final Criterion H1_1_3 = createCriterion(
            "Die Methode [[[hash]]] liefert korrekte Hash-Werte im erwarteten Bereich.",
            1,
            () -> p3.ServerMonitorTest.class.getMethod("testHash", String.class, int.class)
    );

    public static final Criterion H1_1_4 = createCriterion(
            "Die Methode [[[remove]]] entfernt korrekte Elemente aus Hashtabelle.",
            1,
            () -> p3.ServerMonitorTest.class.getMethod("testRemoveWithSize", String.class, int.class, boolean.class)
    );

    public static final Criterion H1_2_1 = createCriterion(
            "The table doubles its size correctly when rehashing.",
            1,
            () -> p3.RehashingTest.class.getMethod("testRehashDoublesTableSize")
    );

    public static final Criterion H1_3_1 = createCriterion(
            "Closed checks (value = 0) are removed correctly.",
            1,
            () -> p3.CheckManagementTest.class.getMethod("testRemoveClosedCheck")
    );

    public static final Criterion H1_3_2 = createCriterion(
            "New checks are added to the result correctly.",
            1,
            () -> p3.CheckManagementTest.class.getMethod("testAddNewCheck")
    );

    public static final Criterion H1_1 = createParentCriterion("1.1", "Hash Tables - CRUD Operations", H1_1_1, H1_1_2, H1_1_3, H1_1_4);
    public static final Criterion H1_2 = createParentCriterion("1.2", "Hash Tables - Dynamic Rehashing", H1_2_1);
    public static final Criterion H1_3 = createParentCriterion("1.3", "Check Management for Monitoring", H1_3_1, H1_3_2);
    public static final Criterion H1 = createParentCriterion("1", "Hash Tables", H1_1, H1_2, H1_3);


    // ====================================================================
    // H2 - GRAPH REPRESENTATIONS (6 Points)
    // ====================================================================

    // H2a) AdjacencyMatrix (2 Points)
    public static final Criterion H2_1_1 = createCriterion(
            "Die Methoden [[[hasEdge]]] und [[[addEdge]]] der Klasse [[[AdjacencyMatrix]]] funktionieren vollständig korrekt",
            1,
            () -> AdjacencyMatrixTest.class.getMethod("testHasEdge", JsonParameterSet.class),
            () -> AdjacencyMatrixTest.class.getMethod("testAddEdge", JsonParameterSet.class)
    );

    public static final Criterion H2_1_2 = createCriterion(
            "Die Methode [[[getAdjacentIndices]]] der Klasse [[[AdjacencyMatrix]]] funktioniert vollständig korrekt",
            1,
            () -> AdjacencyMatrixTest.class.getMethod("testGetAdjacentIndices", JsonParameterSet.class)
    );

    public static final Criterion H2_1 = createParentCriterion("2a", "AdjacencyMatrix", H2_1_1, H2_1_2);

    // H2b) AdjacencyGraph (4 Points)
    public static final Criterion H2_2_1 = createCriterion(
            "Die Methode [[[addNode]]] der Klasse [[[AdjacencyGraph]]] funktioniert vollständig korrekt",
            1,
            () -> AdjacencyGraphTest.class.getMethod("testAddNode", JsonParameterSet.class)
    );

    public static final Criterion H2_2_2 = createUntestedCriterion("Die Methode [[[addEdge]]] der Klasse [[[AdjacencyGraph]]] funktioniert vollständig korrekt", 1);

    public static final Criterion H2_2_3 = createUntestedCriterion("Die Methode [[[getEdge]]] der Klasse [[[AdjacencyGraph]]] funktioniert vollständig korrekt", 1);

    public static final Criterion H2_2_4 = createCriterion(
            "Der Konstruktor der Klasse [[[AdjacencyGraph]]] funktioniert vollständig korrekt",
            1,
            () -> AdjacencyGraphTest.class.getMethod("testConstructor", JsonParameterSet.class)
    );

    public static final Criterion H2_2 = createParentCriterion("2b", "AdjacencyGraph", H2_2_1, H2_2_2, H2_2_3, H2_2_4);

    public static final Criterion H2 = createParentCriterion("2", "Graphenrepräsentationen", H2_1, H2_2);

    // ====================================================================
    // H3 - GRAPH TRIANGLES (10 Points)
    // ====================================================================

    // H3a) ToAdjacencyMatrix (3 Points) - Currently not tested
    public static final Criterion H3_1 = createUntestedCriterion("Die Methode [[[toAdjacencyMatrix]]] der Klasse [[[GraphTrianglesCalculator]]] funktioniert vollständig korrekt", 3);

    // H3b) MatrixMultiplication und Trace (3 Points)
    public static final Criterion H3_2_1 = createCriterion(
            "Die Methode [[[matrixMultiply]]] der Klasse [[[GraphTrianglesCalculator]]] funktioniert vollständig korrekt",
            2,
            () -> GraphTrianglesCalculatorTest.class.getMethod("testMatrixMultiply", JsonParameterSet.class)
    );

    public static final Criterion H3_2_2 = createCriterion(
            "Die Methode [[[trace]]] der Klasse [[[GraphTrianglesCalculator]]] funktioniert vollständig korrekt",
            1,
            () -> GraphTrianglesCalculatorTest.class.getMethod("testTraces", JsonParameterSet.class)
    );

    public static final Criterion H3_2 = createParentCriterion("3b", "MatrixMultiplication und Trace", H3_2_1, H3_2_2);

    // H3c) GraphTriangles (4 Points)
    public static final Criterion H3_3 = createCriterion(
            "Die Methode [[[graphTriangles]]] der Klasse [[[GraphTrianglesCalculator]]] funktioniert vollständig korrekt",
            1,
            () -> GraphTrianglesCalculatorTest.class.getMethod("testGraphTriangles", JsonParameterSet.class)
    );

    public static final Criterion H3 = createParentCriterion("3", "Graphendreiecke", H3_1, H3_2, H3_3);

    // ========== Rubric Definition ==========
    public static final Rubric RUBRIC = Rubric.builder()
            .title("P3 Public Tests")
            .addChildCriteria(H1)
            .addChildCriteria(H2)
            .addChildCriteria(H3)
            .build();
    @Override
    public Rubric getRubric() {
        return Rubric.builder()
                .title("P3 - Graph Representations and Triangles")
                .addChildCriteria(H1)
                .addChildCriteria(H2, H3)
                .build();
    }
}