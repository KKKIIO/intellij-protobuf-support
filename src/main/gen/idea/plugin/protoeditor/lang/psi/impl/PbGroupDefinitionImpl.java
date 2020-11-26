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
import idea.plugin.protoeditor.lang.stub.PbGroupDefinitionStub;
import com.intellij.psi.stubs.IStubElementType;
import static idea.plugin.protoeditor.lang.psi.ProtoTokenTypes.*;

public class PbGroupDefinitionImpl extends PbGroupDefinitionMixin implements PbGroupDefinition {

  public PbGroupDefinitionImpl(ASTNode node) {
    super(node);
  }

  public PbGroupDefinitionImpl(PbGroupDefinitionStub stub, IStubElementType type) {
    super(stub, type);
  }

  public void accept(@NotNull PbVisitor visitor) {
    visitor.visitGroupDefinition(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PbVisitor) accept((PbVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PbGroupOptionContainer getGroupOptionContainer() {
    return PbPsiTreeUtil.getChildOfType(this, PbGroupOptionContainer.class);
  }

  @Override
  @Nullable
  public PsiElement getNameIdentifier() {
    return findChildByType(IDENTIFIER_LITERAL);
  }

  @Override
  @Nullable
  public PbMessageBody getBody() {
    return PbPsiTreeUtil.getChildOfType(this, PbMessageBody.class);
  }

  @Override
  @Nullable
  public PbNumberValue getFieldNumber() {
    return PbPsiTreeUtil.getChildOfType(this, PbNumberValue.class);
  }

  @Override
  @Nullable
  public PbFieldLabel getDeclaredLabel() {
    return PbPsiTreeUtil.getChildOfType(this, PbFieldLabel.class);
  }

}
