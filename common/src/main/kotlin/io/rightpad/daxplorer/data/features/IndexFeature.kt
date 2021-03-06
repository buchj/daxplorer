package io.rightpad.daxplorer.data.features

import io.rightpad.daxplorer.data.datapoints.absolute.IndexDataPoint

class IndexFeature : Feature<IndexDataPoint> {
    override var featureData: List<IndexDataPoint>? = null

    override fun calculate(indexData: List<IndexDataPoint>) {
        this.featureData = indexData
    }
}