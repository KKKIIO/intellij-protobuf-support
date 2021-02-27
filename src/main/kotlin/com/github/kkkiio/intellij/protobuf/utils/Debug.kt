package com.github.kkkiio.intellij.protobuf.utils

import com.goide.psi.GoCallExpr
import com.goide.psi.GoExpression
import com.goide.psi.GoFunctionOrMethodDeclaration
import com.goide.psi.GoType
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType

fun String.quoteKt() = StringUtil.wrapWithDoubleQuote(StringUtil.escapeStringCharacters(this))
fun PsiElement.repr() = "{text=${text.quoteKt()}, elementType=$elementType, class=${javaClass}}"
fun GoExpression.repr() = "{text=${text.quoteKt()}, class=${javaClass}}"
fun GoCallExpr.repr() = "{expression=${expression.repr()}, args=${argumentList.repr()}, class=${javaClass}}"
fun GoFunctionOrMethodDeclaration.repr() = "{name=$name, class=$javaClass}"
fun GoType.repr() = "{presentationText=${presentationText.quoteKt()}}"
