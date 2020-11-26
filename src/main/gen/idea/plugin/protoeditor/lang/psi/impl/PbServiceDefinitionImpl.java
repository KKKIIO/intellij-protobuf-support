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
import idea.plugin.protoeditor.lang.stub.PbServiceDefinitionStub;
import com.intellij.psi.stubs.IStubElementType;
import static idea.plugin.protoeditor.lang.psi.ProtoTokenTypes.*;

public class PbServiceDefinitionImpl extends PbServiceDefinitionMixin implements PbServiceDefinition {

  public PbServiceDefinitionImpl(ASTNode node) {
    super(node);
  }

  public PbServiceDefinitionImpl(PbServiceDefinitionStub stub, IStubElementType type) {
    super(stub, type);
  }

  public void accept(@NotNull PbVisitor visitor) {
    visitor.visitServiceDefinition(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PbVisitor) accept((PbVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PsiElement getNameIdentifier() {
    return findChildByType(IDENTIFIER_LITERAL);
  }

  @Override
  @Nullable
  public PbServiceBody getBody() {
    return PbPsiTreeUtil.getChildOfType(this, PbServiceBody.class);
  }

}
