# 🏥 SafeDeliver API

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![Security](https://img.shields.io/badge/Security-JWT%20%26%20RBAC-blue)](https://spring.io/projects/spring-security)

**SafeDeliver** é uma API robusta de logística crítica desenvolvida para o transporte seguro de órgãos e suprimentos médicos controlados entre hospitais. O foco principal do projeto é a **segurança de dados (Multi-tenancy)** e a **integridade do fluxo de estados**.

---

## 🚀 Diferenciais Técnicos

Este projeto foi construído seguindo as melhores práticas de desenvolvimento corporativo:

- **Isolamento de Dados (Logical Multi-tenancy):** Managers de um hospital não podem visualizar ou interferir em dados de outras instituições.
- **Segurança Granular (Ownership Check):** Motoristas possuem acesso restrito apenas às cargas que lhes foram atribuídas via filtros em nível de banco de dados e verificações de posse (*Ownership*).
- **Máquina de Estados Finita (FSM):** O ciclo de vida da carga (`PENDENTE` -> `EM_TRANSITO` -> `ENTREGUE`) é rigorosamente controlado, impedindo saltos de status inválidos.
- **Auditoria Completa:** Todas as mudanças críticas de status são registradas em uma tabela de auditoria, capturando o histórico de alterações e o autor da ação.
- **Arquitetura Limpa:** Separação clara entre Entidades JPA e DTOs (utilizando Java Records), garantindo que dados sensíveis (como hashes de senhas) nunca vazem para a camada de visualização.

---

## 🛠️ Tecnologias Utilizadas

- **Java 21** (Uso de Records, Switch Expressions e Pattern Matching)
- **Spring Boot 3**
- **Spring Security** (Autenticação Stateless via JWT)
- **Spring Data JPA** (Relacionamentos complexos e Query Methods otimizados)
- **PostgreSQL / H2** (Persistência de dados)
- **Lombok** (Produtividade)
- **Bean Validation** (Integridade de entrada)

---

## 🔐 Níveis de Acesso (RBAC)

| Role | Permissões |
| :--- | :--- |
| **SYS_ADMIN** | Gestão de Hospitais e criação de Managers. |
| **HOSPITAL_MANAGER** | Criação de cargas, gestão de motoristas e monitoramento do seu hospital. |
| **DRIVER** | Visualização de cargas próprias e atualização de status de transporte. |

---

## 🛣️ Endpoints Principais

### Autenticação
- `POST /login` - Autentica usuário e retorna o Token JWT.

### Administrativo
- `POST /hospitals` - Cadastro de novas unidades (SysAdmin).
- `POST /register` - Cadastro de usuários respeitando a hierarquia de criação.

### Logística (Shipments)
- `POST /shipment` - Criação de nova remessa de transporte.
- `PATCH /shipment/{id}/assign` - Atribuição de motorista (Manager).
- `PATCH /shipment/{id}/status` - Atualização do fluxo de entrega (Driver).
- `GET /shipment` - Listagem inteligente (Filtra dados conforme o usuário logado).

---

## ⚙️ Como Executar

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/seu-usuario/safe-deliver.git](https://github.com/seu-usuario/safe-deliver.git)

2. **Configure as variáveis de ambiente**

    No arquivo `application.properties`, configure sua Secret do JWT:

    ```properties
    jwt.secret=${JWT_SECRET:sua_chave_secreta_aqui}

3. **Compile e execute:**

    ```bash
    mvn spring-boot:run

---

## 📝 Auditoria e Logs

O sistema implementa uma camada de auditoria persistente. Cada transição de status gera um registro no banco de dados contendo:

- ID da entidade afetada.
- ID do autor da ação.
- Valor anterior e novo valor.
- Timestamp da transação.

Desenvolvido por Ryan.



