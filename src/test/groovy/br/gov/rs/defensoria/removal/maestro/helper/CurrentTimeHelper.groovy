package br.gov.rs.defensoria.removal.maestro.helper

import org.joda.time.DateTime
import org.joda.time.DateTimeUtils
import org.joda.time.LocalDateTime
import org.springframework.stereotype.Service

@Service
class CurrentTimeHelper {

    void setCurrentTime(LocalDateTime current) {
        DateTime dateTime = current.toDateTime()
        Long millis = dateTime.getMillis()
        DateTimeUtils.setCurrentMillisFixed(millis)
    }
}
