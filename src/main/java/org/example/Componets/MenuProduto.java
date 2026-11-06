package org.example.Componets;

import org.example.Dao.ProdutoDao;
import org.example.Domain.Eletrodomestico;
import org.example.Domain.Movel;
import org.example.Domain.Produto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public final class MenuProduto {
    private static final ProdutoDao DAO = new ProdutoDao();

    private MenuProduto() {
    }

    public static void exibir(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n===== MENU DE PRODUTOS =====");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Listar produtos");
            System.out.println("3 - Buscar produto");
            System.out.println("4 - Atualizar produto");
            System.out.println("5 - Excluir produto");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = MenuGeral.lerOpcao(scanner);

            switch (opcao) {
                case 1 -> cadastrar(scanner);
                case 2 -> listar();
                case 3 -> buscar(scanner);
                case 4 -> atualizar(scanner);
                case 5 -> excluir(scanner);
                case 0 -> System.out.println("Voltando ao menu geral...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private static void cadastrar(Scanner scanner) {
        System.out.println("Tipo: 1 - Eletrodoméstico | 2 - Móvel");
        System.out.print("Digite 1 para Eletrodoméstico ou 2 para Móvel: ");
        String tipo = scanner.nextLine().trim();
        Produto produto;
        if (tipo.equals("1") || tipo.equalsIgnoreCase("eletrodomestico") || tipo.equalsIgnoreCase("eletrodoméstico")) {
            produto = new Eletrodomestico();
        } else if (tipo.equals("2") || tipo.equalsIgnoreCase("movel") || tipo.equalsIgnoreCase("móvel")) {
            produto = new Movel();
        } else {
            System.out.println("Tipo inválido.");
            return;
        }
        if (lerDadosProduto(produto, scanner)) {
            DAO.salvar(produto);
            System.out.println("Produto cadastrado com sucesso.");
        }
    }

    private static void listar() {
        List<Produto> produtos = DAO.listar();
        if (produtos.isEmpty()) {
            System.out.println("Ainda não há produtos cadastrados.");
            return;
        }
        for (int i = 0; i < produtos.size(); i++) {
            mostrarProduto(i, produtos.get(i));
        }
    }

    private static void buscar(Scanner scanner) {
        int indice = lerIndice(scanner, "Digite o número do produto: ");
        if (!indiceValido(indice)) {
            System.out.println("Produto não encontrado.");
            return;
        }
        mostrarProduto(indice, DAO.buscar(indice));
    }

    private static void atualizar(Scanner scanner) {
        int indice = lerIndice(scanner, "Digite o número do produto que deseja atualizar: ");
        if (!indiceValido(indice)) {
            System.out.println("Número inválido.");
            return;
        }
        Produto antigo = DAO.buscar(indice);
        Produto atualizado = antigo instanceof Eletrodomestico
                ? new Eletrodomestico() : new Movel();
        if (lerDadosProduto(atualizado, scanner)) {
            DAO.atualizar(indice, atualizado);
            System.out.println("Produto atualizado com sucesso.");
        }
    }

    private static void excluir(Scanner scanner) {
        int indice = lerIndice(scanner, "Digite o número do produto que deseja excluir: ");
        if (!indiceValido(indice)) {
            System.out.println("Número inválido.");
            return;
        }
        DAO.excluir(indice);
        System.out.println("Produto excluído.");
    }

    private static boolean lerDadosProduto(Produto produto, Scanner scanner) {
        System.out.print("Nome: ");
        try {
            produto.setNome(scanner.nextLine().trim());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
        System.out.print("Preço: ");
        try {
            BigDecimal preco = new BigDecimal(scanner.nextLine().trim().replace(",", "."));
            produto.setPreco(preco);
        } catch (IllegalArgumentException e) {
            System.out.println("Preço inválido.");
            return false;
        }
        System.out.print("Data de criação (AAAA-MM-DD): ");
        try {
            produto.setDataCriacao(LocalDate.parse(scanner.nextLine().trim()));
        } catch (DateTimeParseException e) {
            System.out.println("Data inválida. Use o formato AAAA-MM-DD.");
            return false;
        }
        return true;
    }

    private static int lerIndice(Scanner scanner, String mensagem) {
        System.out.print(mensagem);
        return MenuGeral.lerOpcao(scanner);
    }

    private static boolean indiceValido(int indice) {
        return indice >= 0 && indice < DAO.listar().size();
    }

    private static void mostrarProduto(int indice, Produto produto) {
        System.out.println(indice + " - Nome: " + produto.getNome() + " | Preço: R$ " + produto.getPreco() + " | Data de criação: " + produto.getDataCriacao());
    }
}
