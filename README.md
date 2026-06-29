<div align="center"> 
   
   # Software - Museu Treze de Maio </div>
Projeto extensionista da disciplina de Projeto de Software para o curso de Ciência da Computação da [Universidade Franciscana](https://site.ufn.edu.br/).

Foram realizadas realizadas tarefas de análise, modelagem, implementação e refinamento das funcionalidades do sistema, utilizando os artefatos como requisitos, casos de uso, diagramas e protótipos. 

## Contexto e justificativa
O **Museu Treze de Maio**, dedicado à preservação da cultura afro-brasileira em Santa Maria, possui um importante patrimônio que precisa ser digitalizado, catalogado e disponibilizado. 
Esse acervo, no entanto, não possuía um sistema digital de
catalogação, dificultando a busca, organização e divulgação dos itens tanto para a equipe interna quanto para o público externo O museu necessita de soluções tecnológicas para gerenciar a Biblioteca Comunitária (**livros, revistas e jornais**). 


## Solução
Esse projeto consiste em um **sistema web de gestão e consulta de acervo digital** que permite:  
- Cadastro, edição e listagem de itens do acervo (livros, revistas e jornais), incluindo dados como autor/colaborador, editora, assunto, localização física e digital, e imagem de capa;
- Pesquisa pública do acervo por título, autor, assunto, tipo de item, entre outros filtros;
- Controle de acesso por perfil de usuário (Administrador, Bibliotecário), restringindo o cadastro e a edição de itens a usuários autorizados;


## 🛠️ Tecnologias utilizadas
- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- Thymeleaf
- Bootstrap 5

## 🛠️ Arquitetura
O projeto segue a arquitetura em camadas, padrão do
ecossistema Spring:
```
Templates (Thymeleaf) -> Controller → Service → Repository → Model → Banco de Dados
```
- **Model**: entidades JPA que representam as tabelas do banco
(`Usuario`, `Jornal`, `Livro`, `Revista`, `Editora`, `Colaborador`, `Assunto`)
- **Repository**: interfaces JpaRepository responsáveis pelo acesso direto ao banco de dados
- **Service**: camada de regras de negócio (validações, criptografia de senha, regras de unicidade, upload de imagens, etc.)
- **Controller**: camada responsável por receber requisições HTTP, orquestrar chamadas aos Services e decidir qual template Thymeleaf renderizar
- **Security**: configuração centralizada de autenticação e autorização, com controle de acesso por Authority (`ADMIN`, `BIBLIOTECARIO`)

## 📌 Instalação e execução
### Pré-requisitos
- Java JDK [versão 21] ou superior
- Maven
- MySQL instalado e em execução

### Passo a passo

1. **Clone o repositório**
```bash
   git clone https://github.com/ericmds/Trabalhos-em-Grupo.git
   cd museutrezemaio
```

2. **Configure o banco de dados**
Crie um banco de dados local e configure as credenciais no arquivo `src/main/resources/application.properties`:

```properties
   spring.jpa.hibernate.ddl-auto=update
   spring.datasource.url=jdbc:mysql://localhost:[porta]/[nome_do_banco]
   spring.datasource.username=[usuario]
   spring.datasource.password=[senha]
```

3. **Compile e execute o projeto**
```bash
   ./mvnw spring-boot:run
```
   ou, caso utilize uma IDE (IntelliJ, Eclipse, etc.), execute a classe
   principal anotada com `@SpringBootApplication`.

4. **Acesse o sistema**  
   Abra o navegador em: http://localhost:8080

5. **Login**  
   Durante o primeiro acesso, o projeto possui usuário e senha padrões que podem ser inativados após a criação de outros usuários

   #### ADMINISTRADOR  
   ```
   email: admin@email.com  
   senha: 1234  
    ```

   #### BIBLIOTECÁRIO  
   ```
   email: bibliotecario@email.com  
   senha: 1234  
   ```

## 👥 Desenvolvedores
* Éric Martins da Silva - [@ericmds](https://github.com/ericmds)
* Frazzon - [@Akumashii](https://github.com/Akumashii)
* Luiza Lewandowski Karlec - [@luizakarlec](https://github.com/luizakarlec)
* Vanessa Cezar do Nascimento - [@vanessacezarn](https://github.com/vanessacezarn)

## 🎬 Vídeo Demostrativo

🔗 [Sistema para o Museu Treze de Maio](https://www.youtube.com/watch?v=TVyPvR3B7H8)
