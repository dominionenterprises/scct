package com.sqality.scct

import scala.util.matching.Regex

class CoverageFilter(excludeFiles: Array[Regex]) {

  private var debug = System.getProperty("scct.debug") == "true"

  private def isIncluded(block: CoveredBlock) = {
    val fullName = block.name.packageName + "." + block.name.className
    val isMatched = excludeFiles.filter(_.findFirstIn(fullName).isDefined).size > 0

    if (debug && isMatched) println("scct : excluding " + block.name.sourceFile)

    !isMatched
  }

  def filter(data: List[CoveredBlock]): List[CoveredBlock] = data.filter(isIncluded)
}