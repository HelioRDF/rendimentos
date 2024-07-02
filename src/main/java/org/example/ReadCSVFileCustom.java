package org.example;

import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

public class ReadCSVFileCustom {
    static int empresas = 0;
    static BigDecimal emprestimos = new BigDecimal(0);
    static BigDecimal mediaJuros = new BigDecimal(0);
    static BigDecimal somaParcelas = new BigDecimal(0);
    static BigDecimal recebido = new BigDecimal(0);
    static BigDecimal lucroMes = new BigDecimal(0);
    static BigDecimal lucroTotal = new BigDecimal(0);
    static BigDecimal credito = new BigDecimal(0);
    static BigDecimal deposito = new BigDecimal(47000);
    static BigDecimal totalParcelas = new BigDecimal(24);
    static BigDecimal somaDiferenca = new BigDecimal(24);


    public static void main(String[] args) {
        imprimir();
    }

    public static void imprimir() {
        final String csvFile = "C:/Users/Gamer/Downloads/dashboard.csv";
        try {
            List<String[]> lines = CSVProcessor.readCSV(csvFile);
            if (!lines.isEmpty()) {
                lines.remove(0); // Remove a primeira linha (cabeçalho)
            }
            imprimirTabela(lines);
            imprimirEstatisticas();
            imprimirResumoFinal();
            calcularLucro();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    private static void imprimirTabela(List<String[]> lines) {
        System.out.println("+------------+--------+---------+---------+------+-----------+-----------+");
        System.out.println("| Valor      | Juros  | Mensal  | Pagas   | Parc | Total     | Crédito   |  Lucro   | Nexoos   | Diferença   | Empresa   |");
        System.out.println("+------------+--------+---------+---------+------+-----------+-----------+");
        for (String[] line : lines) {
            empresas = lines.size();
            processarLinha(line);
        }
        System.out.println("+------------------------------------------------------------------------+\n");
    }

    private static void processarLinha(String[] line) {

        if (line.length >= 4) {
            BigDecimal valor = new BigDecimal(line[3].replaceAll("[^\\d,]", "").replace(",", ".")).setScale(0, BigDecimal.ROUND_DOWN);
            BigDecimal juros = new BigDecimal(line[5].replaceAll("[^\\d,]", "").replace(",", "."));
            String empresa = line[2];
            BigDecimal mensal = new BigDecimal(line[6]);
            String parcelas = line[7];
            BigDecimal pago = new BigDecimal(line[8]);


            String[] parcelasAux = parcelas.split("/");
            parcelas = parcelasAux[1];
            BigDecimal total = mensal.multiply(new BigDecimal(parcelas));
            emprestimos = emprestimos.add(valor);
            mediaJuros = mediaJuros.add(juros);
            somaParcelas = somaParcelas.add(mensal);
            recebido = recebido.add(pago);
            BigDecimal creditoAux = total.subtract(pago).setScale(0, BigDecimal.ROUND_DOWN);
            credito = credito.add(creditoAux);
            BigDecimal lucro = somarJurosMês(valor, juros, parcelas);
            BigDecimal nexoos = total.subtract(valor);
            BigDecimal diferenca = lucro.subtract(nexoos);
            somaDiferenca = somaDiferenca.add(diferenca);
            System.out.printf("| %-10s | %-6s | %-7s | %-7s | %-5s| %-10s| %-10s| %-10s| %-10s|%-10s|%-10s|\n", valor, juros, mensal, pago, parcelas, total, creditoAux, lucro, nexoos, diferenca,empresa );

        } else {
            System.out.println("A linha não possui todas as colunas (a, b, c).");
        }
    }

    private static BigDecimal somarJurosMês(BigDecimal valor, BigDecimal jurosAno, String parcelas) {
        int parcelasAux = Integer.parseInt(parcelas);
        int cont = 1;
        BigDecimal valorAux = valor;
        BigDecimal totalJurosMes = jurosAno.divide(new BigDecimal(12), 4, RoundingMode.HALF_UP);
        BigDecimal parcelaEmprestimo = valorAux.divide(new BigDecimal(parcelasAux), 4, RoundingMode.HALF_UP);
        BigDecimal valorJurosMes = new BigDecimal(0);
        BigDecimal valorTotal = new BigDecimal(0);
        BigDecimal somaValorTotal = new BigDecimal(0);

        while (cont <= parcelasAux) {
            valorJurosMes = valorAux.divide(new BigDecimal(100)).multiply(totalJurosMes);
            valorTotal = parcelaEmprestimo.add(valorJurosMes);
            somaValorTotal = somaValorTotal.add(valorTotal);
            valorAux = valorAux.subtract(parcelaEmprestimo);
            //    System.out.println(cont + " - " + valorAux + " - " + valorJurosMes + " - " + parcelaEmprestimo + " - " +parcelaEmprestimo.add(valorJurosMes)+ " - " + somaValorTotal);
            cont++;
        }
        BigDecimal lucro = somaValorTotal.subtract(valor).setScale(2, BigDecimal.ROUND_DOWN);

        return lucro;
    }

    private static void imprimirEstatisticas() {
        BigDecimal mediaJurosAux = mediaJuros.divide(new BigDecimal(empresas), 2, RoundingMode.HALF_UP);
        System.out.println("+----------+--------+--------------+-----------+-------------+-----------+");
        System.out.println("| Empresas | Juros  | Emprestimo   | Parcelas  | Recebido    | Crédito   |");
        System.out.printf("| %-8s | %-6s | %-12s | %-9s | %-10s  | %-10s|\n", empresas, mediaJurosAux + "%", "R$ " + emprestimos, "R$ " + somaParcelas.setScale(0, BigDecimal.ROUND_DOWN), "R$ " + recebido.setScale(0, BigDecimal.ROUND_DOWN), "R$ " + credito.setScale(0, BigDecimal.ROUND_DOWN));
        System.out.println("+----------+--------+--------------+-----------+-------------+-----------+");
    }

    private static void imprimirResumoFinal() {
        lucroMes = credito.subtract(deposito).divide(totalParcelas, BigDecimal.ROUND_HALF_UP);
        lucroTotal = credito.subtract(deposito);
        System.out.println("+----------+----------+--------------+-----------+-------------+---------+");
        System.out.println("| Deposito | Lucro    | Lucro mês    | xxxxxxxx  | xxxxxxxx    | xxxxx   |");
        System.out.printf("| %-8s | %-6s | %-12s | %-9s | %-10s  | %-8s|\n", "R$ " + deposito, "R$ " + lucroTotal, "R$ " + lucroMes, "R$ ", "R$ ", "R$ ");
        System.out.println("+----------+----------+--------------+-----------+-------------+---------+");
    }

    public static void calcularLucro() {
        int cont = 1;
        int ParcAux = totalParcelas.intValue();
        BigDecimal pagamentoAux = somaParcelas.setScale(0, BigDecimal.ROUND_DOWN);
        BigDecimal rendimento = pagamentoAux.multiply(new BigDecimal(0.35)).setScale(0, BigDecimal.ROUND_DOWN);
        BigDecimal somaRendimento = new BigDecimal(0);
        BigDecimal lucroTotalAux = new BigDecimal(0);


        System.out.println("\n+-----+-----------+------------+------------+-------------+--------------+");
        System.out.println("| Mês | Pagamento | Lucro mês  | Rendimento | Soma        | Lucro Total  |");
        System.out.println("+-----+-----------+------------+------------+-------------+--------------+");
        while (cont <= ParcAux) {
            somaRendimento = somaRendimento.add(rendimento);
            lucroTotalAux = lucroTotal.add(somaRendimento);
            System.out.printf("| %-3s | %-9s | %-10s | %-10s | %-10s  | %-13s|\n", cont, "R$ " + pagamentoAux.multiply(new BigDecimal(cont)), "R$ " + lucroMes.multiply(new BigDecimal(cont)), "R$ " + rendimento, "R$ " + somaRendimento, "R$ " + lucroTotalAux);
            cont++;
        }
        System.out.println("+-----+-----------+-------------+-----------+-------------+--------------+");
        System.out.println("Crédito: " + lucroTotalAux.add(deposito) + " | Diferenca: " + somaDiferenca);
    }
}
