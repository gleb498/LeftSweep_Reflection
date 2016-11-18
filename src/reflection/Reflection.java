package reflection;

import matrix.Matrix;

import java.util.Scanner;

public class Reflection {
    private Matrix A;
    private double[][] V;
    private int amountStrings;
    private double[] f;
    private double[] x;
    private double[] w;
    private double[] y;
    private double p;
    private double alpha;
    private double[] e;
    private double[][] E;

    public Reflection() {
        amountStrings = 3;
        A = new Matrix(amountStrings, amountStrings);
        V = new double[amountStrings][amountStrings];
        f = new double[amountStrings];
        x = new double[amountStrings];
        w = new double[amountStrings];
        y = new double[amountStrings];
        e = new double[amountStrings];
        p = 0;
        alpha = 0;
        E = new double[amountStrings][amountStrings];
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountStrings; j++) {
                if (i == j) {
                    E[i][j] = 1;
                } else {
                    E[i][j] = 0;
                }

            }
        }
    }

    private void fillEq() {
        Scanner sc = new Scanner(System.in);
        System.out.println("matrix.Matrix A: ");
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountStrings; j++) {
                A.matrix[i][j] = sc.nextDouble();
            }
        }
        System.out.println("F: ");
        for (int i = 0; i < amountStrings; i++) {
            f[i] = sc.nextDouble();
        }
    }

    private double scalarMultiply(double[] a, double[] b) {
        double result = 0;
        int length = a.length;
        for (int i = 0; i < length; i++) {
            result += a[i] * b[i];
        }
        return result;
    }

    private double[] multiplyMatrix_Array(double[][] m, double[] a) {
        double[] result = new double[amountStrings];
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountStrings; j++) {
                result[i] += V[i][j] * f[j];
            }
        }
        return result;
    }


    private void fillY(int k) {
        if (k != 0) {
            for (int i = 0; i < k; i++) {
                y[i] = 0;
            }
        }
        for (int i = k; i < amountStrings; i++) {
            y[i] = A.matrix[i][k];
        }
    }

    private void fillAlpha() {
        alpha = Math.sqrt(scalarMultiply(y, y));
    }

    private void fillArrayE(int k) {
        e[k] = 1;
        for (int i = 0; i < amountStrings; i++) {
            if (i != k) {
                e[i] = 0;
            }
        }
    }

    private void fillP() {
        double[] changedY = new double[amountStrings];
        for (int i = 0; i < amountStrings; i++) {
            changedY[i] = y[i] - alpha * e[i];
        }
        p = Math.sqrt(2 * scalarMultiply(y, changedY));
    }

    private void fillW() {
        for (int i = 0; i < amountStrings; i++) {
            w[i] = (y[i] - alpha * e[i]) / p;
        }
    }

    private void fillV() {
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountStrings; j++) {
                V[i][j] = E[i][j] - 2 * w[i] * w[j];
            }
        }
    }

    private void orthogonalization() {
        for (int k = 0; k < amountStrings - 1; k++) {
            fillY(k);
            fillAlpha();
            fillArrayE(k);
            fillP();
            fillW();
            fillV();
            A.matrix = Matrix.multiplyMatrix(V, A.matrix);
            f = multiplyMatrix_Array(V, f);
        }
        System.out.println("NEW MATRIX:");
        A.print();
    }

    private void findX() {
        for (int i = 0; i < amountStrings; i++) {
            x[i] = f[i];
        }
        x[amountStrings - 1] /= A.matrix[amountStrings - 1][amountStrings - 1];
        for (int i = amountStrings - 2; i >= 0; i--) {
            for (int j = i + 1; j < amountStrings; j++) {
                x[i] -= x[j] * A.matrix[i][j];
            }
            x[i] /= A.matrix[i][i];
        }
        System.out.println("X:");
        for (double item : x) {
            System.out.println(item);
        }
    }

    private void checkEqualization(double[][] checkA, double[] checkF) {
        double[] r = new double[amountStrings];
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountStrings; j++) {
                r[i] += checkA[i][j] * x[j];
            }
            r[i] -= checkF[i];
        }
        System.out.println("r = A * x - f: ");
        for (double item : r) {
            System.out.printf("%E", item);
            System.out.println();
        }
        double max = r[0];
        for (int i = 1; i < amountStrings; i++) {
            if (Math.abs(max) < Math.abs(r[i])) {
                max = r[i];
            }
        }
        System.out.println("||r|| = " + max);
    }

    public void solve() {
        fillEq();
        Matrix checkA = new Matrix(A);
        double[] checkF = new double[amountStrings];
        for (int i = 0; i < amountStrings; i++) {
            checkF[i] = f[i];
        }
        orthogonalization();
        findX();
        checkEqualization(checkA.matrix, checkF);
    }
}
