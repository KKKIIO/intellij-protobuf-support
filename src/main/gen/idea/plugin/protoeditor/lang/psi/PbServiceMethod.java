// This is a generated file. Not intended for manual editing.
package idea.plugin.protoeditor.lang.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface PbServiceMethod extends PbNamedElement, PbOptionStatementOwner {

  @Nullable
  PbMethodOptions getMethodOptions();

  @NotNull
  List<PbServiceMethodType> getServiceMethodTypeList();

  @Nullable
  PsiElement getNameIdentifier();

}
