# Biblioteca Digital
Este release notes tem como objetivo apresentar a versão [1.0.0](https://semver.org) do projeto de gerenciamento de uma biblioteca com suas funcionalidades atuais e futuras.

a.Data de liberação:
No dia 21 de Maio de 2018 foi entregue a Biblioteca Digital Solution versão 1.0.0

b.Funcionalidades implementadas
  -  Cadastro manual de usuário
  -  Validação de login
  -  Validação de usuário (admin)
  -  Consulta de livros
  -  Cadastro manual de livros
  -  Cadastro de livros com uso da [API do Google](https://developers.google.com/books/)
  -  Prazo máximo de duração do empréstimo (07 dias)
  -  Empréstimo de livros (máximo um livro por usuário)
  -  Reserva de livros (máximo uma reserva por usuário)
  -  Notificação de efetuamento de empréstimo de livros (via email)
  -  Notificão de alerta para a devolução de livros (via email à partir do 5º dia do prazo do empréstimo)
  -  Notificação de disponibilidade do livro
  -  Notificação de disponibilidade de reserva
  -  Devolução de livros
  -  Relatar avarias do livro (admin)
  
c.Funcionalidades não implementadas
  -  Uso do login da Cognizant(SSO)
  -  Sugestão de livros caso ele não exista no sistema
  -  Renovar empréstimo do livro
  -  Usuário admim pode gerenciar outros usuários
  -  Bloqueio da página do usuário (botões),caso ele não devolva o livro no prazo determinado (até que ele devolva o livro)
  -  Geração de relatórios de livros por meio de filtro por categorias (acesso para o usuário)
  -  Amostra de livros em destaque
  -  Lista de livros desejados pelo usuario (WishList)
  -  Mostrar livros mais procurados no mês
  -  Mostrar o top ten dos usuarios do aplicativo (os que mais lêem)
  -  Tela para o admin ver os livros sugeridos pelo usuário
  -  Usuário poderá pegar mais livros emprestados de uma única vez
  -  Mais reservas poderão ser feitas por unidade de livro
  -  recomendações de livros entre usuários
  -  Sugestão de leitura 
  -  Tela para o admin com lista de livros sugeridos pelos usuários
  -  Empréstimo de curto prazo
  -  Tela com lista de emprestimos para poder ser editada pelo usuário
  -  Notificação de prazo de reserva do usuário para o admin
  -  Exportar informações de reserva para a agenda do usuário
  -  Pop-up com as avarias do livro
  -  Perfil do usuário no menu
  -  Botão de esqueci minha senha
  -  Página do perfil do usuário
  -  
  
  
  

## Web application about a Library System.
- **Tools used**:
  - [Eclipse EE 4.4.0/4.7.3a](http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/oxygen3a)\*;
  - [MySQL 5.7.7](https://www.mysql.com/products/community/)\*;
  - [Maven](https://maven.apache.org/);
  - [Hibernate](http://hibernate.org/);
  - [Spring](https://spring.io/);
  - ~~Node.js~~;
  - ~~Apache Tomcat~~;
  - [Thymeleaf 3.0.9](https://www.thymeleaf.org/download.html);
  - [Bootstrap 4.1.0](https://getbootstrap.com/docs/4.1/getting-started/introduction/);
  - and [Markdown](https://guides.github.com/features/mastering-markdown/).
  
  \* *Open a Software Request in [OneIT](https://onecognizant.cognizant.com)*.


## Documentation
The [Wiki](https://github.com/guiilhermehn/Biblioteca-digital/wiki) contains all the information about the project (in progress). You can contribute to the Wiki directly here on GitHub! :+1: See how in [Markdown](https://guides.github.com/features/mastering-markdown/).


![Logo da Cognizant](https://raw.githubusercontent.com/guiilhermehn/Biblioteca-digital/9aa74df204d0ecb8126cf191a9409230caed15eb/biblioteca-digital/src/main/resources/static/assets/img/logo_cognizant.png.png?token=AVPL5KmAk7jBDuF145lbCo_qskNeMHpiks5a9amzwA%3D%3D)
