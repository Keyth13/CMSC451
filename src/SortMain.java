import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The SortMain program implements an application that
 * calculates the recursive and iterative version of the
 * heapsort algorithm. It displays the Average Critical
 * Operation Count, Coefficient of Variance of Count
 * Average Execution Time, and Coefficient of Variance Time
 * to a web application table view.
 *
 * @author Lance Gundersen
 * @version 1.0
 * @since 2018-09-13
 */
public class SortMain extends Application {

    static {
        jvmLoader.load();
    }

    /**
     * Public method per UML.
     *
     * Mmain method which loads the application, initializes
     * JVM warmup and initializes SortMain.
     *
     * @param args Used for Application.launch
     */
    public static void main(String[] args) {

        Application.launch(args);

        jvmLoader.load();

        new SortMain();

    }

    /**
     * Initiates main benchmarkSorts method..
     * Initiates main benchmarkSorts method..
     *
     * @param stage Used for JavaFX scene.
     */
    public void start(Stage stage) {

        int[] dataSetSizes = new int[]{144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946};
        BenchmarkSorts benchmarkSorts = new BenchmarkSorts(dataSetSizes);
        try {
            benchmarkSorts.runSorts();

            VBox root = benchmarkSorts.displayReport();

            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);

            stage.show();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    static class jvmWarmUp {
        void m() {
        }
    }

    /**
     * JVM Loader reference https://www.baeldung.com/java-jvm-warmup
     */
    private static class jvmLoader {
        static void load() {
            for (int i = 0; i < 10000; i++) {
                jvmWarmUp warmUp = new jvmWarmUp();
                warmUp.m();
            }
        }
    }

}