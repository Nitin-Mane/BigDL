/*
 * Copyright 2016 The BigDL Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intel.analytics.bigdl.ppml.utils

import com.intel.analytics.bigdl.dllib.tensor.Tensor
import org.apache.spark.sql.DataFrame

object TensorUtils {
  def fromDataFrame(df: DataFrame,
                    columns: Array[String]) = {
    if (columns == null) {
      null
    } else {
      var rowNum = 0
      val dataArray = df.collect().map(row => {
        if (rowNum == 0) rowNum = row.length
        val rowArray = new Array[Float](row.length)
        columns.indices.foreach(i => {
          rowArray(i) = row.getAs[Float](columns(i))
        })
        rowArray
      })
      Tensor[Float](dataArray.flatten, Array(rowNum, columns.length))
    }
  }
}
