package com.vladsch.flexmark.ext.tables.internal;

import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.util.options.DataHolder;

class TableParserOptions {
    public final int maxHeaderRows;
    public final int minHeaderRows;
    public final boolean appendMissingColumns;
    public final boolean discardExtraColumns;
    public final boolean columnSpans;
    public final boolean trimCellWhitespace;
    public final boolean headerSeparatorColumnMatch;
    public final String className;

    TableParserOptions(DataHolder options) {
        this.maxHeaderRows = TablesExtension.MAX_HEADER_ROWS.getFrom(options);
        this.minHeaderRows = TablesExtension.MIN_HEADER_ROWS.getFrom(options);
        this.appendMissingColumns = TablesExtension.APPEND_MISSING_COLUMNS.getFrom(options);
        this.discardExtraColumns = TablesExtension.DISCARD_EXTRA_COLUMNS.getFrom(options);
        this.columnSpans = TablesExtension.COLUMN_SPANS.getFrom(options);
        this.trimCellWhitespace = TablesExtension.TRIM_CELL_WHITESPACE.getFrom(options);
        this.headerSeparatorColumnMatch = TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH.getFrom(options);
        this.className = TablesExtension.CLASS_NAME.getFrom(options);
    }
}
