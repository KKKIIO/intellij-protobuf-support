// This is a generated file. Not intended for manual editing.
package idea.plugin.protoeditor.lang.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import idea.plugin.protoeditor.lang.stub.PbOneofDefinitionStub;

public interface PbOneofDefinition extends PbDefinition, PbNamedElement, StubBasedPsiElement<PbOneofDefinitionStub> {

  @Nullable
  PsiElement getNameIdentifier();

  @Nullable
  PbOneofBody getBody();

}
