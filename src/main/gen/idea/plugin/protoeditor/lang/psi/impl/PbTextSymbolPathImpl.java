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

public class PbTextSymbolPathImpl extends PbTextSymbolPathMixin implements PbTextSymbolPath {

  public PbTextSymbolPathImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PbTextVisitor visitor) {
    visitor.visitSymbolPath(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PbTextVisitor) accept((PbTextVisitor)visitor);
    else super.accept(visitor);
  }

}
