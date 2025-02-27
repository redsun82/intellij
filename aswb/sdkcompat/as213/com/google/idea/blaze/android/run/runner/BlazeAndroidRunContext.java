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
package com.google.idea.blaze.android.run.runner;

import com.android.ddmlib.IDevice;
import com.android.tools.idea.run.ApplicationIdProvider;
import com.android.tools.idea.run.ConsolePrinter;
import com.android.tools.idea.run.ConsoleProvider;
import com.android.tools.idea.run.LaunchOptions;
import com.android.tools.idea.run.editor.AndroidDebugger;
import com.android.tools.idea.run.editor.AndroidDebuggerState;
import com.android.tools.idea.run.editor.ProfilerState;
import com.android.tools.idea.run.tasks.ConnectDebuggerTask;
import com.android.tools.idea.run.tasks.LaunchTask;
import com.android.tools.idea.run.tasks.LaunchTasksProvider;
import com.android.tools.idea.run.util.LaunchStatus;
import com.google.common.collect.ImmutableList;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import javax.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

/** Instantiated when the configuration wants to run. */
public interface BlazeAndroidRunContext {

  BlazeAndroidDeviceSelector getDeviceSelector();

  void augmentLaunchOptions(LaunchOptions.Builder options);

  ConsoleProvider getConsoleProvider();

  ApkBuildStep getBuildStep();

  ApplicationIdProvider getApplicationIdProvider() throws ExecutionException;

  LaunchTasksProvider getLaunchTasksProvider(LaunchOptions.Builder launchOptionsBuilder)
      throws ExecutionException;

  /** Returns the tasks to deploy the application. */
  ImmutableList<LaunchTask> getDeployTasks(IDevice device, LaunchOptions launchOptions)
      throws ExecutionException;

  /** Returns the task to launch the application. */
  @Nullable
  LaunchTask getApplicationLaunchTask(
      LaunchOptions launchOptions,
      @Nullable Integer userId,
      @NotNull String contributorsAmStartOptions,
      LaunchStatus launchStatus)
      throws ExecutionException;

  /** Returns the task to connect the debugger. */
  @Nullable
  ConnectDebuggerTask getDebuggerTask(
      AndroidDebugger androidDebugger, AndroidDebuggerState androidDebuggerState)
      throws ExecutionException;

  @Nullable
  Integer getUserId(IDevice device, ConsolePrinter consolePrinter) throws ExecutionException;

  String getAmStartOptions();

  Executor getExecutor();

  ProfilerState getProfileState();
}
