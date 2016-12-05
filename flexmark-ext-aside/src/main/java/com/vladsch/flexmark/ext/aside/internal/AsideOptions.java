package com.vladsch.flexmark.ext.aside.internal;

import com.vladsch.flexmark.ext.aside.AsideExtension;
import com.vladsch.flexmark.util.options.DataHolder;

class AsideOptions {
    public final boolean extendToBlankLine;
    public final boolean ignoreBlankLine;

    public AsideOptions(DataHolder options) {
        this.extendToBlankLine = options.get(AsideExtension.EXTEND_TO_BLANK_LINE);
        this.ignoreBlankLine = options.get(AsideExtension.IGNORE_BLANK_LINE);
    }
}
