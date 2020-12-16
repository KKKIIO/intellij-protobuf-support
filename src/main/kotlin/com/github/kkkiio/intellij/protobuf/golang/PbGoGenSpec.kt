package com.github.kkkiio.intellij.protobuf.golang

import com.intellij.psi.util.QualifiedName

object PbGoGenSpec {
    fun enumValueName(messageGoName: String?,
                      enumGoName: String,
                      enumValueName: String): String {
        // A top-level enum value's name is: EnumName_ValueName
        // An enum value contained in a message is: MessageName_ValueName
        //
        // For historical reasons, enum value names are not camel-cased.
        return (messageGoName ?: enumGoName) + "_" + enumValueName
    }

    /**
     *  newGoIdent returns the Go identifier for a descriptor.
     */
    fun newGoIdent(pbSymbolName: QualifiedName,
                   pbPackageName: QualifiedName): String {
        require(pbSymbolName.matchesPrefix(pbPackageName))
        return goCamelCase(pbSymbolName.removeHead(pbPackageName.componentCount)
                .join("."))
    }

    /**
     * goCamelCase camel-cases a protobuf name for use as a Go identifier.
     *
     * If there is an interior underscore followed by a lower case letter,
     * drop the underscore and convert the letter to upper case.
     *
     * copy from google.golang.org/protobuf/internal/strs/strings.go
     */
    fun goCamelCase(s: String): String {
        // Invariant: if the next letter is lower case, it must be converted
        // to upper case.
        // That is, we process a word at a time, where words are marked by _ or
        // upper case letter. Digits are treated as words.
        val b = StringBuilder()
        var i = 0
        while (i < s.length) {
            var c = s[i]
            when {
                c == '.' && i + 1 < s.length && s[i + 1].isASCIILower() ->
                    // Skip over '.' in ".{{lowercase}}".
                    Unit
                c == '.' ->
                    b.append('_') // convert '.' to '_'
                c == '_' && (i == 0 || s[i - 1] == '.') ->
                    // Convert initial '_' to ensure we start with a capital letter.
                    // Do the same for '_' after '.' to match historic behavior.
                    b.append('X') // convert '_' to 'X'
                c == '_' && i + 1 < s.length && s[i + 1].isASCIILower() ->
                    // Skip over '_' in "_{{lowercase}}".
                    Unit
                c.isASCIIDigit() ->
                    b.append(c)
                else -> {
                    // Assume we have a letter now - if not, it's a bogus identifier.
                    // The next word is a sequence of characters that must start upper case.
                    if (c.isASCIILower()) {
                        // convert lowercase to uppercase
                        c = c.toUpperCase()
                    }
                    b.append(c)
                    // Accept lower case sequence that follows.
                    while (i + 1 < s.length && s[i + 1].isASCIILower()) {
                        b.append(s[i + 1])
                        i += 1
                    }
                }
            }
            i += 1
        }
        return b.toString()
    }

    fun Char.isASCIILower(): Boolean {
        return this in 'a'..'z'
    }

    fun Char.isASCIIUpper(): Boolean {
        return this in 'A'..'Z'
    }

    fun Char.isASCIIDigit(): Boolean {
        return this in '0'..'9'
    }
}
