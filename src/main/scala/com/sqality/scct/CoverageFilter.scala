package com.sqality.scct

import scala.util.matching.Regex

object CoverageFilter {
  def filter(data: List[CoveredBlock], excludeFiles: List[Regex], excludeClasses: List[Regex]): List[CoveredBlock] =
    data.filter(isIncluded(_, excludeFiles, excludeClasses))

  private def isIncluded(block: CoveredBlock, excludeFiles: List[Regex], excludeClasses: List[Regex]) = {
    // Create full class name
    val fullClassName = block.name.packageName + "." + block.name.className

    // Match file name using provided regular expressions
    val isFileNameMatched = excludeFiles.exists(_.findFirstIn(block.name.sourceFile).isDefined)

    // Match class name using provided regular expressions
    val isClassNameMatched = excludeClasses.exists(_.findFirstIn(fullClassName).isDefined)

    // Filter out if either file name or class name matches
    val isMatched = isFileNameMatched || isClassNameMatched

    if (debug && isMatched) println("scct : excluding " + block.name.sourceFile)

    !isMatched
  }

  private def debug = System.getProperty("scct.debug") == "true"
}