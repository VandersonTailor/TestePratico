import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Principal {
    private static final DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DecimalFormat formatadorValor;
    private static final BigDecimal salarioMinimo = new BigDecimal("1212.00");

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        formatadorValor = new DecimalFormat("#,##0.00", symbols);
    }

    public static void main(String[] args) {
        List<Funcionario> funcionarios = criarFuncionarios();

        for (int i = 0; i < funcionarios.size(); i++) {
            if (funcionarios.get(i).getNome().equalsIgnoreCase("João")) {
                funcionarios.remove(i);
                break;
            }
        }

        System.out.println("3.3 - Lista de funcionarios:");
        for (Funcionario funcionario : funcionarios) {
            imprimirFuncionario(funcionario);
        }

        for (Funcionario funcionario : funcionarios) {
            BigDecimal aumento = funcionario.getSalario().multiply(new BigDecimal("0.10"));
            BigDecimal novoSalario = funcionario.getSalario().add(aumento);
            funcionario.setSalario(novoSalario.setScale(2, RoundingMode.HALF_UP));
        }

        System.out.println();
        System.out.println("3.4 - Lista de funcionarios com 10% de aumento:");
        for (Funcionario funcionario : funcionarios) {
            imprimirFuncionario(funcionario);
        }

        Map<String, List<Funcionario>> funcionariosPorFuncao = new LinkedHashMap<>();
        for (Funcionario funcionario : funcionarios) {
            String funcao = funcionario.getFuncao();

            if (!funcionariosPorFuncao.containsKey(funcao)) {
                funcionariosPorFuncao.put(funcao, new ArrayList<>());
            }

            funcionariosPorFuncao.get(funcao).add(funcionario);
        }

        System.out.println();
        System.out.println("3.6 - Funcionarios agrupados por funcao:");
        for (String funcao : funcionariosPorFuncao.keySet()) {
            System.out.println("Funcao: " + funcao);

            for (Funcionario funcionario : funcionariosPorFuncao.get(funcao)) {
                imprimirFuncionario(funcionario);
            }
        }

        System.out.println();
        System.out.println("3.8 - Funcionarios que fazem aniversario nos meses 10 e 12:");
        for (Funcionario funcionario : funcionarios) {
            int mes = funcionario.getDataNascimento().getMonthValue();

            if (mes == 10 || mes == 12) {
                imprimirFuncionario(funcionario);
            }
        }

        System.out.println();
        System.out.println("3.9 - Funcionario com maior idade:");
        Funcionario funcionarioMaisVelho = funcionarios.get(0);
        for (Funcionario funcionario : funcionarios) {
            if (funcionario.getDataNascimento().isBefore(funcionarioMaisVelho.getDataNascimento())) {
                funcionarioMaisVelho = funcionario;
            }
        }
        int idade = Period.between(funcionarioMaisVelho.getDataNascimento(), LocalDate.now()).getYears();
        System.out.println("Nome: " + funcionarioMaisVelho.getNome() + " | Idade: " + idade);

        System.out.println();
        System.out.println("3.10 - Funcionarios em ordem alfabetica:");
        List<Funcionario> funcionariosOrdenados = new ArrayList<>(funcionarios);
        Collections.sort(funcionariosOrdenados, (funcionario1, funcionario2) ->
                funcionario1.getNome().compareToIgnoreCase(funcionario2.getNome()));
        for (Funcionario funcionario : funcionariosOrdenados) {
            imprimirFuncionario(funcionario);
        }

        System.out.println();
        System.out.println("3.11 - Total dos salarios:");
        BigDecimal totalSalarios = BigDecimal.ZERO;
        for (Funcionario funcionario : funcionarios) {
            totalSalarios = totalSalarios.add(funcionario.getSalario());
        }
        System.out.println("Total: R$ " + formatarValor(totalSalarios));

        System.out.println();
        System.out.println("3.12 - Quantidade de salarios minimos por funcionario:");
        for (Funcionario funcionario : funcionarios) {
            BigDecimal quantidade = funcionario.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println(funcionario.getNome() + ": " + formatarValor(quantidade) + " salarios minimos");
        }
    }

    private static List<Funcionario> criarFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();

        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloisa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        return funcionarios;
    }

    private static void imprimirFuncionario(Funcionario funcionario) {
        System.out.println(
                "Nome: " + funcionario.getNome() +
                        " | Data Nascimento: " + funcionario.getDataNascimento().format(formatadorData) +
                        " | Salario: R$ " + formatarValor(funcionario.getSalario()) +
                        " | Funcao: " + funcionario.getFuncao()
        );
    }

    private static String formatarValor(BigDecimal valor) {
        return formatadorValor.format(valor);
    }
}
