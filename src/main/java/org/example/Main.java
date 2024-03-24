package org.example;

import java.text.DecimalFormat;

public class Main {

    public static double investimento = 0;
    public static double juros = 30;
    public static double meses = 24;
    public static double rendimento = 0;
    public static double recebiveis = 0;
    public static double repasse = 0;
    public static double transferencia =0;
    public static double transferenciaTotal =30000;
    //public static double crédito = 0;
    public static DecimalFormat df = new DecimalFormat("#,###");


    public static void main(String[] args) {
        simular();
    }

    public static void simular() {
        System.out.println("\n####################################################################################");
        System.out.print("Período: " + meses + "x | Juros: " + juros + "%\n");
        System.out.println("####################################################################################\n");
        investir(transferenciaTotal);
        double pago = 0;
        for (int mes = 1; mes <= meses; mes++) {

            recebiveis += (investimento + rendimento) / meses;
            repasse += (investimento+(rendimento/2) ) / meses;
           // repasse += (investimento + rendimento) / meses;
            System.out.println("\n---------------------------------------------------------------------\n");
            System.out.print("Mês: " + mes);
            valorAtualizdo();
            double somaRepasse = 0;
            while (repasse >= 500) {
                repasse -= 500;
                somaRepasse += 500;
                investir(500);
            }
            System.out.println("-Transferência: R$" + df.format(transferencia) + " | Repasse R$" + df.format(somaRepasse) + " = +R$" + df.format(transferencia + somaRepasse));
            transferenciaTotal+=transferencia;
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
        double receber = (total - recebiveis);
        double saldo = (repasse+(total-recebiveis));
        double rendimentoPorcento = (rendimento/transferenciaTotal*100);

        System.out.println("\n-Saldo: R$" + df.format(saldo) + " |  Transferência: R$"+ df.format(transferenciaTotal) );
        System.out.println("-Investimento: R$" + df.format(investimento) + " | Rendimento: R$" + df.format(rendimento)+ " (" +df.format(rendimentoPorcento)+ "%)");
        System.out.println("-Total: R$" + df.format(total) + " | Recebiveis: R$" + df.format(recebiveis) + "  | Receber R$" + df.format(receber));
        System.out.println("-Pagamento: R$" + df.format(investimento / meses) + " | Juros: R$" + df.format(rendimento / meses) + " = R$" + (df.format((rendimento + investimento) / meses)) + " | Carteira R$" + df.format(repasse) );

    }


}