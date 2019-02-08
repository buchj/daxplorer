package io.rightpad.daxplorer.data

import java.time.LocalDateTime

open class SimpleValueDataPoint(
        timestamp: LocalDateTime,
        var value: Float
): TimeSeriesDataPoint(timestamp)
