package org.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ReadCSVFile {
    static int empresas = 0;
    static BigDecimal emprestimos = new BigDecimal(0);
    static BigDecimal mediaJuros = new BigDecimal(0);
    static BigDecimal somaParcelas = new BigDecimal(0);
    static BigDecimal recebido = new BigDecimal(0);
    static BigDecimal credito = new BigDecimal(0);
    static BigDecimal deposito = new BigDecimal(56500);

    public static void main(String[] args) {
        imprimir();
    }

    public static void imprimir() {
        final String csvFile = "C:/Users/franca/Downloads/arquivo.csv";
        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            // Lê todas as linhas do arquivo CSV
            List<String[]> lines = reader.readAll();
            // Pula a primeira linha (cabeçalho) se houver pelo menos uma linha no arquivo
            if (!lines.isEmpty()) {
                lines.remove(0); // Remove a primeira linha
            }
            System.out.println("+------------+--------+---------+---------+------+-----------+-----------+");
            System.out.println("| Valor      | Juros  | Mensal  | Pagas   | Parc | Total     | Crédito   |");
            System.out.println("+------------+--------+---------+---------+------+-----------+-----------+");
            // Itera sobre cada linha
            for (String[] line : lines) {
                empresas = lines.size();

                if (line.length >= 4) { // Verifica se há pelo menos 3 colunas (a, b, c)
                    String empresa = line[2];
                    BigDecimal valor = new BigDecimal(line[3].replaceAll("[^\\d,]", "").replace(",", ".")).setScale(0, BigDecimal.ROUND_DOWN);;
                    BigDecimal juros = new BigDecimal(line[5].replaceAll("[^\\d,]", "").replace(",", "."));
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
                    BigDecimal creditoAux = new BigDecimal(0);
                    creditoAux = total.subtract(pago).setScale(0, BigDecimal.ROUND_DOWN);;
                    credito = credito.add(creditoAux);
                    System.out.printf("| %-10s | %-6s | %-7s | %-7s | %-5s| %-10s| %-10s|\n", valor, juros, mensal, pago, parcelas, total, creditoAux);
                } else {
                    System.out.println("A linha não possui todas as colunas (a, b, c).");
                }
            }
            BigDecimal mediaJurosAux = mediaJuros.divide(new BigDecimal(empresas), 2, RoundingMode.HALF_UP);
            System.out.println("+------------------------------------------------------------------------+\n");
            System.out.println("+----------+--------+--------------+-----------+-------------+-----------+");
            System.out.println("| Empresas | Juros  | Emprestimo   | Parcelas  | Recebido    | Crédito   |");
            System.out.printf("| %-8s | %-6s | %-12s | %-9s | %-10s  | %-10s|\n", empresas, mediaJurosAux + "%", "R$ " + emprestimos, "R$ " + somaParcelas.setScale(0, BigDecimal.ROUND_DOWN), "R$ " + recebido.setScale(0, BigDecimal.ROUND_DOWN), "R$ " + credito.setScale(0, BigDecimal.ROUND_DOWN));
            System.out.println("+----------+--------+--------------+-----------+-------------+-----------+");


            System.out.println("+----------+----------+--------------+-----------+-------------+---------+");
            System.out.println("| Deposito | Lucro    | xxxxxxxxxx   | xxxxxxxx  | xxxxxxxx    | xxxxx   |");
            System.out.printf("| %-8s | %-6s | %-12s | %-9s | %-10s  | %-8s|\n", "R$ "+deposito, "R$ "+credito.subtract(deposito), "R$ " , "R$ ", "R$ " , "R$ ");
            System.out.println("+----------+----------+--------------+-----------+-------------+---------+");

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }
}
