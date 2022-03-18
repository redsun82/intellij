/*
 * Copyright 2017 The Bazel Authors. All rights reserved.
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
package com.google.idea.blaze.java.run.producers;

import static com.google.common.truth.Truth.assertWithMessage;

import com.google.common.collect.ImmutableSet;
import com.google.idea.blaze.base.run.producers.BlazeRunConfigurationProducer;
import com.google.idea.blaze.base.run.producers.BlazeRunConfigurationProducerTestCase;
import com.intellij.execution.actions.RunConfigurationProducer;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Integration tests for {@link NonBlazeProducerSuppressor}. This test covers Java and Kotlin
 * producers.
 */
@RunWith(JUnit4.class)
public class NonBlazeJavaProducerSuppressorTest extends BlazeRunConfigurationProducerTestCase {

  private static final ImmutableSet<String> ACCEPTED_NON_BAZEL_PRODUCERS =
      ImmutableSet.of(
          "com.intellij.execution.jar.JarApplicationConfigurationProducer",
          "com.intellij.execution.scratch.JavaScratchConfigurationProducer",
          "org.jetbrains.kotlin.idea.run.script.standalone.KotlinStandaloneScriptRunConfigurationProducer");

  @Before
  public final void suppressNativeProducers() {
    NonBlazeProducerSuppressor.suppressProducers(getProject());
  }

  @Test
  public void testNonBlazeProducersSuppressed() {
    List<RunConfigurationProducer<?>> producers =
        RunConfigurationProducer.getProducers(getProject());
    for (RunConfigurationProducer<?> producer : producers) {
      if (!(producer instanceof BlazeRunConfigurationProducer)) {
        assertWithMessage(
                "The Producer %s was not suppressed correctly. If you do not wish to suppress it,"
                    + " please add it to ACCEPTED_NON_BAZEL_PRODUCERS list",
                producer.getClass().getName())
            .that(ACCEPTED_NON_BAZEL_PRODUCERS.contains(producer.getClass().getName()))
            .isTrue();
      }
    }
  }
}
