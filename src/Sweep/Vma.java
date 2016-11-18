package Sweep;

import matrix.Matrix;

import java.util.Scanner;

public class Vma {
    private Matrix A;
    private int amountStrings;
    private double[] f;
    private double[] x;
    private double[] alpha;
    private double[] beta;
    private double[] a;
    private double[] b;
    private double[] c;

    public Vma() {
        amountStrings = 3;
        A = new Matrix(amountStrings, amountStrings);
        f = new double[amountStrings];
        x = new double[amountStrings];
        alpha = new double[amountStrings - 1];
        beta = new double[amountStrings];
        a = new double[amountStrings - 1];
        b = new double[amountStrings - 1];
        c = new double[amountStrings];
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

    private void fillABC() {
        int temp = 0;
        for (int i = 0; i < amountStrings - 1; i++) {
            a[i] = (-1) * A.matrix[i + 1][temp];
            b[i] = (-1) * A.matrix[temp++][i + 1];
        }
        for (int i = 0; i < amountStrings; i++) {
            c[i] = A.matrix[i][i];
        }
    }

    private void fillCoefficient() {
        alpha[amountStrings - 2] = a[amountStrings - 2] / c[amountStrings - 1];
        beta[amountStrings - 1] = f[amountStrings - 1] / c[amountStrings - 1];
        for (int i = amountStrings - 3; i >= 0; i--) {
            alpha[i] = a[i] / (c[i + 1] - b[i + 1] * alpha[i + 1]);
        }
        for (int i = amountStrings - 2; i >= 0; i--) {
            beta[i] = (f[i] + b[i] * beta[i + 1]) / (c[i] - b[i] * alpha[i]);
        }
    }

    private void fillX() {
        x[0] = beta[0];
        for (int i = 1; i < amountStrings; i++) {
            x[i] = alpha[i - 1] * x[i - 1] + beta[i];
        }
    }

    public void printArray(double[] array) {
        for (double item : array) {
            System.out.print(item + " ");
        }
        System.out.println();
    }


    private void solve() {
        fillABC();
        fillCoefficient();
        fillX();
        printArray(alpha);
        printArray(beta);
        printArray(x);
    }

    private void checkEqualization() {
        double[] r = new double[amountStrings];
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountStrings; j++) {
                r[i] += A.matrix[i][j] * x[j];
            }
            r[i] -= f[i];
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

    public static void FUNC() {
        Vma v = new Vma();
        v.fillEq();
        v.solve();
        v.checkEqualization();

    }
}
