// This is a generated file. Not intended for manual editing.
package idea.plugin.protoeditor.lang.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static idea.plugin.protoeditor.lang.psi.PbTextTypes.*;
import idea.plugin.protoeditor.lang.psi.*;
import static idea.plugin.protoeditor.lang.psi.ProtoTokenTypes.*;

public class PbTextStringPartImpl extends PbTextElementBase implements PbTextStringPart {

  public PbTextStringPartImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PbTextVisitor visitor) {
    visitor.visitStringPart(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PbTextVisitor) accept((PbTextVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public PsiElement getStringLiteral() {
    return findNotNullChildByType(STRING_LITERAL);
  }

}
