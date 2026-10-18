# Sistema Web de Gestão Financeira Pessoal

Trabalho final de Desenvolvimento de Aplicações Web I (IFTM - Campus Avançado Uberaba Parque Tecnológico)

- **Professor:** Rafael Godoi Orbolato
- **Dupla:** Lucas Emmanuel e Rafaella Nunes

## 1. Tema

Um sistema Web em que cada pessoa registra suas receitas e despesas, separadas por conta e por categoria, e define orçamentos de gasto. O sistema mostra o saldo de cada conta, quanto já foi gasto em relação ao orçamento e gera um relatório mensal em PDF.

A ideia surgiu porque quem anota os gastos em lugares diferentes (papel, planilhas soltas) acaba sem saber para onde o dinheiro foi e quanto ainda pode gastar no mês.

## 2. Diagrama de classes

![Diagrama de classes simplificado (Versão 02)](diagrama-classes/diagrama-classes-final.png)

O código será escrito em inglês. No diagrama os nomes estão em português; no código as classes se chamam assim:

| Diagrama | Classe | O que representa |
|---|---|---|
| Usuário | `User` | Quem acessa o sistema, com o papel `USER` ou `ADMIN`. |
| Conta | `Account` | Onde o dinheiro está (carteira, banco, poupança). |
| Lançamento | `Transaction` | Uma receita ou despesa, com valor e data. É a classe central da lógica de negócio. |
| Categoria | `Category` | A classificação do lançamento (Alimentação, Salário...). As categorias são as mesmas para todos e quem cuida delas é o ADMIN. |
| Orçamento | `Budget` | O limite de gasto que um usuário define para uma categoria. |

Este é um diagrama de **classes**, e não um diagrama entidade-relacionamento. Cada seta indica que a classe de origem tem um atributo do tipo da classe de destino:

| Classe | Atributo | Leitura |
|---|---|---|
| `Account` | `User user` | Cada conta pertence a um usuário. Um usuário pode ter várias contas. |
| `Budget` | `User user` | Cada orçamento pertence a um usuário. Um usuário pode ter vários orçamentos. |
| `Budget` | `Category category` | Cada orçamento é de uma categoria. |
| `Transaction` | `Account account` | Cada lançamento é feito em uma conta. Uma conta pode ter vários lançamentos. |
| `Transaction` | `Category category` | Cada lançamento tem uma categoria. Uma categoria pode estar em vários lançamentos. |

`User` e `Category` não apontam para nenhuma outra classe: elas não guardam listas de contas, orçamentos ou lançamentos.

A evolução do diagrama (rascunho no papel, Versão 01 e Versão 02) está no arquivo [`diagrama-classes.excalidraw`](diagrama-classes/diagrama-classes.excalidraw).

## 3. Requisitos do enunciado

| # | Requisito | Como vamos atender |
|---|---|---|
| 1 | Relacionamento um para muitos (fora usuários) | Uma `Account` tem várias `Transaction`; uma `Category` tem várias `Transaction` |
| 2 | JPA, Hibernate, Flyway e Spring Data | Entidades mapeadas, repositories e migrações do Flyway |
| 3 | Thymeleaf, htmx, Tailwind e componentes JS | Filtros com htmx, máscara de dinheiro, campo de data e confirmação antes de excluir |
| 4 | Validação no backend | Bean Validation e regras nos services |
| 5 | Paginação | Listas de lançamentos e de usuários |
| 6 | Páginas de erro | 403, 404 e 500 |
| 7 | Senha, HTTPS e 2 tipos de usuário | Spring Security, senha criptografada, HTTPS, `USER` e `ADMIN` |
| 8 | Administrador cadastra usuários | Tela do ADMIN com nome, e-mail, senha e papel |
| 9 | CRUD fora da lógica de negócio | CRUD de `Category` |
| 10 | Relatório PDF com sub-relatório | Relatório do mês por categoria, com os lançamentos de cada uma no sub-relatório |

## 4. Tipos de usuário

| Ação | USER | ADMIN |
|---|:---:|:---:|
| Entrar e sair do sistema | Sim | Sim |
| Cuidar das próprias contas, lançamentos e orçamentos | Sim | Sim |
| Gerar o próprio relatório | Sim | Sim |
| Ver dados financeiros de outro usuário | Não | Não |
| Cadastrar usuários e definir o papel | Não | Sim |
| CRUD de `Category` | Não | Sim |

## 5. Lógica de negócio

- **Lançamentos:** cadastrar receitas e despesas, listar com paginação e filtrar por período, tipo, conta e categoria.
- **Saldo da conta:** saldo inicial + receitas - despesas, atualizado sempre que um lançamento é criado, alterado ou excluído.
- **Orçamento:** somar as despesas do usuário na categoria do orçamento, comparar com o limite e avisar quando estiver perto de estourar ou já tiver passado.
- **Painel:** resumo do mês com receitas, despesas, saldo e situação dos orçamentos.

## 6. O que não vamos fazer

Integração com banco ou importação de extrato, investimentos, pagamentos de verdade, aplicativo de celular, contas compartilhadas entre usuários e lançamentos recorrentes ou parcelados.

## 7. Etapas

| Etapa | Entrega | Situação |
|---|---|---|
| 1 | Tema, dupla e diagrama de classes simplificado | Apresentada |
| 2 | Classes do modelo | Em andamento |
| 3 | Mapeamento JPA | Pendente |
| 4 | CRUD de `Category` | Pendente |
| 5 | Lógica de negócio | Pendente |
| 6 | Segurança, cadastro de usuários e HTTPS | Pendente |
| 7 | Relatório PDF | Pendente |
| 8 | Apresentação | Pendente |
