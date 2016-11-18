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
        System.out.println("B: ");
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

    private void makeV() {
        for (int i = 0; i < amountStrings; i++) {
            y[i] = A.matrix[i][0];
        }
        alpha = scalarMultiply(y, y);
        double[] changedY = new double[amountStrings];
        for (int i = 0; i < amountStrings; i++) {
            changedY[i] = y[i] - alpha * e[i];
        }
        p = Math.sqrt(2 * scalarMultiply(y, changedY));
        for (int i = 0; i < amountStrings; i++) {
            w[i] = (y[i] - alpha * e[i]) / p;
        }
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountStrings; j++) {

            }
        }
    }

    public static void solve() {
        Reflection r = new Reflection();
        r.fillEq();
    }
}
