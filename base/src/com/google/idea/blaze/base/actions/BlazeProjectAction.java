/*
 * Copyright 2016 The Bazel Authors. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.idea.blaze.base.actions;

import com.google.idea.blaze.base.settings.Blaze;
import com.google.idea.blaze.base.settings.BuildSystemName;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import javax.annotation.Nullable;
import javax.swing.Icon;

/** Base class action that hides for non-blaze projects. */
public abstract class BlazeProjectAction extends AnAction {
  protected BlazeProjectAction() {}

  protected BlazeProjectAction(Icon icon) {
    super(icon);
  }

  protected BlazeProjectAction(@Nullable String text) {
    super(text);
  }

  protected BlazeProjectAction(
      @Nullable String text, @Nullable String description, @Nullable Icon icon) {
    super(text, description, icon);
  }

  @Override
  public final void update(AnActionEvent e) {
    Project project = e.getProject();
    if (project == null || !Blaze.isBlazeProject(project)) {
      e.getPresentation().setEnabledAndVisible(false);
      return;
    }

    e.getPresentation().setEnabledAndVisible(true);

    if (!compatibleBuildSystem(project)) {
      e.getPresentation().setEnabled(false);
      return;
    }

    updateForBlazeProject(project, e);
  }

  @Override
  public final void actionPerformed(AnActionEvent anActionEvent) {
    Project project = anActionEvent.getProject();
    if (project == null) {
      return;
    }
    actionPerformedInBlazeProject(project, anActionEvent);
  }

  protected void updateForBlazeProject(Project project, AnActionEvent e) {}

  protected abstract void actionPerformedInBlazeProject(Project project, AnActionEvent e);

  private boolean compatibleBuildSystem(Project project) {
    BuildSystemName requiredBuildSystemName = requiredBuildSystem();
    if (requiredBuildSystemName == null) {
      return true;
    }
    return Blaze.getBuildSystemName(project) == requiredBuildSystemName;
  }

  @Nullable
  protected BuildSystemName requiredBuildSystem() {
    return null;
  }
}
