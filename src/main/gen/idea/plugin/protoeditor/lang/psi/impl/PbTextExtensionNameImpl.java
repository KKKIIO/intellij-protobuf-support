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

public class PbTextExtensionNameImpl extends PbTextExtensionNameMixin implements PbTextExtensionName {

  public PbTextExtensionNameImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PbTextVisitor visitor) {
    visitor.visitExtensionName(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PbTextVisitor) accept((PbTextVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PbTextDomain getDomain() {
    return findChildByClass(PbTextDomain.class);
  }

  @Override
  @Nullable
  public PbTextSymbolPath getSymbolPath() {
    return findChildByClass(PbTextSymbolPath.class);
  }

}
