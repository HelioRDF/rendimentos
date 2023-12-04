package org.example;

import java.text.DecimalFormat;

public class Main {

    public static double investimento = 0;
    public static double juros = 35;
    public static double meses = 36;
    public static double rendimento = 0;
    public static double recebiveis = 0;
    public static double repasse = 0;
    public static double transferencia = 6000;
    public static DecimalFormat df = new DecimalFormat("#,###");


    public static void main(String[] args) {
        simular();
    }

    public static void simular() {
        System.out.println("\n####################################################################################");
        System.out.print("Período: " + meses + "x | Juros: " + juros + "%\n");
        System.out.println("####################################################################################\n");
        investir(15000);
        double pago = 0;
        for (int mes = 1; mes <= meses; mes++) {

            recebiveis += (investimento + rendimento) / meses;
            repasse += (investimento + rendimento) / meses;
            System.out.println("\n---------------------------------------------------------------------\n");
            System.out.print("Mês: " + mes);
            valorAtualizdo();
            double somaRepasse = 0;
            while (repasse >= 500) {
                repasse -= 500;
                somaRepasse += 500;
                investir(500);
            }
            System.out.println("\nTransferência: R$" + df.format(transferencia) + " | Repasse R$" + df.format(somaRepasse) + " = +R$" + df.format(transferencia + somaRepasse));
            System.out.println("Saldo: R$" + df.format(repasse));
            investir(transferencia);
        }
        System.out.println("\n####################################################################################\n");
    }

    public static void investir(double valor) {
        rendimento += (valor / 100 * juros);
        investimento += valor;
    }

    public static void valorAtualizdo() {
        double total = (investimento + rendimento);
        System.out.println("\nInvestimento: R$" + df.format(investimento) + " | Rendimento: R$" + df.format(rendimento));
        System.out.println("Total: R$" + df.format(total) + " | Recebiveis: R$" + df.format(recebiveis) + " = R$" + df.format(total - recebiveis));
        System.out.println("Pagamento: R$" + df.format(investimento / meses) + " | Juros: R$" + df.format(rendimento / meses) + " = R$" + (df.format((rendimento + investimento) / meses)) + " ( R$" + df.format(repasse) + ")");

    }


}