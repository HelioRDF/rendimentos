package org.example;

import java.text.DecimalFormat;

public class Main2 {
    public static double deposito=30000;
    public static double juros=30;
    public static double meses=24;

    public static double receber=0;
    public static double retornoMes=0;
    public static double pago=0;
    public static DecimalFormat df = new DecimalFormat("#,###");

    public static void main(String[] args) {
        info();
        pagamento();
    }

    public static void info(){
        double retorno = deposito+calcularJurosDoEmprestimo();
        retornoMes =retorno/meses;
        System.out.println("========================================================");
        System.out.println("Meses: "+df.format(meses));
        System.out.println("Deposito: R$"+df.format(deposito));
        System.out.println("Juros Ano: "+df.format(juros)+"%" + " | Juros Mês: "+jurosPorc()+"%");
        System.out.println("Juros: R$"+df.format(calcularJurosDoEmprestimo())  + " | Mês R$"+(calcularJurosDoEmprestimo()/meses));
        System.out.println("Retorno: R$"+df.format(retorno)+ " | Mês R$"+retorno/meses);
        System.out.println("========================================================");
    }

    public static double jurosPorc() {
    double jurosMesPorc = (juros/12);
       return jurosMesPorc;
    }

    public static double calcularPagamentoComJuros(double receber) {
        double calcularValor = (receber/meses)*jurosPorc();
       // System.out.println("-> "+calcularValor);
        return 0;
    }

    public static double calcularJurosDoEmprestimo(){
        receber = deposito;
        double totalJuros=0;
        double cont=meses;
       // System.out.println("Mês | Receber  | JurosMês | Total de Juros");
        while (cont>0){
            double jurosMes=receber/100*jurosPorc();
            totalJuros+= jurosMes;
           // System.out.println(df.format(cont) +"  | R$"+ df.format(receber)+" | R$"+ df.format(jurosMes) +"    | R$"+ df.format(totalJuros));
            receber-= receber/cont;
            calcularPagamentoComJuros(receber);
            //receber-= receber
            cont--;
        };
        return totalJuros;
    }

    public static void pagamento(){
        int cont=1;
        double carteira=0;
        double novoInvestimento=0;
        System.out.println("Mês | Pago    | Carteira | Investimento | Juros");

        while (cont<=meses){
            pago+=retornoMes;
            carteira+=retornoMes;

            System.out.println(cont+"   | R$"+df.format(pago)+ " | R$"+df.format(carteira)+ " |  R$"+df.format(novoInvestimento)+" |  R$" );
            cont++;

            while(carteira>=1000){
                carteira-=1000;
                novoInvestimento+=1000;
            }
        }

    }

}