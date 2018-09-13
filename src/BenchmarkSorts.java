public class BenchmarkSorts {

    HeapSort heapSort;

    private int[][][] dataSet;

    private long[] iArrayAverageCriticalOperationCount;
    private long[] iArrayAverageExecutionTime;
    private long[][] iArrayCount;
    private long[][] iArrayTime;

    private long[] rArrayAverageCriticalOperationCount;
    private long[] rArrayAverageExecutionTime;
    private long[][] rArrayCount;
    private long[][] rArrayTime;

    BenchmarkSorts(int[] sizes) {
        heapSort = new HeapSort();
        dataSet = new int[sizes.length][50][];

        for (int i = 0; i < dataSet.length; i++) {

            for (int j = 0; j < dataSet[i].length; j++)
                dataSet[i][j] = randomizedData(sizes[i]);

            iArrayAverageCriticalOperationCount = new long[dataSet.length];
            iArrayAverageExecutionTime = new long[dataSet.length];
            rArrayAverageCriticalOperationCount = new long[dataSet.length];
            rArrayAverageExecutionTime = new long[dataSet.length];
        }
        iArrayCount = new long[dataSet.length][dataSet[0].length];
        iArrayTime = new long[dataSet.length][dataSet[0].length];
        rArrayCount = new long[dataSet.length][dataSet[0].length];
        rArrayTime = new long[dataSet.length][dataSet[0].length];
    }

    private static boolean sorted(int[] list) {
        for (int i = 0; i < list.length - 1; i++)
            if (list[i] > list[i + 1])
                return false;
        return true;
    }

    public void runSorts() throws UnsortedException {

        for (int i = 0; i < dataSet.length; i++) {

            for (int j = 0; j < dataSet[i].length; j++) {
                int[] arrayA, arrayB;
                arrayA = dataSet[i][j].clone();
                arrayB = dataSet[i][j].clone();
                heapSort.recursiveSort(arrayA);

                if (!sorted(arrayA))
                    throw new UnsortedException("Recursive sort did not return a sorted array.\n");

                rArrayCount[i][j] = heapSort.getCount();
                rArrayTime[i][j] = heapSort.getTime();
                heapSort.reset();
                heapSort.iterativeSort(arrayB);

                if (!sorted(arrayB))
                    throw new UnsortedException("Iterative sort did not return a sorted array.\n");

                iArrayCount[i][j] = heapSort.getCount();
                iArrayTime[i][j] = heapSort.getTime();
                heapSort.reset();
            }
            rArrayAverageCriticalOperationCount[i] = getAverage(rArrayCount[i]);
            rArrayAverageExecutionTime[i] = getAverage(rArrayTime[i]);
            iArrayAverageCriticalOperationCount[i] = getAverage(iArrayCount[i]);
            iArrayAverageExecutionTime[i] = getAverage(iArrayTime[i]);
        }

    }

    private long getAverage(long[] data) {
        long sum = 0;
        for (long aData : data) sum += aData;
        return (sum / data.length);
    }

    private double getCoefficientVariance(long[] data) {
        long mean = getAverage(data);
        long sum = 0;
        for (long aData : data)
            sum += Math.pow(aData - mean, 2);
        return Math.sqrt(sum / (data.length - 1));
    }

    private int[] randomizedData(int size) {
        int[] data = new int[size];
        for (int i = 0; i < size; i++)
            data[i] = (int) (Math.random() * Integer.MAX_VALUE);
        return data;
    }

    public void displayReport() {

        // TODO: Output to something pretty

        for (int i = 0; i < dataSet.length; i++) {

            // Data set size n
            int dataSetSize = (dataSet[i][0].length);
            
            // ITERATIVE
            // Average critical operation count
            long l1 = iArrayAverageCriticalOperationCount[i];

            // Coefficient of variance of count
            double iterativeVarianceCount = getCoefficientVariance(iArrayCount[i]);

            // Average execution time
            double averageIterativeExecutionTime = (iArrayAverageExecutionTime[i]);

            // Coefficient of variance of time
            double iterativeCoefficientVariance = Math.round(getCoefficientVariance(iArrayTime[i]) * 1000.0) / 1000.0;

            /// RECURSIVE
            // Average critical operation count
            long l = rArrayAverageCriticalOperationCount[i];

            // Coefficient of variance of count
            double recursiveCountSd = Math.round(getCoefficientVariance(rArrayCount[i]) * 1000.0) / 1000.0;

            // Average execution time
            double averageRecursiveExecutionTime = (rArrayAverageExecutionTime[i]);

            // Coefficient of variance of time
            double recursiveCoefficientVariance = Math.round(getCoefficientVariance(rArrayTime[i]) * 1000.0) / 1000.0;
        }


    }

}
