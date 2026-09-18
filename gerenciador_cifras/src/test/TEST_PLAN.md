# Test Plan — API de Repositório de Cifras

## 1. Testes Unitários

### Usuário

| ID        | Test Case           | Given                           | When                             | Then                                 |
| --------- | ------------------- | ------------------------------- | -------------------------------- | ------------------------------------ |
| UT-USR-01 | Criar usuário       | Dados válidos                   | Criar usuário                    | Usuário é criado                     |
| UT-USR-02 | Nome obrigatório    | Nome nulo                       | Criar usuário                    | Operação é rejeitada                 |
| UT-USR-03 | Nome único          | Nome já existente               | Criar usuário                    | Operação é rejeitada                 |
| UT-USR-04 | Atualizar nome      | Usuário existente e nome válido | Atualizar nome                   | Nome é atualizado                    |
| UT-USR-05 | Atualizar descrição | Usuário existente               | Atualizar descrição              | Descrição é atualizada               |
| UT-USR-06 | Mostrar cifras      | Usuário com cifras              | Solicitar cifras                 | Apenas suas cifras são retornadas    |
| UT-USR-07 | Mostrar playlists   | Usuário com playlists           | Solicitar playlists              | Apenas suas playlists são retornadas |
| UT-USR-08 | Mostrar favoritas   | Usuário com playlists favoritas | Solicitar favoritas              | Playlists favoritas são retornadas   |
| UT-USR-09 | Adicionar favorita  | Playlist existente              | Adicionar playlist aos favoritos | Playlist é adicionada à lista        |
| UT-USR-10 | Remover favorita    | Playlist favoritada             | Remover favorita                 | Playlist é removida da lista         |

### Cifra

| ID        | Test Case               | Given                                | When                                | Then                             |
| --------- | ----------------------- | ------------------------------------ | ----------------------------------- | -------------------------------- |
| UT-CIF-01 | Criar cifra             | Dados obrigatórios válidos           | Criar cifra                         | Cifra é criada                   |
| UT-CIF-02 | Instrumento obrigatório | Instrumento nulo                     | Criar cifra                         | Operação é rejeitada             |
| UT-CIF-03 | Estilo obrigatório      | Estilo nulo                          | Criar cifra                         | Operação é rejeitada             |
| UT-CIF-04 | Key obrigatória         | Key nula                             | Criar cifra                         | Operação é rejeitada             |
| UT-CIF-05 | Dificuldade obrigatória | Dificuldade nula                     | Criar cifra                         | Operação é rejeitada             |
| UT-CIF-06 | Conteúdo obrigatório    | Conteúdo nulo                        | Criar cifra                         | Operação é rejeitada             |
| UT-CIF-07 | BPM positivo            | BPM maior que zero                   | Criar cifra                         | Cifra é criada                   |
| UT-CIF-08 | BPM inválido            | BPM menor ou igual a zero            | Criar cifra                         | Operação é rejeitada             |
| UT-CIF-09 | Atualizar cifra         | Cifra existente e usuário autorizado | Atualizar cifra                     | Dados são atualizados            |
| UT-CIF-11 | Remover cifra           | Cifra existente                      | Remover cifra                       | Cifra é removida                 |
| UT-CIF-12 | Mostrar cifra           | Cifra existente                      | Buscar cifra                        | Cifra é retornada                |
| UT-CIF-13 | Anonimizar cifra        | Cifra vinculada a usuário            | Anonimizar cifra                    | usuarioId recebe usuário anônimo |

### Playlist

| ID        | Test Case              | Given                                | When                                   | Then                             |
| --------- | ---------------------- | ------------------------------------ | -------------------------------------- | -------------------------------- |
| UT-PLY-01 | Criar playlist         | Usuário e cifra válidos              | Criar playlist                         | Playlist é criada                |
| UT-PLY-02 | Playlist sem cifras    | Lista de cifras vazia                | Criar playlist                         | Operação é rejeitada             |
| UT-PLY-03 | Remover playlist       | Playlist existente                   | Remover playlist                       | Playlist é removida              |
| UT-PLY-04 | Adicionar cifra        | Playlist existente e cifra existente | Adicionar cifra                        | Cifra é adicionada               |
| UT-PLY-05 | Remover cifra          | Playlist contém cifra                | Remover cifra                          | Cifra é removida                 |
| UT-PLY-06 | Trocar posição         | Playlist com duas ou mais cifras     | Trocar posições                        | Ordem das cifras é alterada      |
| UT-PLY-07 | Posição inválida       | Índice inexistente                   | Trocar posição                         | Operação é rejeitada             |
| UT-PLY-08 | Anonimizar playlist    | Playlist vinculada a usuário         | Anonimizar playlist                    | usuarioId recebe usuário anônimo |

### Regras de domínio

| ID        | Test Case                       | Given                           | When                              | Then                              |
| --------- | ------------------------------- | ------------------------------- | --------------------------------- | --------------------------------- |
| UT-RUL-01 | Cifra possui um proprietário    | Cifra vinculada ao usuário A    | Atualizar cifra para usuário B    | Operação é rejeitada              |
| UT-RUL-02 | Playlist possui um proprietário | Playlist vinculada ao usuário A | Atualizar playlist para usuário B | Operação é rejeitada              |
| UT-RUL-03 | Playlist exige cifra            | Playlist sem cifras             | Criar playlist                    | Operação é rejeitada              |
| UT-RUL-04 | Usuário anônimo único           | Usuário anônimo já existe       | Anonimizar recurso                | Mesmo usuário anônimo é utilizado |

