Scala.md
========

**Scala.md** is a rewrite of [flexmark-java](https://github.com/vsch/flexmark-java) into Scala, while
_**flexmark-java** is a Java implementation of **[CommonMark (spec 0.28)]** parser using the blocks first, inlines after Markdown parsing architecture_ which boasts:

> Its strengths are speed, flexibility, Markdown source element based AST with details of the
source position down to individual characters of lexemes that make up the element and
extensibility.
>
> The API allows granular control of the parsing process and is optimized for parsing with a large
number of installed extensions. The parser and extensions come with plenty of options for parser
behavior and HTML rendering variations. The end goal is to have the parser and renderer be able
to mimic other parsers with great degree of accuracy. This is now partially complete with the
implementation of [Markdown Processor Emulation](#markdown-processor-emulation)
>
> Motivation for this project was the need to replace [pegdown] parser in my [Markdown Navigator]
plugin for JetBrains IDEs. [pegdown] has a great feature set but its speed in general is less
than ideal and for pathological input either hangs or practically hangs during parsing.

Scala.md was created because:

 - I needed slightly more Scala-idiomatic API - that could have been addressed with a wrapper, though
 - I needed the code to be cross-compiled between Scala and Scala Native - I want to be able to develop static-site
   generator for Markdown using Scala CLI - users would be able to customize its behavior in Scala CLI AND run
   their modifications as a Native app - this would be possible with GraalVM... but native-image build takes A LOT of
   time and resources, so it doesn't have a nice feedback loop
 - I thought it would be fun, and you cannot take that away from me

Progress and scope
------------------

Scala.md is not intended to be as fast as flexmark-java - as long as I would be able to build sited noticeable faster
than with `jekyll` I'd be pretty happy. It will not be as modular, nor implement all the modules that flexmark did
(e.g. it doesn't provide: DOCX-rendering or PDF-rendering). 

License
-------

Copyright (c) 2015-2016 Atlassian and others.

Copyright (c) 2016-2023, Vladimir Schneider,

BSD (2-clause) licensed, see [LICENSE.txt] file.

[Admonition Extension, Material for MkDocs]: https://squidfunk.github.io/mkdocs-material/reference/admonitions/
[CommonMark]: https://commonmark.org
[CommonMark (spec 0.27)]: https://spec.commonmark.org/0.27
[CommonMark (spec 0.28)]: https://spec.commonmark.org/0.28
[DocxConverter Sample]: flexmark-java-samples/src/com/vladsch/flexmark/java/samples/DocxConverterCommonMark.java
[Extensions.java]: flexmark-profile-pegdown/src/main/java/com/vladsch/flexmark/profile/pegdown/Extensions.java
[GitHub]: https://github.com/vsch/laravel-translation-manager
[GitHub Issues page]: ../../issues
[HtmlToMarkdownCustomizedSample.java]: flexmark-java-samples/src/com/vladsch/flexmark/java/samples/HtmlToMarkdownCustomizedSample.java
[Include Markdown and HTML File Content]: ../../wiki/Usage#include-markdown-and-html-file-content
[Jekyll]: https://jekyllrb.com
[Kramdown]: https://kramdown.gettalong.org
[LICENSE.txt]: LICENSE.txt
[League/CommonMark]: https://github.com/thephpleague/commonmark
[Markdown]: https://daringfireball.net/projects/markdown/
[Markdown Navigator]: https://github.com/vsch/idea-multimarkdown
[MultiMarkdown]: https://fletcherpenney.net/multimarkdown
[Open HTML To PDF]: https://github.com/danfickle/openhtmltopdf
[PHP Markdown Extra]: https://michelf.ca/projects/php-markdown/extra/#abbr
[PegdownOptionsAdapter.java]: flexmark-profile-pegdown/src/main/java/com/vladsch/flexmark/profile/pegdown/PegdownOptionsAdapter.java
[VERSION.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test-data/performance/VERSION.md
[commonmark-java]: https://github.com/atlassian/commonmark-java
[commonMarkSpec.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test-data/performance/commonMarkSpec.md
[custom link resolver]: flexmark-java-samples/src/com/vladsch/flexmark/java/samples/PegdownCustomLinkResolverOptions.java
[docx4j]: https://www.docx4java.org/trac/docx4j
[flexmark-java]: https://github.com/vsch/flexmark-java
[hang-pegdown.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test-data/performance/hang-pegdown.md
[hang-pegdown2.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test-data/performance/hang-pegdown2.md
[intellij-markdown]: https://github.com/valich/intellij-markdown
[pegdown]: https://github.com/sirthias/pegdown
[spec.txt]: https://github.com/vsch/idea-multimarkdown/blob/master/test-data/performance/spec.md
[wrap.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test-data/performance/wrap.md
