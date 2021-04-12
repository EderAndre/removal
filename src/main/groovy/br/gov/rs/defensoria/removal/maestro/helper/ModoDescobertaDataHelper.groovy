package br.gov.rs.defensoria.removal.maestro.helper

import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.springframework.stereotype.Service

@Service
class ModoDescobertaDataHelper {
    private final Integer MAX_YEAR = 9999
    private final Integer MAX_MONTH = 12
    private final Integer MAX_DAY = 31
    private final Integer MAX_HOUR = 23
    private final Integer MAX_MIN = 59
    private final LocalDateTime maxDate

    ModoDescobertaDataHelper() {
        maxDate = new LocalDateTime(MAX_YEAR, MAX_MONTH, MAX_DAY, MAX_HOUR, MAX_MIN)
    }

    LocalDateTime getData(String dataReferencia) {
        LocalDateTime convertedDate
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
            convertedDate = formatter.parseLocalDateTime(dataReferencia)
        } catch (Exception e) {
            convertedDate = maxDate
        }

        return convertedDate
    }
}
