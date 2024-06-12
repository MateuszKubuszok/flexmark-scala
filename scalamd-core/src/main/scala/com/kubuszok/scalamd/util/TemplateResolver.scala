package com.kubuszok.scalamd.util

import java.util.regex.{Matcher, Pattern}
import scala.util.matching.Regex

// TemplateUtil.java rewritten

sealed trait TemplateResolver {
  
    def resolve(groups: Array[String]): Option[String]

    extension (text: CharSequence)
      def resolveRefs(pattern: Pattern): String = {
        val matcher = pattern.matcher(text)
        if matcher.find then {
          val sb = new StringBuffer
          while matcher.find do {
            val groups = new Array[String](matcher.groupCount + 1)
            for i <- groups.indices do groups(i) = matcher.group(i)
            val resolved = resolve(groups)
            matcher.appendReplacement(
              sb,
              resolved.fold("")(_.replace("\\", "\\\\").replace("$", "\\$"))
            )
          }
          matcher.appendTail(sb)
          sb.toString
        } else text.toString
      }
}
object TemplateResolver {

  object Empty extends TemplateResolver {
    override def resolve(groups: Array[String]): Option[String] = None
  }

  final class Mapped(var resolved: Map[String, String]) extends TemplateResolver {
    def this() = this(Map.empty)
    def this(name: String, value: String) = this(Map(name -> value))

    def set(name: String, value: String): Mapped =
      resolved = resolved.updated(name, value)
      this

    override def resolve(groups: Array[String]): Option[String] = if groups.length > 2 then None else resolved.get(groups(1))
  }
}
