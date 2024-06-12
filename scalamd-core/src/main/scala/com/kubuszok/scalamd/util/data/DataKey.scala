package com.kubuszok.scalamd.util.data

import com.kubuszok.scalamd.util.Extension

type OptionalDataValueFactory[A] = DataHolder => Option[A]
type DataValueFactory[A] = DataHolder => A

sealed abstract class DataKey[A] {
  val name: String

  type Of[A1]
  def factory: DataHolder => Of[A]
  def defaultValue(holder: Of[DataHolder]): Of[A]
  def get(holder: Of[DataHolder]): Of[A]
  def set(holder: MutableDataHolder, value: Of[A]): MutableDataHolder
}
object DataKey {

  final case class Optional[A](name: String, factory: OptionalDataValueFactory[A], defaultValue: Option[A]) extends DataKey[A] {

    type Of[A1] = Option[A1]
    def defaultValue(holder: Option[DataHolder]): Option[A] = holder.fold(defaultValue)(a => factory(a))
    def get(holder: Option[DataHolder]): Option[A] = holder.fold(defaultValue)(a => factory(a))
    def set(holder: MutableDataHolder, value: Option[A]): MutableDataHolder = holder.set(this, value)
  }
  final case class Required[A](name: String, factory: DataValueFactory[A], defaultValue: A) extends DataKey[A] {

    type Of[A1] = A1
    def defaultValue(holder: DataHolder): A = factory(holder)
    def get(holder: DataHolder): A = factory(holder)
    def set(holder: MutableDataHolder, value: A): MutableDataHolder = holder.set(this, value)
  }

  def of[A](name: String, value: A): Required[A] = Required(name, _ => value, value)

  // SharedDataKeys
  
  // BuilderBase
  val EXTENSIONS: Required[Seq[Extension]] = of("EXTENSIONS", Seq.empty)

  // Parser
  val HEADING_NO_ATX_SPACE: Required[Boolean] = of("HEADING_NO_ATX_SPACE", false)
  // used to set escaping of # at start independent of HEADING_NO_ATX_SPACE setting if desired
  val ESCAPE_HEADING_NO_ATX_SPACE: Required[Boolean] = Required("ESCAPE_HEADING_NO_ATX_SPACE", HEADING_NO_ATX_SPACE.get, false);
  val HTML_FOR_TRANSLATOR: Required[Boolean] = of("HTML_FOR_TRANSLATOR", false);
  val INTELLIJ_DUMMY_IDENTIFIER: Required[Boolean] = of("INTELLIJ_DUMMY_IDENTIFIER", false);
  val PARSE_INNER_HTML_COMMENTS: Required[Boolean] = of("PARSE_INNER_HTML_COMMENTS", false);
  val BLANK_LINES_IN_AST: Required[Boolean] = of("BLANK_LINES_IN_AST", false);
  val TRANSLATION_HTML_BLOCK_TAG_PATTERN: Required[String] = of("TRANSLATION_HTML_BLOCK_TAG_PATTERN", "___(?:\\d+)_");
  val TRANSLATION_HTML_INLINE_TAG_PATTERN: Required[String] = of("TRANSLATION_HTML_INLINE_TAG_PATTERN", "__(?:\\d+)_");
  val TRANSLATION_AUTOLINK_TAG_PATTERN: Required[String] = of("TRANSLATION_AUTOLINK_TAG_PATTERN", "____(?:\\d+)_");

  val RENDERER_MAX_TRAILING_BLANK_LINES: Required[Int] = of("RENDERER_MAX_TRAILING_BLANK_LINES", 1);
  val RENDERER_MAX_BLANK_LINES: Required[Int] = of("RENDERER_MAX_BLANK_LINES", 1);
  val INDENT_SIZE: Required[Int] = of("INDENT_SIZE", 0);
  val PERCENT_ENCODE_URLS: Required[Boolean] = of("PERCENT_ENCODE_URLS", false);
  val HEADER_ID_GENERATOR_RESOLVE_DUPES: Required[Boolean] = of("HEADER_ID_GENERATOR_RESOLVE_DUPES", true);
  val HEADER_ID_GENERATOR_TO_DASH_CHARS: Required[String] = of("HEADER_ID_GENERATOR_TO_DASH_CHARS", " -_");
  val HEADER_ID_GENERATOR_NON_DASH_CHARS: Required[String] = of("HEADER_ID_GENERATOR_NON_DASH_CHARS", "");
  val HEADER_ID_GENERATOR_NO_DUPED_DASHES: Required[Boolean] = of("HEADER_ID_GENERATOR_NO_DUPED_DASHES", false);
  val HEADER_ID_GENERATOR_NON_ASCII_TO_LOWERCASE: Required[Boolean] = of("HEADER_ID_GENERATOR_NON_ASCII_TO_LOWERCASE", true);
  val HEADER_ID_REF_TEXT_TRIM_LEADING_SPACES: Required[Boolean] = of("HEADER_ID_REF_TEXT_TRIM_LEADING_SPACES", true);
  val HEADER_ID_REF_TEXT_TRIM_TRAILING_SPACES: Required[Boolean] = of("HEADER_ID_REF_TEXT_TRIM_TRAILING_SPACES", true);
  val HEADER_ID_ADD_EMOJI_SHORTCUT: Required[Boolean] = of("HEADER_ID_ADD_EMOJI_SHORTCUT", false);
  val RENDER_HEADER_ID: Required[Boolean] = of("RENDER_HEADER_ID", false);
  val GENERATE_HEADER_ID: Required[Boolean] = of("GENERATE_HEADER_ID", true);
  val DO_NOT_RENDER_LINKS: Required[Boolean] = of("DO_NOT_RENDER_LINKS", false);

  // Formatter
  val FORMATTER_MAX_BLANK_LINES: Required[Int] = of("FORMATTER_MAX_BLANK_LINES", 2);
  val FORMATTER_MAX_TRAILING_BLANK_LINES: Required[Int] = of("FORMATTER_MAX_TRAILING_BLANK_LINES", 1);
  val BLOCK_QUOTE_BLANK_LINES: Required[Boolean] = of("BLOCK_QUOTE_BLANK_LINES", true);

  val APPLY_SPECIAL_LEAD_IN_HANDLERS: Required[Boolean] = of("APPLY_SPECIAL_LEAD_IN_HANDLERS", true);
  val ESCAPE_SPECIAL_CHARS: Required[Boolean] = Required("ESCAPE_SPECIAL_CHARS", APPLY_SPECIAL_LEAD_IN_HANDLERS.get, true);
  val ESCAPE_NUMBERED_LEAD_IN: Required[Boolean] = Required("ESCAPE_NUMBERED_LEAD_IN", APPLY_SPECIAL_LEAD_IN_HANDLERS.get, true);
  val UNESCAPE_SPECIAL_CHARS: Required[Boolean] = Required("UNESCAPE_SPECIAL_CHARS", APPLY_SPECIAL_LEAD_IN_HANDLERS.get, true);
  val RUNNING_TESTS: Required[Boolean] = of("RUNNING_TESTS", false);
}
