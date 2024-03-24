package org.example;

import java.text.DecimalFormat;

public class Main3 {
    public static DecimalFormat df = new DecimalFormat("#,###");
    public static DecimalFormat dfp = new DecimalFormat("##.#");
    public static double deposito=125000;
    public static double juros=30;
    public static double meses=24;

    public static double lucro=0;
    public static double saldoFinal=0;




    public static void main(String[] args) {
        info();
        calcularLucro();
    }

    public static void info(){

        System.out.println("========================================================");
        System.out.println("Meses: "+df.format(meses));
        System.out.println("Deposito: R$"+df.format(deposito));
        System.out.println("Juros Ano: "+dfp.format(juros)+"%" + " | Juros Mês: "+dfp.format(juros/12)+"%");
        System.out.println("========================================================");
    }

    public static void calcularLucro(){
        int cont=1;
        double investimento=deposito;
        double jurosMesPerc = juros/12;
        double saldo = 0;
        System.out.println("Ref | investimento |pagamento | lucroMes  | totalMes |lucro          | Saldo");

        while (cont<=meses){
            double pagamento = deposito/meses;
            double lucroMes= (investimento/100)*jurosMesPerc;
            double totalMes= pagamento+lucroMes;
            saldo+=totalMes;
            investimento-=pagamento;
            lucro+=lucroMes;
            while(saldo>=1000){
            saldo-=1000;
            investimento+=1000;
            }
            System.out.println(cont+"   | R$"+df.format(investimento)+ "     | R$"+df.format(pagamento)+"  | R$"+df.format(lucroMes)+"     | R$"+df.format(totalMes) +"  | R$"+ df.format(lucro)+" ("+dfp.format(lucro/investimento*100)+"%)  | R$"+ df.format(saldo));
            cont++;
        }

    }







}