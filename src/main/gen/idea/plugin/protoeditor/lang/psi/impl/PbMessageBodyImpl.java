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

public class PbMessageBodyImpl extends PbMessageBodyMixin implements PbMessageBody {

  public PbMessageBodyImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PbVisitor visitor) {
    visitor.visitMessageBody(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PbVisitor) accept((PbVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<PbEnumDefinition> getEnumDefinitionList() {
    return PbPsiTreeUtil.getChildrenOfTypeAsList(this, PbEnumDefinition.class);
  }

  @Override
  @NotNull
  public List<PbExtendDefinition> getExtendDefinitionList() {
    return PbPsiTreeUtil.getChildrenOfTypeAsList(this, PbExtendDefinition.class);
  }

  @Override
  @NotNull
  public List<PbExtensionsStatement> getExtensionsStatementList() {
    return PbPsiTreeUtil.getChildrenOfTypeAsList(this, PbExtensionsStatement.class);
  }

  @Override
  @NotNull
  public List<PbGroupDefinition> getGroupDefinitionList() {
    return PbPsiTreeUtil.getChildrenOfTypeAsList(this, PbGroupDefinition.class);
  }

  @Override
  @NotNull
  public List<PbMapField> getMapFieldList() {
    return PbPsiTreeUtil.getChildrenOfTypeAsList(this, PbMapField.class);
  }

  @Override
  @NotNull
  public List<PbMessageDefinition> getMessageDefinitionList() {
    return PbPsiTreeUtil.getChildrenOfTypeAsList(this, PbMessageDefinition.class);
  }

  @Override
  @NotNull
  public List<PbOneofDefinition> getOneofDefinitionList() {
    return PbPsiTreeUtil.getChildrenOfTypeAsList(this, PbOneofDefinition.class);
  }

  @Override
  @NotNull
  public List<PbReservedStatement> getReservedStatementList() {
    return PbPsiTreeUtil.getChildrenOfTypeAsList(this, PbReservedStatement.class);
  }

  @Override
  @NotNull
  public List<PbSimpleField> getSimpleFieldList() {
    return PbPsiTreeUtil.getChildrenOfTypeAsList(this, PbSimpleField.class);
  }

  @Override
  @NotNull
  public List<PbOptionStatement> getOptionStatements() {
    return PbPsiTreeUtil.getChildrenOfTypeAsList(this, PbOptionStatement.class);
  }

  @Override
  @NotNull
  public PsiElement getStart() {
    return notNullChild(findChildByType(LBRACE));
  }

  @Override
  @Nullable
  public PsiElement getEnd() {
    return findChildByType(RBRACE);
  }

}
