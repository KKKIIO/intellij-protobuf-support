// This is a generated file. Not intended for manual editing.
package idea.plugin.protoeditor.lang.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface PbMethodOptions extends PbBlockBody {

  @NotNull
  List<PbOptionStatement> getOptionStatements();

  @NotNull
  PsiElement getStart();

  @Nullable
  PsiElement getEnd();

}