## 2. Testes de Integração

### Usuário + Cifra

| ID        | Test Case                  | Given                             | When             | Then                                             |
| --------- | -------------------------- | --------------------------------- | ---------------- | ------------------------------------------------ |
| IT-USR-01 | Criar usuário no MongoDB   | Dados válidos                     | Executar criação | Usuário é persistido                             |
| IT-USR-02 | Nome duplicado             | Usuário com mesmo nome existente  | Criar usuário    | Persistência é rejeitada                         |
| IT-USR-03 | Listar cifras do usuário   | Usuário possui cifras persistidas | Solicitar cifras | Apenas cifras do usuário são retornadas          |
| IT-USR-04 | Deletar usuário com cifras | Usuário possui cifras             | Deletar usuário  | Cifras são anonimizadas                          |
| IT-USR-05 | Verificar anonimização     | Usuário possui cifras             | Deletar usuário  | usuarioId das cifras aponta para usuário anônimo |

### Usuário + Playlist

| ID        | Test Case                     | Given                    | When                | Then                                                |
| --------- | ----------------------------- | ------------------------ | ------------------- | --------------------------------------------------- |
| IT-USR-06 | Listar playlists              | Usuário possui playlists | Solicitar playlists | Apenas suas playlists são retornadas                |
| IT-USR-07 | Adicionar favorita            | Playlist persistida      | Favoritar playlist  | playlistId é persistido nos favoritos               |
| IT-USR-08 | Remover favorita              | Playlist está favoritada | Remover favorita    | playlistId deixa de existir nos favoritos           |
| IT-USR-09 | Deletar usuário com playlists | Usuário possui playlists | Deletar usuário     | Playlists são anonimizadas                          |
| IT-USR-10 | Verificar anonimização        | Usuário possui playlists | Deletar usuário     | usuarioId das playlists aponta para usuário anônimo |

### Cifra + Playlist

| ID        | Test Case                | Given                         | When            | Then                                     |
| --------- | ------------------------ | ----------------------------- | --------------- | ---------------------------------------- |
| IT-CIF-01 | Criar cifra              | Usuário persistido            | Criar cifra     | Cifra é persistida com usuarioId correto |
| IT-CIF-02 | Criar playlist com cifra | Usuário e cifra persistidos   | Criar playlist  | Playlist é persistida contendo a cifra   |
| IT-CIF-03 | Adicionar cifra          | Playlist e cifra persistidas  | Adicionar cifra | cifraId é persistido na playlist         |
| IT-CIF-04 | Remover cifra            | Playlist contém cifra         | Remover cifra   | cifraId é removido da playlist           |
| IT-CIF-05 | Trocar posição           | Playlist possui várias cifras | Trocar posição  | Nova ordem é persistida                  |
| IT-CIF-06 | Playlist sem cifra       | Nenhuma cifra associada       | Criar playlist  | Operação é rejeitada                     |

### Fluxos completos

| ID        | Test Case                                         | Given                                  | When                                    | Then                                              |
| --------- | ------------------------------------------------- | -------------------------------------- | --------------------------------------- | ------------------------------------------------- |
| IT-FLW-01 | Criar usuário e cifra                             | Usuário válido                         | Criar usuário → criar cifra             | Ambos são persistidos e relacionados              |
| IT-FLW-02 | Criar playlist                                    | Usuário e cifra existentes             | Criar playlist com cifra                | Playlist é criada com proprietário e cifra        |
| IT-FLW-03 | Montar playlist com cifras de usuários diferentes | Cifras pertencem a usuários diferentes | Criar playlist                          | Playlist é criada mantendo seu único proprietário |
| IT-FLW-04 | Favoritar playlist                                | Usuário e playlist existentes          | Favoritar playlist                      | Relação é persistida no usuário                   |
| IT-FLW-05 | Remover usuário                                   | Usuário possui cifras e playlists      | Remover usuário                         | Cifras e playlists são anonimizadas               |
| IT-FLW-06 | Acessar dados após anonimização                   | Usuário foi removido                   | Consultar cifras/playlists anonimizadas | Recursos continuam existindo com usuário anônimo  |


3. Critérios de cobertura

Os testes unitários devem cobrir:
* Todas as operações de usuário.
* Todas as operações de cifra.
* Todas as operações de playlist.
* Campos obrigatórios e opcionais.
* Regras de propriedade de cifra e playlist.
* Regra de playlist com no mínimo uma cifra.
* Validação de BPM(valores inteiros entre 30 a 300)
* Anonimização.

Os testes de integração devem cobrir:

* Persistência e recuperação no MongoDB.
* Relacionamentos por `usuarioId`, `playlistId` e `cifraId`.
* Fluxos envolvendo múltiplas entidades.
* Persistência das alterações nas listas de cifras e favoritos.
* Anonimização após remoção do usuário.
* Integridade das regras de negócio através das camadas da aplicação.

## 5. Stack de testes

A estratégia considera a stack definida no projeto: Java 21, Spring Boot 4, MongoDB, JUnit 6 e Mockito 5.

Testes unitários: JUnit + Mockito, isolando services/use cases e suas dependências.

Testes de integração: Spring Boot Test + MongoDB, validando o fluxo entre aplicação, repositories e banco de dados.
