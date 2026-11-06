package org.example.Componets;

import org.example.Dao.PessoaDao;
import org.example.Domain.Endereco;
import org.example.Domain.Pessoa;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public final class MenuPessoa {
    private static final PessoaDao DAO = new PessoaDao();

    private MenuPessoa() {
    }

    public static void exibir(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n===== MENU DE CLIENTES =====");
            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Listar clientes");
            System.out.println("3 - Buscar cliente pelo nome");
            System.out.println("4 - Atualizar cliente");
            System.out.println("5 - Excluir cliente");
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
        Pessoa pessoa = lerDadosPessoa(scanner);
        if (pessoa != null) {
            DAO.salvar(pessoa);
            System.out.println("Cliente cadastrado com sucesso.");
        }
    }

    private static void listar() {
        List<Pessoa> pessoas = DAO.listar();
        if (pessoas.isEmpty()) {
            System.out.println("Ainda não há clientes cadastrados.");
            return;
        }
        for (int i = 0; i < pessoas.size(); i++) {
            mostrarPessoa(i, pessoas.get(i));
        }
    }

    private static void buscar(Scanner scanner) {
        System.out.print("Digite o nome ou parte do nome do cliente: ");
        String nomeBuscado = scanner.nextLine().trim().toLowerCase(Locale.ROOT);
        if (nomeBuscado.isBlank()) {
            System.out.println("Digite um nome para fazer a busca.");
            return;
        }

        List<Pessoa> pessoas = DAO.listar();
        boolean encontrou = false;
        for (int i = 0; i < pessoas.size(); i++) {
            Pessoa pessoa = pessoas.get(i);
            if (pessoa.getNome() != null
                    && pessoa.getNome().toLowerCase(Locale.ROOT).contains(nomeBuscado)) {
                mostrarPessoa(i, pessoa);
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum cliente encontrado com esse nome.");
        }
    }

    private static void atualizar(Scanner scanner) {
        int indice = lerIndice(scanner, "Digite o número do cliente que deseja atualizar: ");
        if (!indiceValido(indice)) {
            System.out.println("Número inválido.");
            return;
        }
        System.out.println("Digite os novos dados do cliente.");
        Pessoa pessoaAtualizada = lerDadosPessoa(scanner);
        if (pessoaAtualizada != null) {
            DAO.atualizar(indice, pessoaAtualizada);
            System.out.println("Cliente atualizado com sucesso.");
        }
    }

    private static void excluir(Scanner scanner) {
        int indice = lerIndice(scanner, "Digite o número do cliente que deseja excluir: ");
        if (!indiceValido(indice)) {
            System.out.println("Número inválido.");
            return;
        }
        DAO.excluir(indice);
        System.out.println("Cliente excluído.");
    }

    private static Pessoa lerDadosPessoa(Scanner scanner) {
        Pessoa pessoa = new Pessoa();
        pessoa.setCriadoPor("Sistema da loja");

        System.out.print("Nome: ");
        pessoa.setNome(scanner.nextLine().trim());

        System.out.print("Data de nascimento (AAAA-MM-DD): ");
        try {
            pessoa.setDataNascimento(LocalDate.parse(scanner.nextLine().trim()));
        } catch (DateTimeParseException e) {
            System.out.println("Data inválida. Use o formato AAAA-MM-DD.");
            return null;
        }

        System.out.print("E-mail: ");
        pessoa.setEmail(scanner.nextLine().trim());
        System.out.print("Telefone: ");
        pessoa.setTelefone(scanner.nextLine().trim());
        System.out.print("Gênero: ");
        pessoa.setGenero(scanner.nextLine().trim());
        System.out.print("CPF: ");
        pessoa.setCpf(scanner.nextLine().trim());

        Endereco endereco = new Endereco();
        System.out.print("Estado: ");
        endereco.setEstado(scanner.nextLine().trim());
        System.out.print("Cidade: ");
        endereco.setCidade(scanner.nextLine().trim());
        System.out.print("Rua: ");
        endereco.setRua(scanner.nextLine().trim());
        System.out.print("Número da residência: ");
        try {
            endereco.setNumero(Integer.parseInt(scanner.nextLine().trim()));
        } catch (NumberFormatException e) {
            System.out.println("Número inválido.");
            return null;
        }
        pessoa.setEndereco(endereco);
        return pessoa;
    }

    private static int lerIndice(Scanner scanner, String mensagem) {
        System.out.print(mensagem);
        return MenuGeral.lerOpcao(scanner);
    }

    private static boolean indiceValido(int indice) {
        return indice >= 0 && indice < DAO.listar().size();
    }

    private static void mostrarPessoa(int indice, Pessoa pessoa) {
        Endereco endereco = pessoa.getEndereco();
        System.out.println("\n" + indice + " - Nome: " + pessoa.getNome()
                + " | Nascimento: " + pessoa.getDataNascimento()
                + " | E-mail: " + pessoa.getEmail()
                + " | Telefone: " + pessoa.getTelefone()
                + " | Gênero: " + pessoa.getGenero()
                + " | CPF: " + pessoa.getCpf()
                + " | Endereço: " + endereco.getRua() + ", " + endereco.getNumero()
                + " - " + endereco.getCidade() + ", " + endereco.getEstado());
    }
}
