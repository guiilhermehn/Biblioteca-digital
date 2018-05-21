# Biblioteca Digital
Aplicação Web para Gerenciamento de Biblioteca

| [<img src="index-printscreen.png" width="330px;"/><br /><sub>Index</sub>](index-printscreen.png)<br /> |  [<img src="home-books-printscreen.png" width="330px;"/><br /><sub>Home</sub>](home-books-printscreen.png)<br /> | [<img src="books-new-printscreen.png" width="330px;"/><br /><sub>Cadastro</sub>](books-new-printscreen.png)<br /> | 
| :---: | :---: | :---: |



> Este release notes tem como objetivo apresentar a versão [1.0.0](https://semver.org) do projeto de gerenciamento de uma biblioteca com suas funcionalidades atuais e futuras.

![Logo da Cognizant](https://www.owasp.org/images/5/57/Cognizant.png)

## a.Data de liberação:
No dia 21 de Maio de 2018 foi entregue a Biblioteca Digital Solution versão 1.0.0

## b.Funcionalidades implementadas
  -  Cadastro manual de usuário
  -  Validação de login
  -  Validação de usuário (*admin*)
  -  Consulta de livros
  -  Cadastro manual de livros (*admin*)
  -  Busca automatizada de detalhes do livro com uso da [API do Google](https://developers.google.com/books/) (*admin*)
  -  Prazo máximo de duração do empréstimo (07 dias)
  -  Empréstimo de livros (máximo um livro por usuário)
  -  Reserva de livros (máximo uma reserva por usuário)
  -  Notificação de efetuamento de empréstimo de livros (via email)
  -  Notificão de alerta para a devolução de livros (via email à partir do 5º dia do prazo do empréstimo)
  -  Notificação de disponibilidade do livro
  -  Notificação de disponibilidade de reserva
  -  Devolução de livros
  -  Relatar avarias do livro (*admin*)
  
## c.Funcionalidades não implementadas
  -  Uso do login da Cognizant (SSO)
  -  Sugestão de livros caso ele não exista no sistema
  -  Renovar empréstimo do livro
  -  Usuário admim pode gerenciar outros usuários (*admin*)
  -  Bloqueio da página do usuário (botões),caso ele não devolva o livro no prazo determinado (até que ele devolva o livro)
  -  Geração de relatórios de livros por meio de filtro por categorias (acesso para o usuário)
  -  Amostra de livros em destaque
  -  Lista de livros desejados pelo usuario (WishList)
  -  Mostrar livros mais procurados no mês
  -  Mostrar o Top 10 dos usuarios do aplicativo (os que mais lêem)
  -  Tela para o admin ver os livros sugeridos pelo usuário (admin)
  -  Usuário poderá pegar mais livros emprestados de uma única vez (empréstimo curto prazo)
  -  Mais reservas poderão ser feitas por unidade de livro
  -  Recomendações de livros entre usuários
  -  Sugestão de leitura 
  -  Tela para o admin com lista de livros sugeridos pelos usuários (admin)
  -  Empréstimo de curto prazo
  -  Tela com lista de emprestimos para poder ser editada pelo usuário
  -  Notificação de prazo de reserva do usuário para o admin (admin)
  -  Exportar informações de reserva para a agenda do usuário
  -  Pop-up com as avarias do livro (página de detalhes do livro)
  -  Perfil do usuário no menu
  -  Funcionalidade do botão de esqueci minha senha
  -  Página do perfil do usuário  

## **Tools used**:
  - [Eclipse EE 4.4.0/4.7.3a](http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/oxygen3a)\*;
  - [MySQL 5.7.7](https://www.mysql.com/products/community/)\*;
  - [Maven](https://maven.apache.org/);
  - [Hibernate](http://hibernate.org/);
  - [Spring](https://spring.io/);
  - [Thymeleaf 3.0.9](https://www.thymeleaf.org/download.html);
  - [Bootstrap 4.1.0](https://getbootstrap.com/docs/4.1/getting-started/introduction/);
  - and [Markdown](https://guides.github.com/features/mastering-markdown/).
  
  \* *Open a Software Request in [OneIT](https://onecognizant.cognizant.com)*.
  
  
## Problemas Conhecidos
### Lógica Empréstimo/Reserva/Wishlist
 Problema na lógica da integração das regras de negócio quando se há mais de um empréstimo por livro e por usuário.
 Limitamos a quantidade de empréstimos por livro para o máximo de 01 empréstimo e máximo de 01 reserva
   
### Mais de um livro por ISBN 
 Não há uma regularização sobre o ISBN ser único mundialmente. Na API do Google, houve casos em que encontramos +27 livros para um mesmo ISBN.    
 
## Como começar 
#### Utilizando o Git (*GitHub*) em equipe
Optamos por utilizar o GitHub como ferramenta para gerenciamento de versão num repositório **privado**.

**Se** o projeto continuar nele, sugerimos que planejem os processos de utilização dessa ferramenta utilizando a [sugestão do Github](https://guides.github.com/introduction/flow/) e deixar a Master somente para versões disponíveis para uso do usuário final (*restrita*), uma branch de Homologação (*restrita*), e uma branch para cada dev (*se estiverem em pair-programing, uma para os dois*).

Também indicamos a utilizar a aba [Issues](../../issues) para tracking de bugs (*se necessário*) e a aba [Projects](../../projects) (ou o [Trello](http://trello.com/)) para gerenciar o projeto

#### Comecem clonando o repositório com o GitHub Desktop
Importem o projeto para o Eclipse e deixem o Maven cuidar das dependências (*Spring, Hibernate, ThymeLeaf*)
Criem um banco com o nome de "biblioteca_db", o Hibernate se encarregará de criar as tabelas.

#### Thymeleaf como motor de templates construídos com Bootstrap
Todas as páginas são geradas utilizando templates "*tageados*" com Thymeleaf.
Os templates ficam em [resources/templates](https://github.com/guiilhermehn/Biblioteca-digital/tree/master/biblioteca-digital/src/main/resources)

### :star: UI Kit Design :star:
Foi montada a ***UI Kit Design*** para guiar a identidade visual da aplicação.
Consulte-a no repositório base

### Consulta API Google Books
A consulta através do ISBN do livro é realizada no Front-End utilizando JavaScript
Após inserir um ISBN, os detalhes do livro serão preenchidos automaticamente
**Lembre-se**: [O erro relatado sobre um ISBN para mais de um livro](../../#mais-de-um-livro-por-isbn). Caso haja mais de um, o primeiro relacionado é será preenchido

## Contribuições
### 1º  Graduation Program:
<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
| [<img src="https://avatars2.githubusercontent.com/u/36986045?s=400&v=4" width="110px;"/><br /><sub>Alexandre</sub>](https://github.com/alexandrenunes-cs)<br /> | [<img src="https://avatars0.githubusercontent.com/u/17655232?s=400&v=4" width="110px;"/><br /><sub>Bruno</sub>](https://github.com/brunojsan)<br /> | [<img src="https://avatars3.githubusercontent.com/u/22897211?s=460&v=4" width="110px;"/><br /><sub>Diego</sub>](https://github.com/DiegoMatsuki)<br /> | [<img src="https://avatars3.githubusercontent.com/u/38724994?s=460&v=4" width="110px;"/><br /><sub>Francisco</sub>](https://github.com/franrock)<br /> | [<img src="https://avatars1.githubusercontent.com/u/17627827?s=460&v=4" width="110px;"/><br /><sub>Iury</sub>](https://github.com/Sizzin)<br /> |
 | :---: | :---: | :---: | :---: | :---: |
 | [<img src="https://avatars2.githubusercontent.com/u/26582544?s=460&v=4" width="110px;"/><br /><sub>Guilherme</sub>](https://github.com/guiilhermehn)<br /> | [<img src="https://avatars3.githubusercontent.com/u/13643850?s=460&v=4" width="110px;"/><br /><sub>Jackson</sub>](https://github.com/jacksonsfranca)<br /> | [<img src="https://avatars0.githubusercontent.com/u/22268900?s=460&v=4" width="110px;"/><br /><sub>Luís Ângelo</sub>](https://github.com/luisangelorjr)<br /> :octocat:| [<img src="https://avatars0.githubusercontent.com/u/38658934?s=460&v=4" width="110px;"/><br /><sub>Philipe</sub>](https://github.com/philipe-silva)<br /> | [<img src="https://avatars2.githubusercontent.com/u/38658746?s=460&v=4" width="110px;"/><br /><sub>Raquel</sub>](https://github.com/psousaraquel)<br /> |
<!-- ALL-CONTRIBUTORS-LIST:END -->

### 2º Graduation Program:

    ###Preencher###

## Versionamento
Nós recomendamos utilizar o [SemVer](http://semver.org/) para versionamento.
Para verificar as versões disponíveis da Biblioteca Digital, veja as [tags no repositório](../../releases).
