// This is a generated file. Not intended for manual editing.
package idea.plugin.protoeditor.lang.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface PbGroupOptionContainer extends PbElement {

  @NotNull
  PbOptionList getOptionList();

  @Nullable
  List<PbOptionExpression> getOptions();

}
