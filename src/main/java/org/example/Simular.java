package org.example;

import java.text.DecimalFormat;

public class Simular {
    public static DecimalFormat df = new DecimalFormat("#,###");
    public static DecimalFormat dfp = new DecimalFormat("##.##");
    //2024=56000 | 2026=165000 | 2028=297292 | *2030=536750 | 2032=969500 | 2034=1752208
    public static double deposito = 100000;
    public static double juros = 18;
    public static double meses = 24;
    public static double lucro = 0;
    public static double Recebivel = 0;
    public static double adicional =0;

    public static void main(String[] args) {
        info();
        calcularLucro();
    }

    public static void info() {
        System.out.println("========================================================");
        System.out.println("Meses: " + df.format(meses));
        System.out.println("Deposito: R$" + df.format(deposito));
        System.out.println("Juros Ano: " + dfp.format(juros) + "%" + " | Juros Mês: " + dfp.format(juros / 12) + "%");
        System.out.println("========================================================");
    }

    public static void calcularLucro() {
        int cont = 1;
        double investimento = deposito;
        double jurosMesPerc = juros / 12;
        double saldo = 0;
        System.out.println("Ref | investimento | pagamento | lucroMes     | totalMes  | lucro            | Saldo");

        while (cont <= meses) {
            double pagamento = investimento / meses;
            double lucroMes = (investimento / 100) * jurosMesPerc;
            double totalMes = pagamento + lucroMes;
            saldo += totalMes;
            investimento -= pagamento;
            lucro += lucroMes;
            investimento += adicional;
            Recebivel+= totalMes;
            while (saldo >= 1000) {
                saldo -= 1000;
                investimento += 1000;
                deposito+= 1000;
            }
            System.out.println(cont + "   | R$" + df.format(investimento) + "     | R$" + df.format(pagamento) + "  | R$" + df.format(lucroMes) + "     | R$" + df.format(totalMes) + "  | R$" + df.format(lucro) + " (" + dfp.format(lucro / investimento * 100) + "%)  | R$" + df.format(saldo)+" - "+ df.format(Recebivel));
            cont++;
        }
    }
}