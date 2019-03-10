package org.assertj.vavr.api;

/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * <p>
 * Copyright 2012-2019 the original author or authors.
 */

import org.junit.jupiter.api.Test;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class MapAssert_containsOnly_Test {
  @Test
  void should_pass_if_Map_contains_only_given_entry() {
    final Map<String, String> actual = HashMap.of("key", "value");

    assertThat(actual).containsOnly(List.of(Map.entry("key", "value")));
  }

  @Test
  void should_shrug() {
    final Map<String, String> actual = HashMap.of("key", "value");

    assertThatThrownBy(
        () -> assertThat(actual).containsOnly(null)
    )
        .isInstanceOf(AssertionError.class)
        .hasMessage("Expected entries should not be null");
  }

  @Test
  void should_fail_if_Map_contains_more_than_given_entry() {
    final Map<String, String> actual = HashMap.of("key-1", "value-1", "key-2", "value-2");

    assertThatThrownBy(
        () -> assertThat(actual).containsOnly(HashMap.of("key-1", "value-1"))
    )
        .isInstanceOf(AssertionError.class)
        .hasMessage(
            "\n" +
                "Expecting:\n" +
                "  <HashMap((key-1, value-1), (key-2, value-2))>\n" +
                "to contain only:\n" +
                "  <HashMap((key-1, value-1))>\n" +
                "but the following elements were unexpected:\n" +
                "  <HashMap((key-2, value-2))>\n"
        );
  }

  @Test
  void should_fail_if_Map_has_same_size_but_contains_different_entries() {
    final Map<String, String> actual = HashMap.of("key-1", "value-1", "key-2", "value-2");

    assertThatThrownBy(
        () -> assertThat(actual).containsOnly(HashMap.of("key-1", "value-1", "key-3", "value-3"))
    )
        .isInstanceOf(AssertionError.class)
        .hasMessage(
            "\n" +
                "Expecting:\n" +
                "  <HashMap((key-1, value-1), (key-2, value-2))>\n" +
                "to contain only:\n" +
                "  <HashMap((key-1, value-1), (key-3, value-3))>\n" +
                "elements not found:\n" +
                "  <HashMap((key-3, value-3))>\n" +
                "and elements not expected:\n" +
                "  <HashMap((key-2, value-2))>\n"
        );
  }
}