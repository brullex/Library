# Sistema de Gerenciamento de Biblioteca

Este é um sistema simples de gerenciamento de biblioteca baseado em console, escrito em Java. O sistema permite que os usuários gerenciem livros, autores e usuários, além de realizar operações como empréstimo e devolução de livros. Os dados são armazenados em arquivos de texto, facilitando a persistência e recuperação das informações.

## Funcionalidades

- **Gerenciar Usuários**: 
  - Adicionar novos usuários.
  - Listar todos os usuários.
  - Excluir usuários por ID.
  
- **Gerenciar Autores**: 
  - Adicionar novos autores.
  - Listar todos os autores.
  - Excluir autores por ID (somente se não houver livros associados ao autor).
  
- **Gerenciar Livros**: 
  - Adicionar novos livros.
  - Listar livros disponíveis.
  - Listar livros emprestados.
  - Listar livros por autor.
  - Emprestar livros (somente livros disponíveis podem ser emprestados).
  - Devolver livros.
  - Excluir livros por ID.
  
- **Histórico de Empréstimos**: 
  - Visualizar o histórico de empréstimos de todos os usuários.


## Pré-requisitos

- **Java JDK 8 ou superior**: Certifique-se de ter o Java Development Kit (JDK) instalado na sua máquina.

# Estrutura do Menu

1. Usuários
   1. Cadastrar Usuário
   2. Listar Usuários
   3. Excluir Usuário
2. Autores
   1. Cadastrar Autor
   2. Listar Autores
   3. Excluir Autor
3. Livros
   1. Cadastrar Livro
   2. Listar Livros Disponíveis
   3. Listar Livros Emprestados
   4. Listar Livros por Autor
   5. Emprestar Livro
   6. Consultar Histórico de Empréstimos
   7. Excluir Livro
4. Sair

Adicionando um Autor:

É necessário adicionar autores antes de cadastrar livros associados a eles.
Emprestando um Livro:

Somente livros marcados como disponíveis podem ser emprestados. Após o empréstimo, o status do livro será atualizado para não disponível.
Devolvendo um Livro:

O registro de empréstimo será atualizado com a data de devolução, e o livro será marcado como disponível novamente.
