/*
 * Copyright 2020 JetBrains s.r.o. and Kotlin Deep Learning project contributors. All Rights Reserved.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE.txt file.
 */

package examples.transferlearning

import org.jetbrains.kotlinx.dl.api.core.Sequential
import org.jetbrains.kotlinx.dl.api.core.loss.Losses
import org.jetbrains.kotlinx.dl.api.core.metric.Metrics
import org.jetbrains.kotlinx.dl.api.core.optimizer.Adam
import org.jetbrains.kotlinx.dl.datasets.Dataset
import org.jetbrains.kotlinx.dl.datasets.handlers.*

/**
 * This examples demonstrates the weird inference case:
 *
 * Weights are not loaded, but initialized via initialized defined in configuration, configuration is loaded from .json file.
 *
 * Model is evaluated after loading to obtain accuracy value.
 *
 * No additional training.
 *
 * No new layers are added.
 *
 * NOTE: Model and weights are resources in api module.
 */
fun main() {
    val (train, test) = Dataset.createTrainAndTestDatasets(
        FASHION_TRAIN_IMAGES_ARCHIVE,
        FASHION_TRAIN_LABELS_ARCHIVE,
        FASHION_TEST_IMAGES_ARCHIVE,
        FASHION_TEST_LABELS_ARCHIVE,
        NUMBER_OF_CLASSES,
        ::extractFashionImages,
        ::extractFashionLabels
    )

    val jsonConfigFile = getJSONConfigFile()
    val model = Sequential.loadModelConfiguration(jsonConfigFile)

    model.use {
        it.compile(
            optimizer = Adam(),
            loss = Losses.SOFT_MAX_CROSS_ENTROPY_WITH_LOGITS,
            metric = Metrics.ACCURACY
        )
        it.init()
        it.summary()

        val accuracy = it.evaluate(dataset = test, batchSize = 100).metrics[Metrics.ACCURACY]

        println("Accuracy training $accuracy")
    }
}





