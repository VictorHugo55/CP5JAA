package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Entidades.Album;
import org.example.Entidades.Artista;
import org.example.Entidades.Musica;
import org.example.Repositorios.RepositorioAlbum;
import org.example.Repositorios.RepositorioArtista;
import org.example.Repositorios.RepositorioMusica;
import org.example.Servico_De_Validacao.ValidacaoEntidade;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Logger logger = LogManager.getLogger(Main.class);
    private static RepositorioArtista repositorioArtista = new RepositorioArtista();
    private static RepositorioAlbum repositorioAlbum = new RepositorioAlbum();
    private static RepositorioMusica repositorioMusica = new RepositorioMusica();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        while (opcao != 4){
            System.out.println("==== BEM-VINDO ====");
            System.out.println("\t");
            System.out.println("\t1 - Cadastro");
            System.out.println("\t2 - Mostrar");
            System.out.println("\t3 - Buscar");
            System.out.println("\t4 - Excluir");
            System.out.println("\t5 - Atualiazar");
            System.out.println("\t0 - Sair");
            System.out.println("\t");
            System.out.println("Escolha uma das opções: ");

            int escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha){
                case 1:
                    while (true){
                        System.out.println("==== CADASTRO ====");
                        System.out.println("\t1 - Cadastrar Artista");
                        System.out.println("\t2 - Cadastrar Álbum");
                        System.out.println("\t3 - Cadastrar Musica");
                        System.out.println("\t0 - Sair");

                        int escolhaCadastro = scanner.nextInt();
                        scanner.nextLine();

                        switch (escolhaCadastro){
                            case 1:
                                System.out.println("==== CADASTRAR ARTISTA ====");
                                System.out.println("Nome do Artista: ");
                                String nomeArtista = scanner.nextLine();
                                System.out.println("Gênero Musical: ");
                                String generoArtista = scanner.nextLine();

                                Artista artista = new Artista(nomeArtista, generoArtista);
                                if(ValidacaoEntidade.vaidacaoArtista(artista)){
                                    artista.setNome(nomeArtista);
                                    artista.setGeneroMusical(generoArtista);
                                    repositorioArtista.adicionar(artista);
                                    break;
                                }else {
                                    System.out.println("Dados Inválidos");
                                    logger.warn("Artista com dados inválidos. O Artista não pode ser vazio");
                                    break;
                                }
                            case 2:
                                System.out.println("==== Cadastrar Album ====");
                                System.out.println("Titulo do Album: ");
                                String tituloAlbum = scanner.nextLine();
                                System.out.println("Ano de Lançamento: ");
                                int anoAlbum = scanner.nextInt();
                                scanner.nextLine();

                                System.out.println("ID do Artista: ");
                                int idArtista = scanner.nextInt();
                                scanner.nextLine();

                                Artista artistaAlbum = repositorioArtista.buscarPorId(idArtista);
                                if(artistaAlbum!=null){
                                    Album album = new Album(tituloAlbum, anoAlbum, artistaAlbum);
                                    if (ValidacaoEntidade.vaidacaoAlbum(album)){
                                        album.setTitulo(tituloAlbum);
                                        album.setAnoLancamento(anoAlbum);
                                        album.setArtista(artistaAlbum);
                                        repositorioAlbum.adicionar(album);
                                        break;
                                    }else {
                                        System.out.println("Dados Inválidos");
                                        logger.warn("Album com dados inválidos");
                                    }

                                }else {
                                    System.out.println("ID do artista não foi encontrado");
                                    break;
                                }

                            case 3:
                                System.out.println("==== CADASTRO DE MÚSICA ====");
                                System.out.println("Título da Música: ");
                                String tituloMusica = scanner.nextLine();
                                System.out.println("Duração da música: ");
                                double duracaoMusica = scanner.nextInt();
                                scanner.nextLine();

                                System.out.println("ID do Album: ");
                                int idAlbum = scanner.nextInt();
                                scanner.nextLine();

                                Album album = repositorioAlbum.buscarPorId(idAlbum);
                                if(album!= null){
                                    Musica musica = new Musica(tituloMusica,duracaoMusica,album);
                                    if(ValidacaoEntidade.validacaoMusica(musica)){
                                        musica.setTitulo(tituloMusica);
                                        musica.setDuracao(duracaoMusica);
                                        musica.setAlbum(album);
                                        repositorioMusica.adicionar(musica);
                                        break;

                                    } else{
                                        System.out.println("Dados inválidos");
                                        logger.warn("Música cadastrada com dados inválidos");
                                        break;
                                    }

                                }
                                else {
                                    System.out.println("ID do álbum  não foi encontrado");
                                    break;
                                }
                            case 0:
                                break;

                            default:
                                System.out.println("Opção Inválida. Tente Novamente");

                        }
                        if (escolhaCadastro == 0){
                            break;
                        }
                    }
                    break;


                case 2:
                    while (true){
                        System.out.println("=== EXIBIÇÂO ===");
                        System.out.println("\t1 - Exibir Artistas");
                        System.out.println("\t2 - Exibir Albuns");
                        System.out.println("\t3 - Exibir Músicas");
                        System.out.println("\t0 - sair");

                        int escolhaExibir = scanner.nextInt();
                        scanner.nextLine();
                        switch (escolhaExibir){
                            case 1:
                                System.out.println("==== LISTA DOS ARTISTAS");
                                for(Artista artista1 : repositorioArtista.exibir())
                                    System.out.println("\n- ID: " + artista1.getId()
                                            + "\n- Nome: " + artista1.getNome()
                                            + "\n= Gênero Musical" + artista1.getGeneroMusical());
                                break;

                            case 2:
                                System.out.println("==== LISTA DE ALBUNS ====");
                                for (Album album1 : repositorioAlbum.exibir())
                                    System.out.println("\n- ID: " + album1.getId()
                                            + "\n- Titulo: " + album1.getTitulo()
                                            + "\n- Ano de Lançamento: " + album1.getAnoLancamento()
                                            + "\n- Artista: " + album1.getArtista().getNome());
                                break;

                            case 3:
                                System.out.println("==== LISTA DE MUSICAS ====");
                                for (Musica musica1 : repositorioMusica.exibir())
                                    System.out.println("\n- ID: " + musica1.getId()
                                            + "\n- Titulo: " + musica1.getTitulo()
                                            + "\n- Duração: " + musica1.getDuracao()
                                            + "\n- Album: " + musica1.getAlbum().getTitulo());
                                break;

                            case 0:
                                break;

                            default:
                                System.out.println("Opção Inválida. Tente novamente");
                        }
                        if (escolhaExibir == 0){
                            break;
                        }
                    }
                    break;

                case 3:
                    while (true){
                        System.out.println("==== BUSCAR ====");
                        System.out.println("\t1 - Buscar Artista");
                        System.out.println("\t2 - Buscar Album");
                        System.out.println("\t0 - Sair");

                        int escolhaBusca = scanner.nextInt();
                        scanner.nextLine();

                        switch (escolhaBusca){
                            case 1 :
                                System.out.println("==== BUSCAR ARTISTA ====");
                                System.out.println("\t");
                                System.out.println("Coloque o gênero musical: ");
                                String genero = scanner.nextLine();

                                List<Artista> artistas = repositorioArtista.buscarPorGenero(genero);
                                if (artistas.isEmpty()){
                                    System.out.println("nunhum gênero encontrado");
                                    break;
                                }else {
                                    System.out.println("Artista Encontrado");
                                    for (Artista artista1: artistas){
                                        System.out.println("\n- ID: " + artista1.getId()
                                                + "\n- Nome: " +artista1.getNome());
                                    }
                                    break;
                                }
                            case 2 :
                                System.out.println("==== BUSCAR ALBUM POR ANO DE LANÇAMENTO ====");
                                System.out.println("Coloque o Ano de Lançamento: ");
                                int ano = scanner.nextInt();

                                List<Album> albums = repositorioAlbum.buscarPorAno(ano);
                                if(albums.isEmpty()){
                                    System.out.println("Nenhum album encontrado para esse ano.");
                                    break;

                                }else {
                                    System.out.println("==== Albuns encontrados ====");
                                    for (Album album1 : albums){
                                        System.out.println("\n- ID: " + album1.getId()
                                                + "\n- Titulo: " + album1.getTitulo());
                                    }
                                        break;
                                }

                            case 0:
                                break;

                            default:
                                System.out.println("Opção invalida. Tente novamente");

                        }
                        if (escolhaBusca == 0){
                            break;
                        }
                    }
                    break;

                case 4:
                    while (true){
                        System.out.println("==== EXCLUIR ====");
                        System.out.println("\t1 - Excluir Artista");
                        System.out.println("\t2 - Excluir Album");
                        System.out.println("\t3 - Excluir Música");
                        System.out.println("\t0 - Voltar");

                        int escolhaExcluir = scanner.nextInt();
                        scanner.nextLine();

                        switch (escolhaExcluir){
                            case 1:
                                System.out.println("==== EXCLUIR ARTISTA ====");
                                System.out.println("\t");
                                for (Artista artista1 : repositorioArtista.exibir())
                                    System.out.println("\n- ID: " + artista1.getId()
                                            + "\n- Nome: " + artista1.getNome()
                                            + "\n- Gênero Muiscal: " + artista1.getGeneroMusical());
                                System.out.println("\t");

                                System.out.println("ID do artista que deseja excuir: ");
                                int idArtista = scanner.nextInt();
                                scanner.nextLine();

                                Artista artista = repositorioArtista.buscarPorId(idArtista);
                                if (artista != null){
                                    repositorioArtista.excluir(artista.getId());
                                    break;
                                }else {
                                    System.out.println("Nenhum artista encontrado");
                                    break;
                                }

                            case 2:
                                System.out.println("==== EXCLUIR ALBUM ====");
                                System.out.println("\t");
                                for (Album album1 : repositorioAlbum.exibir())
                                    System.out.println("\n- ID: " + album1.getId()
                                            + "\n- Titulo: " + album1.getTitulo()
                                            + "\n- Ano de Lançamento: " + album1.getAnoLancamento()
                                            + "\n= Artista: " + album1.getArtista());
                                System.out.println("\t");

                                System.out.println("ID do Album que deseja excluir: ");
                                int idAlbum = scanner.nextInt();
                                scanner.nextLine();
                                Album album = repositorioAlbum.buscarPorId(idAlbum);
                                if (album != null){
                                    repositorioAlbum.excluir(album.getId());
                                    break;
                                }else {
                                    System.out.println("Nenhum Album encontrado");
                                    break;
                                }

                            case 3:
                                System.out.println("==== EXCUIR MUSICA ====");
                                System.out.println("\t");
                                for (Musica musica1 : repositorioMusica.exibir())
                                    System.out.println("\n- ID: " + musica1.getId()
                                            + "\n- Titulo: " + musica1.getTitulo()
                                            + "\n- Duração: " + musica1.getDuracao()
                                            + "\n- Album: " + musica1.getAlbum());
                                System.out.println("\t");

                                System.out.println("Id da Música que deseja excluir: ");
                                int idMusica = scanner.nextInt();
                                scanner.nextLine();
                                Musica musica = repositorioMusica.buscarPorId(idMusica);
                                if (musica != null){
                                    repositorioMusica.excluir(musica.getId());
                                    break;
                                }else {
                                    System.out.println("Nenhuma Música encontrada");
                                    break;
                                }

                            case 0:
                                break;

                            default:
                                System.out.println("Opção Inválida. Tente Novamente");

                        }
                        if (escolhaExcluir == 0){
                            break;
                        }

                    }
                    break;
                case 5:
                    while (true){
                        System.out.println("==== ATUALIZAR ====");
                        System.out.println("\t1 - Atualizar Artista");
                        System.out.println("\t2 - Atualizaar Album");
                        System.out.println("\t3 - Atualizar Música");
                        System.out.println("\t0 - Voltar");

                        int escolhaAtualizar = scanner.nextInt();
                        scanner.nextLine();

                        switch (escolhaAtualizar){
                            case 1:
                                System.out.println("==== ATUALIZAR ARTISTA ====");
                                System.out.println("\t");
                                for (Artista artista: repositorioArtista.exibir())
                                    System.out.println("\n- ID: " + artista.getId()
                                            + "\n- Nome: " + artista.getNome()
                                            + "\n- Gênero Muiscal: " + artista.getGeneroMusical());
                                System.out.println("\t");

                                System.out.println("ID do Artista que deseja atualizar: ");
                                int idArtista = scanner.nextInt();
                                scanner.nextLine();

                                Artista artistaParaAtualizar = repositorioArtista.buscarPorId(idArtista);
                                if (artistaParaAtualizar != null){
                                    System.out.println("Novo nome do Artista: ");
                                    String novoNome = scanner.nextLine();

                                    System.out.println("Novo Gênero Musical: ");
                                    String novoGenero = scanner.nextLine();

                                    artistaParaAtualizar.setNome(novoNome);
                                    artistaParaAtualizar.setGeneroMusical(novoGenero);

                                    repositorioArtista.editar(artistaParaAtualizar);
                                    break;
                                }else {
                                    System.out.println("Nenhum Artista encontrado");
                                }
                            case 2:
                                System.out.println("==== ATUALiZAR ALBUm ====");
                                System.out.println("\t");
                                for (Album album : repositorioAlbum.exibir())
                                    System.out.println("\n- ID: " + album.getId()
                                            + "\n- Titulo: " + album.getTitulo()
                                            + "\n- Ano de Lançamento: " + album.getAnoLancamento()
                                            + "\n= Artista: " + album.getArtista());
                                System.out.println("\t");

                                System.out.println("ID do Album que deseja atualizar: ");
                                int idAlbum = scanner.nextInt();
                                scanner.nextLine();

                                Album albumParaAtualizar = repositorioAlbum.buscarPorId(idAlbum);
                                if (albumParaAtualizar != null){
                                    System.out.println("Novo titulo do Album: ");
                                    String novoTituloAlbum = scanner.nextLine();

                                    System.out.println("Novo Ano de Lançamento: ");
                                    int novoAnoLancamento = scanner.nextInt();
                                    scanner.nextLine();

                                    albumParaAtualizar.setTitulo(novoTituloAlbum);
                                    albumParaAtualizar.setAnoLancamento(novoAnoLancamento);

                                    repositorioAlbum.editar(albumParaAtualizar);
                                    break;
                                }else {
                                    System.out.println("nenhum Album encontrado");
                                }

                            case 3:
                                System.out.println("==== ATUALIZAR MÚSICA ====");
                                System.out.println("\t");
                                for (Musica musica : repositorioMusica.exibir())
                                    System.out.println("\n- ID: " + musica.getId()
                                            + "\n- Titulo: " + musica.getTitulo()
                                            + "\n- Duração: " + musica.getDuracao()
                                            + "\n- Album: " + musica.getAlbum());
                                System.out.println("\t");

                                System.out.println("Id da Música que deseja atualizar: ");
                                int idMusica = scanner.nextInt();
                                scanner.nextLine();

                                Musica musicaParaAtualizar = repositorioMusica.buscarPorId(idMusica);
                                if (musicaParaAtualizar != null){
                                    System.out.println("Novo Tiulo da Música: ");
                                    String novoTituloMusica = scanner.nextLine();

                                    System.out.println("Nova Duração da Música: ");
                                    int novaDuracao = scanner.nextInt();
                                    scanner.nextLine();

                                    musicaParaAtualizar.setTitulo(novoTituloMusica);
                                    musicaParaAtualizar.setDuracao(novaDuracao);

                                    repositorioMusica.editar(musicaParaAtualizar);
                                    break;
                                }else {
                                    System.out.println("Nenhua Música foi encontrada");
                                    break;
                                }

                            case 0:
                                break;
                            default:
                                System.out.println("Opção Inválida. Tente Novamente");
                        }
                        if (escolhaAtualizar == 0){
                            break;
                        }
                    }
                    break;
                case 0:
                    opcao = 4;
                    break;

                default:
                    System.out.println("Opção Inválida. Tente Novamente");
            }
        }


    }
}