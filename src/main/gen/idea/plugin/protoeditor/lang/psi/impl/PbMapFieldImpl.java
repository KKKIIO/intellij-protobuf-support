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

public class PbMapFieldImpl extends PbMapFieldMixin implements PbMapField {

  public PbMapFieldImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PbVisitor visitor) {
    visitor.visitMapField(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PbVisitor) accept((PbVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PbOptionList getOptionList() {
    return PbPsiTreeUtil.getChildOfType(this, PbOptionList.class);
  }

  @Override
  @Nullable
  public PsiElement getNameIdentifier() {
    return findChildByType(IDENTIFIER_LITERAL);
  }

  @Override
  @Nullable
  public PbNumberValue getFieldNumber() {
    return PbPsiTreeUtil.getChildOfType(this, PbNumberValue.class);
  }

  @Override
  @Nullable
  public PbTypeName getKeyType() {
    List<PbTypeName> p1 = PbPsiTreeUtil.getChildrenOfTypeAsList(this, PbTypeName.class);
    return p1.size() < 1 ? null : p1.get(0);
  }

  @Override
  @Nullable
  public PbTypeName getValueType() {
    List<PbTypeName> p1 = PbPsiTreeUtil.getChildrenOfTypeAsList(this, PbTypeName.class);
    return p1.size() < 2 ? null : p1.get(1);
  }

  @Override
  @Nullable
  public PbFieldLabel getDeclaredLabel() {
    return PbPsiTreeUtil.getChildOfType(this, PbFieldLabel.class);
  }

}
