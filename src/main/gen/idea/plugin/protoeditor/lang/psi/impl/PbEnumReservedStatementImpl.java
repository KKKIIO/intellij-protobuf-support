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

public class PbEnumReservedStatementImpl extends PbStatementBase implements PbEnumReservedStatement {

  public PbEnumReservedStatementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PbVisitor visitor) {
    visitor.visitEnumReservedStatement(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PbVisitor) accept((PbVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<PbEnumReservedRange> getEnumReservedRangeList() {
    return PbPsiTreeUtil.getChildrenOfTypeAsList(this, PbEnumReservedRange.class);
  }

  @Override
  @NotNull
  public List<PbStringValue> getStringValueList() {
    return PbPsiTreeUtil.getChildrenOfTypeAsList(this, PbStringValue.class);
  }

}
