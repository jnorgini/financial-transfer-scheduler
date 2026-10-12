# 🏦 Sistema de Agendamento de Transferências Financeiras

Este repositório contém a solução completa para o teste técnico de agendamento de transferências financeiras com cálculo automatizado de taxas regressivas de acordo com a antecedência do agendamento.

O projeto foi desenvolvido com foco absoluto em **Clean Code (Código Limpo), eficiência, robustez de dados e arquitetura desacoplada**, atendendo integralmente a todos os entregáveis e observações solicitados pelo escopo da vaga.

---

## 📐 Regra de Negócio (Cálculo de Taxas)

A taxa é calculada com base na quantidade de dias entre a **data de agendamento (hoje)** e a **data da transferência**:

| Dias de Antecedência (De / Até) | Valor Fixo (R\$) | Percentual (%) |
| :---: | :---: | :---: |
| **0 dia** (Mesmo dia) | R\$ 3,00 | 2,5% |
| **1 a 10 dias** | R\$ 12,00 | 0,0% |
| **11 a 20 dias** | R\$ 0,00 | 8,2% |
| **21 a 30 dias** | R\$ 0,00 | 6,9% |
| **31 a 40 dias** | R\$ 0,00 | 4,7% |
| **41 a 50 dias** | R\$ 0,00 | 1,7% |

> ⚠️ **Restrição:** Caso não haja uma taxa aplicável para o intervalo de dias escolhido (ex: acima de 50 dias ou datas retroativas), o backend interrompe a operação lançando um alerta de erro e impedindo o agendamento.

---

## 🔌 Endpoints da API

| Método | Endpoint | Descrição |
| :---: | :--- | :--- |
| **POST** | `/api/transfers` | Agenda uma nova transferência financeira aplicando o motor de taxas. |
| **GET** | `/api/transfers` | Recupera o histórico de agendamentos cadastrados para o extrato. |

---

## 🛠️ Tecnologias e Ferramentas

### Back-end (API REST)
* **Java 11:** Versão mandatória especificada nos requisitos.
* **Spring Boot 2.7.x:** Ecossistema estável para o desenvolvimento corporativo.
* **Spring Data JPA:** Abstração e agilidade na camada de persistência.
* **Banco de Dados H2:** Banco de dados em memória solicitado no teste.
* **MapStruct / Lombok:** Mapeamento performático e eliminação de boilerplate.
* **Bean Validation:** Validação rigorosa de formato e dados na entrada da API.

### Front-end (Interface Gráfica)
* **Vue.js 3:** Abordagem moderna com `<script setup>` e Composition API.
* **Bootstrap 5:** Layout responsivo e interface limpa.
* **Axios:** Cliente HTTP para comunicação assíncrona.

---

## 📐 Decisões Arquiteturais e Boas Práticas

* **Precisão Financeira:** Uso de `BigDecimal` com `RoundingMode.HALF_UP` para garantir a integridade dos cálculos monetários.
* **Regras Desacopladas:** Regras de negócio isoladas no Enum `FeeRule`, aplicando o princípio de responsabilidade única (SRP).
* **Exceções Centralizadas:** Validações de data disparam uma exceção customizada (`BusinessException`) tratada globalmente no back-end.
* **Proteção com DTOs:** Uso de `TransferRequest`/`Response` com validação `@Pattern` para proteger as entidades de banco de dados.
* **CORS Centralizado:** Configuração isolada na classe `CorsConfig` para comunicação segura com o Vue.js sem código duplicado.
* **Front-end Reativo:** Sanitização de dados em tempo real e atualização de estado via eventos nativos sem recarregar a página.

---

## 🧪 Testes Automatizados

A aplicação utiliza **JUnit 5**, **Mockito** e **MockMvc** para garantir a qualidade dos fluxos:

* **`TransferServiceTest`:** Valida todas as faixas de cálculo de taxas e o bloqueio de prazos inválidos.
* **`TransferControllerTest`:** Valida o comportamento dos endpoints, payloads e respostas HTTP.

Para rodar os testes, execute na raiz do back-end:

* **No Git Bash, VS Code, Linux ou macOS:** `./mvnw test`
* **No Prompt de Comando (CMD) do Windows:** `mvnw.cmd test`

---

## 🚀 Como Executar o Projeto

### Pré-requisitos Obrigatórios
* **JDK 11** instalado na máquina.
* **Node.js** instalado (Versão 16 ou superior).

### ⚙️ Executando o Back-end (Spring Boot)
1. Importe o projeto do backend na sua IDE de preferência como um **Existing Maven Project**.
2. Execute a classe principal do projeto como um **Spring Boot App**.
3. A API iniciará na porta padrão `8080`.

> 💡 **Acesso ao Banco de Dados (H2 Console):**
> Visualizável em `http://localhost:8080/h2-console` utilizando as credenciais:
> * **JDBC URL:** `jdbc:h2:mem:transfer`
> * **User Name:** `sa`
> * **Password:** _(deixe em branco)_

### 💻 Executando o Front-end (Vue.js)
1. Abra o terminal na pasta `frontend` do projeto.
2. Instale as dependências locais executando: `npm install`
3. Inicie o servidor de desenvolvimento rodando: `npm run dev`
4. Abra o link gerado no seu navegador (geralmente `http://localhost:5173`) para utilizar o sistema!

---

## 🔮 Roadmap de Evoluções Futuras

Caso a plataforma escalasse para um ambiente de produção, os próximos passos técnicos planejados seriam:

1. **Paginação de Dados (`Pageable`):** Implementação de paginação nativa na camada do Spring Data JPA e controle dinâmico no Vue.js para otimizar o tempo de resposta do Extrato.
2. **Segurança de Rotas:** Integração do ecossistema com Spring Security e autenticação baseada em tokens JWT.
