# Avaliacao da entrega 3

Base: cda5e49 + fontes locais preservadas em C:/Users/Jonas/AppData/Local/Temp/receitas-base-5cfaa849378c4fdc865e11c2fcee01c1.
Referencia: Trabalho de Android - 3.pdf e aulas 10 a 14, sobretudo aula-14/material/MyApplication(5).zip.

Plano: 1) banco/model/DAO e autenticacao; 2) ViewModels e CRUDs; 3) navegacao e telas; 4) testes e revisao independente.
Interfaces: IDs Long entre model, DAO e rotas; campos de receita em texto como no formulario; banco unico compartilhado. Sessao guardada em memoria como no professor.
Decisao atual: o usuario autorizou commit e push desta versao e definiu a branch feat/jonas. O trabalho permanece na pasta original, com copia dos arquivos anteriores preservada. A validacao abaixo ocorreu antes dessa autorizacao.
Ruling: Preservar grade, cores e imagens existentes; componentes herdados sem evidencia nos ZIPs ficam explicitados na avaliacao. Custo: conformidade visual com a restricao da aula exige validacao do professor.
Ruling: Ingredientes/preparo persistidos como TEXT, exibidos por linhas; campos do formulario preservados. Custo: model deixa de usar listas nesse ponto.
Ruling: Preservar as seis receitas e imagens iniciais; novas receitas usam imagem opcional (sem seletor nem foto inventada). Custo: novo cadastro nao exibe foto.
Ruling: Banco novo receitas_diarias.db versao 1, sem migracao destrutiva; onUpgrade rejeita uma versao nao prevista em vez de apagar dados. Nao ha banco anterior nesse aplicativo.

## Evidencias de execucao
Validacao concluida antes da transferencia para a pasta original.

- Base local: testDebugUnitTest passou antes da implementacao (3 testes).
- RED inicial: 3 testes do emulador falharam por ausencia de compras, conta sem SQLite e cadastro sem CRUD.
- Primeira versao: assembleDebug e 3 unitarios + 6 instrumentados passaram.
- Revisao independente: nenhum Critical; perda de rascunho nos dois formularios (Important); aba de cadastro perde estado ao recriar (Minor reclassificado como Important por quebrar feedback do cadastro).
- RED de revisao: tres testes de recriacao falharam, reproduzindo perda de rascunhos/aba. Correcao: carregar formulario apenas se VM nao carregado e rememberSaveable para aba/nome/email/mensagem (senha continua apenas em remember).
- Integridade: 43 arquivos da base original conferidos por SHA256, nenhuma divergencia.
- Ruling de revisao: nao ampliar para sincronizacao, recuperacao de senha, seletor de fotos ou migracao de versao futura. Nao foram pedidos; limites expostos no parecer.

- Final: fixed perda de rascunhos e aba de cadastro: tres regressoes RED -> GREEN; suite final 3 unitarios + 9 instrumentados, zero falhas/ignorados.
- Final: lintDebug aprovado, zero erros e 22 avisos descritos no parecer. Avisos de ferramentas antigas, recursos/icones e convencoes ficam adiados por limite de escopo.
- Tasks 1-4: complete; build/testes/revisao concluidos sem commits.
- Decisao do usuario reforcada: manter entrega pequena para duas aulas noturnas; sem novas camadas ou funcionalidades alem dos requisitos.
- Transferencia: branch renomeada para entrega3 e selecionada na pasta original nosso-projeto/dev-mobile. Os 31 arquivos diferentes foram copiados e conferidos por SHA256; HEAD permaneceu cda5e49, sem commit. A copia auxiliar deixou de ser usada para desenvolvimento.
- Backup anterior a transferencia: C:/Users/Jonas/AppData/Local/Temp/receitas-transferencia-623914a995c849b985ea61cc5b40a781.
- Preparacao para envio: branch renomeada de entrega3 para feat/jonas. Fontes e configuracoes Gradle conferidas por SHA256 contra a versao validada, sem divergencias. Criado .gitignore na raiz e retirados do versionamento os arquivos de compilacao, caches e configuracoes locais; os arquivos existentes no computador foram preservados.
