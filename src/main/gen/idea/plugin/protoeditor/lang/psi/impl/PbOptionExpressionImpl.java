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

public class PbOptionExpressionImpl extends PbElementBase implements PbOptionExpression {

  public PbOptionExpressionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PbVisitor visitor) {
    visitor.visitOptionExpression(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PbVisitor) accept((PbVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PbAggregateValue getAggregateValue() {
    return PbPsiTreeUtil.getChildOfType(this, PbAggregateValue.class);
  }

  @Override
  @Nullable
  public PbIdentifierValue getIdentifierValue() {
    return PbPsiTreeUtil.getChildOfType(this, PbIdentifierValue.class);
  }

  @Override
  @Nullable
  public PbNumberValue getNumberValue() {
    return PbPsiTreeUtil.getChildOfType(this, PbNumberValue.class);
  }

  @Override
  @NotNull
  public PbOptionName getOptionName() {
    return notNullChild(PbPsiTreeUtil.getChildOfType(this, PbOptionName.class));
  }

  @Override
  @Nullable
  public PbStringValue getStringValue() {
    return PbPsiTreeUtil.getChildOfType(this, PbStringValue.class);
  }

}
