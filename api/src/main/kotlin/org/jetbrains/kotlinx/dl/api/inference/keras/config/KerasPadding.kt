/*
 * Copyright 2020 JetBrains s.r.o. and Kotlin Deep Learning project contributors. All Rights Reserved.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE.txt file.
 */

package org.jetbrains.kotlinx.dl.api.inference.keras.config

internal sealed class KerasPadding {
    object Same : KerasPadding()

    object Valid : KerasPadding()

    object Full : KerasPadding()

    class ZeroPadding2D : KerasPadding {
        val padding: IntArray = IntArray(4) { 0 }

        constructor(padding: Int) {
            this.padding[0] = padding
        }

        constructor(padding: IntArray) {
            this.padding[0] = padding[0]
            this.padding[1] = padding[1]
        }

        constructor(padding: Array<IntArray>) {
            this.padding[0] = padding[0][0]
            this.padding[1] = padding[0][1]
            this.padding[2] = padding[1][0]
            this.padding[3] = padding[1][1]
        }
    }
}