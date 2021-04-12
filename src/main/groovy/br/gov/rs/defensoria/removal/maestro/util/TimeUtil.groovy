package br.gov.rs.defensoria.removal.maestro.util

import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.springframework.stereotype.Component

@Component
class TimeUtil {

    LocalDateTime stringToLocalDateTime(String time) {
        String[] dataHora = time.split(' - ')
        String[] data = dataHora[0].split('/')
        int dia = Integer.parseInt(data[0])
        int mes = Integer.parseInt(data[1])
        int ano = Integer.parseInt(data[2])

        int h = 0
        int minutos = 0

        if (dataHora.size() > 1) {
            String[] hora = dataHora[1].split(':')
            h = Integer.parseInt(hora[0])
            minutos = Integer.parseInt(hora[1])
        }

        LocalDateTime convertedTime = new LocalDateTime(ano, mes, dia, h, minutos)

        return convertedTime
    }

    LocalDateTime convertPickerAngularJS(String dataHora) {
        LocalDateTime convertedTime

        try {
            dataHora = dataHora.substring(0, dataHora.indexOf(' GMT-'))
            convertedTime = LocalDateTime.parse(dataHora, DateTimeFormat.forPattern("E MMM dd yyyy HH:mm:ss").withLocale(Locale.ENGLISH))
        } catch (StringIndexOutOfBoundsException siobe) {
            try {
                convertedTime = LocalDateTime.parse(dataHora, DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm"))
            } catch (IllegalArgumentException iae) {
                try {
                    convertedTime = LocalDateTime.parse(dataHora, DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"))
                } catch (IllegalArgumentException iaeAux) {
                    convertedTime = LocalDateTime.parse(dataHora, DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"))
                }
            }
        }

        return convertedTime
    }
}
