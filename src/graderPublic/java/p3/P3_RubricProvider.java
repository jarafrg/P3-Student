package p3;

import org.opentest4j.AssertionFailedError;
import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.GradeResult;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import static org.sourcegrade.jagr.api.rubric.Grader.*;

public class P3_RubricProvider implements RubricProvider {
    private static Criterion createUntestedCriterion(String shortDescription, Callable<Method> illegalMethodCheck) {
        StringBuilder comment = new StringBuilder("Not graded by public grader");

        if (illegalMethodCheck != null) {
            try {
                Method method = illegalMethodCheck.call();
                Object instance = method.getDeclaringClass().getDeclaredConstructor().newInstance();
                method.invoke(instance);
            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof AssertionFailedError) {
                    comment.append("\n").append(e.getCause().getMessage());
                } else {
                    comment.append("\nMethod threw an exception. Could not check whether illegal methods have been used. Exception: ")
                            .append(e.getCause().getMessage());
                }
            } catch (Exception ignored) {
            }
        }

        return Criterion.builder()
                .shortDescription(shortDescription)
                .grader((testCycle, criterion) ->
                        GradeResult.of(criterion.getMinPoints(), criterion.getMaxPoints(), comment.toString()))
                .maxPoints(1)
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

    // ========== Criteria ==========

    public static final Criterion H1_1 = createCriterion(
            "Die Methode [[[put]]] überschreibt korrekt vorhandene Einträge.",
            1,
            () -> p3.ServerMonitorTest.class.getMethod("testPutOverwriteWithSize", String.class, int.class, int.class)
    );

    public static final Criterion H1_2 = createCriterion(
            "Die Methode [[[get]]] liefert korrekte Werte für vorhandene Schlüssel.",
            1,
            () -> p3.ServerMonitorTest.class.getMethod("testGet", String.class, Integer.class, Boolean.class)
    );

    public static final Criterion H1_3 = createCriterion(
            "Die Methode [[[hash]]] liefert korrekte Hash-Werte im erwarteten Bereich.",
            1,
            () -> p3.ServerMonitorTest.class.getMethod("testHash", String.class, int.class)
    );

    public static final Criterion H1_4 = createCriterion(
            "Die Methode [[[remove]]] entfernt korrekte Elemente aus Hashtabelle.",
            1,
            () -> p3.ServerMonitorTest.class.getMethod("testRemoveWithSize", String.class, int.class, boolean.class)
    );

    public static final Criterion H1_6 = createCriterion(
            "The table doubles its size correctly when rehashing.",
            1,
            () -> p3.RehashingTest.class.getMethod("testRehashDoublesTableSize")
    );

    public static final Criterion H1_9 = createCriterion(
            "Closed checks (value = 0) are removed correctly.",
            1,
            () -> p3.CheckManagementTest.class.getMethod("testRemoveClosedCheck")
    );

    public static final Criterion H1_10 = createCriterion(
            "New checks are added to the result correctly.",
            1,
            () -> p3.CheckManagementTest.class.getMethod("testAddNewCheck")
    );

    // ========== Task Group ==========

    public static final Criterion H1 = createParentCriterion("1.1", "Hash Tables - CRUD Operations", H1_1, H1_2, H1_3, H1_4);
    public static final Criterion H2 = createParentCriterion("1.2", "Hash Tables - Dynamic Rehashing", H1_6);
    public static final Criterion H3 = createParentCriterion("1.3", "Check Management for Monitoring", H1_9, H1_10);

    // ========== Rubric Definition ==========
    public static final Rubric RUBRIC = Rubric.builder()
            .title("H1 - Hashtable in Action for Monitoring")
            .addChildCriteria(H1)
            .addChildCriteria(H2)
            .addChildCriteria(H3)
            .build();
    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }
}
