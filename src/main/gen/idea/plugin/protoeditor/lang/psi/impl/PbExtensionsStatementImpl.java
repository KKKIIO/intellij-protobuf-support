// This is a generated file. Not intended for manual editing.
package idea.plugin.protoeditor.lang.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import idea.plugin.protoeditor.lang.psi.util.PbPsiTreeUtil;
import static idea.plugin.protoeditor.lang.psi.PbTypes.*;
import idea.plugin.protoeditor.lang.psi.*;
import idea.plugin.protoeditor.lang.psi.util.PbPsiImplUtil;

public class PbExtensionsStatementImpl extends PbExtensionsStatementMixin implements PbExtensionsStatement {

  public PbExtensionsStatementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PbVisitor visitor) {
    visitor.visitExtensionsStatement(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PbVisitor) accept((PbVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<PbExtensionRange> getExtensionRangeList() {
    return PbPsiTreeUtil.getChildrenOfTypeAsList(this, PbExtensionRange.class);
  }

  @Override
  @Nullable
  public PbOptionList getOptionList() {
    return PbPsiTreeUtil.getChildOfType(this, PbOptionList.class);
  }

}
