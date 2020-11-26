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
import static idea.plugin.protoeditor.lang.psi.ProtoTokenTypes.*;

public class PbReservedRangeImpl extends PbReservedRangeMixin implements PbReservedRange {

  public PbReservedRangeImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PbVisitor visitor) {
    visitor.visitReservedRange(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PbVisitor) accept((PbVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<PbNumberValue> getNumberValueList() {
    return PbPsiTreeUtil.getChildrenOfTypeAsList(this, PbNumberValue.class);
  }

  @Override
  @NotNull
  public PbNumberValue getFromValue() {
    List<PbNumberValue> p1 = getNumberValueList();
    return p1.get(0);
  }

  @Override
  @Nullable
  public PbNumberValue getToValue() {
    List<PbNumberValue> p1 = getNumberValueList();
    return p1.size() < 2 ? null : p1.get(1);
  }

}
