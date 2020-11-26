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
import idea.plugin.protoeditor.lang.stub.PbExtendDefinitionStub;
import com.intellij.psi.stubs.IStubElementType;

public class PbExtendDefinitionImpl extends PbExtendDefinitionMixin implements PbExtendDefinition {

  public PbExtendDefinitionImpl(ASTNode node) {
    super(node);
  }

  public PbExtendDefinitionImpl(PbExtendDefinitionStub stub, IStubElementType type) {
    super(stub, type);
  }

  public void accept(@NotNull PbVisitor visitor) {
    visitor.visitExtendDefinition(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PbVisitor) accept((PbVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PbExtendBody getBody() {
    return PbPsiTreeUtil.getChildOfType(this, PbExtendBody.class);
  }

  @Override
  @Nullable
  public PbMessageTypeName getTypeName() {
    return PbPsiTreeUtil.getChildOfType(this, PbMessageTypeName.class);
  }

}
