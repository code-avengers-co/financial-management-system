# Sistema Web de Gestão Financeira Pessoal

Projeto acadêmico da disciplina de Desenvolvimento de Aplicações Web I do IFTM.

O sistema permitirá que cada usuário registre receitas e despesas, organize lançamentos por contas e categorias, defina limites mensais de gastos e acompanhe sua situação financeira por meio de dashboard e relatório em PDF.

## Situação atual

O projeto está na fase de pré-projeto e validação do tema, correspondente à Etapa 1 do trabalho final.

O escopo recomendado prioriza:

- lançamentos de receitas e despesas;
- contas e categorias financeiras;
- orçamento mensal por categoria;
- dashboard com receitas, despesas e saldo;
- perfis `USER` e `ADMIN`;
- CRUD administrativo de categorias;
- relatório financeiro mensal em PDF com subreport de lançamentos;
- investimentos apenas como extensão opcional após a conclusão do núcleo obrigatório.

## Tecnologias previstas

- Java e Spring Boot
- JPA e Hibernate
- Spring Data
- Flyway
- Thymeleaf
- htmx
- Tailwind CSS
- Spring Security e HTTPS

## Documentação

- [Relatório de pré-projeto](docs/pre-projeto/Relatorio-de-Pre-Projeto-Gestao-Financeira.docx)
- [Índice da documentação](docs/README.md)
- [Diagrama de classes simplificado](docs/pre-projeto/diagramas/01-diagrama-classes-simplificado.excalidraw)
- [Fluxo de lançamento e orçamento](docs/pre-projeto/diagramas/02-fluxo-lancamento-orcamento.excalidraw)
- [Navegação e telas](docs/pre-projeto/diagramas/03-diagrama-navegacao-telas.excalidraw)
- [Arquitetura em camadas](docs/pre-projeto/diagramas/04-arquitetura-em-camadas.excalidraw)

Os diagramas podem ser abertos diretamente no [Excalidraw](https://excalidraw.com/) pela opção **Abrir**.

## Etapas do trabalho

1. Aprovação do tema, integrantes e diagrama de classes simplificado.
2. Implementação das classes do domínio.
3. Mapeamento objeto-relacional e persistência.
4. CRUD completo de Categoria.
5. Lógica de negócio financeira e orçamento.
6. Segurança, usuários, papéis e HTTPS.
7. Relatório PDF com subreport.
8. Apresentação do sistema funcionando.

## Estrutura atual

```text
.
├── docs
│   ├── README.md
│   └── pre-projeto
│       ├── Relatorio-de-Pre-Projeto-Gestao-Financeira.docx
│       └── diagramas
│           ├── 01-diagrama-classes-simplificado.excalidraw
│           ├── 02-fluxo-lancamento-orcamento.excalidraw
│           ├── 03-diagrama-navegacao-telas.excalidraw
│           └── 04-arquitetura-em-camadas.excalidraw
├── .gitignore
├── LICENSE
└── README.md
```

A estrutura da aplicação Spring Boot será adicionada nas próximas etapas, depois da aprovação do modelo pelo professor.
