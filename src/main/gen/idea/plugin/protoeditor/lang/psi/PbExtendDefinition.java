// This is a generated file. Not intended for manual editing.
package idea.plugin.protoeditor.lang.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import idea.plugin.protoeditor.lang.stub.PbExtendDefinitionStub;

public interface PbExtendDefinition extends PbDefinition, StubBasedPsiElement<PbExtendDefinitionStub> {

  @Nullable
  PbExtendBody getBody();

  @Nullable
  PbMessageTypeName getTypeName();

}
