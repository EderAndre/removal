# Sistema de remoção (Vacância)

## Deploy

- Realizar o download do artefato gerado no GitLab
- Colocar o .jar em `srvdpejb01:/java-apps/removal-service/`
- Reiniciar o servico vacancia:
    ```bash
    cd /java-apps/removal-service/
    sudo ./stop.sh
    sudo ./start.sh
    ```
