/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package idea.plugin.protoeditor.lang.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.QualifiedName;
import idea.plugin.protoeditor.lang.descriptor.Descriptor;
import idea.plugin.protoeditor.lang.descriptor.DescriptorOptionType;
import idea.plugin.protoeditor.lang.psi.PbEnumBody;
import idea.plugin.protoeditor.lang.psi.PbEnumDefinition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

abstract class PbEnumBodyMixin extends PbElementBase implements PbEnumBody {

  PbEnumBodyMixin(ASTNode node) {
    super(node);
  }

  @NotNull
  @Override
  public QualifiedName getDescriptorOptionsTypeName(Descriptor descriptor) {
    return DescriptorOptionType.ENUM_OPTIONS.forDescriptor(descriptor);
  }

  @Override
  @Nullable
  public QualifiedName getExtensionOptionScope() {
    PsiElement parent = getParent();
    if (!(parent instanceof PbEnumDefinition)) {
      return null;
    }
    PbEnumDefinition enumDefinition = (PbEnumDefinition) parent;
    QualifiedName name = enumDefinition.getQualifiedName();
    return name != null ? name.removeLastComponent() : null;
  }
}
