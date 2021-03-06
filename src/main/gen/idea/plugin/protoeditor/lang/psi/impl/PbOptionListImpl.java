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

public class PbOptionListImpl extends PbElementBase implements PbOptionList {

  public PbOptionListImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PbVisitor visitor) {
    visitor.visitOptionList(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PbVisitor) accept((PbVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<PbOptionExpression> getOptions() {
    return PbPsiTreeUtil.getChildrenOfTypeAsList(this, PbOptionExpression.class);
  }

  @Override
  @NotNull
  public PsiElement getStart() {
    return notNullChild(findChildByType(LBRACK));
  }

  @Override
  @Nullable
  public PsiElement getEnd() {
    return findChildByType(RBRACK);
  }

}
