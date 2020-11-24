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
package idea.plugin.protoeditor.lang.resolve.directive;

import idea.plugin.protoeditor.ide.editing.MissingSchemaNotificationProvider;
import idea.plugin.protoeditor.lang.psi.PbMessageType;
import idea.plugin.protoeditor.lang.psi.PbTextFile;
import idea.plugin.protoeditor.lang.resolve.SchemaInfo;
import idea.plugin.protoeditor.lang.resolve.SchemaProvider;
import org.jetbrains.annotations.Nullable;

/** Provides the root {@link PbMessageType} defined in directive comments, if any. */
public class SchemaDirectiveSchemaProvider implements SchemaProvider {
  @Nullable
  @Override
  public SchemaInfo getSchemaInfo(PbTextFile file) {
    SchemaDirective comment = SchemaDirective.find(file);
    if (comment != null) {
      // Remove the missing schema notification if it's displayed.
      MissingSchemaNotificationProvider.update(file);
      PbMessageType message = comment.getMessage();
      if (message != null) {
        return SchemaInfo.create(message, comment.getExtensionResolver());
      }
    }
    return null;
  }
}
