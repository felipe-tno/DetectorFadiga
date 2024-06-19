# Monitoramento de Fadiga para Motoristas de Caminhão

Este projeto Android Studio foi desenvolvido em Kotlin e utiliza a biblioteca MediaPipe para detectar pontos faciais de um motorista de caminhão enquanto ele estiver com a câmera do celular apontada para o rosto. O aplicativo monitora sinais de fadiga e envia um alerta ao detectar indícios de cansaço, recomendando que o motorista estacione o caminhão e pare de dirigir para evitar acidentes.

## Funcionalidades

- **Detecção Facial:** Utiliza a biblioteca MediaPipe para detectar pontos faciais.
- **Monitoramento de Fadiga:** Analisa os pontos faciais para identificar sinais de fadiga.
- **Alerta de Fadiga:** Envia uma notificação ao motorista sugerindo que ele pare de dirigir ao detectar sinais de fadiga.

## Tecnologias Utilizadas

- **Kotlin:** Linguagem principal para o desenvolvimento do aplicativo.
- **Android Studio:** IDE utilizada para o desenvolvimento do projeto.
- **MediaPipe:** Biblioteca utilizada para a detecção de pontos faciais.

## Instalação

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/nome-do-repositorio.git
   ```
2. **Abra o projeto no Android Studio:**
   - Selecione "Open an existing Android Studio project".
   - Navegue até o diretório onde você clonou o repositório e selecione-o.

3. **Configure o projeto:**
   - Certifique-se de que todas as dependências estão corretamente instaladas.
   - Sincronize o projeto com o Gradle.

4. **Execute o aplicativo:**
   - Conecte um dispositivo Android ou use um emulador.
   - Clique em "Run" no Android Studio.

## Uso

1. **Inicialização:**
   - Ao abrir o aplicativo, a câmera frontal do dispositivo será ativada.
   - O aplicativo começará a detectar pontos faciais automaticamente.

2. **Monitoramento:**
   - O aplicativo monitora os sinais de fadiga analisando a abertura dos olhos e a posição da cabeça.
   - Se sinais de fadiga forem detectados, uma notificação será enviada.

3. **Alerta:**
   - A notificação irá sugerir que o motorista pare o caminhão em um local seguro e descanse.

## Contribuição

1. **Fork o projeto.**
2. **Crie uma nova branch:**
   ```bash
   git checkout -b minha-nova-funcionalidade
   ```
3. **Faça as alterações desejadas e commit:**
   ```bash
   git commit -m "Adiciona minha nova funcionalidade"
   ```
4. **Envie para o branch original:**
   ```bash
   git push origin minha-nova-funcionalidade
   ```
5. **Crie um Pull Request.**


## Contato

- **Desenvolvedores:** Felipe Tavares, Felipe Filgueiras, Gabriel Rosa, Felipe Seda e Giovanna Amaral.

Sinta-se à vontade para enviar sugestões, reportar bugs ou contribuir com o projeto!

---

Agradecemos por utilizar nosso aplicativo e contribuir para a segurança nas estradas.
