package com.sqality.scct

import org.specs2.mutable._
import com.sqality.scct.ClassTypes.ClassType

class CoverageFilterSpec extends Specification {
  "empty filter does pass thru" in {
    val data = List(block("/home/scct/file.scala", "com.package.MyClass"))

    CoverageFilter.filter(data, Nil, Nil) mustEqual data
  }

  "excludeClasses" should {
    "non matching filter does pass thru" in {
      val data = List(block("", "com.package.MyClass"))

      CoverageFilter.filter(data, Nil, "nonMatching".r :: Nil) mustEqual data
    }

    "filter excludes matches" in {
      val blockMatching = block("", "com.package.MyClass")
      val blockNonMatching = block("", "nonmatching")

      CoverageFilter.filter(List(blockMatching, blockNonMatching), Nil, "com.package*".r :: Nil) mustEqual List(blockNonMatching)
    }

    "multiple filters" in {
      val blockMatching1 = block("", "com.package.MyClass")
      val blockMatching2 = block("", "com.package.data.MyClass2")
      val blockNonMatching = block("", "nonmatching")

      CoverageFilter.filter(List(blockMatching1, blockMatching2, blockNonMatching), Nil,
        "com.package.My*".r :: "com.package.data*".r :: Nil) mustEqual List(blockNonMatching)
    }
  }

  "excludeFiles" should {
    "non matching filter does pass thru" in {
      val data = List(block("/home/scct/file.scala", "com.package.MyClass"))

      CoverageFilter.filter(data, "nonMatching".r :: Nil, Nil) mustEqual data
    }

    "filter excludes matches" in {
      val blockMatching = block("/home/scct/file.html", "com.package.MyClass")
      val blockNonMatching = block("/home/scct/file.scala", "com.package.MyClass2")

      CoverageFilter.filter(List(blockMatching, blockNonMatching), ".*.html".r :: Nil, Nil) mustEqual List(blockNonMatching)
    }

    "multiple filters" in {
      val blockMatching1 = block("/home/scct/file.html", "com.package.MyClass")
      val blockMatching2 = block("/home/scct/play/core.scala", "org.package.data.MyClass2")
      val blockNonMatching = block("/home/scct/file.scala", "nonmatching")

      CoverageFilter.filter(List(blockMatching1, blockMatching2, blockNonMatching),
        ".*.html".r :: "/home/scct/play*".r :: Nil, Nil) mustEqual List(blockNonMatching)
    }
  }

  private def block(fileName: String, packageName: String) =
    new CoveredBlock("c1", 1, Name(fileName, ClassTypes.Class, packageName, "className", "projectName"), 1, false)
}